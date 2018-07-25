package com.sanguine.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsStockAdjustmentBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;
import com.sanguine.model.clsStkAdjustmentHdModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.util.clsComparatorForOwnSorting;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStkAdjustmentController {
	final static Logger logger = Logger.getLogger(clsStkAdjustmentController.class);
	@Autowired
	private clsStkAdjustmentService objStkAdjService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsProductMasterService objProductMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	private clsGlobalFunctions objGlobal = null;

	/**
	 * Open Stock Adjustment Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmStockAdjustment", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmStockAdjustment");
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
			docCode = request.getParameter("authorizationSACode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}

		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationSACode", docCode);
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStockAdjustment_1", "command", new clsStockAdjustmentBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStockAdjustment", "command", new clsStockAdjustmentBean());
		} else {
			return null;
		}

	}

	/**
	 * Load Stock Adjustment Data
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmStockAdjustment1", method = RequestMethod.GET)
	public @ResponseBody clsStockAdjustmentBean funOpenFormWithSACode(Map<String, Object> model, HttpServletRequest req) {
		clsStockAdjustmentBean bean = new clsStockAdjustmentBean();
		String saCode = req.getParameter("SACode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List listStkAdjHd = objStkAdjService.funGetObject(saCode, clientCode);
		if (listStkAdjHd.isEmpty()) {
			bean.setStrSACode("Invalid Code");
			return bean;
		} else {
			objGlobal = new clsGlobalFunctions();
			bean = objGlobal.funPrepareStockAdjBean(listStkAdjHd);
			List listStkAdjDtl = objStkAdjService.funGetDtlList(saCode, clientCode);
			List<clsStkAdjustmentDtlModel> stkAdjDtlList = funPrepareStkAdjDtlModel(listStkAdjDtl);
			bean.setListStkAdjDtl(stkAdjDtlList);
			return bean;
		}

	}

	/**
	 * Save Stock Adjustment Data
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveStkAdjustment", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsStockAdjustmentBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		clsStkAdjustmentHdModel objHdModel = funPrepareModel(objBean, userCode, clientCode, propCode, startDate, req);
		objGlobal = new clsGlobalFunctions();
		if (!result.hasErrors()) {
			List<clsStkAdjustmentDtlModel> listStkAdjDtl = objBean.getListStkAdjDtl();
			if (null != listStkAdjDtl && listStkAdjDtl.size() > 0) {
				objStkAdjService.funAddUpdate(objHdModel);
				String saCode = objHdModel.getStrSACode();
				objStkAdjService.funDeleteDtl(saCode, clientCode);
				boolean flagDtlDataInserted = false;
				for (clsStkAdjustmentDtlModel ob : listStkAdjDtl) {

					if (null != ob.getStrProdCode()) {
						ob.setStrSACode(saCode);
						ob.setStrProdChar(" ");
						ob.setStrClientCode(clientCode);
						// if(ob.getStrWSLinkedProdCode()==null)
						// {
						// ob.setStrWSLinkedProdCode("");
						// }
						objStkAdjService.funAddUpdateDtl(ob);
						flagDtlDataInserted = true;

					}
				}
				if (flagDtlDataInserted) {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "SA Code : ".concat(objHdModel.getStrSACode()));
					req.getSession().setAttribute("rptSACode", objHdModel.getStrSACode());
				}
			}
			return new ModelAndView("redirect:/frmStockAdjustment.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmStockAdjustment?saddr=" + urlHits);
		}
	}

	/**
	 * Prepare Stock Adjustment DtlModel
	 * 
	 * @param listStkAdjDtl
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<clsStkAdjustmentDtlModel> funPrepareStkAdjDtlModel(List listStkAdjDtl) {
		List<clsStkAdjustmentDtlModel> stkAdjDtlList = new ArrayList<clsStkAdjustmentDtlModel>();

		for (int i = 0; i < listStkAdjDtl.size(); i++) {
			Object[] ob = (Object[]) listStkAdjDtl.get(i);
			clsStkAdjustmentDtlModel stkAdjDtl = (clsStkAdjustmentDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			clsStkAdjustmentDtlModel objStkAdjDtl = new clsStkAdjustmentDtlModel();

			double totalWt = prodMaster.getDblWeight() * stkAdjDtl.getDblQty();
			objStkAdjDtl.setStrSACode(stkAdjDtl.getStrSACode());
			objStkAdjDtl.setStrProdCode(stkAdjDtl.getStrProdCode());
			objStkAdjDtl.setStrProdName(prodMaster.getStrProdName());
			objStkAdjDtl.setStrProdType(prodMaster.getStrProdType());
			objStkAdjDtl.setStrPOSItemCode(prodMaster.getStrPartNo());
			objStkAdjDtl.setStrUOM(prodMaster.getStrUOM());
			objStkAdjDtl.setDblQty(stkAdjDtl.getDblQty());
			objStkAdjDtl.setDblRate(stkAdjDtl.getDblRate());
			objStkAdjDtl.setDblPrice(stkAdjDtl.getDblPrice());
			objStkAdjDtl.setDblWeight(prodMaster.getDblWeight());
			objStkAdjDtl.setDblTotalWt(totalWt);
			objStkAdjDtl.setStrType(stkAdjDtl.getStrType());
			objStkAdjDtl.setStrRemark(stkAdjDtl.getStrRemark());
			objStkAdjDtl.setStrProdChar(stkAdjDtl.getStrProdChar());
			objStkAdjDtl.setStrClientCode(stkAdjDtl.getStrClientCode());
			objStkAdjDtl.setStrWSLinkedProdCode(stkAdjDtl.getStrWSLinkedProdCode());
			stkAdjDtlList.add(objStkAdjDtl);
		}
		return stkAdjDtlList;
	}

	/**
	 * Prepare Stock Adjustment HdModel
	 * 
	 * @param objBean
	 * @param userCode
	 * @param clientCode
	 * @param propCode
	 * @param startDate
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private clsStkAdjustmentHdModel funPrepareModel(clsStockAdjustmentBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsStkAdjustmentHdModel objHdModel;
		if (objBean.getStrSACode().length() == 0) {
			String strStkCode = objGlobalFunctions.funGenerateDocumentCode("frmStockAdjustment", objBean.getDtSADate(), request);
			objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(strStkCode, clientCode));

			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			List listStkAdjHd = objStkAdjService.funGetObject(objBean.getStrSACode(), clientCode);
			if (listStkAdjHd.size() == 0) {
				String strStkCode = objGlobalFunctions.funGenerateDocumentCode("frmStockAdjustment", objBean.getDtSADate(), request);
				objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(strStkCode, clientCode));

				objHdModel.setIntId(lastNo);
				objHdModel.setStrUserCreated(userCode);
				objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmStockAdjustment", request)) {
					funSaveAudit(objBean.getStrSACode(), "Edit", request);
				}
				objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(objBean.getStrSACode(), clientCode));
			}
		}

		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Stock Adjustment")) {
				res = true;
			}
		}
		if (res) {
			objHdModel.setStrAuthorise("No");
		} else {
			objHdModel.setStrAuthorise("Yes");
		}

		objHdModel.setDtSADate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtSADate()));
		objHdModel.setStrLocCode(objBean.getStrLocCode());
		objHdModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), " ", objBean.getStrNarration()));
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objHdModel.setStrReasonCode(objBean.getStrReasonCode());
		objHdModel.setDblTotalAmt(objBean.getDblTotalAmt());
		objHdModel.setStrConversionUOM(objBean.getStrConversionUOM());
		return objHdModel;
	}

	/**
	 * Auditing function Start
	 * 
	 * @param saCode
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String saCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List listStkAdjHd = objStkAdjService.funGetObject(saCode, clientCode);
			objGlobal = new clsGlobalFunctions();
			if (!listStkAdjHd.isEmpty()) {
				Object[] ob = (Object[]) listStkAdjHd.get(0);
				clsStkAdjustmentHdModel stkAdjHd = (clsStkAdjustmentHdModel) ob[0];
				List listStkAdjDtl = objStkAdjService.funGetDtlList(saCode, clientCode);
				if (null != stkAdjHd) {
					if (null != listStkAdjDtl && listStkAdjDtl.size() > 0) {
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + stkAdjHd.getStrSACode() + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");

						if (!list.isEmpty()) {
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(stkAdjHd);
							if (strTransMode.equalsIgnoreCase("Deleted")) {
								model.setStrTransCode(stkAdjHd.getStrSACode());
							} else {
								model.setStrTransCode(stkAdjHd.getStrSACode() + "-" + count);
							}
							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (int i = 0; i < listStkAdjDtl.size(); i++) {
								Object[] ob1 = (Object[]) listStkAdjDtl.get(i);
								clsStkAdjustmentDtlModel stkAdjDtl = (clsStkAdjustmentDtlModel) ob1[0];
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(model.getStrTransCode());
								AuditMode.setStrProdCode(stkAdjDtl.getStrProdCode());
								AuditMode.setDblQty(stkAdjDtl.getDblQty());
								AuditMode.setDblUnitPrice(stkAdjDtl.getDblRate());
								AuditMode.setDblTotalPrice(stkAdjDtl.getDblPrice());
								AuditMode.setStrRemarks(stkAdjDtl.getStrRemark());
								AuditMode.setStrType(stkAdjDtl.getStrType());
								AuditMode.setStrClientCode(stkAdjDtl.getStrClientCode());
								AuditMode.setStrUOM("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode("");
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
	 * Prepare Audit HdModel
	 * 
	 * @param stkAdjHd
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsStkAdjustmentHdModel stkAdjHd) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (stkAdjHd != null) {
			AuditHdModel.setStrTransCode(stkAdjHd.getStrSACode());
			AuditHdModel.setDtTransDate(stkAdjHd.getDtSADate());
			AuditHdModel.setStrTransType("Stock Adjustment");
			AuditHdModel.setStrLocCode(stkAdjHd.getStrLocCode());
			AuditHdModel.setStrAuthorise(stkAdjHd.getStrAuthorise());
			AuditHdModel.setStrNarration(stkAdjHd.getStrNarration());
			AuditHdModel.setDblTotalAmt(stkAdjHd.getDblTotalAmt());
			AuditHdModel.setDtDateCreated(stkAdjHd.getDtCreatedDate());
			AuditHdModel.setStrUserCreated(stkAdjHd.getStrUserCreated());
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrCode(stkAdjHd.getStrReasonCode());
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
	 * Open StockAdjustment Report Form
	 * 
	 * @return
	 * @throws JRException
	 */

	@RequestMapping(value = "/frmStockAdjustmentSlip", method = RequestMethod.GET)
	public ModelAndView funOpenStkAdjSlipForm(Map<String, Object> model, HttpServletRequest request) {
		logger.info("Open slip jsp : frmStockAdjustmentSlip");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStockAdjustmentSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmStockAdjustmentSlip", "command", new clsReportBean());
		}

	}

	/**
	 * Calling Stock Adjustment Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptStockAdjustmentSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String stockAdjCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		// funCallReport(stockAdjCode,type,resp,req);
		if (objBean.getStrReportType().equals("List")) {
			funCallReportBeanWise(stockAdjCode, type, resp, req);
		} else {
			funCallReportRecipeWise(stockAdjCode, type, resp, req);
		}

	}

	@RequestMapping(value = "/openRptStockAdjustmentSlipSecondRpt", method = RequestMethod.GET)
	private void funReport2(HttpServletResponse resp, HttpServletRequest req) {
		String stockAdjCode = req.getParameter("rptSACode").toString();
		String type = "pdf";
		funCallReportRecipeWise(stockAdjCode, type, resp, req);

	}

	@RequestMapping(value = "/openRptStockAdjustmentSlip", method = RequestMethod.GET)
	private void funOpenReportOnSave(HttpServletResponse resp, HttpServletRequest req) {
		String stockAdjCode = req.getParameter("rptSACode").toString();
		req.getSession().removeAttribute("rptSACode");
		String type = "pdf";
		// funCallReport(stockAdjCode,type,resp,req);
		funCallReportBeanWise(stockAdjCode, type, resp, req);
		// funCallReportRecipeWise(stockAdjCode,type,resp,req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReportBeanWise(String stockAdjCode, String type, HttpServletResponse resp, HttpServletRequest req) {
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
			List listStkAdjHd = objStkAdjService.funGetObject(stockAdjCode, clientCode);
			Object[] ob = (Object[]) listStkAdjHd.get(0);
			clsStkAdjustmentHdModel objStkAdjHd = (clsStkAdjustmentHdModel) ob[0];
			objStkAdjHd.setDtSADate(objGlobal.funGetDate("yyyy/MM/dd", objStkAdjHd.getDtSADate()));
			String reportName = "";
			if (type.equalsIgnoreCase("Text")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentTextSlip.jrxml");
			} else {
				reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentBeanSlip.jrxml");
			}

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String strSACode = "", dtSADate = "", strLocCode = "", strLocName = "", strNarration = "", strAuthorise = "";

			String sqlHDQuery = "select a.strSACode as strSACode,a.dtSADate,a.strLocCode,b.strLocName,a.strNarration,a.strAuthorise " + " from tblstockadjustmenthd a,tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strSACode='" + stockAdjCode + "' and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "'";

			List listProdHD = objGlobalFunctionsService.funGetDataList(sqlHDQuery, "sql");
			for (int j = 0; j < listProdHD.size(); j++) {
				Object[] hdArr = (Object[]) listProdHD.get(j);
				{
					strSACode = hdArr[0].toString();
					dtSADate = hdArr[1].toString();
					strLocCode = hdArr[2].toString();
					strLocName = hdArr[3].toString();
					strNarration = hdArr[4].toString();
					strAuthorise = hdArr[5].toString();
				}
			}

			String sqlDtlQuery = "select a.strProdCode,a.strRemark,b.strProdName, " + "b.strProdType,sum(a.dblQty),b.strUOM,a.strType,a.dblRate,a.strDisplayQty,a.dblPrice from tblstockadjustmentdtl a, " + "tblproductmaster b where a.strProdCode=b.strProdCode and  a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and a.strSACode='" + stockAdjCode + "' "
					+ " group by a.strProdCode order by b.strProdName";

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlDtlQuery, "sql");
			List<clsStkAdjustmentDtlModel> listDtlBean = new ArrayList<clsStkAdjustmentDtlModel>();
			for (int j = 0; j < listProdDtl.size(); j++) {
				clsStkAdjustmentDtlModel objModelDtl = new clsStkAdjustmentDtlModel();
				Object[] prodArr = (Object[]) listProdDtl.get(j);
				{
					objModelDtl.setStrProdCode(prodArr[0].toString());
					objModelDtl.setStrProdName(prodArr[2].toString());
					objModelDtl.setStrProdType(prodArr[3].toString());
					objModelDtl.setDblQty(Double.parseDouble(prodArr[4].toString()));
					objModelDtl.setStrUOM(prodArr[5].toString());
					objModelDtl.setStrType(prodArr[6].toString());
					objModelDtl.setDblRate(Double.parseDouble(prodArr[7].toString()));
					objModelDtl.setStrDisplayQty(funGetDispalyUOMQty(prodArr[0].toString(), Double.parseDouble(prodArr[4].toString()), clientCode));
					objModelDtl.setDblPrice(Double.parseDouble(prodArr[9].toString()));
					listDtlBean.add(objModelDtl);
				}
			}

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
			hm.put("strSACode", strSACode);
			hm.put("dtSADate", dtSADate);
			hm.put("strLocCode", strLocCode);
			hm.put("strLocName", strLocName);
			hm.put("strNarration", strNarration);
			hm.put("strAuthorise", strAuthorise);
			hm.put("listDtlBean", listDtlBean);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (type.trim().equalsIgnoreCase("pdf")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkAjdustmentSlip." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {

					if (type.trim().equalsIgnoreCase("Text")) {
						ServletOutputStream outStream = resp.getOutputStream();
						JRTextExporter exporterTxt = new JRTextExporter();
						exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporterTxt.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Float(6.55));// 6.55
																											// //6
						exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Float(11.9)); // 11//10
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkAjdustmentSlip_" + stockAdjCode + "_" + userCode + ".txt");
						resp.setContentType("application/text");
						exporterTxt.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					} else {

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xlsx");
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkAjdustmentSlip." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				try {
					resp.getWriter().append("No Record Found");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReportRecipeWise(String stockAdjCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		logger.info("In funCallReportRecipeWise : stockAdjCode"+ stockAdjCode);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();

		try {
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			List listStkAdjHd = objStkAdjService.funGetObject(stockAdjCode, clientCode);
			Object[] ob = (Object[]) listStkAdjHd.get(0);
			clsStkAdjustmentHdModel objStkAdjHd = (clsStkAdjustmentHdModel) ob[0];
			objStkAdjHd.setDtSADate(objGlobal.funGetDate("yyyy/MM/dd", objStkAdjHd.getDtSADate()));
			String reportName = "";
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String strSACode = "", dtSADate = "", strLocCode = "", strLocName = "", strNarration = "", strAuthorise = "";

			List<clsStkAdjustmentDtlModel> listDtlBean = new ArrayList<clsStkAdjustmentDtlModel>();
			List<clsStkAdjustmentDtlModel> listProcuredProduct = new ArrayList<clsStkAdjustmentDtlModel>();

			String sqlHDQuery = "select a.strSACode as strSACode,a.dtSADate,a.strLocCode,b.strLocName,a.strNarration,a.strAuthorise " + " from tblstockadjustmenthd a,tbllocationmaster b,tblpossalesdtl c " + " where a.strSACode=c.strSACode and a.strLocCode=b.strLocCode and a.strSACode='" + stockAdjCode + "' and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "' group by a.strSACode ";
			logger.info(" sqlHDQuery : "+ sqlHDQuery);
			List listProdHD = objGlobalFunctionsService.funGetDataList(sqlHDQuery, "sql");
			for (int j = 0; j < listProdHD.size(); j++) {
				Object[] hdArr = (Object[]) listProdHD.get(j);
				{
					strSACode = hdArr[0].toString();
					dtSADate = hdArr[1].toString();
					strLocCode = hdArr[2].toString();
					strLocName = hdArr[3].toString();
					strNarration = hdArr[4].toString();
					strAuthorise = hdArr[5].toString();
				}
			}

			// List<clsStkAdjustmentDtlModel> listDateWiseDtl =
			// objStkAdjService.funGetDatewiseDtlList("2017-05-01",
			// "2017-05-31", clientCode);

			List<clsStkAdjustmentDtlModel> listDtl = objStkAdjService.funGetDtlList(stockAdjCode, clientCode);
			HashSet<String> hsRemarkProd = new HashSet<String>();

			/*for (Object dtArr : listDtl) {
				Object[] objArr = (Object[]) dtArr;
				clsStkAdjustmentDtlModel objAstkAdj = (clsStkAdjustmentDtlModel) objArr[0];

				String remark = objAstkAdj.getStrRemark();
				String[] spRemark = remark.split(":");
				hsRemarkProd.add(spRemark[3] + "#" + objAstkAdj.getStrType() + "#" + spRemark[1] + "#" + spRemark[5] + "#" + objAstkAdj.getDblRate() + "#" + spRemark[7]);
			//	In hsRemarkProd ("ParentProdCode#Out#BOMCode#parentQty#RateofParentProd#ProductName")
			}*/
			
			String sqlParentProductDtl=" select a.strWSItemCode,'Out',ifnull(b.strBOMCode,a.strWSItemCode),a.dblQuantity,a.dblRate,a.strPOSItemName,(a.dblAmount-a.dblPercentAmt) as ParentDiscountedAmt "
					+ " from tblpossalesdtl a "
					+ "   left outer join tblbommasterhd b on a.strWSItemCode=b.strParentCode where  a.strSACode='" + stockAdjCode + "' and a.strClientCode='" + clientCode + "' ";

			List listParentChildProductDtl = objGlobalFunctionsService.funGetDataList(sqlParentProductDtl, "sql");
			for (int j = 0; j < listParentChildProductDtl.size(); j++) {
				Object[] objParentChild = (Object[]) listParentChildProductDtl.get(j);
				{
					hsRemarkProd.add(objParentChild[0].toString() + "#" + objParentChild[1].toString() + "#" + objParentChild[2].toString() + "#" + objParentChild[3].toString() + "#" + objParentChild[4].toString() + "#" + objParentChild[5].toString() + "#"  +objParentChild[6].toString() );
				}
			}
			for (String strProdsINOUT : hsRemarkProd) {
				String[] spCodeInOutQty = strProdsINOUT.split("#");
				String strProd = spCodeInOutQty[0];
				String inOut = spCodeInOutQty[1];
				String strBOMCode = spCodeInOutQty[2];
				double dblmainQty = Double.parseDouble(spCodeInOutQty[3]);
				double dblParentRate = Double.parseDouble(spCodeInOutQty[4]);
				String itemName = spCodeInOutQty[5];
				double dblParentAmtWithDiscountedAmt = Double.parseDouble(spCodeInOutQty[6]);
				String sqlRecipeQuery = " select b.strChildCode,b.dblQty,b.strUOM from tblbommasterhd a,tblbommasterdtl b where a.strBOMCode=b.strBOMCode " + " and a.strParentCode='" + strProd + "' ";
				List listRecipeChildProd = objGlobalFunctionsService.funGetDataList(sqlRecipeQuery, "sql");

				if (listRecipeChildProd.size() > 0) {
					for (int i = 0; i < listRecipeChildProd.size(); i++) {
						Object[] objBOM = (Object[]) listRecipeChildProd.get(i);
						String strRecChild = objBOM[0].toString();
						{
//							String parentProd = "%" + strProd + "%";
							String parentProd =  strProd;
							String sqlChildProdQuery = "  select sum(a.dblQty), sum(a.dblPrice) from tblstockadjustmentdtl a ,tblpossalesdtl b  where a.strSACode=b.strSACode and a.strProdCode='" + strRecChild + "' and  b.strWSItemCode=a.strWSLinkedProdCode  and  b.strWSItemCode='"+parentProd+"'  and a.strSACode='"+stockAdjCode+"' and a.strType='" + inOut + "' group by b.strWSItemCode; ";
								// old query			+  " select sum(a.dblQty), sum(a.dblPrice) from tblstockadjustmentdtl a " + " where a.strProdCode='" + strRecChild + "' " + " and a.strRemark like '" + parentProd + "' and a.strSACode='" + stockAdjCode + "' and a.strType='" + inOut + "' ";
							if (strProd.equalsIgnoreCase("P0000450")) {
								System.out.println("");
							}

							List listChildProdQuery = objGlobalFunctionsService.funGetDataList(sqlChildProdQuery, "sql");
							for (int j = 0; j < listChildProdQuery.size(); j++) {
								Object[] objChildQtyPrice = (Object[]) listChildProdQuery.get(j);
								{
									clsStkAdjustmentDtlModel objModel = new clsStkAdjustmentDtlModel();

									objModel.setStrBOMCode(strBOMCode);
									objModel.setStrSACode(strSACode);
									objModel.setStrParentCode(strProd);
									clsProductMasterModel objParentprod = objProductMasterService.funGetObject(strProd, clientCode);
									objModel.setStrParentName(objParentprod.getStrProdName());
									objModel.setDblParentQty(dblmainQty);
									objModel.setStrProdCode(strRecChild);
									objModel.setDblParentRate(dblParentRate);
									objModel.setDblParentDiscountedAmt(dblParentAmtWithDiscountedAmt);
									objModel.setStrPartNo(objParentprod.getStrPartNo());
									System.out.println(strBOMCode + "   " + objParentprod.getStrProdName());
									clsProductMasterModel objChildprod = objProductMasterService.funGetObject(strRecChild, clientCode);
									objModel.setStrProdName(objChildprod.getStrProdName());
									objModel.setDblQty(Double.parseDouble(objChildQtyPrice[0].toString()));
									objModel.setStrDisplayQty(funGetDispalyUOMQty(strRecChild, Double.parseDouble(objChildQtyPrice[0].toString()), clientCode));
									objModel.setDblRate(objChildprod.getDblCostRM());
									objModel.setDblPrice(Double.parseDouble(objChildQtyPrice[1].toString()));
									objModel.setStrType(inOut);
									objModel.setDblRecipeConversion(Double.parseDouble(objBOM[1].toString()));
									objModel.setStrRecipeUOM(objBOM[2].toString());
									objModel.setDblStandardPrice(objChildprod.getDblUnitPrice());
									objModel.setDblStandardAmt(Double.parseDouble(objChildQtyPrice[0].toString()) * objChildprod.getDblUnitPrice());
									listDtlBean.add(objModel);
								}

							}

						}

					}
				} else {
					clsStkAdjustmentDtlModel objModel = new clsStkAdjustmentDtlModel();
					double qty = 0.00;
					double price = 0.00;
					double parentRate = 0.00;

					objModel.setStrSACode(strSACode);
					objModel.setStrParentCode(itemName);
					objModel.setStrParentName(itemName);
					objModel.setStrBOMCode("One to one");
					objModel.setDblParentRate(dblParentRate);
					objModel.setDblParentDiscountedAmt(dblParentAmtWithDiscountedAmt);
					objModel.setStrPartNo("");
					objModel.setStrProdCode(strProd);
					clsProductMasterModel objChildprod = objProductMasterService.funGetObject(strProd, clientCode);
					objModel.setStrProdName(objChildprod.getStrProdName());
					for (Object dtArr : listDtl) {
						Object[] objArr = (Object[]) dtArr;
						clsStkAdjustmentDtlModel objAstkAdj = (clsStkAdjustmentDtlModel) objArr[0];
						if (objAstkAdj.getStrWSLinkedProdCode().equals(strProd)) {
							qty = qty + objAstkAdj.getDblQty();
							objModel.setDblQty(qty);
							objModel.setStrDisplayQty(funGetDispalyUOMQty(strProd, qty, clientCode));
							objModel.setStrType(objAstkAdj.getStrType());
							objModel.setDblRate(objChildprod.getDblCostRM());
							price = price + objAstkAdj.getDblPrice();
							objModel.setDblPrice(price);
							objModel.setDblRecipeConversion(objChildprod.getDblRecipeConversion());
							objModel.setStrRecipeUOM(objChildprod.getStrRecipeUOM());
							parentRate = objAstkAdj.getDblRate();
							objModel.setDblParentRate(parentRate);
							objModel.setDblParentQty(qty);
							objModel.setDblStandardPrice(objChildprod.getDblUnitPrice());
							objModel.setDblStandardAmt(qty * objChildprod.getDblUnitPrice());

						}

					}
					listProcuredProduct.add(objModel);

				}
			}

			Collections.sort(listDtlBean, new clsComparatorForOwnSorting());

			for (clsStkAdjustmentDtlModel objModel : listProcuredProduct) {
				listDtlBean.add(objModel);
			}

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
			hm.put("strLocCode", strLocCode);
			hm.put("strSACode", strSACode);
			hm.put("strLocName", strLocName);
			hm.put("strAuthorise", strAuthorise);
			hm.put("listDtlBean", listDtlBean);
			reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentRecipeWiseSlip.jrxml");
			hm.put("dtSADate", objGlobal.funGetDate("dd-MM-yyyy", dtSADate));
			// hm.put("dtToSADate", objGlobal.funGetDate("dd-MM-yyyy",
			// dtSADate));
			hm.put("strNarration", strNarration);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (type.trim().equalsIgnoreCase("pdf")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkAjdustmentSlipRecipeWise." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkAjdustmentSlipRecipeWise." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				try {
					resp.getWriter().append("No Record Found");
				} catch (IOException e1) {
					logger.info("Error in jRropt", e1);
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private String funGetDispalyUOMQty(String strProdCode, double dblQty, String clientCode) {
		String strDispQty = "";
		DecimalFormat df3Zeors = new DecimalFormat("0.000");
		clsProductMasterModel objProdModel = objProductMasterService.funGetObject(strProdCode, clientCode);
		if (dblQty < 1) {
			double qtytemp = Double.parseDouble(df3Zeors.format(dblQty * objProdModel.getDblRecipeConversion()).toString());
			strDispQty = qtytemp + " " + objProdModel.getStrRecipeUOM();

		} else {
			Double qty = dblQty;
			String[] spqty = (qty.toString()).split("\\.");
			double lowest = qty - Double.parseDouble(spqty[0]);
			double qtytemp = Double.parseDouble(df3Zeors.format(lowest * objProdModel.getDblRecipeConversion()).toString());
			strDispQty = spqty[0] + " " + objProdModel.getStrReceivedUOM() + "." + qtytemp + " " + objProdModel.getStrRecipeUOM();
		}

		return strDispQty;
	}

	@RequestMapping(value = "/frmStockAdjustmentMonthly", method = RequestMethod.GET)
	public ModelAndView funOpenStkAdjMonthlyForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStockAdjustmentMonthly_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmStockAdjustmentMonthly", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/rptStockAdjustmentMonthly", method = RequestMethod.GET)
	private void funReportMonthly(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String dtFromDate = objBean.getDtFromDate();
		String dtToDate = objBean.getDtToDate();
		String type = objBean.getStrDocType();
		String strLocCode = objBean.getStrLocationCode();
		String strLocName = "";

		String dtFDate = dtFromDate.split("-")[2] + "-" + dtFromDate.split("-")[1] + "-" + dtFromDate.split("-")[0];
		String dtTDate = dtToDate.split("-")[2] + "-" + dtToDate.split("-")[1] + "-" + dtToDate.split("-")[0];

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		try {
			String sqlDtlQuery = "";
			String reportName = "";
			String strSACode = "", dtSADate = "", strNarration = "", strAuthorise = "";
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(strLocCode, clientCode);
			if (objLocCode != null) {
				strLocName = objLocCode.getStrLocName();
			}

			if (objBean.getStrReportType().equals("Physical Stock") || objBean.getStrReportType().equals("Manually")) {

				if (objBean.getStrReportType().equals("Physical Stock")) {
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentBeanSlip.jrxml");

					strSACode = "";
					dtSADate = objBean.getDtFromDate() + " To " + objBean.getDtToDate();
					strNarration = "Auto Generated By Physical Stock Posting";
					strAuthorise = "";

					sqlDtlQuery = " SELECT b.strProdCode,b.strRemark,c.strProdName, c.strProdType, " + " SUM(b.dblQty),c.strUOM,b.strType,  b.dblRate,b.strDisplayQty,b.dblPrice " + " FROM tblstockadjustmenthd a,tblstockadjustmentdtl b, tblproductmaster c " + " WHERE a.strSACode=b.strSACode and Date(a.dtSADate) between '" + dtFDate + "' and '" + dtTDate + "' "
							+ " and b.strProdCode=c.strProdCode AND b.strClientCode='" + clientCode + "' " + " and b.strRemark like '%Physical Stock%' AND b.strClientCode='" + clientCode + "'  ";
					if (!strLocCode.equals("")) {
						sqlDtlQuery += " and a.strLocCode='" + strLocCode + "' ";
					}
					sqlDtlQuery += " GROUP BY b.strProdCode,b.strType ORDER BY c.strProdName  ";
				}
				if (objBean.getStrReportType().equals("Manually")) {
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentBeanSlip.jrxml");
					strSACode = "";
					dtSADate = objBean.getDtFromDate() + " To " + objBean.getDtToDate();
					strNarration = "Stock Adjustment Manually";
					strAuthorise = "";

					sqlDtlQuery = " SELECT b.strProdCode,b.strRemark,c.strProdName, c.strProdType,  SUM(b.dblQty), " + " c.strUOM,b.strType, b.dblRate,b.strDisplayQty,b.dblPrice " + " FROM tblstockadjustmenthd a,tblstockadjustmentdtl b, tblproductmaster c " + " WHERE a.strSACode=b.strSACode and " + " Date(a.dtSADate) between '" + dtFDate + "' and '" + dtTDate + "' "
							+ " and b.strProdCode=c.strProdCode AND b.strClientCode='" + clientCode + "' " + " AND b.strClientCode='" + clientCode + "' " + " and a.strNarration not in (  select ph1.strNarration   from tblstockadjustmenthd ph1 " + " where Date(ph1.dtSADate) between '" + dtFDate + "' and '" + dtTDate + "'  "
							+ " and (ph1.strNarration like '%Physical Stock%' or ph1.strNarration like '%Sale Data%' " + " or ph1.strNarration like '%Sales Data%' AND ph1.strClientCode='" + clientCode + "' )) " + " GROUP BY b.strProdCode,b.strType " + " ORDER BY a.strSACode; ";
				}
				List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlDtlQuery, "sql");
				List<clsStkAdjustmentDtlModel> listDtlBean = new ArrayList<clsStkAdjustmentDtlModel>();
				for (int j = 0; j < listProdDtl.size(); j++) {
					clsStkAdjustmentDtlModel objModelDtl = new clsStkAdjustmentDtlModel();
					Object[] prodArr = (Object[]) listProdDtl.get(j);
					{
						objModelDtl.setStrProdCode(prodArr[0].toString());
						objModelDtl.setStrProdName(prodArr[2].toString());
						objModelDtl.setStrProdType(prodArr[3].toString());
						objModelDtl.setDblQty(Double.parseDouble(prodArr[4].toString()));
						objModelDtl.setStrUOM(prodArr[5].toString());
						objModelDtl.setStrType(prodArr[6].toString());
						objModelDtl.setDblRate(Double.parseDouble(prodArr[7].toString()));
						objModelDtl.setStrDisplayQty(funGetDispalyUOMQty(prodArr[0].toString(), Double.parseDouble(prodArr[4].toString()), clientCode));
						objModelDtl.setDblPrice(Double.parseDouble(prodArr[9].toString()));
						listDtlBean.add(objModelDtl);
					}
				}
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
				hm.put("strSACode", "");
				hm.put("dtSADate", dtSADate);
				hm.put("strLocCode", strLocCode);
				hm.put("strLocName", strLocName);
				hm.put("strNarration", strNarration);
				hm.put("strAuthorise", strAuthorise);
				hm.put("listDtlBean", listDtlBean);

				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);

				JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				jprintlist.add(jp);

				if (jprintlist.size() > 0) {
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (type.trim().equalsIgnoreCase("pdf")) {
						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkAjdustmentSlip." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					} else {

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xlsx");
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkAjdustmentSlip." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					try {
						resp.getWriter().append("No Record Found");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			if (objBean.getStrReportType().equals("POS") && objBean.getStrReportView().equalsIgnoreCase("Recipe")) {
				reportName = reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentRecipeWiseMonthly.jrxml");

				List<clsStkAdjustmentDtlModel> listDtlBean = new ArrayList<clsStkAdjustmentDtlModel>();
				List<clsStkAdjustmentDtlModel> listProcuredProduct = new ArrayList<clsStkAdjustmentDtlModel>();

				String sqlHDQuery = " select a.strNarration,a.strAuthorise from tblstockadjustmenthd a,tblpossalesdtl b " + " where a.strSACode=b.strSACode and a.dtSADate between '" + dtFDate + "' and '" + dtTDate + "' " + " and LENGTH(a.strNarration)>0 " +  " and a.strClientCode='" + clientCode + "' limit 1;";

				List listProdHD = objGlobalFunctionsService.funGetDataList(sqlHDQuery, "sql");
				for (int j = 0; j < listProdHD.size(); j++) {
					Object[] hdArr = (Object[]) listProdHD.get(j);
					{
						strNarration = hdArr[0].toString();
						strAuthorise = hdArr[1].toString();
					}
				}

				// List<clsStkAdjustmentDtlModel> listDtl =
				// objStkAdjService.funGetDatewiseDtlList(dtFDate, dtTDate,
				// clientCode);

				List<clsStkAdjustmentDtlModel> listGroupedDtl = objStkAdjService.funGetReportMonthlyDatewiseDtlList(dtFDate, dtTDate, clientCode);
				// List<clsStkAdjustmentDtlModel> listDtl =
				// objStkAdjService.funGetDtlList(stockAdjCode, clientCode);
				HashMap<String, clshmReportBean> hmRemarkProd = new HashMap<String, clshmReportBean>();

				List<clsStkAdjustmentDtlModel> listSingleDtl = new ArrayList<clsStkAdjustmentDtlModel>();
				for (clsStkAdjustmentDtlModel objAstkAdj : listGroupedDtl) {
					logger.info("StockAdjDtl Bean from db :"+objAstkAdj);
					System.out.println("StockAdjDtl Bean from db :"+objAstkAdj);
					String remark = objAstkAdj.getStrRemark();
				//	String[] spRemark = remark.split(":");
					String strCheckkey = objAstkAdj.getStrWSLinkedProdCode() + "#" + objAstkAdj.getStrSACode() + "#" + objAstkAdj.getStrParentName();

					if (hmRemarkProd.containsKey(objAstkAdj.getStrWSLinkedProdCode() + "#" + objAstkAdj.getStrParentName())) {
						clshmReportBean objBeanHm = hmRemarkProd.get(objAstkAdj.getStrWSLinkedProdCode() + "#" + objAstkAdj.getStrParentName());
						String mapChekKey = objBeanHm.getStrPraentProdCode() + "#" + objBeanHm.getStrSACode() + "#" + objAstkAdj.getStrParentName();
						if (!strCheckkey.equals(mapChekKey)) {
							/*
							 * if(objBeanHm.getStrPraentProdCode().equalsIgnoreCase
							 * ("P0000776")) { System.out.println(""); }
							 */

							objBeanHm.setDblQty(objBeanHm.getDblQty() + objAstkAdj.getDblParentQty());
							objBeanHm.setStrSACode(objBeanHm.getStrSACode());
							objBeanHm.setDblParentAmtWithDiscountedAmt(objBeanHm.getDblParentAmtWithDiscountedAmt()+objAstkAdj.getDblParentDiscountedAmt());
							hmRemarkProd.put(objBeanHm.getStrPraentProdCode() + "#" + objAstkAdj.getStrParentName(), objBeanHm);
						}
					} else {
						clshmReportBean objBeanHm = new clshmReportBean();
						objBeanHm.setStrPraentProdCode(objAstkAdj.getStrWSLinkedProdCode());
						objBeanHm.setStrBOMCode(objAstkAdj.getStrBOMCode());
						objBeanHm.setStrItemName(objAstkAdj.getStrParentName());
						objBeanHm.setStrInOut("Out");
						objBeanHm.setDblprice(objAstkAdj.getDblPrice());
						objBeanHm.setDblRate(objAstkAdj.getDblRate());
						objBeanHm.setDblQty(objAstkAdj.getDblParentQty());
						objBeanHm.setStrSACode(objAstkAdj.getStrSACode());
						objBeanHm.setDblParentAmtWithDiscountedAmt(objAstkAdj.getDblParentDiscountedAmt());
						hmRemarkProd.put(objBeanHm.getStrPraentProdCode() + "#" + objAstkAdj.getStrParentName(), objBeanHm);
					}
				}

				String[] spkkk = { "" };
				for (Map.Entry<String, clshmReportBean> entry : hmRemarkProd.entrySet()) {

					clshmReportBean objBeanHm = entry.getValue();

					// String[] spCodeInOutQty =strProdsINOUT.split("#");
					String strProd = objBeanHm.getStrPraentProdCode();
					/*
					 * if(strProd.equalsIgnoreCase("P0000776")) {
					 * System.out.println(""); }
					 */
					String inOut = objBeanHm.getStrInOut();
					String strBOMCode = objBeanHm.getStrBOMCode();
					double dblmainQty = objBeanHm.getDblQty();
					double dblParentRate = objBeanHm.getDblRate();
					String itemName = objBeanHm.getStrItemName();
					double dblParentAmtWithDis = objBeanHm.getDblParentAmtWithDiscountedAmt();
					List listRecipeChildProd = new ArrayList<Object[]>();
//					String strProductName = objProductMasterService.funGetProductName(strProd, clientCode);
//					if (strProductName.equalsIgnoreCase(itemName)) {
						String sqlRecipeQuery = " select b.strChildCode,b.dblQty,b.strUOM from tblbommasterhd a,tblbommasterdtl b,tblproductmaster c " + " where a.strBOMCode=b.strBOMCode and a.strParentCode=c.strProdCode " + " and a.strParentCode='" + strProd + "'and c.strProdName='" + itemName + "' ";
						listRecipeChildProd = objGlobalFunctionsService.funGetDataList(sqlRecipeQuery, "sql");
						if (listRecipeChildProd.size() > 0) {
							for (int i = 0; i < listRecipeChildProd.size(); i++) {
								Object[] objBOM = (Object[]) listRecipeChildProd.get(i);
								String strRecChild = objBOM[0].toString();
								{
									String parentProd = strProd;
									String positemName = "%" + itemName + "%";
									/*String sqlChildProdQuery = " select sum(a.dblQty), sum(a.dblPrice) " + " from tblstockadjustmentdtl a,tblstockadjustmenthd b  " + " where a.strSACode=b.strSACode and b.dtSADate between '" + dtFDate + "' and '" + dtTDate + "' " + "  and a.strProdCode='" + strRecChild + "' and b.strNarration like '%Sales Data%' " + "  and a.strRemark like '" + positemName
											+ "' and a.strWSLinkedProdCode='" + parentProd + "' " + " and a.strType='" + inOut + "'  ";*/
									String sqlChildProdQuery = " select sum(a.dblQty), sum(a.dblPrice) " + " from tblstockadjustmentdtl a,tblstockadjustmenthd b  ,tblpossalesdtl c   " 
											+ " where a.strSACode=b.strSACode and a.strSACode=c.strSACode and a.strWSLinkedProdCode=c.strWSItemCode "
											+ " and b.dtSADate between '" + dtFDate + "' and '" + dtTDate + "' " 
											+ "  and a.strProdCode='" + strRecChild + "' group by a.strProdCode,a.strWSLinkedProdCode " 
											+ " and a.strWSLinkedProdCode='" + parentProd + "' " + " and a.strType='" + inOut + "'  ";
									/*
									 * if(strProd.equalsIgnoreCase("P0000776"))
									 * { System.out.println(""); }
									 */

									List listChildProdQuery = objGlobalFunctionsService.funGetDataList(sqlChildProdQuery, "sql");
									for (int j = 0; j < listChildProdQuery.size(); j++) {
										Object[] objChildQtyPrice = (Object[]) listChildProdQuery.get(j);
										{
											clsStkAdjustmentDtlModel objModel = new clsStkAdjustmentDtlModel();
											objModel.setStrBOMCode(strBOMCode);
											objModel.setStrSACode(strSACode);
											objModel.setStrParentCode(strProd);
											clsProductMasterModel objParentprod = objProductMasterService.funGetObject(strProd, clientCode);
											objModel.setStrParentName(objParentprod.getStrProdName());
											objModel.setDblParentQty(dblmainQty);
											objModel.setStrProdCode(strRecChild);
											objModel.setDblParentRate(dblParentRate);
											objModel.setStrPartNo(objParentprod.getStrPartNo());
											clsProductMasterModel objChildprod = objProductMasterService.funGetObject(strRecChild, clientCode);
											objModel.setStrProdName(objChildprod.getStrProdName());
											objModel.setDblQty(Double.parseDouble(objChildQtyPrice[0].toString()));
											objModel.setStrDisplayQty(funGetDispalyUOMQty(strRecChild, Double.parseDouble(objChildQtyPrice[0].toString()), clientCode));
											objModel.setDblRate(objChildprod.getDblCostRM());
											objModel.setDblPrice(Double.parseDouble(objChildQtyPrice[1].toString()));
											objModel.setStrType(inOut);
											objModel.setDblRecipeConversion(Double.parseDouble(objBOM[1].toString()));
											objModel.setStrRecipeUOM(objBOM[2].toString());
											objModel.setDblStandardPrice(objChildprod.getDblUnitPrice());
											objModel.setDblStandardAmt(Double.parseDouble(objChildQtyPrice[0].toString()) * objChildprod.getDblUnitPrice());
											objModel.setDblParentDiscountedAmt(dblParentAmtWithDis);
											listDtlBean.add(objModel);
										}

									}

								}

							}
						} else {
							clsStkAdjustmentDtlModel objModel = new clsStkAdjustmentDtlModel();
							double qty = 0.00;
							double price = 0.00;
							double parentRate = 0.00;

							objModel.setStrSACode(strSACode);
							objModel.setStrParentCode(itemName);
							objModel.setStrParentName(itemName);
							objModel.setStrBOMCode("One to one");
							objModel.setDblParentRate(dblParentRate);
							objModel.setStrPartNo("");
							objModel.setStrProdCode(strProd);
							objModel.setDblParentDiscountedAmt(dblParentAmtWithDis);
							clsProductMasterModel objChildprod = objProductMasterService.funGetObject(strProd, clientCode);
							objModel.setStrProdName(objChildprod.getStrProdName());
							for (Map.Entry<String, clshmReportBean> enties : hmRemarkProd.entrySet()) {

								clshmReportBean objBeanHmSingle = enties.getValue();
								if (objBeanHmSingle.getStrPraentProdCode().equals(strProd)) {
									qty = qty + objBeanHmSingle.getDblQty();
									objModel.setDblQty(qty);
									objModel.setStrDisplayQty(funGetDispalyUOMQty(strProd, qty, clientCode));
									objModel.setStrType(objBeanHmSingle.getStrInOut());
									objModel.setDblRate(objChildprod.getDblCostRM());
									price = price + objBeanHmSingle.getDblprice();
									objModel.setDblPrice(price);
									objModel.setDblRecipeConversion(objChildprod.getDblRecipeConversion());
									objModel.setStrRecipeUOM(objChildprod.getStrRecipeUOM());
									parentRate = objBeanHmSingle.getDblRate();
									objModel.setDblParentRate(parentRate);
									objModel.setDblParentQty(qty);
									objModel.setDblStandardPrice(objChildprod.getDblUnitPrice());
									objModel.setDblStandardAmt(qty * objChildprod.getDblUnitPrice());

								}

							}
							listProcuredProduct.add(objModel);

						}
//					}
				}

				Collections.sort(listDtlBean, new clsComparatorForOwnSorting());

				for (clsStkAdjustmentDtlModel objModel : listProcuredProduct) {
					listDtlBean.add(objModel);
				}

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
				hm.put("strLocCode", strLocCode);
				hm.put("strLocName", strLocName);
				hm.put("strAuthorise", strAuthorise);
				hm.put("listDtlBean", listDtlBean);

				hm.put("dtFromSADate", dtFromDate);
				hm.put("dtToSADate", dtToDate);
				hm.put("strNarration", strNarration);

				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);

				JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				jprintlist.add(jp);

				if (jprintlist.size() > 0) {
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (type.trim().equalsIgnoreCase("pdf")) {
						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkAjdustmentSlipRecipeWiseMonthly." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					} else {
						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xlsx");
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkAjdustmentSlipRecipeWiseMonthly." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					try {
						resp.getWriter().append("No Record Found");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
			if (objBean.getStrReportType().equals("POS") && objBean.getStrReportView().equalsIgnoreCase("List")) {

				reportName = servletContext.getRealPath("/WEB-INF/reports/rptStockAdjustmentBeanSlip.jrxml");
				strSACode = "";
				dtSADate = objBean.getDtFromDate() + " To " + objBean.getDtToDate();
				strNarration = "Stock Adjustment POS Sales";
				strAuthorise = "";

				sqlDtlQuery = " SELECT b.strProdCode,b.strRemark,c.strProdName, c.strProdType,  " + " SUM(b.dblQty),c.strUOM,b.strType,  b.dblRate,b.strDisplayQty,b.dblPrice  " + "  FROM tblstockadjustmenthd a,tblstockadjustmentdtl b, tblproductmaster c   " + "   WHERE a.strSACode=b.strSACode  and Date(a.dtSADate) between '" + dtFDate + "' and '" + dtTDate + "'  "
						+ " and b.strProdCode=c.strProdCode AND b.strClientCode='" + clientCode + "'  	" + "AND b.strClientCode='" + clientCode + "' " + "	and a.strLocCode='" + strLocCode + "' and LENGTH(a.strNarration)>0  and a.strNarration like '%Sales Data%' " + " GROUP BY b.strProdCode,b.strType ORDER BY c.strProdName  ";

				List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlDtlQuery, "sql");
				List<clsStkAdjustmentDtlModel> listDtlBean = new ArrayList<clsStkAdjustmentDtlModel>();
				for (int j = 0; j < listProdDtl.size(); j++) {
					clsStkAdjustmentDtlModel objModelDtl = new clsStkAdjustmentDtlModel();
					Object[] prodArr = (Object[]) listProdDtl.get(j);
					{
						objModelDtl.setStrProdCode(prodArr[0].toString());
						objModelDtl.setStrProdName(prodArr[2].toString());
						objModelDtl.setStrProdType(prodArr[3].toString());
						objModelDtl.setDblQty(Double.parseDouble(prodArr[4].toString()));
						objModelDtl.setStrUOM(prodArr[5].toString());
						objModelDtl.setStrType(prodArr[6].toString());
						objModelDtl.setDblRate(Double.parseDouble(prodArr[7].toString()));
						objModelDtl.setStrDisplayQty(funGetDispalyUOMQty(prodArr[0].toString(), Double.parseDouble(prodArr[4].toString()), clientCode));
						objModelDtl.setDblPrice(Double.parseDouble(prodArr[9].toString()));
						listDtlBean.add(objModelDtl);
					}
				}
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
				hm.put("strSACode", "");
				hm.put("dtSADate", dtSADate);
				hm.put("strLocCode", strLocCode);
				hm.put("strLocName", strLocName);
				hm.put("strNarration", strNarration);
				hm.put("strAuthorise", strAuthorise);
				hm.put("listDtlBean", listDtlBean);

				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);

				JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				jprintlist.add(jp);

				if (jprintlist.size() > 0) {
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (type.trim().equalsIgnoreCase("pdf")) {
						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=" + "rptStkAjdustmentSlip." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					} else {

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xlsx");
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptStkAjdustmentSlip." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					try {
						resp.getWriter().append("No Record Found");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private class clshmReportBean {
		private String strPraentProdCode;

		private String strBOMCode;

		private String strInOut;

		private double dblQty;

		private double dblRate;

		private String strItemName;

		private String strSACode;

		private double dblprice;
		
		private double dblParentAmtWithDiscountedAmt;

		public String getStrPraentProdCode() {
			return strPraentProdCode;
		}

		public void setStrPraentProdCode(String strPraentProdCode) {
			this.strPraentProdCode = strPraentProdCode;
		}

		public String getStrBOMCode() {
			return strBOMCode;
		}

		public void setStrBOMCode(String strBOMCode) {
			this.strBOMCode = strBOMCode;
		}

		public String getStrInOut() {
			return strInOut;
		}

		public void setStrInOut(String strInOut) {
			this.strInOut = strInOut;
		}

		public double getDblQty() {
			return dblQty;
		}

		public void setDblQty(double dblQty) {
			this.dblQty = dblQty;
		}

		public double getDblprice() {
			return dblprice;
		}

		public void setDblprice(double dblprice) {
			this.dblprice = dblprice;
		}

		public String getStrItemName() {
			return strItemName;
		}

		public void setStrItemName(String strItemName) {
			this.strItemName = strItemName;
		}

		public String getStrSACode() {
			return strSACode;
		}

		public void setStrSACode(String strSACode) {
			this.strSACode = strSACode;
		}

		public double getDblRate() {
			return dblRate;
		}

		public void setDblRate(double dblRate) {
			this.dblRate = dblRate;
		}

		public double getDblParentAmtWithDiscountedAmt() {
			return dblParentAmtWithDiscountedAmt;
		}

		public void setDblParentAmtWithDiscountedAmt(
				double dblParentAmtWithDiscountedAmt) {
			this.dblParentAmtWithDiscountedAmt = dblParentAmtWithDiscountedAmt;
		}

	}

}
