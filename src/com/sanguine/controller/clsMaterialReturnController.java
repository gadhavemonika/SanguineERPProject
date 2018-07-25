package com.sanguine.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
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
import com.sanguine.bean.clsMaterialReturnBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsMaterialReturnDtlModel;
import com.sanguine.model.clsMaterialReturnHdModel;
import com.sanguine.model.clsMaterialReturnHdModel_ID;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTransactionTimeModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsMISService;
import com.sanguine.service.clsMaterialReturnService;
import com.sanguine.service.clsRequisitionService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTransactionTimeService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsMaterialReturnController {
	@Autowired
	private clsMaterialReturnService objMRService;
	@Autowired
	private clsLocationMasterService objLocService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsRequisitionService objReqService;
	@Autowired
	private clsMISService objMISService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsTransactionTimeService objTransactionTimeService;

	clsGlobalFunctions objGlobal = null;

	/**
	 * Open Material Return form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmMaterialReturn", method = RequestMethod.GET)
	public ModelAndView funOpenMISForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmMaterialReturn");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		/**
		 * Checking Authorization
		 */
		String docCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			docCode = request.getParameter("authorizationMatRetCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationMatRetCode", docCode);
		}

		model.put("urlHits", urlHits);
		/*
		 * Set Process
		 */
		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmMaterialReturn", propCode, clientCode);
		if (list.size() > 0) {
			model.put("strProcessList", list);
		} else {
			list = new ArrayList<String>();
			model.put("strProcessList", list);
		}
		clsMaterialReturnBean bean = new clsMaterialReturnBean();
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialReturn_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialReturn", "command", bean);
		} else {
			return null;
		}

	}

	/**
	 * Save Material Return form
	 * 
	 * @param objMRBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveMaterialReturn", method = RequestMethod.POST)
	public ModelAndView funSaveMaterialReturn(@ModelAttribute("command") clsMaterialReturnBean objMRBean, BindingResult result, HttpServletRequest req) throws ParseException {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String loginLocationCode = req.getSession().getAttribute("locationCode").toString();

		List<clsTransactionTimeModel> listclsTransactionTimeModel = new ArrayList<clsTransactionTimeModel>();
		listclsTransactionTimeModel = objTransactionTimeService.funLoadTransactionTime(propCode, clientCode, "");
		String fromTime = "", toTime = "";
		if (!result.hasErrors()) {
			if (listclsTransactionTimeModel.size() > 0) {
				clsTransactionTimeModel objTransactionTimeModel = (clsTransactionTimeModel) listclsTransactionTimeModel.get(0);
				SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mma");
				SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
				Date fdate, tdate;
				fromTime = objTransactionTimeModel.getTmeFrom();
				toTime = objTransactionTimeModel.getTmeTo();

				fdate = parseFormat.parse(fromTime);
				tdate = parseFormat.parse(toTime);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String currentTime = sdf.format(cal.getTime());
				Date currentTme = sdf.parse(currentTime);
				System.out.println("System Time=" + sdf.format(cal.getTime()));
				if (loginLocationCode.equalsIgnoreCase(objTransactionTimeModel.getStrLocCode())) {
					if ((fdate.getTime() > currentTme.getTime()) || (tdate.getTime() < currentTme.getTime()))
					// currentTme)&&tdate.before(currentTme)&&fdate.equals(currentTme)&&tdate.equals(currentTme))
					{
						req.getSession().setAttribute("success", false);
						req.getSession().setAttribute("successMessage", "Your Transaction Time Is Over");
						return new ModelAndView("redirect:/frmMaterialReturn.html?saddr=" + urlHits);
					}
				}
			} else {
				List<clsMaterialReturnDtlModel> listMRDtl = objMRBean.getListMaterialRetDtl();
				if (null != listMRDtl && listMRDtl.size() > 0) {
					clsMaterialReturnHdModel objMRHdModel = funPrepareModelHd(objMRBean, req);
					objMRService.funAddUpdateMaterialReturnHd(objMRHdModel);
					String strMRCode = objMRHdModel.getStrMRetCode();
					objMRService.funDeleteDtl(strMRCode, clientCode);

					for (clsMaterialReturnDtlModel objMRDtl : listMRDtl) {
						if (null != objMRDtl.getStrProdCode()) {
							objMRDtl.setStrMRetCode(strMRCode);
							objMRDtl.setStrClientCode(clientCode);
							objMRService.funAddUpdateMaterialReturnDtl(objMRDtl);
						}
					}

					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "MR Code : ".concat(objMRHdModel.getStrMRetCode()));
					req.getSession().setAttribute("rptMRCode", objMRHdModel.getStrMRetCode());
				}
			}
			return new ModelAndView("redirect:/frmMaterialReturn.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmMaterialReturn.html?saddr=" + urlHits);
		}
	}

	/**
	 * Prepare Material Return HdModel
	 * 
	 * @param objMRBean
	 * @param req
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private clsMaterialReturnHdModel funPrepareModelHd(clsMaterialReturnBean objMRBean, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Material Return")) {
				res = true;
			}
		}

		long lastNo = 0;
		objGlobal = new clsGlobalFunctions();
		clsMaterialReturnHdModel objMaterialReturnHd;

		if (objMRBean.getStrMRetCode().trim().length() == 0) {
			String MRCode = objGlobalFunctions.funGenerateDocumentCode("frmMaterialReturn", objMRBean.getDtMRetDate(), req);
			objMaterialReturnHd = new clsMaterialReturnHdModel(new clsMaterialReturnHdModel_ID(MRCode, clientCode));
			objMaterialReturnHd.setIntId(lastNo);
			objMaterialReturnHd.setStrUserCreated(userCode);
			objMaterialReturnHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			List listMRHd = objMRService.funGetObject(objMRBean.getStrMRetCode(), clientCode);

			if (listMRHd.size() == 0) {
				String MRCode = objGlobalFunctions.funGenerateDocumentCode("frmMaterialReturn", objMRBean.getDtMRetDate(), req);
				objMaterialReturnHd = new clsMaterialReturnHdModel(new clsMaterialReturnHdModel_ID(MRCode, clientCode));
				objMaterialReturnHd.setIntId(lastNo);
				objMaterialReturnHd.setStrUserCreated(userCode);
				objMaterialReturnHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmMaterialReturn", req)) {
					funSaveAudit(objMRBean.getStrMRetCode(), "Edit", req);
				}
				objMaterialReturnHd = new clsMaterialReturnHdModel(new clsMaterialReturnHdModel_ID(objMRBean.getStrMRetCode(), clientCode));
			}
		}

		objMaterialReturnHd.setStrLocFrom(objMRBean.getStrLocFrom());
		objMaterialReturnHd.setStrLocTo(objMRBean.getStrLocTo());
		objMaterialReturnHd.setStrAgainst(objMRBean.getStrAgainst());
		if (res) {
			objMaterialReturnHd.setStrAuthorise("No");

		} else {
			objMaterialReturnHd.setStrAuthorise("Yes");
		}
		objMaterialReturnHd.setDtMRetDate(objGlobal.funGetDate("yyyy-MM-dd", objMRBean.getDtMRetDate()));
		objMaterialReturnHd.setStrUserModified(userCode);
		objMaterialReturnHd.setStrMISCode(objGlobal.funIfNull(objMRBean.getStrMISCode(), "", objMRBean.getStrMISCode()));
		objMaterialReturnHd.setStrNarration(objMRBean.getStrNarration());
		objMaterialReturnHd.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objMaterialReturnHd;
	}

	/**
	 * Load Material Return Hd Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadMRHdData", method = RequestMethod.GET)
	public @ResponseBody clsMaterialReturnHdModel funAssignFields(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		clsMaterialReturnHdModel objMaterialReturnHd = null;
		String MRCode = request.getParameter("MRCode").toString();
		List listMRHd = objMRService.funGetObject(MRCode, clientCode);
		if (listMRHd.isEmpty()) {
			objMaterialReturnHd = new clsMaterialReturnHdModel();
			objMaterialReturnHd.setStrMRetCode("Invalid Code");
			return objMaterialReturnHd;
		} else {
			objMaterialReturnHd = (clsMaterialReturnHdModel) listMRHd.get(0);
			clsLocationMasterModel objLocFrom = objLocService.funGetObject(objMaterialReturnHd.getStrLocFrom(), clientCode);
			clsLocationMasterModel objLocTo = objLocService.funGetObject(objMaterialReturnHd.getStrLocTo(), clientCode);
			objMaterialReturnHd.setDtMRetDate(objGlobal.funGetDate("yyyy/MM/dd", objMaterialReturnHd.getDtMRetDate()));
			objMaterialReturnHd.setStrLocFromName(objLocFrom.getStrLocName());
			objMaterialReturnHd.setStrLocToName(objLocTo.getStrLocName());
			return objMaterialReturnHd;
		}
	}

	/**
	 * Load Material Return Dtl Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadMRDtlData", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields1(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String MRCode = request.getParameter("MRCode").toString();
		List listMaterialReturnDtl = objMRService.funGetMRDtlList(MRCode, clientCode);
		return listMaterialReturnDtl;
	}

	/**
	 * 
	 * Audit Function Start
	 * 
	 * @param MRCode
	 * @param req
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funSaveAudit(String MRCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List listMRHd = objMRService.funGetObject(MRCode, clientCode);
			if (!listMRHd.isEmpty()) {
				clsMaterialReturnHdModel objMaterialReturnHd = null;
				objMaterialReturnHd = (clsMaterialReturnHdModel) listMRHd.get(0);
				if (null != objMaterialReturnHd) {
					// String
					// userCode=req.getSession().getAttribute("usercode").toString();
					List<clsMaterialReturnDtlModel> listMaterialReturnDtl = objMRService.funGetMRDtlList(MRCode, clientCode);
					if (null != listMaterialReturnDtl && listMaterialReturnDtl.size() > 0) {
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + objMaterialReturnHd.getStrMRetCode() + "' and strClientCode='" + clientCode + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");
						if (!list.isEmpty()) {
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(objMaterialReturnHd);
							if (strTransMode.equalsIgnoreCase("Deleted")) {
								model.setStrTransCode(objMaterialReturnHd.getStrMRetCode());
							} else {
								model.setStrTransCode(objMaterialReturnHd.getStrMRetCode() + "-" + count);
							}
							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (clsMaterialReturnDtlModel MRDtlModel : listMaterialReturnDtl) {
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(model.getStrTransCode());
								AuditMode.setStrProdCode(MRDtlModel.getStrProdCode());
								AuditMode.setDblQty(MRDtlModel.getDblQty());
								AuditMode.setStrClientCode(MRDtlModel.getStrClientCode());
								AuditMode.setStrUOM("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode("");
								AuditMode.setStrRemarks("");
								AuditMode.setStrTaxType("");
								AuditMode.setStrPICode("");
								objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Prepare Audit Model HdModel
	 * 
	 * @param MRModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsMaterialReturnHdModel MRModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (MRModel != null) {
			AuditHdModel.setStrTransCode(MRModel.getStrMRetCode());
			AuditHdModel.setDtTransDate(MRModel.getDtMRetDate());
			AuditHdModel.setStrTransType("Material Return");
			AuditHdModel.setStrLocBy(MRModel.getStrLocFrom());
			AuditHdModel.setStrLocOn(MRModel.getStrLocTo());
			AuditHdModel.setStrAgainst(MRModel.getStrAgainst());
			AuditHdModel.setStrAuthorise(MRModel.getStrAuthorise());
			AuditHdModel.setStrNarration(MRModel.getStrNarration());
			AuditHdModel.setStrCode(MRModel.getStrMISCode());
			AuditHdModel.setDtDateCreated(MRModel.getDtCreatedDate());
			AuditHdModel.setStrUserCreated(MRModel.getStrUserCreated());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrLocCode("");
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
		}
		return AuditHdModel;
	}

	/**
	 * Open Report form
	 */
	@RequestMapping(value = "/frmMaterialReturnDetail", method = RequestMethod.GET)
	public ModelAndView funOpenProductionOrderSlipForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmWebStockHelpMaterialReturnSlip");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialReturnDetail_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMaterialReturnDetail", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptMaterialReturnDetail", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String ProdCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReport(ProdCode, type, resp, req);
	}

	@RequestMapping(value = "/invokeMaterialReturnDetail", method = RequestMethod.GET)
	private void funCallReportOnClick(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String type = "pdf";
		funCallReport(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(String MRetCode, String type, HttpServletResponse resp, HttpServletRequest req) {
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMaterialReturnDetail.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtlQuery = "select a.strMRetCode,a.strProdCode,a.strProdName,a.dblQty,a.strRemarks,date(b.dtMRetDate) as dtMRetDate, " + "c.strLocName as LocFrom,d.strLocName as ToLoc,b.strMISCode ,b.strAgainst,b.strAuthorise " + "from tblmaterialreturndtl a,tblmaterialreturnhd b,tbllocationmaster c ,tbllocationmaster d "
					+ "where a.strMRetCode=b.strMRetCode and b.strLocFrom=c.strLocCode and b.strLocTo=d.strLocCode  " + "and a.strMRetCode='" + MRetCode + "' " + "and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsMatReturn");
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

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptMaterialReturnDetail." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptMaterialReturnDetail." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
