package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsSubContractorGRNBean;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSubContractorGRNModelDtl;
import com.sanguine.crm.model.clsSubContractorGRNModelHd;
import com.sanguine.crm.model.clsSubContractorGRNModelHd_ID;
import com.sanguine.crm.service.clsSubContractorGRNService;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsSubContractorGRNController {

	@Autowired
	private clsSubContractorGRNService objSubContractorGRNService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;

	// Open SubContractorGRN

	@RequestMapping(value = "/frmSubContractorGRN", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		List<String> strAgainst = new ArrayList<>();
		strAgainst.add("Direct");
		strAgainst.add("Delivery Note");
		strAgainst.add("Pening Stock");
		model.put("againstList", strAgainst);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubContractorGRN_1", "command", new clsSubContractorGRNModelHd());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubContractorGRN", "command", new clsSubContractorGRNModelHd());
		} else {
			return null;
		}

	}

	// Load Dtl Table Data On Form
	@RequestMapping(value = "/loadSubContractorGRNData", method = RequestMethod.GET)
	public @ResponseBody clsSubContractorGRNBean funAssignFields(@RequestParam("docCode") String docCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String userCode=req.getSession().getAttribute("usercode").toString();
		clsSubContractorGRNBean objBean = new clsSubContractorGRNBean();
		List<Object> objSCGRNList = objSubContractorGRNService.funLoadSubContractorGRNHDData(docCode, clientCode);
		// clsSubContractorGRNModelDtl objSubContractorGRNDtl = new
		// clsSubContractorGRNModelDtl();

		clsSubContractorGRNModelHd objSCGRNHDModle = null;
		clsPartyMasterModel objPartyMasterModel = null;
		clsLocationMasterModel objLocationMasterModel = null;

		for (int i = 0; i < objSCGRNList.size(); i++) {
			Object[] ob = (Object[]) objSCGRNList.get(i);
			objSCGRNHDModle = (clsSubContractorGRNModelHd) ob[0];
			objPartyMasterModel = (clsPartyMasterModel) ob[1];
			objLocationMasterModel = (clsLocationMasterModel) ob[2];

		}

		objBean = funPrepardHdBean(objSCGRNHDModle, objPartyMasterModel, objLocationMasterModel);
		objBean.setStrSuppName(objPartyMasterModel.getStrPName());
		objBean.setStrLocName(objLocationMasterModel.getStrLocName());
		List<clsSubContractorGRNModelDtl> listSCGRNDtl = new ArrayList<clsSubContractorGRNModelDtl>();
		List<Object> objSCGRNDtlList = objSubContractorGRNService.funLoadSubContractorGRNDtlData(docCode, clientCode);
		for (int i = 0; i < objSCGRNDtlList.size(); i++) {
			Object[] ob = (Object[]) objSCGRNDtlList.get(i);
			clsSubContractorGRNModelDtl scGRNDtl = (clsSubContractorGRNModelDtl) ob[0];
			clsProductMasterModel prodmast = (clsProductMasterModel) ob[1];
			scGRNDtl.setStrProdName(prodmast.getStrProdName());
			scGRNDtl.setDblQty(scGRNDtl.getDblQtyRbl());
			listSCGRNDtl.add(scGRNDtl);
		}
		objBean.setListSCGRNDtl(listSCGRNDtl);
		return objBean;
	}

	// Save or Update SubContractorGRN
	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveSubContractorGRN", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSubContractorGRNBean objBean, BindingResult result, HttpServletRequest req) {
		boolean flgHdSave = false;
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsSubContractorGRNModelHd objHdModel = funPrepareHdModel(objBean, userCode, clientCode, req);
			flgHdSave = objSubContractorGRNService.funAddUpdateSubContractorGRNHd(objHdModel);
			String strSRCode = objHdModel.getStrSRCode();
			List<clsSubContractorGRNModelDtl> listSCGRNDtlModel = objBean.getListSCGRNDtl();
			if (flgHdSave) {
				if (null != listSCGRNDtlModel) {
					objSubContractorGRNService.funDeleteDtl(strSRCode, clientCode);
					int intindex = 1;

					for (clsSubContractorGRNModelDtl obSCGRNDtl : listSCGRNDtlModel) {
						if (null != obSCGRNDtl.getStrProdCode()) {
							obSCGRNDtl.setIntIndex(intindex);
							obSCGRNDtl.setStrSRCode(strSRCode);
							obSCGRNDtl.setStrClientCode(clientCode);
							obSCGRNDtl.setDblQty(obSCGRNDtl.getDblQtyRbl());
							obSCGRNDtl.setStrDNCode("");
							obSCGRNDtl.setStrDNProcess("");
							obSCGRNDtl.setStrDNProdChar("");
							obSCGRNDtl.setStrDNProdCode("");
							obSCGRNDtl.setStrDNProdName("");
							obSCGRNDtl.setStrProdChar("");
							objSubContractorGRNService.funAddUpdateSubContractorGRNDtl(obSCGRNDtl);
							intindex++;
						}
					}
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "SR Code : ".concat(objHdModel.getStrSRCode()));
					req.getSession().setAttribute("rptSRCode", objHdModel.getStrSRCode());
				}
			}

			return new ModelAndView("redirect:/frmSubContractorGRN.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmSubContractorGRN?saddr=" + urlHits, "command", new clsSubContractorGRNBean());
		}
	}

	// Convert bean to model function
	private clsSubContractorGRNModelHd funPrepareHdModel(clsSubContractorGRNBean objBean, String userCode, String clientCode, HttpServletRequest req) {

		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		clsSubContractorGRNModelHd objModel;
		objGlobal = new clsGlobalFunctions();
		if (objBean.getStrSRCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblscreturnhd", "SRCode", "intId", clientCode);

			String year = objGlobal.funGetSplitedDate(startDate)[2];
			String cd = objGlobal.funGetTransactionCode("SR", propCode, year);
			String strSRCode = cd + String.format("%06d", lastNo);

			objModel = new clsSubContractorGRNModelHd(new clsSubContractorGRNModelHd_ID(strSRCode, clientCode));
			objModel.setStrSRCode(strSRCode);
			objModel.setStrUserCreated(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setIntid(lastNo);
		} else {
			clsSubContractorGRNModelHd objSRModel = objSubContractorGRNService.funGetSubContractorGRNHd(objBean.getStrSRCode(), clientCode);
			if (null == objSRModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblscreturnhd", "SRCode", "intId", clientCode);
				String year = objGlobal.funGetSplitedDate(startDate)[2];
				String cd = objGlobal.funGetTransactionCode("SR", propCode, year);
				String strSRCode = cd + String.format("%06d", lastNo);
				objModel = new clsSubContractorGRNModelHd(new clsSubContractorGRNModelHd_ID(strSRCode, clientCode));
				objModel.setIntid(lastNo);
				objModel.setStrUserCreated(userCode);
				// DCHDModel.setStrPropertyCode(propCode);
				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsSubContractorGRNModelHd(new clsSubContractorGRNModelHd_ID(objBean.getStrSRCode(), clientCode));
				// objModel.setStrPropertyCode(propCode);
			}

		}
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrSRNo(objBean.getStrSRNo());
		objModel.setDteSRDate(objBean.getDteSRDate());
		objModel.setStrSCCode(objBean.getStrSCCode());
		objModel.setStrSCDNCode(objBean.getStrSCDNCode());
		objModel.setDteSCDCDate(objBean.getDteSCDCDate());
		objModel.setStrAgainst(objBean.getStrAgainst());
		objModel.setStrNo(objBean.getStrNo());
		objModel.setStrLocCode(objBean.getStrLocCode());
		objModel.setStrVehNo(objBean.getStrVehNo());
		objModel.setStrInRefNo(objBean.getStrInRefNo());
		objModel.setDteInRefDate(objBean.getDteInRefDate());
		objModel.setDblTotQty(objBean.getDblTotQty());
		objModel.setDblTotWt(objBean.getDblTotWt());
		objModel.setDblTotAmt(objBean.getDblTotAmt());
		objModel.setStrJWCode(objBean.getStrJWCode());
		objModel.setStrVerRemark(objBean.getStrVerRemark());
		objModel.setStrPartDel(objBean.getStrPartDel());
		objModel.setStrDispAction(objBean.getStrDispAction());
		objModel.setStrTimeInOut(objBean.getStrTimeInOut());
		objModel.setStrMInBy(objBean.getStrMInBy());
		objModel.setStrAuthorise("N");

		return objModel;

	}

	@RequestMapping(value = "/frmSubContractorGRNSlip", method = RequestMethod.GET)
	public ModelAndView funSubContractorGRNSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubContractorGRNSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmSubContractorGRNSlip", "command", new clsReportBean());
		}
	}

	private clsSubContractorGRNBean funPrepardHdBean(clsSubContractorGRNModelHd objSCGRNHD, clsPartyMasterModel objPartyMasterModel, clsLocationMasterModel objLocationMasterModel) {
		clsSubContractorGRNBean objBean = new clsSubContractorGRNBean();
		objBean.setStrSRCode(objSCGRNHD.getStrSRCode());
		objBean.setStrSRNo(objSCGRNHD.getStrSRNo());
		objBean.setDteSRDate(objSCGRNHD.getDteSRDate());
		objBean.setStrSCCode(objSCGRNHD.getStrSCCode());
		objBean.setStrSCDNCode(objSCGRNHD.getStrSCDNCode());
		objBean.setDteSCDCDate(objSCGRNHD.getDteSCDCDate());
		objBean.setStrAgainst(objSCGRNHD.getStrAgainst());
		objBean.setStrNo(objSCGRNHD.getStrNo());
		objBean.setStrLocCode(objSCGRNHD.getStrLocCode());
		objBean.setStrLocName(objSCGRNHD.getStrLocCode());
		objBean.setStrVehNo(objSCGRNHD.getStrVehNo());
		objBean.setStrInRefNo(objSCGRNHD.getStrInRefNo());
		objBean.setDteInRefDate(objSCGRNHD.getDteInRefDate());
		objBean.setDblTotAmt(objSCGRNHD.getDblTotAmt());
		objBean.setDblTotQty(objSCGRNHD.getDblTotQty());
		objBean.setDblTotWt(objSCGRNHD.getDblTotWt());
		objBean.setStrVerRemark(objSCGRNHD.getStrVerRemark());
		objBean.setStrJWCode(objSCGRNHD.getStrJWCode());
		objBean.setStrPartDel(objSCGRNHD.getStrPartDel());
		objBean.setStrDispAction(objSCGRNHD.getStrDispAction());
		objBean.setStrMInBy(objSCGRNHD.getStrMInBy());
		objBean.setStrTimeInOut(objSCGRNHD.getStrTimeInOut());

		return objBean;
	}

	@RequestMapping(value = "/rptSubContractorGRNSlip", method = RequestMethod.GET)
	private void funJobOrderReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SOCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallSubContractorGRNSlip(SOCode, type, resp, req);
	}

	@RequestMapping(value = "/openRptSubContractorGRNSlip", method = RequestMethod.GET)
	private void funopenRptSubContractorGRNSlip(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SOCode = req.getParameter("rptSRCode").toString();
		String type = "pdf";
		funCallSubContractorGRNSlip(SOCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallSubContractorGRNSlip(String SOCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String strSRCode = "";
			String strSCCode = "";
			String dteSRDate = "";
			String strAgainst = "";
			String strSCDCCode = "";
			String strLocName = "";
			String strNo = "";
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptSubContractorGRNSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "select a.strSRCode,a.strSCCode,a.dteSRDate,a.strAgainst,a.strNo,a.strSCDNCode,b.strLocName " + "from tblscreturnhd a ,tbllocationmaster b " + "where a.strSRCode='" + SOCode + "' and a.strClientCode='" + clientCode + "' and a.strLocCode=b.strLocCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strSRCode = arrObj[0].toString();
				strSCCode = arrObj[1].toString();
				dteSRDate = arrObj[2].toString();
				strAgainst = arrObj[3].toString();
				strNo = arrObj[4].toString();
				strSCDCCode = arrObj[5].toString();
				strLocName = arrObj[6].toString();
			}

			String sqlDtl = "select a.strProdCode ,b.strProdName,a.dblQtyRbl,a.dblDCQty,a.dblQtyRej,a.dblExpQty,a.dblPrice " + "from  tblscreturndtl a,tblproductmaster b,tblscreturnhd c " + "where a.strSRCode='" + SOCode + "' and a.strClientCode='" + clientCode + "'and a.strProdCode=b.strProdCode and a.strSRCode=c.strSRCode ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsSubContractorGRNSlip");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);

			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strSRCode", strSRCode);
			hm.put("strSCCode", strSCCode);
			hm.put("dteSRDate", dteSRDate);
			hm.put("strAgainst", strAgainst);
			hm.put("strSCDCCode", strSCDCCode);
			hm.put("strLocName", strLocName);
			hm.put("strNo", strNo);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/loadExpRetProducts", method = RequestMethod.GET)
	public @ResponseBody List<clsSubContractorGRNModelDtl> funLoadExpRetProducts(@RequestParam("DNCode") String DNCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		/*
		 * String listOFExpProducts =
		 * " SELECT b.strProdCode,c.strProdName, b.dblQty,(b.dblQty * c.dblWeight) AS totWt, c.dblWeight as weight,"
		 * + " c.dblUnitPrice,SUM(k.dblAcceptQty) as totalReceivedQty," +
		 * " (b.dblQty - SUM(k.dblAcceptQty)) AS pendingQty,i.strProcessName,b.strRemarks,d.dteExpDate,j.strSRCode "
		 * + " FROM tbljoborderallocationhd a, tbljoborderallocationdtl b, " +
		 * " tblproductmaster c,tbldeliverynotehd d, tblpartymaster f," +
		 * " tbljoborderhd g,tblproductmaster h, tblprocessmaster i, " +
		 * " tblscreturnhd j,tblscreturndtl k, tblprodprocess l " +
		 * " WHERE g.strJOCode=b.strJOCode  AND a.strJACode=b.strJACode " +
		 * " AND b.strProdCode=c.strProdCode AND a.strJACode=d.strJACode " +
		 * " AND d.strSCCode=f.strPCode AND g.strProdCode=h.strProdCode " +
		 * " AND l.strProdCode=h.strProdCode AND l.strProcessCode=i.strProcessCode "
		 * + " AND d.strDNCode=j.strSCDNCode  AND j.strSRCode=k.strSRCode " +
		 * " AND g.strProdCode=k.strProdCode  AND d.strDNCode='"+DNCode+"' " +
		 * " AND a.strClientCode='"
		 * +clientCode+"' AND a.strClientCode=b.strClientCode " +
		 * " AND b.strClientCode=d.strClientCode " +
		 * " AND d.strClientCode=j.strClientCode " +
		 * " AND j.strClientCode=k.strClientCode" ;
		 */

		String listOFExpProducts = " SELECT b.strProdCode,c.strProdName, b.dblQty,(b.dblQty * c.dblWeight) AS totWt, " + " c.dblWeight as weight, c.dblUnitPrice, i.strProcessName, " + " b.strRemarks,d.dteExpDate FROM tbljoborderallocationhd a, tbljoborderallocationdtl b,  tblproductmaster c, " + " tbldeliverynotehd d, tblpartymaster f, tbljoborderhd g,tblproductmaster h, tblprocessmaster i, "
				+ "  tblprodprocess l   WHERE g.strJOCode=b.strJOCode   AND a.strJACode=b.strJACode  " + " AND b.strProdCode=c.strProdCode AND a.strJACode=d.strJACode  " + " AND d.strSCCode=f.strPCode AND g.strProdCode=h.strProdCode  AND l.strProdCode=h.strProdCode  " + "  AND l.strProcessCode=i.strProcessCode  " + "  AND d.strDNCode='" + DNCode + "'  AND a.strClientCode='" + clientCode + "' "
				+ "  AND a.strClientCode=b.strClientCode  AND b.strClientCode=d.strClientCode ; ";

		List list = objGlobalFunctionsService.funGetList(listOFExpProducts, "sql");
		List<clsSubContractorGRNModelDtl> resList = new ArrayList<clsSubContractorGRNModelDtl>();

		for (int i = 0; i < list.size(); i++) {
			Object[] arrObj = (Object[]) list.get(0);
			clsSubContractorGRNModelDtl resObj = new clsSubContractorGRNModelDtl();

			// resObj.setStrSRCode(arrObj[11].toString());
			resObj.setStrSRCode("");

			resObj.setStrDNCode(DNCode);

			String strProdCode = arrObj[0].toString();
			resObj.setStrProdCode(strProdCode);

			String strProdName = arrObj[1].toString();
			resObj.setStrProdName(strProdName);

			double dblQtyRbl = Double.parseDouble(arrObj[2].toString());
			resObj.setDblQtyRbl(dblQtyRbl);

			double dblQtyReceived = Double.parseDouble(arrObj[2].toString());
			resObj.setDblQtyReceived(dblQtyReceived);

			double dblDCQty = Double.parseDouble(arrObj[2].toString());
			resObj.setDblDCQty(dblDCQty);

			double dblDCWt = Double.parseDouble(arrObj[3].toString());
			resObj.setDblDCWt(dblDCWt);

			double dblWeight = Double.parseDouble(arrObj[4].toString());
			resObj.setDblWeight(dblWeight);

			double dblTotWt = Double.parseDouble(arrObj[4].toString()) * Double.parseDouble(arrObj[2].toString());
			resObj.setDblTotWt(dblTotWt);

			double dblPrice = Double.parseDouble(arrObj[5].toString());
			resObj.setDblPrice(dblPrice);

			double dblTotPrice = Double.parseDouble(arrObj[5].toString()) * Double.parseDouble(arrObj[2].toString());
			;
			resObj.setDblTotPrice(dblTotPrice);

			// double dblQtyRej = Double.parseDouble(arrObj[7].toString());;
			double dblQtyRej = 0.0;
			resObj.setDblQtyRej(dblQtyRej);

			// double dblAcceptQty = Double.parseDouble(arrObj[6].toString());
			double dblAcceptQty = Double.parseDouble(arrObj[2].toString());
			resObj.setDblAcceptQty(dblAcceptQty);

			// double dblDiff = Double.parseDouble(arrObj[7].toString());;
			double dblDiff = 0.0;
			resObj.setDblDiff(dblDiff);

			double dblDiffPer = (dblDiff / dblDCQty) * 100;
			resObj.setDblDiffPer(dblDiffPer);

			// double dblScrap = Double.parseDouble(arrObj[7].toString());
			double dblScrap = 0.0;
			resObj.setDblScrap(dblScrap);

			String strRemarks = arrObj[7].toString();
			resObj.setStrRemarks(strRemarks);

			double dblWtReceivable = (dblWeight * dblAcceptQty);
			resObj.setDblWtReceivable(dblWtReceivable);

			String strProdChar = "NA";
			resObj.setStrProdChar(strProdChar);

			String strDNProdCode = "NA";
			resObj.setStrDNProdCode(strDNProdCode);

			String strDNProdName = "NA";
			resObj.setStrDNProdName(strDNProdName);

			// String strDNProcess = arrObj[8].toString();
			String strDNProcess = "";
			resObj.setStrDNProcess(strDNProcess);

			double dblDNQty = 0.00;
			resObj.setDblDNQty(dblDNQty);

			double dblDNWeight = 0.00;
			resObj.setDblDNWeight(dblDNWeight);

			String strDNProdChar = "NA";
			resObj.setStrDNProdChar(strDNProdChar);

			resObj.setStrClientCode(clientCode);

			resObj.setIntIndex(i);

			resList.add(resObj);

		}

		return resList;
	}

}
