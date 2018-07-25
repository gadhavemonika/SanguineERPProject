package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsBillDtl;
import com.sanguine.webpos.bean.clsPOSAssignHomeDeliveryBean;
import com.sanguine.webpos.bean.clsPOSReprintDocumentsBean;

@Controller
public class clsPOSReprintController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;
	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();
	JSONObject josnObjRet;

	@RequestMapping(value = "/frmPOSReprint", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSAssignHomeDeliveryBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		List posList = new ArrayList();
		// posList.add("ALL");

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}

		model.put("posList", posList);

		String posCode = request.getSession().getAttribute("loginPOS").toString();

		String strPOSName = "";

		if (map.containsKey(posCode)) {
			strPOSName = (String) map.get(posCode);
		}
		request.setAttribute("posName", strPOSName);
		// List list =loadFunExecute(request);
		//
		// model.put("tblheader",list.get(0));
		// model.put("details",list.get(1));

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSReprint_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSReprint");
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/loadFunExecute", method = RequestMethod.GET)
	public @ResponseBody JSONObject loadFunExecute(HttpServletRequest req) {
		List listmain = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		String operationType = req.getParameter("operationType");
		String kotFor = req.getParameter("kotFor");

		JSONArray jArrAllTableList = null;
		List<clsPOSReprintDocumentsBean> listReprintDocsData = new ArrayList<clsPOSReprintDocumentsBean>();

		JSONObject jObjAllTableData = new JSONObject();

		JSONArray jArrForDina = null;
		JSONArray jArrForDirectBiller = null;
		JSONArray jArrForBill = null;

		String posUrl = "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funExecute" + "?posCode=" + posCode + "&oprType=" + operationType + "&kotFor=" + kotFor;

		jObjAllTableData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		String strOperation = (String) jObjAllTableData.get("strOperation");

		if (strOperation.equals("Dina")) {
			jArrForDina = (JSONArray) jObjAllTableData.get("TblData");

		} else if (strOperation.equals("DirectBiller")) {

			jArrForDina = (JSONArray) jObjAllTableData.get("TblData");
		} else {

			jArrForDina = (JSONArray) jObjAllTableData.get("TblData");
		}

		JSONObject jObjTblDataDtl = new JSONObject();

		jObjTblDataDtl.put("AllTblData", jArrForDina);
		jObjTblDataDtl.put("strOpr", strOperation);

		return jObjTblDataDtl;

	}

	// @RequestMapping(value = "/funViewButtonPressed", method =
	// RequestMethod.GET)
	// public @ResponseBody JSONObject funViewButtonPressed(HttpServletRequest
	// req)
	// {
	// String transactionType=req.getParameter("transactionType");
	// String kotFor=req.getParameter("kotFor");
	// String docNo=req.getParameter("code");
	// JSONObject jObjData=new JSONObject();
	// String posUrl =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funViewButtonPressed"
	// + "?docNo="+docNo+"&transactionType="+transactionType+"&kotFor="+kotFor;
	// jObjData =objGlobal.funGETMethodUrlJosnObjectData(posUrl);
	//
	// return jObjData;
	//
	// }

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmViewData", method = RequestMethod.GET)
	public void funOpenFormViewData(@ModelAttribute("command") clsPOSReprintDocumentsBean objBean, HttpServletResponse resp, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String kotFor = request.getParameter("kotFor");
		String code = request.getParameter("code");
		String transactionType = request.getParameter("transactionType");
		String posName = null;
		String webStockUserCode = request.getSession().getAttribute("usercode").toString();
		String strDocType = "PDF";
		String companyName = request.getSession().getAttribute("companyName").toString();

		String posUrL = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
		JSONObject jObjPosDate = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

		String posDate = (String) jObjPosDate.get("POSDate");
		String[] output = posDate.split(" ");
		String POSDate = output[0];

		// ============================ this function for pringVatNoPOS
		// ==========================//
		JSONObject jObjPrintVatNoPOS = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funPrintVatNoPOS" + "?posCode=" + posCode;

		jObjPrintVatNoPOS = objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		String PrintVatNoPOS = (String) jObjPrintVatNoPOS.get("printVatNo");

		JSONObject jObjVatNoPos = new JSONObject();
		posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funVatNoPOS" + "?posCode=" + posCode;
		jObjVatNoPos = objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		String vatNo = (String) jObjVatNoPos.get("vatNo");

		JSONObject jObjPintServiceTaxNoPos = new JSONObject();
		posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funPrintServiceTaxNo" + "?posCode=" + posCode;
		jObjPintServiceTaxNoPos = objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		String printServiceTaxNo = (String) jObjPintServiceTaxNoPos.get("printServiceTaxNo");

		JSONObject jObjServiceTaxNoPos = new JSONObject();
		posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funServiceTaxNoPOS" + "?posCode=" + posCode;
		jObjServiceTaxNoPos = objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		String serviceTaxNo = (String) jObjServiceTaxNoPos.get("serviceTaxNo");

		JSONArray jArryPosList = new JSONArray();
		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetPOSName(posCode);
		if (null != jArrList) {
			for (int i = 0; i < jArrList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArrList.get(i);

				posName = (String) josnObjRet.get("strPosName");
			}
		}

		List<List<clsBillDtl>> listData = new ArrayList<>();

		try {

			// String urlHits="1";
			// try{
			// urlHits=request.getParameter("saddr").toString();
			// }catch(NullPointerException e){
			// urlHits="1";
			// }
			// model.put("urlHits",urlHits);
			// // JSONArray jArryPosList=new JSONArray();
			// JSONArray jArrList=new JSONArray();
			// jArrList =objPOSGlobal.funGetPOSName(posCode);
			// for(int i =0 ;i<jArrList.size();i++)
			// {
			// JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			//
			// posName=(String) josnObjRet.get("strPosName");
			// }
			// JSONObject jObjData=new JSONObject();
			//
			// String posUrl =
			// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funViewButtonPressed"
			// +
			// "?code="+code+"&transactionType="+transactionType+"&kotFor="+kotFor+"&posCode="+posCode+"&clientCode="+clientCode+"&posName="+posName+"&webStockUserCode="+webStockUserCode;
			// jObjData =objGlobal.funGETMethodUrlJosnObjectData(posUrl);
			//
			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("clientCode", clientCode);
			jObjFillter.put("loginPOS", posCode);
			jObjFillter.put("kotFor", kotFor);
			jObjFillter.put("code", code);
			jObjFillter.put("transactionType", transactionType);
			jObjFillter.put("strPosName", posName);
			jObjFillter.put("usercode", webStockUserCode);
			jObjFillter.put("POSDate", POSDate);
			jObjFillter.put("PrintVatNoPOS", PrintVatNoPOS);
			jObjFillter.put("vatNo", vatNo);
			jObjFillter.put("printServiceTaxNo", printServiceTaxNo);
			jObjFillter.put("serviceTaxNo", serviceTaxNo);
			//
			// String posUrL
			// ="http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate"
			// + "?POSCode="+posCode;
			// JSONObject
			// jObjForDate=objGlobal.funGETMethodUrlJosnObjectData(posUrL);
			//
			// String posDate=(String)jObjForDate.get("POSDate");
			// jObjFillter.put("POSDate", posDate);
			//
			String KOT = "", kotType = "", dublicate = "", POS = "", hostName = "", costCenter = "", PAX = "", DATE_TIME = "", KOTorNC = "", tableNo = "", waiterName = "";

			JSONObject jObj = objGlobal.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReprintDocuments/funViewButtonPressedReport", jObjFillter);
			List<clsBillDtl> list = new ArrayList<>();
			JSONArray jarr = new JSONArray();
			JSONArray jarr1 = new JSONArray();
			JSONArray jArrBillDtl = new JSONArray();
			String format = "";
			String noOfLines = "", imagePath = "";
			HashMap hm = new HashMap();

			String reportName = "";
			if (kotFor.equalsIgnoreCase("Dina")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptGenrateKOTJasperReport.jrxml");
				imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
				jarr = (JSONArray) jObj.get("jArr");
				jarr1 = (JSONArray) jObj.get("listOfItemDtl");
				noOfLines = (String) jObj.get("gNoOfLinesInKOTPrint");

				if (null != jarr) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);
						KOTorNC = jObjtemp.get("KOTorNC").toString();
						tableNo = jObjtemp.get("tableNo").toString();
						KOT = jObjtemp.get("KOT").toString();
						kotType = jObjtemp.get("KOTType").toString();
						dublicate = jObjtemp.get("dublicate").toString();
						POS = jObjtemp.get("POS").toString();
						hostName = jObjtemp.get("KOTFrom").toString();
						waiterName = jObjtemp.get("waiterName").toString();
						costCenter = jObjtemp.get("costCenter").toString();
						PAX = jObjtemp.get("PAX").toString();
						DATE_TIME = jObjtemp.get("DATE_TIME").toString();
					}
				}
				if (null != jarr1) {
					for (int i = 0; i < jarr1.size(); i++) {
						JSONObject jObjtemp1 = (JSONObject) jarr1.get(i);

						clsBillDtl objClsBillDtl = new clsBillDtl();

						objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("itemQty").toString()));
						objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());

						// objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
						// objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp.get("dblQuantity").toString()));
						// objClsBillDtl.setDblSubTotal(Double.parseDouble(jObjtemp.get("dblAmtLessDis").toString()));
						// objClsBillDtl.setDblDisAmt(Double.parseDouble(jObjtemp.get("dblDiscountAmt").toString()));

						list.add(objClsBillDtl);
					}
				}
				for (int cntLines = 0; cntLines < Integer.parseInt(noOfLines); cntLines++) {
					clsBillDtl objBillDtl = new clsBillDtl();
					objBillDtl.setDblQuantity(0);
					objBillDtl.setStrItemName("");
					list.add(objBillDtl);
				}

				hm.put("KOT", KOT);
				hm.put("KOTorNC", KOTorNC);
				hm.put("dublicate", dublicate);
				hm.put("tableNo", tableNo);
				hm.put("POS", POS);
				hm.put("waiterName", waiterName);
				hm.put("KOT From", hostName);
				hm.put("costCenter", costCenter);
				hm.put("PAX", PAX);
				hm.put("DATE_TIME", DATE_TIME);
				hm.put("KOTType", kotType);
				hm.put("imagePath", imagePath);
				hm.put("clientName", companyName);
				hm.put("shiftNo", "1");
				hm.put("userName", webStockUserCode);
				hm.put("listOfItemDtl", list);

				listData.add(list);
			} else if (kotFor.equalsIgnoreCase("DirectBiller")) {

				format = (String) jObj.get("format");
				boolean flag_DirectBillerBlill = (boolean) jObj.get("flag_DirectBillerBlill");
				if (format.equalsIgnoreCase("Jasper1")) {
					jarr1 = (JSONArray) jObj.get("listOfBillDetail");
					long result = (long) jObj.get("result");
					long lengthListOfHomeDeliveryDtl = (long) jObj.get("lengthListOfHomeDeliveryDtl");

					String posWiseHeading = "", duplicate = "", BillType = "", ClientName = "", ClientAddress1 = "", ClientAddress2 = "", ClientAddress3 = "", ClientCity = "", TEL_NO = "";
					String EMAIL_ID = "", TAX_INVOICE = "", BillNo = "", Totl = "", name = "", address = "", mobile_no = "", footer = "";
					String taxAmount = "", taxDesc = "", discount = "", discText = "", discAmt = "", reason = "", remark = "";

					clsBillDtl objBillDtl = new clsBillDtl();
					jarr = (JSONArray) jObj.get("jArr");
					List<clsBillDtl> listOfGrandTotal = new ArrayList<>();
					List<clsBillDtl> listOfHomeDeliveryDtl = new ArrayList<>();
					List<clsBillDtl> listOfServiceVatDetail = new ArrayList<>();
					List<clsBillDtl> listOfFooterDtl = new ArrayList<>();
					List<clsBillDtl> listOfTaxDtl = new ArrayList<>();
					List<clsBillDtl> listOfDiscountDtl = new ArrayList<>();
					List<clsBillDtl> listOfSettlementDetail = new ArrayList<>();

					if (null != jarr) {
						for (int i = 0; i < jarr.size(); i++) {
							JSONObject jObjtemp = (JSONObject) jarr.get(i);

							JSONArray jarrTotal = (JSONArray) jObjtemp.get("listOfGrandTotalDtl");

							if (null != jarrTotal) {
								for (i = 0; i < jarrTotal.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjTot = (JSONObject) jarrTotal.get(i);
									objBillDtl.setDblAmount(Double.parseDouble(jObjTot.get("grandTotal").toString()));

									listOfGrandTotal.add(objBillDtl);

								}
							}

							JSONArray jarrSettle = (JSONArray) jObjtemp.get("listOfSettlementDetail");

							if (null != jarrSettle) {
								for (i = 0; i < jarrSettle.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjSettle = (JSONObject) jarrSettle.get(i);
									objBillDtl.setStrItemName(jObjSettle.get("settleDesc").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjSettle.get("settleAmt").toString()));
									listOfSettlementDetail.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName(jObjSettle.get("PaidAmtTxt").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjSettle.get("paidAmt").toString()));
									listOfSettlementDetail.add(objBillDtl);

								}
							}

							JSONArray jarrHomeDel = (JSONArray) jObjtemp.get("listOfHomeDeliveryDtl");

							if (null != jarrHomeDel) {
								for (i = 0; i < jarrHomeDel.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjHomeDel = (JSONObject) jarrHomeDel.get(i);
									objBillDtl.setStrItemName("Name         : " + jObjHomeDel.get("Name         : ").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName("Address      : " + jObjHomeDel.get("Address      : ").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName("MOBILE_NO  :" + jObjHomeDel.get("MOBILE_NO  :").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

								}
							}

							JSONArray jarrServiceTaxDtl = (JSONArray) jObjtemp.get("listOfServiceVatDetail");

							if (null != jarrServiceTaxDtl) {
								for (i = 0; i < jarrServiceTaxDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjServieTaxDtl = (JSONObject) jarrServiceTaxDtl.get(i);
									objBillDtl.setStrItemName("Service Tax No.    :" + jObjServieTaxDtl.get("Service Tax No.:").toString());
									listOfServiceVatDetail.add(objBillDtl);
								}
							}

							JSONArray jarrFooterDtl = (JSONArray) jObjtemp.get("listOfFooterDtl");

							if (null != jarrFooterDtl) {
								for (i = 0; i < jarrFooterDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjFooterDtl = (JSONObject) jarrFooterDtl.get(i);
									objBillDtl.setStrItemName(jObjFooterDtl.get("Thank").toString());
									listOfFooterDtl.add(objBillDtl);
								}
							}
							JSONArray jarrTaxDtl = (JSONArray) jObjtemp.get("listOfTaxDtl");

							if (null != jarrTaxDtl) {
								for (i = 0; i < jarrTaxDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjTax = (JSONObject) jarrTaxDtl.get(i);
									objBillDtl = new clsBillDtl();

									objBillDtl.setDblAmount(Double.parseDouble(jObjTax.get("taxAmount").toString()));
									objBillDtl.setStrItemName(jObjTax.get("taxDesc").toString());

									listOfTaxDtl.add(objBillDtl);
								}
							}

							JSONArray jarrDiscountDtl = (JSONArray) jObjtemp.get("listOfDiscountDtl");

							if (null != jarrDiscountDtl) {
								for (i = 0; i < jarrDiscountDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjDiscount = (JSONObject) jarrDiscountDtl.get(i);
									objBillDtl.setStrItemName(jObjDiscount.get("Discount").toString());
									objBillDtl.setStrItemName(jObjDiscount.get("discText").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjDiscount.get("discAmt").toString()));
									objBillDtl.setStrItemName(jObjDiscount.get("Reason").toString());
									objBillDtl.setStrItemName(jObjDiscount.get("Remark").toString());
									listOfDiscountDtl.add(objBillDtl);

								}
							}

							posWiseHeading = jObjtemp.get("posWiseHeading").toString();
							duplicate = jObjtemp.get("duplicate").toString();
							posName = jObjtemp.get("POS").toString();
							BillType = jObjtemp.get("BillType").toString();
							ClientName = jObjtemp.get("ClientName").toString();
							ClientAddress1 = jObjtemp.get("ClientAddress1").toString();
							ClientAddress2 = jObjtemp.get("ClientAddress2").toString();
							ClientAddress3 = jObjtemp.get("ClientAddress3").toString();
							ClientCity = jObjtemp.get("ClientCity").toString();
							TEL_NO = jObjtemp.get("TEL NO").toString();
							EMAIL_ID = jObjtemp.get("EMAIL ID").toString();
							TAX_INVOICE = jObjtemp.get("TAX_INVOICE").toString();
							DATE_TIME = jObjtemp.get("DATE_TIME").toString();
							BillNo = jObjtemp.get("BillNo").toString();

						}
					}

					if (null != jarr1) {
						for (int i = 0; i < jarr1.size(); i++) {
							JSONObject jObjtemp1 = (JSONObject) jarr1.get(i);

							clsBillDtl objClsBillDtl = new clsBillDtl();

							objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("qty").toString()));
							objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
							objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("amount").toString()));

							list.add(objClsBillDtl);
						}
					}

					hm.put("posWiseHeading", posWiseHeading);
					hm.put("duplicate", duplicate);
					hm.put("POS", posName);
					hm.put("ClientName", ClientName);
					hm.put("TAX_INVOICE", TAX_INVOICE);
					hm.put("ClientAddress1", ClientAddress1);
					hm.put("ClientAddress2", ClientAddress2);
					hm.put("ClientAddress3", ClientAddress3);
					hm.put("ClientCity", ClientCity);
					hm.put("TEL NO", TEL_NO);
					hm.put("EMAIL ID", EMAIL_ID);
					hm.put("BillNo", BillNo);
					hm.put("DATE_TIME", DATE_TIME);
					hm.put("BillType", BillType);
					hm.put("listOfItemDtl", list);
					hm.put("listOfTaxDtl", listOfTaxDtl);
					hm.put("listOfGrandTotalDtl", listOfGrandTotal);
					hm.put("listOfServiceVatDetail", listOfServiceVatDetail);
					hm.put("listOfFooterDtl", listOfFooterDtl);
					hm.put("listOfHomeDeliveryDtl", listOfHomeDeliveryDtl);
					hm.put("listOfDiscountDtl", listOfDiscountDtl);
					hm.put("listOfSettlementDetail", listOfSettlementDetail);

					// hm.put("listOfItemDtl", list);

					listData.add(list);

					// if(format.equalsIgnoreCase("Jasper1"))
					// {
					if (listOfHomeDeliveryDtl.size() > 0) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat5JasperReportHD.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					} else if (result == 1) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat5JasperReportNormalBill.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					} else {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat5JasperReportNormalBill.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					}

					//
					//
					//
					// reportName =servletContext.getRealPath(
					// "/WEB-INF/reports/webpos//rptBillFormat5JasperReportNormalBill.jrxml");
					// imagePath =
					// servletContext.getRealPath("/resources/images/company_Logo.png");
					// }

				} else if (format.equalsIgnoreCase("Jasper2")) {
					long result = (long) jObj.get("result");
					// long listOfHDSize= (long) jObj.get("listOfHDSize");

					jarr1 = (JSONArray) jObj.get("listOfBillDetail");

					// long lengthListOfHomeDeliveryDtl = (long)
					// jObj.get("lengthListOfHomeDeliveryDtl");

					String posWiseHeading = "", tableName = "", duplicate = "", BillType = "", ClientName = "", ClientAddress1 = "", ClientAddress2 = "", ClientAddress3 = "", ClientCity = "", TEL_NO = "";
					String EMAIL_ID = "", TAX_INVOICE = "", BillNo = "", Totl = "", name = "", address = "", mobile_no = "", footer = "";
					String taxAmount = "", taxDesc = "", discount = "", discText = "", discAmt = "", reason = "", remark = "";

					clsBillDtl objBillDtl = new clsBillDtl();
					jarr = (JSONArray) jObj.get("jArr");
					List<clsBillDtl> listOfGrandTotal = new ArrayList<>();
					List<clsBillDtl> listOfHomeDeliveryDtl = new ArrayList<>();
					List<clsBillDtl> listOfServiceVatDetail = new ArrayList<>();
					List<clsBillDtl> listOfFooterDtl = new ArrayList<>();
					List<clsBillDtl> listOfTaxDtl = new ArrayList<>();
					List<clsBillDtl> listOfDiscountDtl = new ArrayList<>();
					List<clsBillDtl> listOfCustomerDtl = new ArrayList<>();
					List<clsBillDtl> listOfSettlementDetail = new ArrayList<>();

					if (null != jarr) {
						for (int i = 0; i < jarr.size(); i++) {
							JSONObject jObjtemp = (JSONObject) jarr.get(i);

							JSONArray jarrTotal = (JSONArray) jObjtemp.get("listOfGrandTotalDtl");

							if (null != jarrTotal) {
								for (i = 0; i < jarrTotal.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjTot = (JSONObject) jarrTotal.get(i);
									objBillDtl.setDblAmount(Double.parseDouble(jObjTot.get("grandTotal").toString()));

									listOfGrandTotal.add(objBillDtl);

								}
							}

							JSONArray jarrHomeDel = (JSONArray) jObjtemp.get("listOfHomeDeliveryDtl");

							if (null != jarrHomeDel) {
								for (i = 0; i < jarrHomeDel.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjHomeDel = (JSONObject) jarrHomeDel.get(i);
									objBillDtl.setStrItemName("Name         : " + jObjHomeDel.get("NAME").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName("Address      : " + jObjHomeDel.get("Address").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName("MOBILE_NO  :" + jObjHomeDel.get("MOBILE_NO").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

								}
							}

							JSONArray jarrServiceTaxDtl = (JSONArray) jObjtemp.get("listOfServiceVatDetail");

							if (null != jarrServiceTaxDtl) {
								for (i = 0; i < jarrServiceTaxDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjServieTaxDtl = (JSONObject) jarrServiceTaxDtl.get(i);
									objBillDtl.setStrItemName("Service Tax No.    :" + jObjServieTaxDtl.get("Service Tax No.:").toString());
									listOfServiceVatDetail.add(objBillDtl);
								}
							}

							JSONArray jarrFooterDtl = (JSONArray) jObjtemp.get("listOfFooterDtl");

							if (null != jarrFooterDtl) {
								for (i = 0; i < jarrFooterDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjFooterDtl = (JSONObject) jarrFooterDtl.get(i);
									objBillDtl.setStrItemName(jObjFooterDtl.get("Thank").toString());
									listOfFooterDtl.add(objBillDtl);
								}
							}
							JSONArray jarrTaxDtl = (JSONArray) jObjtemp.get("listOfTaxDtl");

							if (null != jarrTaxDtl) {
								for (i = 0; i < jarrTaxDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjTax = (JSONObject) jarrTaxDtl.get(i);
									objBillDtl = new clsBillDtl();

									objBillDtl.setDblAmount(Double.parseDouble(jObjTax.get("taxAmount").toString()));
									objBillDtl.setStrItemName(jObjTax.get("taxDesc").toString());

									listOfTaxDtl.add(objBillDtl);
								}
							}

							JSONArray jarrDiscountDtl = (JSONArray) jObjtemp.get("listOfDiscountDtl");

							if (null != jarrDiscountDtl) {
								for (i = 0; i < jarrDiscountDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjDiscount = (JSONObject) jarrDiscountDtl.get(i);
									objBillDtl.setStrItemName(jObjDiscount.get("Discount").toString());
									objBillDtl.setStrItemName(jObjDiscount.get("discText").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjDiscount.get("discAmt").toString()));
									objBillDtl.setStrItemName(jObjDiscount.get("Reason").toString());
									objBillDtl.setStrItemName(jObjDiscount.get("Remark").toString());
									listOfDiscountDtl.add(objBillDtl);

								}
							}
							JSONArray jarrSettle = (JSONArray) jObjtemp.get("listOfSettlementDetail");

							if (null != jarrSettle) {
								for (i = 0; i < jarrSettle.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjSettle = (JSONObject) jarrSettle.get(i);
									objBillDtl.setStrItemName(jObjSettle.get("settleDesc").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjSettle.get("settleAmt").toString()));
									listOfSettlementDetail.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName(jObjSettle.get("PaidAmtTxt").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjSettle.get("paidAmt").toString()));
									listOfSettlementDetail.add(objBillDtl);

								}
							}

							JSONArray jarrCustomerDtl = (JSONArray) jObjtemp.get("listOfCustomerDtl");

							if (null != jarrCustomerDtl) {
								for (i = 0; i < jarrCustomerDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjDiscount = (JSONObject) jarrCustomerDtl.get(i);
									objBillDtl.setStrItemName(jObjDiscount.get("CUSTOMER NAME:").toString());
									listOfCustomerDtl.add(objBillDtl);
									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName(jObjDiscount.get("mobileNo").toString());
									listOfCustomerDtl.add(objBillDtl);

								}
							}

							posWiseHeading = jObjtemp.get("posWiseHeading").toString();
							duplicate = jObjtemp.get("duplicate").toString();
							posName = jObjtemp.get("POS").toString();
							BillType = jObjtemp.get("BillType").toString();
							ClientName = jObjtemp.get("ClientName").toString();
							ClientAddress1 = jObjtemp.get("ClientAddress1").toString();
							ClientAddress2 = jObjtemp.get("ClientAddress2").toString();
							ClientAddress3 = jObjtemp.get("ClientAddress3").toString();
							ClientCity = jObjtemp.get("ClientCity").toString();
							TEL_NO = jObjtemp.get("TEL NO").toString();
							EMAIL_ID = jObjtemp.get("EMAIL ID").toString();
							TAX_INVOICE = jObjtemp.get("TAX_INVOICE").toString();
							DATE_TIME = jObjtemp.get("DATE_TIME").toString();
							BillNo = jObjtemp.get("BillNo").toString();
							if (!flag_DirectBillerBlill) {
								tableName = jObjtemp.get("tableName").toString();
								waiterName = jObjtemp.get("waiterName").toString();
							}
						}
					}

					if (null != jarr1) {
						for (int i = 0; i < jarr1.size(); i++) {
							JSONObject jObjtemp1 = (JSONObject) jarr1.get(i);

							clsBillDtl objClsBillDtl = new clsBillDtl();

							objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("saleQty").toString()));
							objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
							objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("dblAmount").toString()));
							objClsBillDtl.setDblRate(Double.parseDouble(jObjtemp1.get("rate").toString()));
							objClsBillDtl.setDblDiscountAmt(Double.parseDouble(jObjtemp1.get("discountAmt").toString()));
							list.add(objClsBillDtl);
						}
					}

					hm.put("posWiseHeading", posWiseHeading);
					hm.put("duplicate", duplicate);
					if (!flag_DirectBillerBlill) {
						hm.put("TABLE NAME", tableName);
						hm.put("waiterName", waiterName);
					}
					hm.put("POS", posName);
					hm.put("ClientName", ClientName);
					hm.put("TAX_INVOICE", TAX_INVOICE);
					hm.put("ClientAddress1", ClientAddress1);
					hm.put("ClientAddress2", ClientAddress2);
					hm.put("ClientAddress3", ClientAddress3);
					hm.put("ClientCity", ClientCity);
					hm.put("TEL NO", TEL_NO);
					hm.put("EMAIL ID", EMAIL_ID);
					hm.put("BillNo", BillNo);
					hm.put("DATE_TIME", DATE_TIME);
					hm.put("BillType", BillType);
					hm.put("listOfItemDtl", list);
					hm.put("listOfTaxDtl", listOfTaxDtl);
					hm.put("listOfGrandTotalDtl", listOfGrandTotal);
					hm.put("listOfServiceVatDetail", listOfServiceVatDetail);
					hm.put("listOfFooterDtl", listOfFooterDtl);
					hm.put("listOfHomeDeliveryDtl", listOfHomeDeliveryDtl);
					hm.put("listOfDiscountDtl", listOfDiscountDtl);
					hm.put("listOfSettlementDetail", listOfSettlementDetail);
					hm.put("listOfCustomerDtl", listOfCustomerDtl);

					// hm.put("listOfItemDtl", list);

					listData.add(list);

					if (listOfHomeDeliveryDtl.size() > 0) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat6ForJasperReport.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else if (result == 1) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat6ForJasperReport.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat6ForJasperReport.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					}

				} else if (format.equalsIgnoreCase("Jasper3")) {
					long result = (long) jObj.get("result");
					// long listOfHDSize= (long) jObj.get("listOfHDSize");

					jarr1 = (JSONArray) jObj.get("listOfFoodBillDetail");

					// long lengthListOfHomeDeliveryDtl = (long)
					// jObj.get("lengthListOfHomeDeliveryDtl");

					String posWiseHeading = "", tableName = "", duplicate = "", BillType = "", ClientName = "", ClientAddress1 = "", ClientAddress2 = "", ClientAddress3 = "", ClientCity = "", TEL_NO = "";
					String EMAIL_ID = "", TAX_INVOICE = "", BillNo = "", Totl = "", name = "", address = "", mobile_no = "", footer = "";
					String taxAmount = "", taxDesc = "", discount = "", discText = "", discAmt = "", reason = "", remark = "";

					clsBillDtl objBillDtl = new clsBillDtl();
					jarr = (JSONArray) jObj.get("jArr");
					List<clsBillDtl> listOfGrandTotal = new ArrayList<>();
					List<clsBillDtl> listOfFoodBillDetail = new ArrayList<>();
					List<clsBillDtl> listOfLiqourBillDetail = new ArrayList<>();
					List<clsBillDtl> listOfHomeDeliveryDtl = new ArrayList<>();
					List<clsBillDtl> listOfServiceVatDetail = new ArrayList<>();
					List<clsBillDtl> listOfFooterDtl = new ArrayList<>();
					List<clsBillDtl> listOfTaxDtl = new ArrayList<>();
					List<clsBillDtl> listOfDiscountDtl = new ArrayList<>();
					List<clsBillDtl> listSubTotal = new ArrayList<>();
					List<clsBillDtl> listOfSettlementDetail = new ArrayList<>();

					if (null != jarr) {
						for (int i = 0; i < jarr.size(); i++) {
							JSONObject jObjtemp = (JSONObject) jarr.get(i);

							JSONArray jarrTotal = (JSONArray) jObjtemp.get("listOfGrandTotalDtl");

							if (null != jarrTotal) {
								for (i = 0; i < jarrTotal.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjTot = (JSONObject) jarrTotal.get(i);
									objBillDtl.setDblAmount(Double.parseDouble(jObjTot.get("grandTotal").toString()));

									listOfGrandTotal.add(objBillDtl);

								}
							}

							JSONArray jarrLiqur = (JSONArray) jObjtemp.get("listOfLiqourBillDetail");
							if (null != jarrLiqur) {
								for (i = 0; i < jarrLiqur.size(); i++) {
									JSONObject jObjtemp1 = (JSONObject) jarrLiqur.get(i);

									clsBillDtl objClsBillDtl = new clsBillDtl();

									objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("saleQty").toString()));
									objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
									objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("dblAmount").toString()));
									listOfLiqourBillDetail.add(objClsBillDtl);
								}
							}

							JSONArray jarrHomeDel = (JSONArray) jObjtemp.get("listOfHomeDeliveryDtl");

							if (null != jarrHomeDel) {
								for (i = 0; i < jarrHomeDel.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjHomeDel = (JSONObject) jarrHomeDel.get(i);
									objBillDtl.setStrItemName("Name         : " + jObjHomeDel.get("NAME").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName("Address      : " + jObjHomeDel.get("Address").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

									objBillDtl = new clsBillDtl();
									objBillDtl.setStrItemName("MOBILE_NO  :" + jObjHomeDel.get("MOBILE_NO").toString());
									listOfHomeDeliveryDtl.add(objBillDtl);

								}
							}

							JSONArray jarrServiceTaxDtl = (JSONArray) jObjtemp.get("listOfServiceVatDetail");

							if (null != jarrServiceTaxDtl) {
								for (i = 0; i < jarrServiceTaxDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjServieTaxDtl = (JSONObject) jarrServiceTaxDtl.get(i);
									objBillDtl.setStrItemName("Service Tax No.    :" + jObjServieTaxDtl.get("Service Tax No.:").toString());
									listOfServiceVatDetail.add(objBillDtl);
								}
							}

							JSONArray jarrFooterDtl = (JSONArray) jObjtemp.get("listOfFooterDtl");

							if (null != jarrFooterDtl) {
								for (i = 0; i < jarrFooterDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjFooterDtl = (JSONObject) jarrFooterDtl.get(i);
									objBillDtl.setStrItemName(jObjFooterDtl.get("Thank").toString());
									listOfFooterDtl.add(objBillDtl);
								}
							}
							JSONArray jarrTaxDtl = (JSONArray) jObjtemp.get("listOfTaxDtl");

							if (null != jarrTaxDtl) {
								for (i = 0; i < jarrTaxDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjTax = (JSONObject) jarrTaxDtl.get(i);
									objBillDtl = new clsBillDtl();

									objBillDtl.setDblAmount(Double.parseDouble(jObjTax.get("taxAmount").toString()));
									objBillDtl.setStrItemName(jObjTax.get("taxDesc").toString());

									listOfTaxDtl.add(objBillDtl);
								}
							}

							JSONArray jarrDiscountDtl = (JSONArray) jObjtemp.get("listOfDiscountDtl");

							if (null != jarrDiscountDtl) {
								for (i = 0; i < jarrDiscountDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjDiscount = (JSONObject) jarrDiscountDtl.get(i);
									objBillDtl.setStrItemName(jObjDiscount.get("Discount").toString());
									objBillDtl.setStrItemName(jObjDiscount.get("discText").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjDiscount.get("discAmt").toString()));
									objBillDtl.setStrItemName(jObjDiscount.get("Reason").toString());
									objBillDtl.setStrItemName(jObjDiscount.get("Remark").toString());
									listOfDiscountDtl.add(objBillDtl);

								}
							}
							JSONArray jarrSettlementDtl = (JSONArray) jObjtemp.get("listOfSettlementDetail");

							if (null != jarrSettlementDtl) {
								for (i = 0; i < jarrSettlementDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjSettlementDtl = (JSONObject) jarrSettlementDtl.get(i);
									objBillDtl.setStrItemName(jObjSettlementDtl.get("settleDesc").toString());
									objBillDtl.setDblAmount(Double.parseDouble(jObjSettlementDtl.get("settleAmt").toString()));
									listOfSettlementDetail.add(objBillDtl);

								}
							}

							JSONArray jarrSubTotalDtl = (JSONArray) jObjtemp.get("listSubTotal");

							if (null != jarrSubTotalDtl) {
								for (i = 0; i < jarrSubTotalDtl.size(); i++) {
									objBillDtl = new clsBillDtl();
									JSONObject jObjSubTotal = (JSONObject) jarrSubTotalDtl.get(i);
									objBillDtl.setDblAmount(Double.parseDouble(jObjSubTotal.get("subTotal").toString()));
									listSubTotal.add(objBillDtl);
								}
							}
							//

							posWiseHeading = jObjtemp.get("posWiseHeading").toString();
							duplicate = jObjtemp.get("duplicate").toString();
							posName = jObjtemp.get("POS").toString();
							BillType = jObjtemp.get("BillType").toString();
							ClientName = jObjtemp.get("ClientName").toString();
							ClientAddress1 = jObjtemp.get("ClientAddress1").toString();
							ClientAddress2 = jObjtemp.get("ClientAddress2").toString();
							ClientAddress3 = jObjtemp.get("ClientAddress3").toString();
							ClientCity = jObjtemp.get("ClientCity").toString();
							TEL_NO = jObjtemp.get("TEL NO").toString();
							EMAIL_ID = jObjtemp.get("EMAIL ID").toString();
							TAX_INVOICE = jObjtemp.get("TAX_INVOICE").toString();
							DATE_TIME = jObjtemp.get("DATE_TIME").toString();
							BillNo = jObjtemp.get("BillNo").toString();
							if (!flag_DirectBillerBlill) {
								tableName = jObjtemp.get("TABLE NAME").toString();
								waiterName = jObjtemp.get("waiterName").toString();
							}

						}
					}

					if (null != jarr1) {
						for (int i = 0; i < jarr1.size(); i++) {
							JSONObject jObjtemp1 = (JSONObject) jarr1.get(i);

							clsBillDtl objClsBillDtl = new clsBillDtl();

							objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("saleQty").toString()));
							objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
							objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("dblAmount").toString()));
							list.add(objClsBillDtl);
						}
					}

					hm.put("posWiseHeading", posWiseHeading);
					hm.put("duplicate", duplicate);
					hm.put("POS", posName);
					hm.put("ClientName", ClientName);
					if (!flag_DirectBillerBlill) {
						hm.put("TABLE NAME", tableName);
						hm.put("waiterName", waiterName);
					}

					hm.put("TAX_INVOICE", TAX_INVOICE);
					hm.put("ClientAddress1", ClientAddress1);
					hm.put("ClientAddress2", ClientAddress2);
					hm.put("ClientAddress3", ClientAddress3);
					hm.put("ClientCity", ClientCity);
					hm.put("TEL NO", TEL_NO);
					hm.put("EMAIL ID", EMAIL_ID);
					hm.put("BillNo", BillNo);
					hm.put("DATE_TIME", DATE_TIME);
					hm.put("BillType", BillType);
					hm.put("listOfFoodBillDetail", list);
					hm.put("listOfLiqourBillDetail", listOfLiqourBillDetail);
					hm.put("listOfTaxDtl", listOfTaxDtl);
					hm.put("listOfGrandTotalDtl", listOfGrandTotal);
					hm.put("listOfServiceVatDetail", listOfServiceVatDetail);
					hm.put("listOfFooterDtl", listOfFooterDtl);
					hm.put("listOfHomeDeliveryDtl", listOfHomeDeliveryDtl);
					hm.put("listOfDiscountDtl", listOfDiscountDtl);
					hm.put("listOfSettlementDetail", listOfSettlementDetail);
					hm.put("listSubToal", listSubTotal);

					// hm.put("listOfItemDtl", list);

					listData.add(list);

					if (listOfHomeDeliveryDtl.size() > 0) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat10ForHDJasperBillPrint.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else if (result == 1) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat10ForHDJasperBillPrint.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat10ForHDJasperBillPrint.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					}

				}

			} else if (kotFor.equalsIgnoreCase("Bill")) {
				long result = (long) jObj.get("result");
				// long listOfHDSize= (long) jObj.get("listOfHDSize");
				format = (String) jObj.get("format");

				jarr1 = (JSONArray) jObj.get("listOfBillDetail");

				JSONArray jArrItemDtl = new JSONArray();

				// long lengthListOfHomeDeliveryDtl = (long)
				// jObj.get("lengthListOfHomeDeliveryDtl");

				String posWiseHeading = "", duplicate = "", BillType = "", ClientName = "", ClientAddress1 = "", ClientAddress2 = "", ClientAddress3 = "", ClientCity = "", TEL_NO = "";
				String EMAIL_ID = "", TAX_INVOICE = "", BillNo = "", Totl = "", name = "", address = "", mobile_no = "", footer = "";
				String taxAmount = "", taxDesc = "", discount = "", discText = "", discAmt = "", reason = "", remark = "";

				clsBillDtl objBillDtl = new clsBillDtl();
				jarr = (JSONArray) jObj.get("jArr");
				List<clsBillDtl> listOfGrandTotal = new ArrayList<>();
				List<clsBillDtl> listOfFoodBillDetail = new ArrayList<>();
				List<clsBillDtl> listOfLiqourBillDetail = new ArrayList<>();
				List<clsBillDtl> listOfHomeDeliveryDtl = new ArrayList<>();
				List<clsBillDtl> listOfServiceVatDetail = new ArrayList<>();
				List<clsBillDtl> listOfFooterDtl = new ArrayList<>();
				List<clsBillDtl> listOfTaxDtl = new ArrayList<>();
				List<clsBillDtl> listOfDiscountDtl = new ArrayList<>();
				List<clsBillDtl> listSubTotal = new ArrayList<>();
				List<clsBillDtl> listOfSettlementDetail = new ArrayList<>();

				if (null != jarr) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						JSONArray jarrTotal = (JSONArray) jObjtemp.get("listOfGrandTotalDtl");

						if (null != jarrTotal) {
							for (i = 0; i < jarrTotal.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjTot = (JSONObject) jarrTotal.get(i);
								objBillDtl.setDblAmount(Double.parseDouble(jObjTot.get("grandTotal").toString()));

								listOfGrandTotal.add(objBillDtl);

							}
						}

						JSONArray jarrHomeDel = (JSONArray) jObjtemp.get("listOfHomeDeliveryDtl");

						if (null != jarrHomeDel) {
							for (i = 0; i < jarrHomeDel.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjHomeDel = (JSONObject) jarrHomeDel.get(i);
								objBillDtl.setStrItemName("Name:" + jObjHomeDel.get("NAME").toString());
								listOfHomeDeliveryDtl.add(objBillDtl);

								objBillDtl = new clsBillDtl();
								objBillDtl.setStrItemName("Address:" + jObjHomeDel.get("Address").toString());
								listOfHomeDeliveryDtl.add(objBillDtl);

								objBillDtl = new clsBillDtl();
								objBillDtl.setStrItemName("Mobile No" + jObjHomeDel.get("MOBILE_NO").toString());
								listOfHomeDeliveryDtl.add(objBillDtl);

							}
						}

						JSONArray jarrServiceTaxDtl = (JSONArray) jObjtemp.get("listOfServiceVatDetail");

						if (null != jarrServiceTaxDtl) {
							for (i = 0; i < jarrServiceTaxDtl.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjServieTaxDtl = (JSONObject) jarrServiceTaxDtl.get(i);
								objBillDtl.setStrItemName("Service Tax No.    :" + jObjServieTaxDtl.get("Service Tax No.:").toString());
								listOfServiceVatDetail.add(objBillDtl);
							}
						}

						JSONArray jarrFooterDtl = (JSONArray) jObjtemp.get("listOfFooterDtl");

						if (null != jarrFooterDtl) {
							for (i = 0; i < jarrFooterDtl.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjFooterDtl = (JSONObject) jarrFooterDtl.get(i);
								objBillDtl.setStrItemName(jObjFooterDtl.get("Thank").toString());
								listOfFooterDtl.add(objBillDtl);
							}
						}
						JSONArray jarrTaxDtl = (JSONArray) jObjtemp.get("listOfTaxDtl");

						if (null != jarrTaxDtl) {
							for (i = 0; i < jarrTaxDtl.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjTax = (JSONObject) jarrTaxDtl.get(i);
								objBillDtl = new clsBillDtl();

								objBillDtl.setDblAmount(Double.parseDouble(jObjTax.get("taxAmount").toString()));
								objBillDtl.setStrItemName(jObjTax.get("taxDesc").toString());

								listOfTaxDtl.add(objBillDtl);
							}
						}

						JSONArray jarrDiscountDtl = (JSONArray) jObjtemp.get("listOfDiscountDtl");

						if (null != jarrDiscountDtl) {
							for (i = 0; i < jarrDiscountDtl.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjDiscount = (JSONObject) jarrDiscountDtl.get(i);
								objBillDtl.setStrItemName(jObjDiscount.get("Discount").toString());
								objBillDtl.setStrItemName(jObjDiscount.get("discText").toString());
								objBillDtl.setDblAmount(Double.parseDouble(jObjDiscount.get("discAmt").toString()));
								objBillDtl.setStrItemName(jObjDiscount.get("Reason").toString());
								objBillDtl.setStrItemName(jObjDiscount.get("Remark").toString());
								listOfDiscountDtl.add(objBillDtl);

							}
						}
						JSONArray jarrSettlementDtl = (JSONArray) jObjtemp.get("listOfSettlementDetail");

						if (null != jarrSettlementDtl) {
							for (i = 0; i < jarrSettlementDtl.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjSettlementDtl = (JSONObject) jarrSettlementDtl.get(i);
								objBillDtl.setStrItemName(jObjSettlementDtl.get("settleDesc").toString());
								objBillDtl.setDblAmount(Double.parseDouble(jObjSettlementDtl.get("settleAmt").toString()));
								listOfSettlementDetail.add(objBillDtl);

							}
						}

						JSONArray jarrLiqur = (JSONArray) jObjtemp.get("listOfLiqourBillDetail");
						if (null != jarrLiqur) {
							for (i = 0; i < jarrLiqur.size(); i++) {
								JSONObject jObjtemp1 = (JSONObject) jarrLiqur.get(i);

								clsBillDtl objClsBillDtl = new clsBillDtl();

								objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("saleQty").toString()));
								objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
								objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("dblAmount").toString()));
								listOfLiqourBillDetail.add(objClsBillDtl);
							}
						}

						JSONArray jarrSubTotalDtl = (JSONArray) jObjtemp.get("listSubTotal");

						if (null != jarrSubTotalDtl) {
							for (i = 0; i < jarrSubTotalDtl.size(); i++) {
								objBillDtl = new clsBillDtl();
								JSONObject jObjSubTotal = (JSONObject) jarrSubTotalDtl.get(i);
								objBillDtl.setDblAmount(Double.parseDouble(jObjSubTotal.get("subTotal").toString()));
								listSubTotal.add(objBillDtl);
							}
						}
						//

						posWiseHeading = jObjtemp.get("posWiseHeading").toString();
						duplicate = jObjtemp.get("duplicate").toString();
						posName = jObjtemp.get("POS").toString();
						BillType = jObjtemp.get("BillType").toString();
						ClientName = jObjtemp.get("ClientName").toString();
						ClientAddress1 = jObjtemp.get("ClientAddress1").toString();
						ClientAddress2 = jObjtemp.get("ClientAddress2").toString();
						ClientAddress3 = jObjtemp.get("ClientAddress3").toString();
						ClientCity = jObjtemp.get("ClientCity").toString();
						TEL_NO = jObjtemp.get("TEL NO").toString();
						EMAIL_ID = jObjtemp.get("EMAIL ID").toString();
						TAX_INVOICE = jObjtemp.get("TAX_INVOICE").toString();
						DATE_TIME = jObjtemp.get("DATE_TIME").toString();
						BillNo = jObjtemp.get("BillNo").toString();

					}
				}

				if (format.equalsIgnoreCase("Jasper1")) {
					if (null != jarr1) {
						for (int i = 0; i < jarr1.size(); i++) {
							JSONObject jObjtemp1 = (JSONObject) jarr1.get(i);

							clsBillDtl objClsBillDtl = new clsBillDtl();

							objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("qty").toString()));
							objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
							objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("amount").toString()));
							list.add(objClsBillDtl);
						}
					}

					hm.put("posWiseHeading", posWiseHeading);
					hm.put("duplicate", duplicate);
					hm.put("POS", posName);
					hm.put("ClientName", ClientName);
					hm.put("TAX_INVOICE", TAX_INVOICE);
					hm.put("ClientAddress1", ClientAddress1);
					hm.put("ClientAddress2", ClientAddress2);
					hm.put("ClientAddress3", ClientAddress3);
					hm.put("ClientCity", ClientCity);
					hm.put("TEL NO", TEL_NO);
					hm.put("EMAIL ID", EMAIL_ID);
					hm.put("BillNo", BillNo);
					hm.put("DATE_TIME", DATE_TIME);
					hm.put("BillType", BillType);
					hm.put("listOfItemDtl", list);
					hm.put("listOfTaxDtl", listOfTaxDtl);
					hm.put("listOfGrandTotalDtl", listOfGrandTotal);
					hm.put("listOfServiceVatDetail", listOfServiceVatDetail);
					hm.put("listOfFooterDtl", listOfFooterDtl);
					hm.put("listOfHomeDeliveryDtl", listOfHomeDeliveryDtl);
					hm.put("listOfDiscountDtl", listOfDiscountDtl);
					hm.put("listOfSettlementDetail", listOfSettlementDetail);

					// hm.put("listOfItemDtl", list);

					listData.add(list);

					if (listOfHomeDeliveryDtl.size() > 0) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat5JasperReportHD.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					} else if (result == 1) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat5JasperReportNormalBill.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					} else {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat5JasperReportNormalBill.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
					}
				} else if (format.equalsIgnoreCase("Jasper2")) {
					jArrItemDtl = (JSONArray) jObj.get("listOfBillDetail");

					if (null != jArrItemDtl) {
						for (int i = 0; i < jArrItemDtl.size(); i++) {
							JSONObject jObjtemp1 = (JSONObject) jArrItemDtl.get(i);

							clsBillDtl objClsBillDtl = new clsBillDtl();

							objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("saleQty").toString()));
							objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
							objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("dblAmount").toString()));
							objClsBillDtl.setDblRate(Double.parseDouble(jObjtemp1.get("rate").toString()));
							objClsBillDtl.setDblDiscountAmt(Double.parseDouble(jObjtemp1.get("discountAmt").toString()));
							list.add(objClsBillDtl);
						}
					}

					hm.put("posWiseHeading", posWiseHeading);
					hm.put("duplicate", duplicate);
					// if(!flag_DirectBillerBlill)
					// {
					// hm.put("TABLE NAME",tableName);
					hm.put("waiterName", waiterName);
					// }
					hm.put("POS", posName);
					hm.put("ClientName", ClientName);
					hm.put("TAX_INVOICE", TAX_INVOICE);
					hm.put("ClientAddress1", ClientAddress1);
					hm.put("ClientAddress2", ClientAddress2);
					hm.put("ClientAddress3", ClientAddress3);
					hm.put("ClientCity", ClientCity);
					hm.put("TEL NO", TEL_NO);
					hm.put("EMAIL ID", EMAIL_ID);
					hm.put("BillNo", BillNo);
					hm.put("DATE_TIME", DATE_TIME);
					hm.put("BillType", BillType);
					hm.put("listOfItemDtl", list);
					hm.put("listOfTaxDtl", listOfTaxDtl);
					hm.put("listOfGrandTotalDtl", listOfGrandTotal);
					hm.put("listOfServiceVatDetail", listOfServiceVatDetail);
					hm.put("listOfFooterDtl", listOfFooterDtl);
					hm.put("listOfHomeDeliveryDtl", listOfHomeDeliveryDtl);
					hm.put("listOfDiscountDtl", listOfDiscountDtl);
					hm.put("listOfSettlementDetail", listOfSettlementDetail);
					// hm.put("listOfCustomerDtl", listOfCustomerDtl);

					// hm.put("listOfItemDtl", list);

					listData.add(list);

					if (listOfHomeDeliveryDtl.size() > 0) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat6ForJasperReport.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else if (result == 1) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat6ForJasperReport.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat6ForJasperReport.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					}

				} else if (format.equalsIgnoreCase("Jasper3")) {
					jArrBillDtl = (JSONArray) jObj.get("listOfFoodBillDetail");
					if (null != jArrBillDtl) {
						for (int i = 0; i < jArrBillDtl.size(); i++) {
							JSONObject jObjtemp1 = (JSONObject) jArrBillDtl.get(i);

							clsBillDtl objClsBillDtl = new clsBillDtl();

							objClsBillDtl.setDblQuantity(Double.parseDouble(jObjtemp1.get("saleQty").toString()));
							objClsBillDtl.setStrItemName(jObjtemp1.get("itemName").toString());
							objClsBillDtl.setDblAmount(Double.parseDouble(jObjtemp1.get("dblAmount").toString()));
							list.add(objClsBillDtl);
						}
					}

					hm.put("posWiseHeading", posWiseHeading);
					hm.put("duplicate", duplicate);
					hm.put("POS", posName);
					hm.put("ClientName", ClientName);
					hm.put("TAX_INVOICE", TAX_INVOICE);
					hm.put("ClientAddress1", ClientAddress1);
					hm.put("ClientAddress2", ClientAddress2);
					hm.put("ClientAddress3", ClientAddress3);
					hm.put("ClientCity", ClientCity);
					hm.put("TEL NO", TEL_NO);
					hm.put("EMAIL ID", EMAIL_ID);
					hm.put("BillNo", BillNo);
					hm.put("DATE_TIME", DATE_TIME);
					hm.put("BillType", BillType);
					hm.put("listOfFoodBillDetail", list);
					hm.put("listOfLiqourBillDetail", listOfLiqourBillDetail);
					hm.put("listOfTaxDtl", listOfTaxDtl);
					hm.put("listOfGrandTotalDtl", listOfGrandTotal);
					hm.put("listOfServiceVatDetail", listOfServiceVatDetail);
					hm.put("listOfFooterDtl", listOfFooterDtl);
					hm.put("listOfHomeDeliveryDtl", listOfHomeDeliveryDtl);
					hm.put("listOfDiscountDtl", listOfDiscountDtl);
					hm.put("listOfSettlementDetail", listOfSettlementDetail);
					hm.put("listSubToal", listSubTotal);

					// hm.put("listOfItemDtl", list);

					listData.add(list);

					if (listOfHomeDeliveryDtl.size() > 0) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat10ForHDJasperBillPrint.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else if (result == 1) {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat10ForHDJasperBillPrint.jrxml.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";
					} else {
						reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillFormat10ForHDJasperBillPrint.jrxml");
						imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
						// reportName =
						// "com/POSGlobal/reports/rptBillFormat6ForJasperReport.jrxml";

					}

				}

			} else {
				jarr = (JSONArray) jObj.get("jArr");

				String reportHeading = "", duplicate = "", discount = "", posCode1 = "", POSName = "", POSDt = "", totalSales = "", floatval = "";
				String cash = "", advance = "", transferIn = "", totalReceipt = "", payment = "", withdrawal = "", transferOut = "";
				String totalPayments = "", cashInHand = "", homeDelivery = "", dining = "", takeAway = "", noOfBills = "";
				String noOfVoidedBills = "", noOfModifiedBills = "", refund = "", noOfPax = "", noOfTakeAway = "";
				String noOfHomeDel = "", dayEndBy = "", noOfNcKot = "", noOfComplimentaryBills = "", noOfVoidKot = "", Total = "";
				String groupName = "", settlementDesc = "";
				double taxableAmount = 0.00, dblNetTotalPlusTax = 0.00;
				double settlementAmt = 0.00;

				JSONArray listGroupAmtWithTaxDtl = new JSONArray();
				JSONArray listSettelementBrkUP = new JSONArray();
				JSONArray listSettelementTaxDtl = new JSONArray();
				if (null != jarr) {
					for (int i = 0; i < jarr.size(); i++) {
						JSONObject jObjtemp = (JSONObject) jarr.get(i);

						JSONArray jarrGroupAmtTax = (JSONArray) jObjtemp.get("listGroupAmtWithTaxDtl");

						if (null != jarrGroupAmtTax) {
							for (i = 0; i < jarrGroupAmtTax.size(); i++) {
								JSONObject jObjRet = new JSONObject();
								JSONObject jObjTot = (JSONObject) jarrGroupAmtTax.get(i);
								//
								// groupName
								// =jObjTot.get("groupName").toString();
								// dblNetTotalPlusTax=Double.parseDouble(jObjTot.get("dblNetTotalPlusTax").toString());
								//
								clsPOSReprintDocumentsBean objGroupDtl = new clsPOSReprintDocumentsBean();
								objGroupDtl.setStrgroupName(jObjTot.get("groupName").toString());
								objGroupDtl.setDblNetTotalPlusTax(Double.parseDouble(jObjTot.get("dblNetTotalPlusTax").toString()));

								listGroupAmtWithTaxDtl.add(objGroupDtl);

							}
						}

						JSONArray jarrSettlementBrkUp = (JSONArray) jObjtemp.get("listSettelementBrkUP");

						if (null != jarrSettlementBrkUp) {
							for (i = 0; i < jarrSettlementBrkUp.size(); i++) {
								JSONObject jObjRet = new JSONObject();
								JSONObject jObjSettleBrkUp = (JSONObject) jarrSettlementBrkUp.get(i);
								// settlementDesc
								// =jObjSettleBrkUp.get("settlementDesc").toString();
								// settlementAmt=Double.parseDouble(jObjSettleBrkUp.get("settlementAmt").toString());

								clsPOSReprintDocumentsBean objBillSettleBrkUp = new clsPOSReprintDocumentsBean();
								objBillSettleBrkUp.setStrSettleDesc(jObjSettleBrkUp.get("settlementDesc").toString());
								objBillSettleBrkUp.setDblSettleAmt(Double.parseDouble(jObjSettleBrkUp.get("settlementAmt").toString()));

								listSettelementBrkUP.add(objBillSettleBrkUp);

							}
						}

						JSONArray jarrSettleTaxDtl = (JSONArray) jObjtemp.get("listSettelementTaxDtl");

						if (null != jarrSettleTaxDtl) {
							for (i = 0; i < jarrSettleTaxDtl.size(); i++) {
								JSONObject jObjRet = new JSONObject();
								JSONObject jObjSettleTaxDtl = (JSONObject) jarrSettleTaxDtl.get(i);
								// taxDesc
								// =jObjSettleTaxDtl.get("taxDesc").toString();
								// taxableAmount=Double.parseDouble(jObjSettleTaxDtl.get("taxableAmount").toString());
								// taxAmount=Double.parseDouble(jObjSettleTaxDtl.get("taxAmount").toString());
								//
								clsPOSReprintDocumentsBean objSettleTaxDtl = new clsPOSReprintDocumentsBean();
								objSettleTaxDtl.setStrTaxDesc(jObjSettleTaxDtl.get("taxDesc").toString());
								objSettleTaxDtl.setDblTaxableAmount(Double.parseDouble(jObjSettleTaxDtl.get("taxableAmount").toString()));
								objSettleTaxDtl.setDblTaxAmount(Double.parseDouble(jObjSettleTaxDtl.get("taxAmount").toString()));

								listSettelementTaxDtl.add(objSettleTaxDtl);
							}
						}

						reportHeading = jObjtemp.get("reportHeading").toString();
						duplicate = jObjtemp.get("duplicate").toString();
						posCode = jObjtemp.get("posCode1").toString();
						posName = jObjtemp.get("posName").toString();
						posDate = jObjtemp.get("posDate").toString();
						totalSales = jObjtemp.get("totalSales").toString();
						floatval = jObjtemp.get("floatval").toString();
						cash = jObjtemp.get("cash").toString();
						advance = jObjtemp.get("advance").toString();
						transferIn = jObjtemp.get("transferIn").toString();
						totalReceipt = jObjtemp.get("totalReceipt").toString();
						payment = jObjtemp.get("payment").toString();
						withdrawal = jObjtemp.get("withdrawal").toString();
						transferOut = jObjtemp.get("transferOut").toString();
						totalPayments = jObjtemp.get("totalPayments").toString();
						cashInHand = jObjtemp.get("cashInHand").toString();
						homeDelivery = jObjtemp.get("homeDelivery").toString();
						dining = jObjtemp.get("dining").toString();
						takeAway = jObjtemp.get("takeAway").toString();
						noOfBills = jObjtemp.get("noOfBills").toString();
						noOfVoidedBills = jObjtemp.get("noOfVoidedBills").toString();
						noOfModifiedBills = jObjtemp.get("noOfModifiedBills").toString();
						refund = jObjtemp.get("refund").toString();
						discount = jObjtemp.get("discount").toString();
						noOfPax = jObjtemp.get("noOfPax").toString();
						noOfTakeAway = jObjtemp.get("noOfTakeAway").toString();
						noOfHomeDel = jObjtemp.get("noOfHomeDel").toString();
						dayEndBy = jObjtemp.get("dayEndBy").toString();
						noOfNcKot = jObjtemp.get("noOfNcKot").toString();
						noOfComplimentaryBills = jObjtemp.get("noOfComplimentaryBills").toString();
						noOfVoidKot = jObjtemp.get("noOfVoidKot").toString();
						Total = jObjtemp.get("Total").toString();

					}
				}

				hm.put("reportHeading", reportHeading);
				hm.put("duplicate", duplicate);
				hm.put("posName", posName);
				hm.put("posCode", posCode);
				hm.put("posDate", posDate);
				hm.put("totalSales", totalSales);
				hm.put("floatval", floatval);
				hm.put("cash", cash);
				hm.put("advance", advance);
				hm.put("transferIn", transferIn);
				hm.put("totalReceipt", totalReceipt);
				hm.put("payment", payment);
				hm.put("withdrawal", withdrawal);
				hm.put("transferOut", transferOut);
				hm.put("totalPayments", totalPayments);
				hm.put("cashInHand", cashInHand);
				hm.put("homeDelivery", homeDelivery);
				hm.put("dining", dining);
				hm.put("takeAway", takeAway);
				hm.put("noOfBills", noOfBills);
				hm.put("noOfVoidedBills", noOfVoidedBills);
				hm.put("noOfModifiedBills", noOfModifiedBills);
				hm.put("refund", refund);
				hm.put("discount", discount);
				hm.put("noOfPax", noOfPax);
				hm.put("noOfTakeAway", noOfTakeAway);
				hm.put("noOfHomeDel", noOfHomeDel);
				hm.put("dayEndBy", dayEndBy);
				hm.put("noOfNcKot", noOfNcKot);
				hm.put("noOfComplimentaryBills", noOfComplimentaryBills);
				hm.put("noOfVoidKot", noOfVoidKot);
				hm.put("Total", Total);
				hm.put("listGroupAmtWithTaxDtl", listGroupAmtWithTaxDtl);
				hm.put("listSettelementTaxDtl", listSettelementTaxDtl);
				hm.put("listSettelementBrkUP", listSettelementBrkUP);

				listData.add(listGroupAmtWithTaxDtl);

				reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptDayEndReprintDocsJasperReport.jrxml");
				imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			}

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			// jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listData, false);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (strDocType.equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=GenrateKOTJasperReport_" + webStockUserCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=GenrateKOTJasperReport_" + webStockUserCode + ".xls");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Hi");

	}
}
