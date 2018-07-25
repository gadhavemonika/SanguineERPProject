package com.sanguine.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ibm.icu.text.DecimalFormat;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsUserMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsExportTallyFileController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsUserMasterService objUserMasterService;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@RequestMapping(value = "/frmExportTallyFile", method = RequestMethod.GET)
	public ModelAndView funOpenFormExportTallyFile(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// Map<String, String> properties=
		// objUserMasterService.funProperties(clientCode);
		// model.put("properties", properties);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExportTallyFile_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmExportTallyFile", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/exportTallyFile", method = RequestMethod.GET)
	public ModelAndView funCreateXml(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (objBean.getStrDocType().equals("Purchase")) {
			// funPurchaseXMLFile(objBean,clientCode );

			funPurchaseXMLFileFormate1(objBean, clientCode, req);

			funSupplierLedgerXMLFile(objBean, clientCode);
		} else {
			funSaleXMLFile(objBean, clientCode);

			funCustomerLedgerXMLFile(objBean, clientCode);

		}
		// Map<String, String> properties=
		// objUserMasterService.funProperties(clientCode);
		// model.put("properties", properties);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExportTallyFile_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmExportTallyFile", "command", new clsReportBean());
		}

	}

	@ModelAttribute("properties")
	public Map<String, String> getAllProperties(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> properties = objUserMasterService.funProperties(clientCode);
		if (properties.isEmpty()) {
			properties.put("", "");
		}
		return properties;
	}

	private void funPurchaseXMLFile(clsReportBean objBean, String clientCode) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element envelopeElement = doc.createElement("ENVELOPE");
			doc.appendChild(envelopeElement);

			envelopeElement.appendChild(getHeader(doc, "1", "Import", "Data", "Vouchers")); // for
																							// Header

			Element bodyElement = doc.createElement("BODY");

			envelopeElement.appendChild(bodyElement);

			bodyElement.appendChild(getDes(doc, "")); // For Desc body

			Element data = doc.createElement("DATA");
			// append root element to document
			bodyElement.appendChild(data);

			Element tallyMessage = doc.createElement("TALLYMESSAGE");

			data.appendChild(tallyMessage);

			// String
			// sql=" select a.strGRNCode,a.strSuppCode,c.strPName,a.strAgainst,a.strBillNo,Date(a.dtGRNDate) , a.dblTotal,d.strTallyCode  "
			// +
			// " from tblgrnhd a ,tblgrntaxdtl b ,tblpartymaster c ,tbltallylinkup d"
			// +
			// " where a.strGRNCode=b.strGRNCode and a.strSuppCode=c.strPCode and d.strGroupCode=a.strSuppCode "
			// +
			// " and date(a.dtGRNDate) between  '"+objBean.getDteFromDate()+"' and '"+objBean.getDteToDate()+"' "
			// + " and a.strClientCode='"+clientCode+"' "
			// + " and b.strClientCode='"+clientCode+"' "
			// + " and c.strClientCode='"+clientCode+"' "
			// + " group by a.strGRNCode ";

			String sql = " select a.strGRNCode,a.strSuppCode,c.strPName,a.strAgainst,a.strBillNo,Date(a.dtGRNDate) , a.dblTotal,ifnull(d.strTallyCode,'')   " + " from tblgrnhd a " + " left outer join tblgrntaxdtl b on a.strGRNCode=b.strGRNCode  and b.strClientCode='" + clientCode + "' " + " left outer join tblpartymaster c on a.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' "
					+ " left outer join tbltallylinkup d on d.strGroupCode=a.strSuppCode " + " where  date(a.dtGRNDate) between  '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "'  " + " and a.strClientCode='" + clientCode + "' " + " group by a.strGRNCode  ";

			System.out.println("mainData=" + sql);
			List list = objGlobalService.funGetList(sql);
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				String grnNo = arrObj[0].toString();
				String strSuppCode = arrObj[1].toString();
				String strPName = arrObj[2].toString();
				String PNameAndCode = strPName + "  (" + strSuppCode + ")";
				String strAgainst = arrObj[3].toString();
				String strBillNo = arrObj[4].toString();
				String dtGRNDate = arrObj[5].toString();
				String grnDate = dtGRNDate.split("-")[0] + dtGRNDate.split("-")[1] + dtGRNDate.split("-")[2];

				String dblTotal = arrObj[6].toString();
				String pNameAlise = arrObj[7].toString();
				int vNo = cnt + 1;

				tallyMessage.appendChild(getData(doc, String.valueOf(vNo), grnDate, strBillNo, "No", String.valueOf(vNo), grnNo, pNameAlise, "No", dblTotal, objBean, clientCode));

			}

			// append first child element to root element
			// tallyMessage.appendChild(getData(doc, "1", "20160710",
			// "Bill No 1012ee", "NO", "01" ,"01GRAG000043",
			// "VIRCHAND KHIMJI  AND CO. (S000049)","No","2055.00" ));

			// //append second child
			// tallyMessage.appendChild(getData(doc, "2", "20160710",
			// "Bill No 101215", "Yes", "02" ,"01GRAG000049",
			// "VIN VIN ENTERPRISES. (S000053)","No","1075.2000" ));

			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			System.out.println(System.getProperty("user.dir") + "\\PUROLD.xml");

			StreamResult file = new StreamResult(new File("PUR.xml").getAbsolutePath());

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			System.out.println("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void funSupplierLedgerXMLFile(clsReportBean objBean, String clientCode) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element envelopeElement = doc.createElement("ENVELOPE");
			doc.appendChild(envelopeElement);

			envelopeElement.appendChild(getHeader(doc, "1", "Import", "Data", "All Masters")); // for
																								// Header

			Element bodyElement = doc.createElement("BODY");

			envelopeElement.appendChild(bodyElement);

			bodyElement.appendChild(getSuppLedgerDesc(doc, "")); // For Desc
																	// body

			Element data = doc.createElement("DATA");
			// append root element to document
			bodyElement.appendChild(data);

			Element tallyMessage = doc.createElement("TALLYMESSAGE");

			data.appendChild(tallyMessage);

			String sql = "  select b.strSuppCode,a.strPName,c.strTallyCode from tblpartymaster a ,tblgrnhd b,tbltallylinkup c   " + " where  a.strPCode=b.strSuppCode and  a.strPType='supp' and c.strGroupCode=b.strSuppCode " + " and date(b.dtGRNDate)  between  '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "' " + " and a.strClientCode='" + clientCode
					+ "'  and b.strClientCode='" + clientCode + "' " + " ORDER by a.strPName  ";

			System.out.println("suppData=" + sql);
			List list = objGlobalService.funGetList(sql);
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				String suppCode = arrObj[0].toString();
				String strPName = arrObj[1].toString();
				String PNameAndCode = strPName + "  (" + suppCode + ")";
				String pNameAlise = arrObj[2].toString();
				tallyMessage.appendChild(getSuppLedgerData(doc, pNameAlise, "Create", objBean, clientCode, "supp"));
			}

			String sqlPer = " select b.strTaxDesc from tblgrnhd a , tblgrntaxdtl b ,tbltaxhd c ,tblpartymaster d " + " where a.strGRNCode=b.strGRNCode and b.strTaxCode=c.strTaxCode and c.strTaxOnSP='Purchase' and a.strSuppCode=d.strPCode  " + " and date(a.dtGRNDate) between '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "' " + " and a.strClientCode='" + clientCode
					+ "' and a.strClientCode='" + clientCode + "' " + " order by d.strPName ";

			System.out.println("taxData=" + sqlPer);
			List listPer = objGlobalService.funGetList(sqlPer);
			for (int cnt = 0; cnt < listPer.size(); cnt++) {
				// String arrObj=(Object[])listPer.get(cnt);
				String taxDesc = (String) listPer.get(cnt);
				tallyMessage.appendChild(getSuppLedgerData(doc, taxDesc, "Create", objBean, clientCode, "Tax"));
			}
			if (listPer.size() == 0) {
				tallyMessage.appendChild(getSuppLedgerData(doc, "", "Create", objBean, clientCode, "Tax"));
			}

			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			System.out.println(System.getProperty("user.dir") + "\\SuppLdg.xml");

			StreamResult file = new StreamResult(new File("SuppLdg.xml").getAbsolutePath());

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			System.out.println("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Node getVoucherElements(Document doc, Element element, String name, String value) {
		System.out.println(name + "-" + value);
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	private Node getData(Document doc, String id, String date, String billNo, String typeSP, String vCount, String strGRNNo, String strSuppName, String isDeempositive, String dblGrandTotal, clsReportBean objBean, String clientCode) {
		Element voucher = doc.createElement("VOUCHER");

		// //set id attribute
		// employee.setAttribute("id", id);

		// create name element
		voucher.appendChild(getVoucherElements(doc, voucher, "DATE", date));

		// create age element
		voucher.appendChild(getVoucherElements(doc, voucher, "NARRATION", billNo));

		// create role element
		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERTYPENAME", typeSP));

		// create gender element
		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERNUMBER", vCount));

		voucher.appendChild(getVoucherElements(doc, voucher, "REFERENCE", strGRNNo));

		Element allLedgerEntriesList = doc.createElement("ALLLEDGERENTRIES.LIST");

		allLedgerEntriesList.appendChild(getLederEnteriesElements(doc, allLedgerEntriesList, "LEDGERNAME", strSuppName));

		allLedgerEntriesList.appendChild(getLederEnteriesElements(doc, allLedgerEntriesList, "ISDEEMEDPOSITIVE", isDeempositive));

		allLedgerEntriesList.appendChild(getLederEnteriesElements(doc, allLedgerEntriesList, "AMOUNT", dblGrandTotal));

		// for Bill
		Element billAlloctionList = doc.createElement("BILLALLOCATIONS.LIST");
		billAlloctionList.appendChild(getLederEnteriesElements(doc, billAlloctionList, "NAME", "49193"));
		billAlloctionList.appendChild(getLederEnteriesElements(doc, billAlloctionList, "BILLTYPE", "Direct"));
		billAlloctionList.appendChild(getLederEnteriesElements(doc, billAlloctionList, "AMOUNT", dblGrandTotal));
		allLedgerEntriesList.appendChild(billAlloctionList);

		voucher.appendChild(allLedgerEntriesList);

		// for percentage

		String sql = "  select b.strTaxDesc,b.strTaxableAmt " + "  from tblgrnhd a ,tblgrntaxdtl b " + "  where a.strGRNCode=b.strGRNCode and " + " a.strGRNCode='" + strGRNNo + "' " + " and date(a.dtGRNDate)  between  '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "' " + "  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'  ";
		System.out.println("PerData=" + sql);
		List list = objGlobalService.funGetList(sql);
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			String strTaxDesc = arrObj[0].toString();
			String strTaxableAmt = arrObj[1].toString();

			Element allLedgerEntriesListPercent = doc.createElement("ALLLEDGERENTRIES.LIST");

			allLedgerEntriesListPercent.appendChild(getLederEnteriesElements(doc, allLedgerEntriesListPercent, "LEDGERNAME", strTaxDesc));

			allLedgerEntriesListPercent.appendChild(getLederEnteriesElements(doc, allLedgerEntriesListPercent, "ISDEEMEDPOSITIVE", isDeempositive));

			allLedgerEntriesListPercent.appendChild(getLederEnteriesElements(doc, allLedgerEntriesListPercent, "AMOUNT", strTaxableAmt));

			voucher.appendChild(allLedgerEntriesListPercent);

		}

		return voucher;
	}

	private Node getLederEnteriesElements(Document doc, Element element, String name, String ledgerName) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(ledgerName));
		return node;
	}

	private Node getBillAllocationList(Document doc, Element element, String name, String billData) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(billData));
		return node;
	}

	private Node getDes(Document doc, String desc) {

		Element descElement = doc.createElement("DESC");
		// descElement.appendChild(getVoucherElements(doc, descElement, "",
		// desc));
		return descElement;
	}

	private Node getHeader(Document doc, String version, String tallyreq, String type, String id) {
		Element headerElement = doc.createElement("HEADER");
		headerElement.appendChild(getVoucherElements(doc, headerElement, "VERSION", version));
		headerElement.appendChild(getVoucherElements(doc, headerElement, "TALLYREQUEST", tallyreq));
		headerElement.appendChild(getVoucherElements(doc, headerElement, "TYPE", type));
		headerElement.appendChild(getVoucherElements(doc, headerElement, "ID", id));

		return headerElement;
	}

	private Node getSuppLedgerDesc(Document doc, String desc) {

		Element descElement = doc.createElement("DESC");

		Element staticvarialesElement = doc.createElement("STATICVARIABLES");

		staticvarialesElement.appendChild(getVoucherElements(doc, staticvarialesElement, "IMPORTDUPS", "@@DUPIGNORECOMBINE"));

		descElement.appendChild(staticvarialesElement);

		return descElement;
	}

	private Node getSuppLedgerData(Document doc, String suppNameORcustNameORTaxDesc, String ledgerAction, clsReportBean objBean, String clientCode, String type) {
		Element ledgerElement = doc.createElement("LEDGER");
		ledgerElement.setAttribute("NAME", suppNameORcustNameORTaxDesc);
		ledgerElement.setAttribute("Action", ledgerAction);
		if (type.equals("supp")) {
			ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "NAME", suppNameORcustNameORTaxDesc));

			ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "PARENT", "Sundry Creditors"));

			ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "OPENINGBALANCE", "0"));

			ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "ISBILLWISEON", "Yes"));
		} else {
			if (type.equals("cust")) {
				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "NAME", suppNameORcustNameORTaxDesc));

				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "PARENT", "Sundry Creditors"));

				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "OPENINGBALANCE", "0"));

				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "ISBILLWISEON", "Yes"));
			} else {
				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "NAME", suppNameORcustNameORTaxDesc));

				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "PARENT", "Purchase Accounts"));

				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "OPENINGBALANCE", "0"));

				ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "ISBILLWISEON", "No"));
			}

		}

		return ledgerElement;
	}

	// ///// Sales Xml Functions Start//////////

	private void funSaleXMLFile(clsReportBean objBean, String clientCode) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element envelopeElement = doc.createElement("ENVELOPE");
			doc.appendChild(envelopeElement);

			envelopeElement.appendChild(getHeader(doc, "1", "Import", "Data", "Vouchers")); // for
																							// Header

			Element bodyElement = doc.createElement("BODY");

			envelopeElement.appendChild(bodyElement);

			bodyElement.appendChild(getDes(doc, "")); // For Desc body

			Element data = doc.createElement("DATA");
			// append root element to document
			bodyElement.appendChild(data);

			Element tallyMessage = doc.createElement("TALLYMESSAGE");

			data.appendChild(tallyMessage);

			String sql = " select a.strInvCode,a.strCustCode,b.strPName,date(a.dteInvDate),a.dblGrandTotal " + " from tblinvoicehd a , tblpartymaster b " + " where a.strCustCode=b.strPCode and " + " date(a.dteInvDate) between '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "' " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' ";
			System.out.println("mainSaleData=" + sql);
			List list = objGlobalService.funGetList(sql);
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				String invCode = arrObj[0].toString();
				String strCustCode = arrObj[1].toString();
				String strPName = arrObj[2].toString();
				String PNameAndCode = strPName + "  (" + strCustCode + ")";
				String dtInvDate = arrObj[3].toString();
				String invDate = dtInvDate.split("-")[0] + dtInvDate.split("-")[1] + dtInvDate.split("-")[2];

				String dblGTotal = arrObj[4].toString();
				int vNo = cnt + 1;

				tallyMessage.appendChild(getSaleData(doc, String.valueOf(vNo), invDate, invCode, "No", String.valueOf(vNo), invCode, PNameAndCode, "No", dblGTotal, objBean, clientCode));

			}

			// append first child element to root element
			// tallyMessage.appendChild(getData(doc, "1", "20160710",
			// "Bill No 1012ee", "NO", "01" ,"01GRAG000043",
			// "VIRCHAND KHIMJI  AND CO. (S000049)","No","2055.00" ));

			// //append second child
			// tallyMessage.appendChild(getData(doc, "2", "20160710",
			// "Bill No 101215", "Yes", "02" ,"01GRAG000049",
			// "VIN VIN ENTERPRISES. (S000053)","No","1075.2000" ));

			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			System.out.println(System.getProperty("user.dir") + "\\Sale.xml");

			StreamResult file = new StreamResult(new File("Sale.xml").getAbsolutePath());

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			System.out.println("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Node getSaleData(Document doc, String id, String date, String invCode, String typeSP, String vCount, String invNo, String strCustName, String isDeempositive, String dblGrandTotal, clsReportBean objBean, String clientCode) {
		Element voucher = doc.createElement("VOUCHER");

		// //set id attribute
		// employee.setAttribute("id", id);

		// create DATE element
		voucher.appendChild(getVoucherElements(doc, voucher, "DATE", date));

		// create NARRATION element
		voucher.appendChild(getVoucherElements(doc, voucher, "NARRATION", invNo));

		// create VOUCHERTYPENAME element
		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERTYPENAME", typeSP));

		// create VOUCHERNUMBER element
		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERNUMBER", vCount));

		voucher.appendChild(getVoucherElements(doc, voucher, "REFERENCE", invCode));

		Element allLedgerEntriesList = doc.createElement("ALLLEDGERENTRIES.LIST");

		allLedgerEntriesList.appendChild(getLederEnteriesElements(doc, allLedgerEntriesList, "LEDGERNAME", strCustName));

		allLedgerEntriesList.appendChild(getLederEnteriesElements(doc, allLedgerEntriesList, "ISDEEMEDPOSITIVE", isDeempositive));

		allLedgerEntriesList.appendChild(getLederEnteriesElements(doc, allLedgerEntriesList, "AMOUNT", dblGrandTotal));

		// for Bill
		Element billAlloctionList = doc.createElement("BILLALLOCATIONS.LIST");
		billAlloctionList.appendChild(getLederEnteriesElements(doc, billAlloctionList, "NAME", "49193"));
		billAlloctionList.appendChild(getLederEnteriesElements(doc, billAlloctionList, "BILLTYPE", "Direct"));
		billAlloctionList.appendChild(getLederEnteriesElements(doc, billAlloctionList, "AMOUNT", dblGrandTotal));
		allLedgerEntriesList.appendChild(billAlloctionList);

		voucher.appendChild(allLedgerEntriesList);
		// for percentage

		String sql = " select b.strTaxDesc,b.dblTaxableAmt from tblinvoicehd a , tblinvtaxdtl b " + " where a.strInvCode=b.strInvCode and date(a.dteInvDate) between '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'   ";
		System.out.println("PerData=" + sql);
		List list = objGlobalService.funGetList(sql);
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			String strTaxDesc = arrObj[0].toString();
			String strTaxableAmt = arrObj[1].toString();

			Element allLedgerEntriesListPercent = doc.createElement("ALLLEDGERENTRIES.LIST");

			allLedgerEntriesListPercent.appendChild(getLederEnteriesElements(doc, allLedgerEntriesListPercent, "LEDGERNAME", strTaxDesc));

			allLedgerEntriesListPercent.appendChild(getLederEnteriesElements(doc, allLedgerEntriesListPercent, "ISDEEMEDPOSITIVE", isDeempositive));

			allLedgerEntriesListPercent.appendChild(getLederEnteriesElements(doc, allLedgerEntriesListPercent, "AMOUNT", strTaxableAmt));

			voucher.appendChild(allLedgerEntriesListPercent);

		}

		return voucher;
	}

	private void funCustomerLedgerXMLFile(clsReportBean objBean, String clientCode) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element envelopeElement = doc.createElement("ENVELOPE");
			doc.appendChild(envelopeElement);

			envelopeElement.appendChild(getHeader(doc, "1", "Import", "Data", "All Masters")); // for
																								// Header

			Element bodyElement = doc.createElement("BODY");

			envelopeElement.appendChild(bodyElement);

			bodyElement.appendChild(getSuppLedgerDesc(doc, "")); // For Desc
																	// body

			Element data = doc.createElement("DATA");
			// append root element to document
			bodyElement.appendChild(data);

			Element tallyMessage = doc.createElement("TALLYMESSAGE");

			data.appendChild(tallyMessage);

			String sql = "select a.strInvCode,b.strPName,a.strCustCode from tblinvoicehd a,tblpartymaster b  " + " where a.strCustCode=b.strPCode and date(a.dteInvDate) between '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "' " + " and a.strClientCode='" + clientCode + "'  and b.strClientCode='" + clientCode + "' " + " ORDER by b.strPName  ";
			System.out.println("CustData=" + sql);
			List list = objGlobalService.funGetList(sql);
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				String invCode = arrObj[0].toString();
				String strPName = arrObj[1].toString();
				String custCode = arrObj[2].toString();
				String PNameAndCode = strPName + "  (" + custCode + ")";
				tallyMessage.appendChild(getSuppLedgerData(doc, PNameAndCode, "Create", objBean, clientCode, "cust"));

				String sqlTax = "select a.strTaxDesc,c.dblPercent  from tblinvtaxdtl a ,tblinvoicehd b,tbltaxhd c " + " where b.strInvCode='" + invCode + "' and a.strInvCode=b.strInvCode and c.strTaxOnSP='Sales' " + " and a.strTaxCode=c.strTaxCode and date(b.dteInvDate) between '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "'  " + " and a.strClientCode='" + clientCode
						+ "'  and b.strClientCode='" + clientCode + "' ";

				System.out.println("taxData=" + sqlTax);
				List listPer = objGlobalService.funGetList(sqlTax);
				for (int i = 0; i < listPer.size(); i++) {
					Object[] arrTax = (Object[]) listPer.get(i);
					String taxDesc = arrTax[0].toString();
					String taxPer = "sales  " + arrTax[1].toString() + " %";

					tallyMessage.appendChild(getInvTaxLedgerData(doc, taxPer, "Create", "Sales Account", objBean, clientCode, "Tax"));
					tallyMessage.appendChild(getInvTaxLedgerData(doc, taxDesc, "taxDesc", "Duties &amp; Taxes", objBean, clientCode, "Tax"));
				}
			}
			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			System.out.println(System.getProperty("user.dir") + "\\CustLdg.xml");

			StreamResult file = new StreamResult(new File("CustLdg.xml").getAbsolutePath());

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			System.out.println("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Node getInvTaxLedgerData(Document doc, String taxDesc, String ledgerAction, String parent, clsReportBean objBean, String clientCode, String type) {
		Element ledgerElement = doc.createElement("LEDGER");
		ledgerElement.setAttribute("NAME", taxDesc);
		ledgerElement.setAttribute("Action", ledgerAction);

		ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "NAME", taxDesc));

		ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "PARENT", parent));

		ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "OPENINGBALANCE", "0"));

		ledgerElement.appendChild(getVoucherElements(doc, ledgerElement, "ISBILLWISEON", "Yes"));

		return ledgerElement;
	}

	// ///// Sales Xml Functions End//////////

	private void funPurchaseXMLFileFormate1(clsReportBean objBean, String clientCode, HttpServletRequest req) {

		DecimalFormat df = new DecimalFormat("0.00");
		;
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {

			String companyName = objBean.getStrDocCode();
			String tempSupp[] = objBean.getStrSuppCode().split(",");
			String strSuppCodes = "";
			for (int i = 0; i < tempSupp.length; i++) {
				if (strSuppCodes.length() > 0) {
					strSuppCodes = strSuppCodes + " or a.strSuppCode='" + tempSupp[i] + "' ";
				} else if (!tempSupp[i].equals("")) {
					strSuppCodes = "a.strSuppCode='" + tempSupp[i] + "' ";
				}

			}

			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element envelopeElement = doc.createElement("ENVELOPE");
			doc.appendChild(envelopeElement);

			envelopeElement.appendChild(getHeaderFormate1(doc)); // for Header

			Element bodyElement = doc.createElement("BODY");
			envelopeElement.appendChild(bodyElement);

			Element importDataElement = doc.createElement("IMPORTDATA");
			bodyElement.appendChild(importDataElement);

			clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(objBean.getStrPropertyCode(), clientCode);
			// clsCompanyMasterModel objCompModel =
			// objSetupMasterService.funGetObject(clientCode);
			importDataElement.appendChild(getRequestDescFormate1(doc, "Vouchers", companyName)); // Company
																									// name
																									// accoring
																									// to
																									// tally
																									// company

			Element requestDataElement = doc.createElement("REQUESTDATA");
			importDataElement.appendChild(requestDataElement);

			Element tallyMessageElement = doc.createElement("TALLYMESSAGE");
			tallyMessageElement.setAttribute("xmlns:UDF", "TallyUDF");
			requestDataElement.appendChild(tallyMessageElement);
			String strPropCode = objBean.getStrPropertyCode() + "%";

			String sql = " select a.strGRNCode,a.strSuppCode,c.strPName,a.strAgainst,a.strBillNo,Date(a.dtGRNDate) , " + " a.dblTotal,ifnull(d.strTallyCode,''),a.strUserModified ,DATE_FORMAT(a.dtDueDate,'%d-%b-%Y') ,DATE_FORMAT(a.dtGRNDate,'%b')   " + " from tblgrnhd a " + " left outer join tblgrntaxdtl b on a.strGRNCode=b.strGRNCode  and b.strClientCode='" + clientCode + "' "
					+ " left outer join tblpartymaster c on a.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " left outer join tbltallylinkup d on d.strGroupCode=a.strSuppCode " + " where  date(a.dtGRNDate) between  '" + objBean.getDteFromDate() + "' and '" + objBean.getDteToDate() + "'  " + " and a.strClientCode='" + clientCode + "' and a.strGRNCode like '" + strPropCode
					+ "'  ";
			if (!strSuppCodes.equals("")) {
				sql = sql + "and " + "(" + strSuppCodes + ") ";
			}
			sql += " group by a.strGRNCode  ";

			List list = objGlobalService.funGetList(sql);
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				String grnNo = arrObj[0].toString();
				String strSuppCode = arrObj[1].toString();
				String strPName = arrObj[2].toString();
				String PNameAndCode = strPName + "  (" + strSuppCode + ")";
				String strAgainst = arrObj[3].toString();
				String strBillNo = arrObj[4].toString();
				String dtGRNDate = arrObj[5].toString();
				String grnDate = dtGRNDate.split("-")[0] + dtGRNDate.split("-")[1] + dtGRNDate.split("-")[2];

				String dblTotal = df.format(Double.parseDouble(arrObj[6].toString())).toString();
				String pNameAlise = arrObj[7].toString();
				// String userName = arrObj[8].toString();
				String billDateddMMyyyy = arrObj[9].toString();
				String billMonth = arrObj[10].toString();
				int vNo = cnt + 1;

				// tallyMessage.appendChild(getData(doc, String.valueOf(vNo),
				// grnDate, strBillNo, "No", String.valueOf(vNo) ,grnNo,
				// pNameAlise,"No",dblTotal,objBean,clientCode ));

				tallyMessageElement.appendChild(getDataFormate1(doc, String.valueOf(vNo), grnDate, strBillNo, "No", String.valueOf(vNo), grnNo, pNameAlise, "No", dblTotal, objBean, clientCode, companyName, objBean.getStrUserCode(), billDateddMMyyyy, billMonth));

			}

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			String guid = UUID.randomUUID().toString();
			Element tallyMessage2Element = doc.createElement("TALLYMESSAGE");
			tallyMessage2Element.setAttribute("xmlns:UDF", "TallyUDF");
			Element companyElement = doc.createElement("COMPANY");
			Element remotedListElement = doc.createElement("REMOTECMPINFO.LIST");
			remotedListElement.setAttribute("MERGE", "Yes");
			companyElement.appendChild(remotedListElement);
			remotedListElement.appendChild(getVoucherElements(doc, remotedListElement, "NAME", guid));
			remotedListElement.appendChild(getVoucherElements(doc, remotedListElement, "REMOTECMPNAME", companyName));
			remotedListElement.appendChild(getVoucherElements(doc, remotedListElement, "REMOTECMPSTATE", objSetup.getStrCity()));
			companyElement.appendChild(remotedListElement);
			tallyMessage2Element.appendChild(companyElement);
			requestDataElement.appendChild(tallyMessage2Element);

			// bodyElement.appendChild(getDes(doc, "")); //For Desc body
			//
			//
			// Element data =doc.createElement("DATA");
			// //append root element to document
			// bodyElement.appendChild(data);
			//
			// Element tallyMessage =doc.createElement("TALLYMESSAGE");
			//
			// data.appendChild(tallyMessage);

			// String
			// sql=" select a.strGRNCode,a.strSuppCode,c.strPName,a.strAgainst,a.strBillNo,Date(a.dtGRNDate) , a.dblTotal,d.strTallyCode  "
			// +
			// " from tblgrnhd a ,tblgrntaxdtl b ,tblpartymaster c ,tbltallylinkup d"
			// +
			// " where a.strGRNCode=b.strGRNCode and a.strSuppCode=c.strPCode and d.strGroupCode=a.strSuppCode "
			// +
			// " and date(a.dtGRNDate) between  '"+objBean.getDteFromDate()+"' and '"+objBean.getDteToDate()+"' "
			// + " and a.strClientCode='"+clientCode+"' "
			// + " and b.strClientCode='"+clientCode+"' "
			// + " and c.strClientCode='"+clientCode+"' "
			// + " group by a.strGRNCode ";

			// String
			// sql=" select a.strGRNCode,a.strSuppCode,c.strPName,a.strAgainst,a.strBillNo,Date(a.dtGRNDate) , a.dblTotal,ifnull(d.strTallyCode,'')   "
			// + " from tblgrnhd a "
			// +
			// " left outer join tblgrntaxdtl b on a.strGRNCode=b.strGRNCode  and b.strClientCode='"+clientCode+"' "
			// +
			// " left outer join tblpartymaster c on a.strSuppCode=c.strPCode and c.strClientCode='"+clientCode+"' "
			// +
			// " left outer join tbltallylinkup d on d.strGroupCode=a.strSuppCode "
			// +
			// " where  date(a.dtGRNDate) between  '"+objBean.getDteFromDate()+"' and '"+objBean.getDteToDate()+"'  "
			// + " and a.strClientCode='"+clientCode+"' "
			// + " group by a.strGRNCode  ";
			//
			// System.out.println("mainData="+sql);
			// List list=objGlobalService.funGetList(sql);
			// for(int cnt=0;cnt<list.size();cnt++)
			// {
			// Object[] arrObj=(Object[])list.get(cnt);
			// String grnNo=arrObj[0].toString();
			// String strSuppCode=arrObj[1].toString();
			// String strPName=arrObj[2].toString();
			// String PNameAndCode = strPName+"  ("+strSuppCode+")";
			// String strAgainst=arrObj[3].toString();
			// String strBillNo=arrObj[4].toString();
			// String dtGRNDate=arrObj[5].toString();
			// String
			// grnDate=dtGRNDate.split("-")[0]+dtGRNDate.split("-")[1]+dtGRNDate.split("-")[2];
			//
			// String dblTotal =arrObj[6].toString();
			// String pNameAlise = arrObj[7].toString();
			// int vNo=cnt+1;

			// tallyMessage.appendChild(getData(doc, String.valueOf(vNo),
			// grnDate, strBillNo, "No", String.valueOf(vNo) ,grnNo,
			// pNameAlise,"No",dblTotal,objBean,clientCode ));

			// }

			// append first child element to root element
			// tallyMessage.appendChild(getData(doc, "1", "20160710",
			// "Bill No 1012ee", "NO", "01" ,"01GRAG000043",
			// "VIRCHAND KHIMJI  AND CO. (S000049)","No","2055.00" ));

			// //append second child
			// tallyMessage.appendChild(getData(doc, "2", "20160710",
			// "Bill No 101215", "Yes", "02" ,"01GRAG000049",
			// "VIN VIN ENTERPRISES. (S000053)","No","1075.2000" ));

			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			System.out.println(System.getProperty("user.dir") + "\\PURNEW" + clientCode + ".xml");

			StreamResult file = new StreamResult(new File("PURNEW" + clientCode + ".xml").getAbsolutePath());

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
			System.out.println("DONE");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Node getHeaderFormate1(Document doc) {
		Element headerElement = doc.createElement("HEADER");
		headerElement.appendChild(getVoucherElements(doc, headerElement, "TALLYREQUEST", "IMPORT DATA"));

		return headerElement;
	}

	private Node getRequestDescFormate1(Document doc, String rptType, String companyName) {
		Element reqDescElement = doc.createElement("REQUESTDESC");
		reqDescElement.appendChild(getVoucherElements(doc, reqDescElement, "REPORTNAME", rptType));

		reqDescElement.appendChild(getStaticVariableFormate1(doc, rptType, companyName));

		return reqDescElement;
	}

	private Node getStaticVariableFormate1(Document doc, String rptType, String companyName) {
		Element staticVariableElement = doc.createElement("STATICVARIABLES");
		staticVariableElement.appendChild(getVoucherElements(doc, staticVariableElement, "SVCURRENTCOMPANY", companyName));
		return staticVariableElement;
	}

	private Node getDataFormate1(Document doc, String id, String date, String billNo, String typeSP, String vCount, String strGRNNo, String strSuppName, String isDeempositive, String dblGrandTotal, clsReportBean objBean, String clientCode, String companyName, String userName, String billDateddMMyyyy, String billMonth) {
		Random rand = new Random();
		DecimalFormat df = new DecimalFormat("0.00");
		String guid = UUID.randomUUID().toString();
		int randomNum = rand.nextInt(99999999) + 1;
		int alterid = rand.nextInt(999999) + 1;
		int masterid = rand.nextInt(999999) + 1;
		int jd = rand.nextInt(99999) + 1;
		long voucherKey = rand.nextLong();
		Element voucher = doc.createElement("VOUCHER");
		voucher.setAttribute("REMOTEID", guid);
		voucher.setAttribute("VCHKEY", UUID.randomUUID().toString() + ":" + randomNum);
		voucher.setAttribute("VCHTYPE", companyName + " Purchase");
		voucher.setAttribute("ACTION", "Create");
		voucher.setAttribute("OBJVIEW", "Accounting Voucher View");
		Element billAlloctionList = doc.createElement("OLDAUDITENTRYIDS.LIST");
		billAlloctionList.setAttribute("TYPE", "Number");
		billAlloctionList.appendChild(getVoucherElements(doc, billAlloctionList, "OLDAUDITENTRYIDS", "-1"));
		voucher.appendChild(billAlloctionList);

		voucher.appendChild(getVoucherElements(doc, voucher, "DATE", date));
		voucher.appendChild(getVoucherElements(doc, voucher, "REFERENCEDATE", date));
		voucher.appendChild(getVoucherElements(doc, voucher, "GUID", guid));
		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERTYPENAME", companyName + " Purchase"));
		voucher.appendChild(getVoucherElements(doc, voucher, "REFERENCE", billNo));

		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERNUMBER", strGRNNo)); // TAlly
																							// system
																							// gentarted
																							// no
		voucher.appendChild(getVoucherElements(doc, voucher, "PARTYLEDGERNAME", strSuppName));
		voucher.appendChild(getVoucherElements(doc, voucher, "CSTFORMISSUETYPE", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "FBTPAYMENTTYPE", "Default"));
		voucher.appendChild(getVoucherElements(doc, voucher, "PERSISTEDVIEW", "Accounting Voucher View"));
		voucher.appendChild(getVoucherElements(doc, voucher, "VCHGSTCLASS", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "ENTEREDBY", userName)); // Tally
																						// User
		voucher.appendChild(getVoucherElements(doc, voucher, "DIFFACTUALQTY", "No")); // confuesd
		voucher.appendChild(getVoucherElements(doc, voucher, "ISMSTFROMSYNC", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ASORIGINAL", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "AUDITED", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "FORJOBCOSTING", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISOPTIONAL", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "EFFECTIVEDATE", date));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFOREXCISE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISFORJOBWORKIN", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ALLOWCONSUMPTION", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORINTEREST", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORGAINLOSS", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORGODOWNTRANSFER", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORCOMPOUND", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORSERVICETAX", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISEXCISEVOUCHER", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "EXCISETAXOVERRIDE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORTAXUNITTRANSFER", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "EXCISEOPENING", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USEFORFINALPRODUCTION", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISTDSOVERRIDDEN", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISTCSOVERRIDDEN", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISTDSTCSCASHVCH", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "INCLUDEADVPYMTVCH", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISSUBWORKSCONTRACT", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISVATOVERRIDDEN", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "IGNOREORIGVCHDATE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISSERVICETAXOVERRIDDEN", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISISDVOUCHER", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISEXCISEOVERRIDDEN", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISEXCISESUPPLYVCH", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISVATPRINCIPALACCOUNT", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISSHIPPINGWITHINSTATE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISCANCELLED", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "HASCASHFLOW", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISPOSTDATED", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "USETRACKINGNUMBER", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISINVOICE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "MFGJOURNAL", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "HASDISCOUNTS", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ASPAYSLIP", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISCOSTCENTRE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISSTXNONREALIZEDVCH", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISEXCISEMANUFACTURERON", "Yes")); // confuesd
		voucher.appendChild(getVoucherElements(doc, voucher, "ISBLANKCHEQUE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISVOID", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISONHOLD", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ORDERLINESTATUS", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "VATISAGNSTCANCSALES", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "VATISPURCEXEMPTED", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISVATRESTAXINVOICE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ISDELETED", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "CHANGEVCHMODE", "No"));
		voucher.appendChild(getVoucherElements(doc, voucher, "ALTERID", String.valueOf(alterid)));
		voucher.appendChild(getVoucherElements(doc, voucher, "MASTERID", String.valueOf(masterid)));
		voucher.appendChild(getVoucherElements(doc, voucher, "VOUCHERKEY", String.valueOf(voucherKey)));
		voucher.appendChild(getVoucherElements(doc, voucher, "EXCLUDEDTAXATIONS.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "OLDAUDITENTRIES.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "ACCOUNTAUDITENTRIES.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "AUDITENTRIES.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "DUTYHEADDETAILS.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "SUPPLEMENTARYDUTYHEADDETAILS.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "INVOICEDELNOTES.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "INVOICEORDERLIST.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "INVOICEINDENTLIST.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "ATTENDANCEENTRIES.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "ORIGINVOICEDETAILS.LIST", ""));
		voucher.appendChild(getVoucherElements(doc, voucher, "INVOICEEXPORTLIST.LIST", ""));

		Element allLedgerEntryList = doc.createElement("ALLLEDGERENTRIES.LIST");

		Element billAlloctionList2 = doc.createElement("OLDAUDITENTRYIDS.LIST");
		billAlloctionList2.setAttribute("TYPE", "Number");

		billAlloctionList2.appendChild(getVoucherElements(doc, billAlloctionList2, "OLDAUDITENTRYIDS", "-1"));
		allLedgerEntryList.appendChild(billAlloctionList2);

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "LEDGERNAME", strSuppName));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "GSTCLASS", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ISDEEMEDPOSITIVE", "No"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "LEDGERFROMITEM", "No"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "REMOVEZEROENTRIES", "No"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ISPARTYLEDGER", "Yes"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ISLASTDEEMEDPOSITIVE", "No"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "AMOUNT", dblGrandTotal));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "VATEXPAMOUNT", dblGrandTotal));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "SERVICETAXDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "BANKALLOCATIONS.LIST", ""));
		Element billAlloctionList3 = doc.createElement("BILLALLOCATIONS.LIST");
		billAlloctionList3.appendChild(getVoucherElements(doc, billAlloctionList3, "NAME", billNo));
		Element billPeriod = doc.createElement("BILLCREDITPERIOD");
		billPeriod.setAttribute("JD", String.valueOf(jd));
		billPeriod.setAttribute("P", billDateddMMyyyy);
		billPeriod.setNodeValue(billNo);
		billAlloctionList3.appendChild(billPeriod);
		billAlloctionList3.appendChild(getVoucherElements(doc, billAlloctionList3, "BILLTYPE", "New Ref"));
		billAlloctionList3.appendChild(getVoucherElements(doc, billAlloctionList3, "TDSDEDUCTEEISSPECIALRATE", "No"));
		billAlloctionList3.appendChild(getVoucherElements(doc, billAlloctionList3, "AMOUNT", dblGrandTotal));
		billAlloctionList3.appendChild(getVoucherElements(doc, billAlloctionList3, "INTERESTCOLLECTION.LIST", ""));
		billAlloctionList3.appendChild(getVoucherElements(doc, allLedgerEntryList, "STBILLCATEGORIES.LIST", ""));
		allLedgerEntryList.appendChild(billAlloctionList3);

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "INTERESTCOLLECTION.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "OLDAUDITENTRIES.LIST", ""));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ACCOUNTAUDITENTRIES.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "AUDITENTRIES.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "INPUTCRALLOCS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "DUTYHEADDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "EXCISEDUTYHEADDETAILS.LIST", ""));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "SUMMARYALLOCS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "STPYMTDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "EXCISEPAYMENTALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "TAXBILLALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "TAXOBJECTALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "TDSEXPENSEALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "VATSTATUTORYDETAILS.LIST", ""));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "COSTTRACKALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "REFVOUCHERDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "INVOICEWISEDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "VATITCDETAILS.LIST", ""));

		voucher.appendChild(allLedgerEntryList);

		// Sub Group Ledger Entry

		String subGroupSql = " select b.strTallyCode,b.strGroupName ,sum(a.dblTotalPrice) " + " from tblgrndtl a , tbltallylinkup b ,tblproductmaster c " + " where a.strProdCode=c.strProdCode  and b.strGroupCode=c.strSGCode  " + " and a.strGRNCode = '" + strGRNNo + "'  " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' " + " and c.strClientCode='"
				+ clientCode + "' " + " group by b.strGroupCode ";

		double drAmt = 0.00;
		List listSG = objGlobalService.funGetList(subGroupSql);
		for (int cnt = 0; cnt < listSG.size(); cnt++) {
			Object[] arrObj = (Object[]) listSG.get(cnt);
			String sgAlsie = arrObj[0].toString();
			String strSGName = arrObj[1].toString();
			String strSubTotal = arrObj[2].toString();
			drAmt += Double.parseDouble(arrObj[2].toString());
			voucher.appendChild(getTaxDataFormate1(doc, sgAlsie, strSGName, "-" + df.format(Double.parseDouble(strSubTotal)), billDateddMMyyyy, strGRNNo, "Yes", "No"));

		}

		//

		String sql = " select c.strTallyCode,b.strTaxDesc,b.strTaxAmt from tblgrnhd a,tblgrntaxdtl b ,tbltallylinkup c " + " where  a.strGRNCode=b.strGRNCode and  b.strTaxCode=c.strGroupCode " + " and a.strGRNCode='" + strGRNNo + "' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'  ";

		List listTax = objGlobalService.funGetList(sql);
		for (int cnt = 0; cnt < listTax.size(); cnt++) {
			Object[] arrObj = (Object[]) listTax.get(cnt);
			String taxAlsie = arrObj[0].toString();
			String strTaxDesc = arrObj[1].toString();
			String strTaxAmt = df.format(Double.parseDouble(arrObj[2].toString())).toString();
			drAmt += Double.parseDouble(arrObj[2].toString());
			voucher.appendChild(getTaxDataFormate1(doc, taxAlsie, strTaxDesc, "-" + strTaxAmt, billDateddMMyyyy, strGRNNo, "Yes", "No"));

		}

		double dblRoundup = Double.parseDouble(dblGrandTotal) - drAmt;
		if (dblRoundup > 0) {
			voucher.appendChild(getTaxDataFormate1(doc, "Rounding Off on Purchase", "", "-" + df.format(Double.parseDouble(String.valueOf(dblRoundup))), billDateddMMyyyy, strGRNNo, "Yes", "Yes"));

		} else if (dblRoundup < 0) {
			voucher.appendChild(getTaxDataFormate1(doc, "Rounding Off on Purchase", "", df.format(Double.parseDouble(String.valueOf(dblRoundup * -1))), billDateddMMyyyy, strGRNNo, "No", "No"));
		}

		return voucher;
	}

	private Element getTaxDataFormate1(Document doc, String taxAlsie, String strTaxDesc, String strTaxAmt, String billDateddMMyyyy, String strGRNNo, String strDepositeYesNo, String isPartyLedger) {

		Random rand = new Random();
		String guid = UUID.randomUUID().toString();
		int randomNum = rand.nextInt(99999999) + 1;
		int alterid = rand.nextInt(999999) + 1;
		int masterid = rand.nextInt(999999) + 1;
		int jd = rand.nextInt(99999) + 1;
		long voucherKey = rand.nextLong();

		Element allLedgerEntryList = doc.createElement("ALLLEDGERENTRIES.LIST");

		Element billAlloctionList2 = doc.createElement("OLDAUDITENTRYIDS.LIST");
		billAlloctionList2.setAttribute("TYPE", "Number");

		billAlloctionList2.appendChild(getVoucherElements(doc, billAlloctionList2, "OLDAUDITENTRYIDS", "-1"));
		allLedgerEntryList.appendChild(billAlloctionList2);

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "LEDGERNAME", taxAlsie));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "GSTCLASS", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ISDEEMEDPOSITIVE", strDepositeYesNo));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "LEDGERFROMITEM", "No"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "REMOVEZEROENTRIES", "No"));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ISPARTYLEDGER", isPartyLedger));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ISLASTDEEMEDPOSITIVE", strDepositeYesNo));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "AMOUNT", strTaxAmt));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "VATEXPAMOUNT", strTaxAmt));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "SERVICETAXDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "BANKALLOCATIONS.LIST", ""));
		Element billAlloctionList3 = doc.createElement("BILLALLOCATIONS.LIST");

		allLedgerEntryList.appendChild(billAlloctionList3);

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "INTERESTCOLLECTION.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "OLDAUDITENTRIES.LIST", ""));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "ACCOUNTAUDITENTRIES.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "AUDITENTRIES.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "INPUTCRALLOCS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "DUTYHEADDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "EXCISEDUTYHEADDETAILS.LIST", ""));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "SUMMARYALLOCS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "STPYMTDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "EXCISEPAYMENTALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "TAXBILLALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "TAXOBJECTALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "TDSEXPENSEALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "VATSTATUTORYDETAILS.LIST", ""));

		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "COSTTRACKALLOCATIONS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "REFVOUCHERDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "INVOICEWISEDETAILS.LIST", ""));
		allLedgerEntryList.appendChild(getVoucherElements(doc, allLedgerEntryList, "VATITCDETAILS.LIST", ""));

		return allLedgerEntryList;
	}

}
