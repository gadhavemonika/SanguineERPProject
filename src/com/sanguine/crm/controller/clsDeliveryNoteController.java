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
import com.sanguine.crm.bean.clsDeliveryNoteBean;
import com.sanguine.crm.model.clsDeliveryNoteDtlModel;
import com.sanguine.crm.model.clsDeliveryNoteHdModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsDeliveryNoteService;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDeliveryNoteController {

	@Autowired
	private clsDeliveryNoteService objDeliveryNoteService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPartyMasterService objPartyService;

	@Autowired
	private clsLocationMasterService objLocationService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private ServletContext servletContext;

	// Open DeliveryNote
	@RequestMapping(value = "/frmDeliveryNote", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryNote_1", "command", new clsDeliveryNoteBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryNote", "command", new clsDeliveryNoteBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveDeliveryNote", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsDeliveryNoteBean objBean, BindingResult result, HttpServletRequest req) {
		Boolean success = false;
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {

			clsDeliveryNoteHdModel objHdModel = funPrepareModel(req, objBean);
			Boolean hdSuccess = objDeliveryNoteService.funAddUpdateDeliveryNoteHd(objHdModel);

			if (hdSuccess) {
				objDeliveryNoteService.funDeleteDtl(objHdModel.getStrDNCode(), objHdModel.getStrClientCode());
				for (clsDeliveryNoteDtlModel objDNDetails : objBean.getListDNDtl()) {
					if (objDNDetails.getStrProdCode() != null) {
						objDNDetails.setStrProdChar("");
						objDNDetails.setStrDNCode(objHdModel.getStrDNCode());
						objDNDetails.setStrClientCode(objHdModel.getStrClientCode());
						success = objDeliveryNoteService.funAddUpdateDeliveryNoteDtl(objDNDetails);
					}
				}
			} else {
				return new ModelAndView("redirect:/frmDeliveryNote.html?saddr=" + urlHits);
			}
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Delivery Note Code : ".concat(objHdModel.getStrDNCode()));
				return new ModelAndView("redirect:/frmDeliveryNote.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmDeliveryNote.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmDeliveryNote.html?saddr=" + urlHits);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadDNData", method = RequestMethod.GET)
	public @ResponseBody clsDeliveryNoteBean funLoadDNData(@RequestParam("DNCode") String DNCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List hdObject = objDeliveryNoteService.funGetDelNoteHdObject(DNCode, clientCode);

		clsDeliveryNoteBean objBean = new clsDeliveryNoteBean();
		if (hdObject.size() > 0) {
			objBean = funPrepareBean(hdObject);
			List<clsDeliveryNoteDtlModel> dtlList = new ArrayList<clsDeliveryNoteDtlModel>();
			List objDtlList = objDeliveryNoteService.funGetDelNoteDtlList(DNCode, clientCode);
			for (Object objDtl : objDtlList) {
				Object[] objArr = (Object[]) objDtl;
				clsDeliveryNoteDtlModel objDNDtl = (clsDeliveryNoteDtlModel) objArr[0];
				clsProductMasterModel objProdMaster = (clsProductMasterModel) objArr[1];
				clsProcessMasterModel objProcessMaster = (clsProcessMasterModel) objArr[2];

				objDNDtl.setStrProdName(objProdMaster.getStrProdName());
				objDNDtl.setStrProdUOM(objProdMaster.getStrUOM());
				objDNDtl.setStrProcessName(objProcessMaster.getStrProcessName());
				dtlList.add(objDNDtl);
			}
			objBean.setListDNDtl(dtlList);
		} else {
			objBean.setStrDNCode("Invalid Code");
		}
		return objBean;
	}

	@SuppressWarnings("rawtypes")
	private clsDeliveryNoteBean funPrepareBean(List hdObject) {

		Object[] obj = (Object[]) hdObject.get(0);
		clsDeliveryNoteHdModel objHdModel = (clsDeliveryNoteHdModel) obj[0];
		clsPartyMasterModel objSCModel = (clsPartyMasterModel) obj[1];

		clsDeliveryNoteBean objBean = new clsDeliveryNoteBean();

		objBean.setStrUserCreated(objHdModel.getStrUserCreated());
		objBean.setDteCreatedDate(objHdModel.getDteCreatedDate());
		objBean.setStrDNCode(objHdModel.getStrDNCode());
		objBean.setIntId(objHdModel.getIntId());

		objBean.setStrSCCode(objHdModel.getStrSCCode());
		objBean.setStrSCName(objSCModel.getStrPName());
		objBean.setStrSCAdd1(objSCModel.getStrSAdd1());
		objBean.setStrSCAdd2(objSCModel.getStrSAdd2());
		objBean.setStrSCCity(objSCModel.getStrSCity());
		objBean.setStrSCCountry(objSCModel.getStrSCountry());
		objBean.setStrSCPin(objSCModel.getStrSPin());
		objBean.setStrSCState(objSCModel.getStrSState());

		objBean.setStrFrom(objHdModel.getStrFrom());
		objBean.setStrLocCode(objHdModel.getStrLocCode());

		switch (objHdModel.getStrFrom()) {
		case "Company":
			clsLocationMasterModel objLocMaster = objLocationService.funGetObject(objHdModel.getStrLocCode(), objHdModel.getStrClientCode());
			objBean.setStrLocName(objLocMaster.getStrLocName());
			objBean.setStrFAdd1("");
			objBean.setStrFAdd2("");
			objBean.setStrFCity("");
			objBean.setStrFCountry("");
			objBean.setStrFPin("");
			objBean.setStrFState("");

			break;

		case "Supplier":
			clsPartyMasterModel objSupplier = objPartyService.funGetObject(objHdModel.getStrLocCode(), objHdModel.getStrClientCode());
			objBean.setStrLocName(objSupplier.getStrPName());
			objBean.setStrFAdd1(objSupplier.getStrSAdd1());
			objBean.setStrFAdd2(objSupplier.getStrSAdd2());
			objBean.setStrFCity(objSupplier.getStrSCity());
			objBean.setStrFCountry(objSupplier.getStrSCountry());
			objBean.setStrFPin(objSupplier.getStrSPin());
			objBean.setStrFState(objSupplier.getStrSState());

			break;

		}

		objBean.setStrNarration(objHdModel.getStrNarration());
		objBean.setStrAuthorise(objHdModel.getStrAuthorise());
		objBean.setStrClientCode(objHdModel.getStrClientCode());
		objBean.setStrDNType(objHdModel.getStrDNType());
		objBean.setStrExpDet(objHdModel.getStrExpDet());

		objBean.setStrGRNCode(objHdModel.getStrGRNCode());
		objBean.setStrJACode(objHdModel.getStrJACode());
		objBean.setStrMInBy(objHdModel.getStrMInBy());
		objBean.setStrTimeInOut(objHdModel.getStrTimeInOut());
		objBean.setStrTypeAgainst(objHdModel.getStrTypeAgainst());
		objBean.setStrTypeCode(objHdModel.getStrTypeCode());
		objBean.setStrVehNo(objHdModel.getStrVehNo());
		objBean.setDblTotal(objHdModel.getDblTotal());
		objBean.setDteDNDate(objHdModel.getDteDNDate());
		objBean.setDteExpDate(objHdModel.getDteExpDate());
		objBean.setStrUserModified(objHdModel.getStrUserModified());
		objBean.setDteLastModified(objHdModel.getDteLastModified());
		objBean.setStrTransportType(objHdModel.getStrTransportType());
		
		return objBean;
	}

	private clsDeliveryNoteHdModel funPrepareModel(HttpServletRequest req, clsDeliveryNoteBean objBean) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		clsDeliveryNoteHdModel objModel = new clsDeliveryNoteHdModel();

		if (objBean.getStrDNCode().isEmpty()) // New Entry
		{
			String dnDate = objBean.getDteDNDate();
			dnDate = dnDate.split("-")[2] + "-" + dnDate.split("-")[1] + "-" + dnDate.split("-")[0];
			String dnCode = objGlobal.funGenerateDocumentCode("frmDN", dnDate, req);

			objModel.setStrDNCode(dnCode);
			objModel.setStrUserCreated(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else // Update Old Entry
		{
			objModel.setStrUserCreated(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrDNCode(objBean.getStrDNCode());
		}

		objModel.setIntId(0);
		objModel.setStrSCCode(objBean.getStrSCCode());
		objModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "NA", objBean.getStrNarration()));
		objModel.setStrAuthorise("N");
		objModel.setStrClientCode(clientCode);
		objModel.setStrDNType(objBean.getStrDNType() != null ? objBean.getStrDNType() : "");
		objModel.setStrExpDet(objBean.getStrExpDet() != null ? objBean.getStrExpDet() : "");
		objModel.setStrFrom(objBean.getStrFrom() != null ? objBean.getStrFrom() : "");
		objModel.setStrFAdd1(objBean.getStrFAdd1() != null ? objBean.getStrFAdd1() : "");
		objModel.setStrFAdd2(objBean.getStrFAdd2() != null ? objBean.getStrFAdd2() : "");
		objModel.setStrFCity(objBean.getStrFCity() != null ? objBean.getStrFCity() : "");
		objModel.setStrFCountry(objBean.getStrFCountry() != null ? objBean.getStrFCountry() : "");
		objModel.setStrFPin(objBean.getStrFPin() != null ? objBean.getStrFPin() : "");
		objModel.setStrFState(objBean.getStrFState() != null ? objBean.getStrFState() : "");
		objModel.setStrSCAdd1(objBean.getStrSCAdd1() != null ? objBean.getStrSCAdd1() : "");
		objModel.setStrSCAdd2(objBean.getStrSCAdd2() != null ? objBean.getStrSCAdd2() : "");
		objModel.setStrSCCity(objBean.getStrSCCity() != null ? objBean.getStrSCCity() : "");
		objModel.setStrSCCountry(objBean.getStrSCCountry() != null ? objBean.getStrSCCountry() : "");
		objModel.setStrSCPin(objBean.getStrSCPin() != null ? objBean.getStrSCPin() : "");
		objModel.setStrSCState(objBean.getStrSCState() != null ? objBean.getStrSCState() : "");
		objModel.setStrGRNCode(objBean.getStrGRNCode() != null ? objBean.getStrGRNCode() : "");
		objModel.setStrJACode(objBean.getStrJACode() != null ? objBean.getStrJACode() : "");
		objModel.setStrLocCode(objBean.getStrLocCode() != null ? objBean.getStrLocCode() : "");
		objModel.setStrMInBy(objBean.getStrMInBy() != null ? objBean.getStrMInBy() : "");
		objModel.setStrTimeInOut(objBean.getStrTimeInOut() != null ? objBean.getStrTimeInOut() : "");
		objModel.setStrTypeAgainst(objBean.getStrTypeAgainst() != null ? objBean.getStrTypeAgainst() : "");
		objModel.setStrTypeCode(objBean.getStrTypeCode() != null ? objBean.getStrTypeCode() : "");
		objModel.setStrVehNo(objBean.getStrVehNo() != null ? objBean.getStrVehNo() : "");
		objModel.setDblTotal(objBean.getDblTotal() != null ? objBean.getDblTotal() : 0);
		objModel.setDteDNDate(objBean.getDteDNDate() != null ? objBean.getDteDNDate() : "");
		objModel.setDteExpDate(objBean.getDteExpDate() != null ? objBean.getDteExpDate() : "");
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrTransportType(objBean.getStrTransportType());

		return objModel;
	}

	@RequestMapping(value = "/frmDeliveryNoteSlip", method = RequestMethod.GET)
	public ModelAndView funDeliveryNoteSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryNoteSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmDeliveryNoteSlip", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/frmDeliveryNoteList", method = RequestMethod.GET)
	public ModelAndView funDeliveryNoteListForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryNoteList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmDeliveryNoteList", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptDeliveryNoteList", method = RequestMethod.GET)
	private void funDeliveryNoteListReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SOCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();

		funCallDeliveryNoteListReport(SOCode, type, fromDate, toDate, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallDeliveryNoteListReport(String SOCode, String type, String fromDate, String toDate, HttpServletResponse resp, HttpServletRequest req) {

		try {

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
			String reportName;

			reportName = servletContext.getRealPath("/WEB-INF/reports/rptDeliveryNoteList.jrxml");

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// // List list=objGlobalFunctionsService.funGetList(sqlHd,"sql");
			// if(!list.isEmpty())
			// {
			// Object[] arrObj=(Object[])list.get(0);
			//
			// dteDCDate=arrObj[0].toString();
			// strPName=arrObj[1].toString();
			// strSAdd1=arrObj[2].toString();
			// strSAdd2=arrObj[3].toString();
			// strSCity=arrObj[4].toString();
			// }

			String sqlDtl;

			sqlDtl = "select a.strDNCode,a.dteDNDate,a.strSCCode,b.strPName,a.strLocCode,c.strLocName,a.dteExpDate " + "from tbldeliverynotehd a, tblpartymaster b ,tbllocationmaster c" + " where a.strSCCode='" + SOCode + "' and a.strSCCode=b.strPCode and a.strClientCode='" + clientCode + "' " + "  and a.strLocCode =c.strLocCode and a.dteDNDate between '" + fromDate + "'and '" + toDate + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset;
			subDataset = (JRDesignDataset) datasetMap.get("dsDeliveryNoteList");
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
			hm.put("strPin", objSetup.getStrPin());
			hm.put("SOCode", SOCode);
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);

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

}
