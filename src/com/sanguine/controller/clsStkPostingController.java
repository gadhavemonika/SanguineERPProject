package com.sanguine.controller;

import java.sql.SQLException;
import java.text.ParseException;
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
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
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
import com.sanguine.bean.clsPhysicalStkPostBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsStkPostingHdModel;
import com.sanguine.model.clsStkPostingHdModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsStkPostingService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStkPostingController {
	@Autowired
	private clsStkPostingService objStkPostService;
	@Autowired
	private clsStkAdjustmentService objStkAdjService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;

	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsProductMasterService objProductMasterService;

	/**
	 * Open Physical Stock Posting Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmPhysicalStkPosting", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmPhysicalStkPosting");
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
			docCode = request.getParameter("authorizationPhyStkCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationPhyStkCode", docCode);
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPhysicalStkPosting_1", "command", new clsPhysicalStkPostBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPhysicalStkPosting", "command", new clsPhysicalStkPostBean());
		} else {
			return null;
		}

	}

	/**
	 * Loading Physical Stock Posting Data
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmPhysicalStkPosting1", method = RequestMethod.GET)
	public @ResponseBody clsPhysicalStkPostBean funOpenFormWithSPCode(Map<String, Object> model, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPhysicalStkPostBean bean = new clsPhysicalStkPostBean();
		String psCode = request.getParameter("PSCode").toString();
		// String
		// startDate=request.getSession().getAttribute("startDate").toString();
		// String toDate=objGlobal.funGetCurrentDate("yyyy-MM-dd");
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		List listStkPostHd = objStkPostService.funGetObject(psCode, clientCode);
		if (listStkPostHd.isEmpty()) {
			bean = new clsPhysicalStkPostBean();
			bean.setStrPSCode("Invalid Code");
			return bean;
		} else {
			bean = funPrepareBean(listStkPostHd);
			request.getSession().setAttribute("transname", "frmPhysicalStkPosting.jsp");
			request.getSession().setAttribute("formname", "Physical Stock Posting");
			request.getSession().setAttribute("code", bean.getStrPSCode());
			List listStkDtl = objStkPostService.funGetDtlList(psCode, clientCode);
			List<clsStkPostingDtlModel> listStkPostDtl = new ArrayList<clsStkPostingDtlModel>();
			for (int i = 0; i < listStkDtl.size(); i++) {
				Object[] ob = (Object[]) listStkDtl.get(i);
				clsStkPostingDtlModel stkPostDtl = (clsStkPostingDtlModel) ob[0];
				clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
				clsStkPostingDtlModel objStkPostDtl = new clsStkPostingDtlModel();
				objStkPostDtl.setStrPSCode(stkPostDtl.getStrPSCode());
				objStkPostDtl.setStrProdCode(stkPostDtl.getStrProdCode());
				objStkPostDtl.setStrProdChar(stkPostDtl.getStrProdChar());
				objStkPostDtl.setStrProdName(prodMaster.getStrProdName());
				objStkPostDtl.setDblPrice(stkPostDtl.getDblPrice());
				objStkPostDtl.setDblWeight(stkPostDtl.getDblWeight());
				objStkPostDtl.setStrClientCode(stkPostDtl.getStrClientCode());
				double variance = stkPostDtl.getDblVariance();
				double adjValue = stkPostDtl.getDblPrice() * variance;
				double actualAdjValue = stkPostDtl.getDblActualRate() * variance;
				double adjWt = stkPostDtl.getDblWeight() * variance;
				objStkPostDtl.setDblVariance(variance);
				objStkPostDtl.setDblAdjValue(adjValue);
				objStkPostDtl.setDblActualRate(stkPostDtl.getDblActualRate());
				objStkPostDtl.setDblActualValue(actualAdjValue);
				objStkPostDtl.setDblAdjWt(adjWt);
				objStkPostDtl.setDblCStock(stkPostDtl.getDblCStock());
				objStkPostDtl.setDblPStock(stkPostDtl.getDblPStock());
				objStkPostDtl.setIntIndex(0);
				objStkPostDtl.setDblLooseQty(stkPostDtl.getDblLooseQty());
				objStkPostDtl.setStrDisplyQty(stkPostDtl.getStrDisplyQty());
				objStkPostDtl.setStrDisplyVariance(stkPostDtl.getStrDisplyVariance());
				listStkPostDtl.add(objStkPostDtl);
			}
			bean.setListStkPostDtl(listStkPostDtl);
		}
		return bean;
	}

	/**
	 * Save Physical Stock Posting Data
	 * 
	 * @param objBean
	 * @param result
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/savePhyStkPosting", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPhysicalStkPostBean objBean, BindingResult result, HttpServletRequest request) throws ParseException {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		int intt = 0;
		String stkAdjCode = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String startDate = request.getSession().getAttribute("startDate").toString();
		clsStkPostingHdModel objHdModel = funPrepareModel(objBean, userCode, clientCode, propCode, startDate, request);
		objGlobal = new clsGlobalFunctions();
		if (!result.hasErrors()) {
			List<clsStkPostingDtlModel> listStkPostDtl = objBean.getListStkPostDtl();
			if (null != listStkPostDtl && listStkPostDtl.size() > 0) {
				boolean flagDtlDataInserted = false;
				objStkPostService.funAddUpdate(objHdModel);
				String psCode = objHdModel.getStrPSCode();
				objStkPostService.funDeleteDtl(psCode, clientCode);
				for (clsStkPostingDtlModel ob : listStkPostDtl) {
					if (null != ob.getStrProdCode()) {
						ob.setStrPSCode(psCode);
						ob.setStrProdChar(" ");
						ob.setStrClientCode(clientCode);
						objStkPostService.funAddUpdateDtl(ob);
						flagDtlDataInserted = true;
						System.out.println(intt++);
					}
				}

				if (flagDtlDataInserted) {
					String strSACode = "";
					// String
					// strSACode=objGlobalFunctions.funGenerateStkAdjustement(objHdModel.getStrPSCode(),,request);
					if (objBean.getStrSACode() == null || objBean.getStrSACode().isEmpty()) {
						strSACode = objGlobalFunctions.funGenerateStkAdjustement(objHdModel.getStrPSCode(), request);
					} else {
						strSACode = objGlobalFunctions.funUpdateStkAdjustement(objHdModel.getStrPSCode(), objBean.getStrSACode(), request);
					}
					request.getSession().setAttribute("success", true);
					request.getSession().setAttribute("successMessage", "PS Code : ".concat(objHdModel.getStrPSCode()));
					request.getSession().setAttribute("rptStockPostingCode", objHdModel.getStrPSCode());
					if (strSACode != null && !strSACode.equals("")) {
						request.getSession().setAttribute("successMessageSA", "SA Code : ".concat(strSACode));
					}
				}
			}

			return new ModelAndView("redirect:/frmPhysicalStkPosting.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmPhysicalStkPosting?saddr=" + urlHits);
		}
	}

	/**
	 * Prepare Physical Stock Posting HdModel
	 * 
	 * @param objBean
	 * @param userCode
	 * @param clientCode
	 * @param SACode
	 * @param propCode
	 * @param startDate
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private clsStkPostingHdModel funPrepareModel(clsPhysicalStkPostBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsStkPostingHdModel objHdModel;
		if (objBean.getStrPSCode().length() == 0) {
			String strStkPosCode = objGlobalFunctions.funGenerateDocumentCode("frmPhysicalStkPosting", objBean.getDtPSDate(), request);
			objHdModel = new clsStkPostingHdModel(new clsStkPostingHdModel_ID(strStkPosCode, clientCode));
			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			List listStkPostHd = objStkPostService.funGetObject(objBean.getStrPSCode(), clientCode);
			if (listStkPostHd.size() == 0) {
				String strStkPosCode = objGlobalFunctions.funGenerateDocumentCode("frmPhysicalStkPosting", objBean.getDtPSDate(), request);
				objHdModel = new clsStkPostingHdModel(new clsStkPostingHdModel_ID(strStkPosCode, clientCode));
				objHdModel.setIntId(lastNo);
				objHdModel.setStrUserCreated(userCode);
				objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmPhysicalStkPosting", request)) {
					funSaveAudit(objBean.getStrPSCode(), "Edit", request);
				}

				objHdModel = new clsStkPostingHdModel(new clsStkPostingHdModel_ID(objBean.getStrPSCode(), clientCode));
			}
		}

		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Physical Stock Posting")) {
				res = true;
			}
		}
		if (res) {
			objHdModel.setStrAuthorise("No");
		} else {
			objHdModel.setStrAuthorise("Yes");
		}
		if (objBean.getStrSACode() != null) {
			objHdModel.setStrSACode(objBean.getStrSACode());
		} else {
			objHdModel.setStrSACode("");
		}

		objHdModel.setDtPSDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtPSDate()));
		objHdModel.setStrLocCode(objBean.getStrLocCode());
		objHdModel.setDblTotalAmt(objBean.getDblTotalAmt());
		objHdModel.setStrNarration(objBean.getStrNarration());
		;
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		//objHdModel.setStrConversionUOM(objGlobal.funIfNull(objBean.getStrConversionUOM(),"RecipeUOM",objBean.getStrConversionUOM()));
		objHdModel.setStrConversionUOM(objBean.getStrConversionUOM());
		if (objBean.getStrPhyStkFrom() != null) {
			objHdModel.setStrPhyStkFrom(objBean.getStrPhyStkFrom());
		} else {
			objHdModel.setStrPhyStkFrom("System");
		}

		return objHdModel;
	}

	/**
	 * Prepare physical Stock Posting DtlModel
	 * 
	 * @param listStkPostHd
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private clsPhysicalStkPostBean funPrepareBean(List listStkPostHd) {
		objGlobal = new clsGlobalFunctions();
		clsPhysicalStkPostBean objBean = new clsPhysicalStkPostBean();
		if (listStkPostHd.size() > 0) {
			Object[] ob = (Object[]) listStkPostHd.get(0);
			clsStkPostingHdModel stkPostHd = (clsStkPostingHdModel) ob[0];
			clsLocationMasterModel locMaster = (clsLocationMasterModel) ob[1];
			objBean.setStrPSCode(stkPostHd.getStrPSCode());
			objBean.setStrSACode(stkPostHd.getStrSACode());
			objBean.setDtPSDate(objGlobal.funGetDate("yyyy/MM/dd", stkPostHd.getDtPSDate()));
			objBean.setStrLocCode(stkPostHd.getStrLocCode());
			objBean.setStrLocName(locMaster.getStrLocName());
			objBean.setDblTotalAmt(stkPostHd.getDblTotalAmt());
			objBean.setStrNarration(stkPostHd.getStrNarration());
			objBean.setStrConversionUOM(stkPostHd.getStrConversionUOM());
		}
		return objBean;
	}

	/**
	 * Auditing function Start
	 * 
	 * @param psCode
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String psCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List listStkPostHd = objStkPostService.funGetObject(psCode, clientCode);
			objGlobal = new clsGlobalFunctions();
			if (!listStkPostHd.isEmpty()) {
				Object[] ob = (Object[]) listStkPostHd.get(0);
				clsStkPostingHdModel stkPHd = (clsStkPostingHdModel) ob[0];
				List listStkDtl = objStkPostService.funGetDtlList(psCode, clientCode);
				if (null != stkPHd) {
					if (null != listStkDtl && listStkDtl.size() > 0) {
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + stkPHd.getStrPSCode() + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");

						if (!list.isEmpty()) {
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(stkPHd);
							if (strTransMode.equalsIgnoreCase("Deleted")) {
								model.setStrTransCode(stkPHd.getStrPSCode());
							} else {
								model.setStrTransCode(stkPHd.getStrPSCode() + "-" + count);
							}

							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (int i = 0; i < listStkDtl.size(); i++) {
								Object[] ob1 = (Object[]) listStkDtl.get(i);
								clsStkPostingDtlModel stkPostDtl = (clsStkPostingDtlModel) ob1[0];
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(model.getStrTransCode());
								AuditMode.setStrProdCode(stkPostDtl.getStrProdCode());
								AuditMode.setDblQty(stkPostDtl.getDblPStock());
								AuditMode.setDblUnitPrice(stkPostDtl.getDblPrice());
								AuditMode.setDblCStock(stkPostDtl.getDblCStock());
								AuditMode.setStrRemarks("");
								AuditMode.setStrUOM("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode("");
								AuditMode.setStrTaxType("");
								AuditMode.setStrPICode("");
								AuditMode.setStrClientCode(stkPostDtl.getStrClientCode());
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
	 * Prepare Audit HdModel
	 * 
	 * @param stkPHd
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsStkPostingHdModel stkPHd) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (stkPHd != null) {
			AuditHdModel.setStrTransCode(stkPHd.getStrPSCode());
			AuditHdModel.setDtTransDate(stkPHd.getDtPSDate());
			AuditHdModel.setStrTransType("Physical Stk Posting");
			AuditHdModel.setStrLocCode(stkPHd.getStrLocCode());
			AuditHdModel.setStrUserCreated(stkPHd.getStrUserCreated());
			AuditHdModel.setDtDateCreated(stkPHd.getDtCreatedDate());
			AuditHdModel.setStrAuthorise(stkPHd.getStrAuthorise());
			AuditHdModel.setStrNarration("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrCode(stkPHd.getStrSACode());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrAgainst("");
		}
		return AuditHdModel;
	}

	/**
	 * Open Physical Stock Posting Report Form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmPhysicalStockPostingSlip", method = RequestMethod.GET)
	public ModelAndView funOpenPSPSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPhysicalStockPostingSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPhysicalStockPostingSlip", "command", new clsReportBean());
		}
	}

	/**
	 * Open Physical Stock Posting Report Slip
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptPhysicalStockPsostingSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String stockPostingCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReport(stockPostingCode, type, resp, req);
	}

	@RequestMapping(value = "/openRptPhysicalStockPostingSlip", method = RequestMethod.GET)
	private void funOpenReportOnSave(HttpServletResponse resp, HttpServletRequest req) {

		String stockPostingCode = req.getParameter("rptStockPostingCode").toString();
		req.getSession().removeAttribute("rptStockPostingCode");
		String type = "pdf";
		funCallReport(stockPostingCode, type, resp, req);
	}

	@RequestMapping(value = "/invokeRptPhyStkSlip", method = RequestMethod.GET)
	private void funCallReportOnClick(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String type = "pdf";
		funCallReport(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(String stockPostingCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPhysicalStockPosting.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = "select a.strPSCode,DATE_FORMAT(a.dtPSDate,'%d-%m-%Y') as dtPSDate,a.strLocCode,b.strLocName " + " from tblstockpostinghd a,tbllocationmaster b " + "where a.strclientcode='" + clientCode + "' and b.strclientCode='" + clientCode + "' and a.strLocCode=b.strLocCode " + "and a.strPSCode='" + stockPostingCode + "' ";

			String sqlDtlQuery = "select a.strProdCode,b.strProdName," + "b.strUOM,b.dblCostRM,b.strWtUOM,a.dblCStock,a.dblPStock," + "a.dblPStock-a.dblCStock as variance,(a.dblPStock-a.dblCStock)*b.dblCostRM as AjdValue " + " ,a.dblActualRate,(a.dblPStock-a.dblCStock)*a.dblActualRate  as actValue   " + "from tblstockpostingdtl a,tblproductmaster b where a.strProdCode=b.strProdCode  "
					+ " and a.strPSCode='" + stockPostingCode + "' " + "and a.strClientCode='" + clientCode + "' and  b.strClientCode='" + clientCode + "'";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPhyStkPost");
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
			hm.put("strFax", objSetup.getStrFax());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptPhysicalStockPsostingSlip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPhysicalStockPsostingSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

}
