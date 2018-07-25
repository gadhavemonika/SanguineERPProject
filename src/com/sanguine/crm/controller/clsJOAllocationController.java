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
import com.sanguine.crm.bean.clsJOAllocationBean;
import com.sanguine.crm.model.clsJOAllocationDtlModel;
import com.sanguine.crm.model.clsJOAllocationHdModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsJOAllocationService;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsJOAllocationController {

	@Autowired
	private clsJOAllocationService objJOAlloService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;

	// Open Job Order Allocation
	@RequestMapping(value = "/frmJOAllocation", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJOAllocation_1", "command", new clsJOAllocationBean());
		} else {
			return new ModelAndView("frmJOAllocation", "command", new clsJOAllocationBean());
		}

	}

	@RequestMapping(value = "/saveJOAllocation", method = RequestMethod.POST)
	public ModelAndView funSaveJobOrder(@ModelAttribute("command") @Valid clsJOAllocationBean objBean, BindingResult result, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		Boolean success = false;
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			clsJOAllocationHdModel objModel = funPrepareHdModel(objBean, req);
			Boolean hdSave = objJOAlloService.funAddUpdateJAHd(objModel);

			if (hdSave) {
				String strJACode = objModel.getStrJACode();
				objJOAlloService.funDeleteDtl(strJACode, clientCode);
				List<clsJOAllocationDtlModel> JAList = objBean.getObjJOList();
				long intIndex = 0;
				for (clsJOAllocationDtlModel objDtlModel : JAList) {
					if (objDtlModel.getStrJOCode() != null) {
						if (objDtlModel.getDblQty() > 0) {
							objDtlModel.setStrJACode(strJACode);
							objDtlModel.setIntIndex(intIndex);
							objDtlModel.setStrClientCode(clientCode);
							Long lastNo = objGlobalFunctionsService.funGetCount("tbljoborderallocationdtl", "intId");
							objDtlModel.setIntId(lastNo);
							success = objJOAlloService.funAddUpdateJADtl(objDtlModel);
							intIndex++;
						}
					}
				}

			}

			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Job Order Allocation Code : ".concat(objModel.getStrJACode()));
				return new ModelAndView("redirect:/frmJOAllocation.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmJOAllocation.html?saddr=" + urlHits);
			}

		} else {
			return new ModelAndView("redirect:/frmJOAllocation.html?saddr=" + urlHits);
		}

	}

	private clsJOAllocationHdModel funPrepareHdModel(clsJOAllocationBean objBean, HttpServletRequest req) {

		objGlobal = new clsGlobalFunctions();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String year = objGlobal.funGetSplitedDate(startDate)[2];

		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsJOAllocationHdModel objModel = new clsJOAllocationHdModel();

		clsJOAllocationHdModel objModel1 = objJOAlloService.funGetJAHdObject(objBean.getStrJACode(), clientCode);

		if (objModel1 != null) {

			objModel.setStrJACode(objModel1.getStrJACode());
			objModel.setIntId(objModel1.getIntId());
			objModel.setStrUserCreated(objModel1.getStrUserCreated());
			objModel.setDteDateCreated(objModel1.getDteDateCreated());
			objModel.setStrAuthorise(objModel1.getStrAuthorise());

		} else {
			Long lastNo = objGlobalFunctionsService.funGetCount("tbljoborderallocationhd", "intId");
			String Jd = objGlobal.funGetTransactionCode("JA", propCode, year);
			String strJACode = Jd + String.format("%06d", lastNo);

			objModel.setStrJACode(strJACode);
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrAuthorise("false");
		}

		objModel.setDteJADate(objBean.getDteJADate());
		objModel.setDbltotQty(objBean.getDbltotQty());
		objModel.setDteRefDate(objBean.getDteRefDate());
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrDispatchMode(objBean.getStrDispatchMode());
		objModel.setStrJANo(objBean.getStrJANo());
		objModel.setStrPayment(objBean.getStrPayment());
		objModel.setStrRef(objBean.getStrRef());
		objModel.setStrSCCode(objBean.getStrSCCode());
		objModel.setStrTaxes(objBean.getStrTaxes());

		return objModel;
	}

	@RequestMapping(value = "/frmJobOrderAllocationSlip", method = RequestMethod.GET)
	public ModelAndView funJobOrderAllocationSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJobOrderAllocationSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmJobOrderAllocationSlip", "command", new clsReportBean());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadJAData", method = RequestMethod.GET)
	public @ResponseBody clsJOAllocationBean funLoadJAData(@RequestParam("JACode") String JACode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsJOAllocationBean objBean = null;

		List objModelList = objJOAlloService.funGetJAHdData(JACode, clientCode);
		if (objModelList.size() > 0) {

			Object[] objHdArr = (Object[]) objModelList.get(0);
			clsJOAllocationHdModel objModel = (clsJOAllocationHdModel) objHdArr[0];
			objBean = funPrepareBean(objModel);
			clsPartyMasterModel objPMaster = (clsPartyMasterModel) objHdArr[1];
			objBean.setStrSCName(objPMaster.getStrPName());
			List dtlList = objJOAlloService.funGetJADtlData(JACode, clientCode);
			List resDtlList = new ArrayList();
			for (int i = 0; i < dtlList.size(); i++) {
				Object[] objDtlArr = (Object[]) dtlList.get(i);

				clsJOAllocationDtlModel objDtlModel = (clsJOAllocationDtlModel) objDtlArr[0];
				// clsJobOrderModel objJO = (clsJobOrderModel) objDtlArr[1];
				clsProductMasterModel prodmast = (clsProductMasterModel) objDtlArr[2];
				objDtlModel.setStrProdName(prodmast.getStrProdName());
				objDtlModel.setDblTotalPrice(objDtlModel.getDblQty() * objDtlModel.getDblRate());
				resDtlList.add(objDtlModel);
			}
			objBean.setObjJOList(resDtlList);

		} else {
			objBean = new clsJOAllocationBean();
			objBean.setStrJACode("Invalid Code");
		}

		return objBean;
	}

	private clsJOAllocationBean funPrepareBean(clsJOAllocationHdModel objModel) {

		clsJOAllocationBean objBean = new clsJOAllocationBean();
		objBean.setStrJACode(objModel.getStrJACode());
		objBean.setIntId(objModel.getIntId());
		objBean.setStrUserCreated(objModel.getStrUserCreated());
		objBean.setDteDateCreated(objModel.getDteDateCreated());
		objBean.setStrAuthorise(objModel.getStrAuthorise());
		objBean.setDteJADate(objModel.getDteJADate());
		objBean.setDbltotQty(objModel.getDbltotQty());
		objBean.setDteRefDate(objModel.getDteRefDate());
		objBean.setStrUserModified(objModel.getStrUserModified());
		objBean.setDteLastModified(objModel.getDteLastModified());
		objBean.setStrClientCode(objModel.getStrClientCode());
		objBean.setStrDispatchMode(objModel.getStrDispatchMode());
		objBean.setStrJANo(objModel.getStrJANo());
		objBean.setStrPayment(objModel.getStrPayment());
		objBean.setStrRef(objModel.getStrRef());
		objBean.setStrSCCode(objModel.getStrSCCode());
		objBean.setStrTaxes(objModel.getStrTaxes());

		return objBean;
	}

	@RequestMapping(value = "/rptJobOrderAllocationSlip", method = RequestMethod.GET)
	private void funJobOrderReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SOCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallJobOrderAllocationSlip(SOCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallJobOrderAllocationSlip(String SOCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String strJANo = "";
			String dteJADate = "";
			String strRef = "";
			String dteRefDate = "";
			String strSCCode = "";
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSState = "";
			String strSCountry = "";
			String strSPin = "";
			String strDispatchMode = "";

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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptJobOrderAllocationSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "select a.strJANo,a.dteJADate,a.strRef,a.strSCCode,a.dteRefDate ,b.strPName,b.strSAdd1 ,b.strSAdd2," + "b.strSCity,b.strSState,b.strSCountry,b.strSPin,a.strDispatchMode " + "from tbljoborderallocationhd a,tblpartymaster b " + "where a.strJACode='" + SOCode + "' and a.strClientCode='" + clientCode + "' " + "and a.strSCCode=b.strPCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strJANo = arrObj[0].toString();
				dteJADate = arrObj[1].toString();
				strRef = arrObj[2].toString();
				strSCCode = arrObj[3].toString();
				dteRefDate = arrObj[4].toString();
				strPName = arrObj[5].toString();
				strSAdd1 = arrObj[6].toString();
				strSAdd2 = arrObj[7].toString();
				strSCity = arrObj[8].toString();
				strSState = arrObj[9].toString();
				strSCountry = arrObj[10].toString();
				strSPin = arrObj[11].toString();
				strDispatchMode = arrObj[12].toString();
			}

			String sqlDtl = "select b.strPartNo,b.strProdName,c.strProcessName,a.dblQty,b.strUOM,a.strProdNo,a.strRemarks,a.dblRate " + "from tbljoborderallocationdtl a,tblproductmaster b,tblprocessmaster c,tblprodprocess d " + "where a.strJACode='" + SOCode + "'and a.strClientCode='" + clientCode + "'and a.strProdCode=b.strProdCode "
					+ "and a.strProdCode=d.strProdCode and d.strProcessCode=c.strProcessCode";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsJobOrderAllocationSlip");
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
			hm.put("strJANo", strJANo);
			hm.put("dteJADate", dteJADate);
			hm.put("strRef", strRef);
			hm.put("dteRefDate", dteRefDate);
			hm.put("strPName", strPName);
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strSCCode", strSCCode);
			hm.put("strDispatchMode", strDispatchMode);

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
