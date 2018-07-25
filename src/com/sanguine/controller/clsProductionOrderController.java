package com.sanguine.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
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
import com.sanguine.bean.clsAutoGenProductionOrderBean;
import com.sanguine.bean.clsAutoGenReqBean;
import com.sanguine.bean.clsProductionOrderBean;
import com.sanguine.crm.bean.clsProductionComPilationBean;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProcessSetupModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductionOrderDtlModel;
import com.sanguine.model.clsProductionOrderHdModel;
import com.sanguine.model.clsProductionOrderHdModel_ID;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsProductionOrderService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsProductionOrderController {

	@Autowired
	clsProductionOrderService objProductionOrderService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsLocationMasterService objLocService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsSalesOrderService objSalesOrderService;

	@Autowired
	clsProductMasterService objProductMasterService;

	// List listChildNodes;
	// Map<String,List<String>> mapChildNodes=null;
	// List<List<String>> listChildNodes=null;
	// private List listChildNodes1=null;

	/**
	 * Open Production Order Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmProductionOrder", method = RequestMethod.GET)
	public ModelAndView funOpenProductionOrderForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmWebStockHelpMealPlanning");
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
			docCode = request.getParameter("authorizationProdOrderCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationProdOrderCode", docCode);
		}

		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmProductionOrder", propCode, clientCode);
		List<String> listSorted = new ArrayList<String>();
		if (list.size() > 0) {
			for (String process : list) {
				if (process.equalsIgnoreCase("Direct")) {
					listSorted.add(process);
				}
			}
			for (String process : list) {
				if (process.equalsIgnoreCase("Sales Order")) {
					listSorted.add(process);
				}
			}
			for (String process : list) {
				if (process.equalsIgnoreCase("Delivery Note")) {
					listSorted.add(process);
				}
			}
		}
		model.put("strProcessList", listSorted);

		model.put("urlHits", urlHits);
		clsProductionOrderBean bean = new clsProductionOrderBean();

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductionOrder_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductionOrder", "command", bean);
		} else {
			return null;
		}

	}

	/**
	 * Save Production Order Data
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveProductionOrderData", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsProductionOrderBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
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
		if (!result.hasErrors()) {
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			String locwiseProductionOrder = "N";
			if (null != objSetup.getStrLocWiseProductionOrder()) {
				locwiseProductionOrder = objSetup.getStrLocWiseProductionOrder();
			}

			if (locwiseProductionOrder.equals("Y")) {
				Map<String, List<clsProductionOrderDtlModel>> hmLocProdDtl = funPrepardModelLocWise(objBean, userCode, clientCode, propCode, startDate, req);

				boolean flagDtlDataInserted = false;
				String PDCode = "";
				for (Map.Entry<String, List<clsProductionOrderDtlModel>> entry : hmLocProdDtl.entrySet()) {
					String locCode = entry.getKey();
					clsProductionOrderHdModel objProductionOrderHdModel = funPrepareMaster(objBean, userCode, clientCode, propCode, startDate, req, locwiseProductionOrder, locCode);
					objProductionOrderService.funAddUpdateProductionHd(objProductionOrderHdModel);
					String OPCode = objProductionOrderHdModel.getStrOPCode();
					objProductionOrderService.funDeleteProductionOrderDtl(OPCode);
					PDCode = PDCode + OPCode + ",";

					for (clsProductionOrderDtlModel ob : entry.getValue()) {
						ob.setStrClientCode(clientCode);
						ob.setStrOPCode(OPCode);
						objProductionOrderService.funAddUpdateProductionDtl(ob);
						flagDtlDataInserted = true;

					}
				}

				if (flagDtlDataInserted) {
					PDCode = PDCode.substring(0, PDCode.length() - 1);
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "PD OrderCode : ".concat(PDCode));
					req.getSession().setAttribute("rptOPCode", PDCode);
					req.getSession().setAttribute("dteFullfillment", objBean.getDtfulfilled());
					// System.out.println(req.getSession().getAttribute("dteFullfillment"));
				}

			} else {
				List<clsProductionOrderDtlModel> listclsProductionOrderDtlModel = objBean.getListclsProductionOrderDtlModel();
				if (null != listclsProductionOrderDtlModel && listclsProductionOrderDtlModel.size() > 0) {
					clsProductionOrderHdModel objProductionOrderHdModel = funPrepareMaster(objBean, userCode, clientCode, propCode, startDate, req, locwiseProductionOrder, objBean.getStrLocCode());
					objProductionOrderService.funAddUpdateProductionHd(objProductionOrderHdModel);
					String OPCode = objProductionOrderHdModel.getStrOPCode();
					objProductionOrderService.funDeleteProductionOrderDtl(OPCode);
					boolean flagDtlDataInserted = false;

					for (clsProductionOrderDtlModel ob : listclsProductionOrderDtlModel) {
						ob.setStrClientCode(clientCode);
						ob.setStrOPCode(OPCode);
						objProductionOrderService.funAddUpdateProductionDtl(ob);
						flagDtlDataInserted = true;

					}
					if (flagDtlDataInserted) {
						req.getSession().setAttribute("success", true);
						req.getSession().setAttribute("successMessage", "PD OrderCode : ".concat(objProductionOrderHdModel.getStrOPCode()));
						req.getSession().setAttribute("rptOPCode", objProductionOrderHdModel.getStrOPCode());
						req.getSession().setAttribute("dteFullfillment", objBean.getDtfulfilled());
					}
				}

			}

			return new ModelAndView("redirect:/frmProductionOrder.html?saddr=" + urlHits);

		}
		return new ModelAndView("redirect:/frmProductionOrder.html?saddr=" + urlHits);
	}

	/**
	 * Prepare Production order HdModel
	 * 
	 * @param objBean
	 * @param userCode
	 * @param clientCode
	 * @param propCode
	 * @param startDate
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsProductionOrderHdModel funPrepareMaster(clsProductionOrderBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest request, String locwiseProductionOrder, String locCode) {
		long lastNo = 0;
		objGlobal = new clsGlobalFunctions();
		clsProductionOrderHdModel objProductionOrderHdModel;
		if (objBean.getStrOPCode().trim().length() == 0) {
			String strMPCode = objGlobalFunctions.funGenerateDocumentCode("frmProductionOrder", objBean.getDtOPDate(), request);
			objProductionOrderHdModel = new clsProductionOrderHdModel(new clsProductionOrderHdModel_ID(strMPCode, clientCode));
			objProductionOrderHdModel.setIntid(lastNo);
			objProductionOrderHdModel.setStrUserCreated(userCode);
			objProductionOrderHdModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsProductionOrderHdModel objclsProductionOrderHdModel = objProductionOrderService.funGetObject(objBean.getStrOPCode(), clientCode);
			if (null == objclsProductionOrderHdModel) {
				String strMPCode = objGlobalFunctions.funGenerateDocumentCode("frmProductionOrder", objBean.getDtOPDate(), request);
				objProductionOrderHdModel = new clsProductionOrderHdModel(new clsProductionOrderHdModel_ID(strMPCode, clientCode));
				objProductionOrderHdModel.setIntid(lastNo);
				objProductionOrderHdModel.setStrUserCreated(userCode);
				objProductionOrderHdModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objProductionOrderHdModel = new clsProductionOrderHdModel(new clsProductionOrderHdModel_ID(objBean.getStrOPCode(), clientCode));
			}
		}
		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Production Order")) {
				res = true;
			}
		}
		if (res) {
			objProductionOrderHdModel.setStrAuthorise("No");
		} else {
			objProductionOrderHdModel.setStrAuthorise("Yes");
		}
		objProductionOrderHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objProductionOrderHdModel.setStrUserModified(userCode);
		objProductionOrderHdModel.setDtOPDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtOPDate()));
		objProductionOrderHdModel.setDtFulmtDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFulmtDate()));
		objProductionOrderHdModel.setDtfulfilled(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtfulfilled()));
		if (locwiseProductionOrder.equals("Y")) {
			objProductionOrderHdModel.setStrLocCode(locCode);
		} else {
			objProductionOrderHdModel.setStrLocCode(objBean.getStrLocCode());
		}
		objProductionOrderHdModel.setStrAgainst(objBean.getStrAgainst());
		objProductionOrderHdModel.setStrNarration(objBean.getStrNarration());
		objProductionOrderHdModel.setStrCode(objBean.getStrDocCode());
		objProductionOrderHdModel.setStrStatus("N");
		objProductionOrderHdModel.setStrLocCode(objBean.getStrLocCode());

		String soDoc = objBean.getStrDocCode();
		String[] SoCode = soDoc.split(",");
		for (int cnt = 0; cnt < SoCode.length; cnt++) {
			objSalesOrderService.funUpdateSOforPO(SoCode[cnt], clientCode);
		}

		return objProductionOrderHdModel;
	}

	/**
	 * Load Production Order Data
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadOPData", method = RequestMethod.GET)
	public @ResponseBody List<clsProductionOrderHdModel> funOpenFormWithMISCode(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String OPCode = req.getParameter("OPCode").toString();
		clsProductionOrderHdModel objclsProductionOrderHdModel = objProductionOrderService.funGetObject(OPCode, clientCode);
		List<clsProductionOrderHdModel> ListOPHd = new ArrayList<clsProductionOrderHdModel>();
		if (null == objclsProductionOrderHdModel) {
			objclsProductionOrderHdModel = new clsProductionOrderHdModel();
			objclsProductionOrderHdModel.setStrOPCode("Invalid Code");
			ListOPHd.add(objclsProductionOrderHdModel);
		} else {

			clsLocationMasterModel objLocationFrom = objLocService.funGetObject(objclsProductionOrderHdModel.getStrLocCode(), clientCode);
			objclsProductionOrderHdModel.setDtOPDate(objGlobal.funGetDate("yyyy/MM/dd", objclsProductionOrderHdModel.getDtOPDate()));
			objclsProductionOrderHdModel.setDtFulmtDate(objGlobal.funGetDate("yyyy/MM/dd", objclsProductionOrderHdModel.getDtFulmtDate()));
			objclsProductionOrderHdModel.setDtfulfilled(objGlobal.funGetDate("yyyy/MM/dd", objclsProductionOrderHdModel.getDtfulfilled()));
			objclsProductionOrderHdModel.setStrLocName(objLocationFrom.getStrLocName());
			ListOPHd.add(objclsProductionOrderHdModel);
		}
		return ListOPHd;
	}

	/**
	 * Load Production order Dtl Data
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadOPDtlData", method = RequestMethod.GET)
	public @ResponseBody List<clsProductionOrderDtlModel> funfillProdData(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String OPCode = req.getParameter("OPCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		@SuppressWarnings({ "rawtypes" })
		List listOPDtl = objProductionOrderService.funGetDtlList(OPCode, clientCode);
		List<clsProductionOrderDtlModel> OPDtl = new ArrayList<clsProductionOrderDtlModel>();
		for (int i = 0; i < listOPDtl.size(); i++) {
			Object[] ob = (Object[]) listOPDtl.get(i);
			clsProductionOrderDtlModel OPProdDtl = (clsProductionOrderDtlModel) ob[0];
			clsProductMasterModel productModel = (clsProductMasterModel) ob[1];
			OPProdDtl.setStrProdName(productModel.getStrProdName());
			OPDtl.add(OPProdDtl);
		}
		return OPDtl;
	}

	/**
	 * Open Production Order Report form
	 * 
	 * @return
	 * @throws JRException
	 */

	@RequestMapping(value = "/frmProductionOrderSlip", method = RequestMethod.GET)
	public ModelAndView funOpenProductionOrderSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProductionOrderSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmProductionOrderSlip", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/invokeProductionOrderSlip", method = RequestMethod.GET)
	private void funCallReportOnClick(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String type = "pdf";
		funCallReport(docCode, type, resp, req);
	}

	@RequestMapping(value = "/rptProductionOrderSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String OPCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		funCallReport(OPCode, type, resp, req);
	}

	@RequestMapping(value = "/openProductionOrderSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String OPCode = req.getParameter("rptOPCode").toString();
		req.getSession().removeAttribute("rptOPCode");
		String type = "pdf";
		String dteFullfillment = req.getSession().getAttribute("dteFullfillment").toString();

		// funCallReport(OPCode,type,resp,req);
		funCallCustomerWiseLocationWiseSOReport(dteFullfillment, resp, req, type);
	}

	@RequestMapping(value = "/openProductionCompilationSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmitProductionCompilation(HttpServletResponse resp, HttpServletRequest req) {
		String OPCode = req.getParameter("rptOPCode").toString();
		req.getSession().removeAttribute("rptOPCode");
		String type = "pdf";
		String dteFullfillment = req.getSession().getAttribute("dteFullfillment").toString();

		// funCallReport(OPCode,type,resp,req);
		funCallProductionCompilationReport(dteFullfillment, resp, req, type);
	}

	@RequestMapping(value = "/openCustomerWiseCategoryWiseSO", method = RequestMethod.GET)
	private void funCallReportOnSubmitCustomerWiseCategoryWiseSalesOrder(HttpServletResponse resp, HttpServletRequest req) {
		String OPCode = req.getParameter("rptOPCode").toString();
		req.getSession().removeAttribute("rptOPCode");
		String type = "pdf";
		String dteFullfillment = req.getSession().getAttribute("dteFullfillment").toString();

		// funCallReport(OPCode,type,resp,req);
		funCallCustomerWiseCategoryWiseSalesOrderReport(dteFullfillment, resp, req, type);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReport(String OPCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptProductionOrderSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = "select strOPcode,date(dtOPDate) as dtOPDate,strNarration,a.strLocCode,strAgainst,b.strLocName " + " from tblproductionorderhd a,tbllocationmaster b " + "where a.strLocCode=b.strLocCode and a.strclientcode='" + clientCode + "' and " + "b.strclientCode='" + clientCode + "' and a.strOPcode='" + OPCode + "' ";
			System.out.println("poCon=" + sqlHDQuery);

			String sqlDtlQuery = "SELECT '' as SrNo, a.strProdCode,b.strProdName,b.strUOM,a.dblQty,a.dblUnitPrice," + "a.dblWeight,a.strSpCode from tblproductionorderdtl a," + " tblproductmaster b where a.strProdCode=b.strProdCode " + "and a.strclientcode='" + clientCode + "' and b.strclientCode='" + clientCode + "' and a.strOPcode='" + OPCode + "'";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);

			JRDesignQuery subQuery = new JRDesignQuery();

			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProdOrderDtl");
			subDataset.setQuery(subQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			System.out.println(imagePath);
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
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptMealPlaningSlip." + type.trim());
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptMealPlaningSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open Pending SO form
	 * 
	 * @return
	 */
	@RequestMapping(value = "/frmPOSales", method = RequestMethod.GET)
	public ModelAndView funOpenPOforMR(Map<String, Object> model, HttpServletRequest req) {
		String locCode = req.getParameter("strlocCode");
		String dtfullfilled = req.getParameter("dtFullFilled");
		model.put("locCode", locCode);
		model.put("dtFullfilled", dtfullfilled);
		return new ModelAndView("frmPOSales", "command", new clsReportBean());

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSOforProductionOrder", method = RequestMethod.GET)
	public @ResponseBody List funLoadPOforGRN(HttpServletRequest request) {
		// String strSuppCode=request.getParameter("strSuppCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String locCode = request.getParameter("strlocCode");
		String dtfullfilled = request.getParameter("dtFullFilled");
		String orderType = request.getParameter("orderType");
		// dtfullfilled=objGlobalFunctions.funGetDate("yyyy-MM-dd",dtfullfilled);
		List SOHelpList = objProductionOrderService.funListSOforProductionOrder(locCode, dtfullfilled, clientCode, orderType);
		return SOHelpList;

	}

	/*--------------------------------Start For Child Product Shown in Grid-------------------------------------- */
	// @RequestMapping(value = "/loadAgainstSO", method = RequestMethod.GET)
	// public @ResponseBody Map<String,List>
	// funAssignFields1(@RequestParam("SOCode") String codes,HttpServletRequest
	// request)
	// {
	// Map<String,List> hmChildNodes=new HashMap<String, List>();
	// objGlobal = new clsGlobalFunctions();
	// listChildNodes1=new ArrayList<String>();
	// listChildNodes1.clear();
	// String[] SOCode = codes.split(",");
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// for(int cnt =0; cnt<SOCode.length;cnt++)
	// {
	// List <Object>
	// objSales=objSalesOrderService.funGetSalesOrderDtl(SOCode[cnt],clientCode);
	// clsSalesOrderHdModel objSalesOrderHdModel = null;
	// clsLocationMasterModel objLocationMasterModel = null;
	// clsPartyMasterModel objPartyMasterModel = null;
	// for(int i=0;i<objSales.size();i++)
	// {
	// Object[] ob = (Object[])objSales.get(i);
	// clsSalesOrderDtl saleDtl = (clsSalesOrderDtl) ob[0];
	// clsProductMasterModel
	// objModel=objProductMasterService.funGetObject(saleDtl.getStrProdCode(),clientCode);
	// hmChildNodes=funGetChildNodes1(saleDtl.getStrProdCode(),saleDtl.getDblQty(),
	// request,objModel.getDblWeight());
	// System.out.println("so"+i+"==="+hmChildNodes);
	// }
	// }

	// return hmChildNodes;

	// }

	// public Map<String,List> funGetChildNodes1(String prodCode,double
	// qty,HttpServletRequest request,double wt)
	// {
	// Map<String,List> hmChildNodes=new HashMap<String, List>();
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// String userCode=request.getSession().getAttribute("usercode").toString();
	// //listChildNodes1=new ArrayList<String>();
	// double reqQty=qty;
	// funGetBOMNodes(prodCode,0,reqQty,wt);
	//
	//
	// for(int cnt=0;cnt<listChildNodes1.size();cnt++)
	// {
	// List arrListBOMProducts=new ArrayList<String>();
	// String temp=(String)listChildNodes1.get(cnt);
	// String proCode=temp.split(",")[0];
	// double reqdQty=Double.parseDouble(temp.split(",")[1]);
	// double reqdWtQty=Double.parseDouble(temp.split(",")[2]);
	//
	// //
	// System.out.println("Prod="+prodCode+"\topenPO="+openPOQty+"\tstk="+currentStock+"\tOrder Qty="+orderQty);
	// String productInfo=funGetProdInfo(proCode,clientCode);
	// String pCode="",prodName="",fqty="",fWt="",fPrice="";
	//
	// if(productInfo.trim().length()>0)
	// {
	//
	// String[] spProd=productInfo.split("#");
	//
	// pCode=spProd[0];
	// prodName=spProd[1];
	// //fqty=spProd[2];
	// fWt=spProd[3];
	// fPrice=spProd[2];;
	//
	// }
	// else
	// {
	// clsProductMasterModel
	// objModel=objProductMasterService.funGetObject(prodCode,clientCode);
	// prodName=objModel.getStrProdName();
	// }

	// Date today = new Date();
	// Calendar cal = Calendar.getInstance();
	// cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(leadTime));
	// Date expDate = cal.getTime();
	// String
	// expectedDate=expDate.getDate()+"/"+(expDate.getMonth()+1)+"/"+(expDate.getYear()+1900);
	//
	// if(null!=hmChildNodes.get(proCode))
	// {
	// List arrListTemp=hmChildNodes.get(proCode);
	// reqdQty=reqdQty+Double.parseDouble(arrListTemp.get(2).toString());
	// reqdWtQty=reqdWtQty+Double.parseDouble(arrListTemp.get(3).toString());
	//
	// System.out.println(Double.parseDouble(arrListTemp.get(3).toString()));
	// //System.out.println(Double.parseDouble(arrListTemp.get(4).toString()));
	// hmChildNodes.remove(prodCode);
	// }
	//
	// arrListBOMProducts.add(proCode);
	// arrListBOMProducts.add(prodName);
	// arrListBOMProducts.add(reqdQty);
	// arrListBOMProducts.add(reqdWtQty);
	// arrListBOMProducts.add(fPrice);
	//
	// hmChildNodes.put(proCode, arrListBOMProducts);
	// }
	//
	// return hmChildNodes;
	// }

	// public List funGetBOMNodes(String parentProdCode,double bomQty,double
	// qty,double childWt)
	// {
	// double finalQty=0;
	// double finalWt=0;
	// List listTemp=new ArrayList<String>();
	// String
	// sql="select b.strChildCode,c.dblWeight  from  tblbommasterhd a,tblbommasterdtl b ,tblproductmaster c "
	// +
	// "where a.strBOMCode=b.strBOMCode and a.strParentCode='"+parentProdCode+"' and b.strChildCode=c.strProdCode ";
	// listTemp=objGlobalFunctionsService.funGetList(sql,"sql");
	// if(listTemp.size()>0)
	// {
	// for(int cnt=0;cnt<listTemp.size();cnt++)
	// {
	// Object[] ob = (Object[]) listTemp.get(cnt);
	// String childNode=ob[0].toString();
	// double cWt=Double.parseDouble(ob[1].toString());
	// bomQty=funGetBOMQty(childNode, parentProdCode);
	// //qty=qty*bomQty;
	// //System.out.println(childNode+"\tbom="+bomQty+"\tQty="+qty);
	// funGetBOMNodes(childNode,bomQty,qty*bomQty,bomQty*cWt);
	// }
	// finalQty=qty;
	// finalWt=childWt;
	// }
	// else
	// {
	// listChildNodes1.add(parentProdCode+","+qty+","+childWt);
	// }
	// return listChildNodes1;
	// }

	// public double funGetBOMQty(String childCode,String parentCode)
	// {
	// double bomQty=0;
	// try
	// {
	// String
	// sql="select ifnull(left(((c.dblReceiveConversion/c.dblIssueConversion)/c.dblRecipeConversion),6) * b.dblQty,0) as BOMQty "
	// + "from tblbommasterhd a,tblbommasterdtl b,tblproductmaster c "
	// +
	// "where a.strBOMCode=b.strBOMCode and a.strParentCode=c.strProdCode and a.strParentCode='"+parentCode+"' "
	// + "and b.strChildCode='"+childCode+"'";
	// List listChildQty=objGlobalFunctionsService.funGetList(sql, "sql");
	// if(listChildQty.size()>0)
	// {
	// bomQty=(Double)listChildQty.get(0);
	// }
	//
	// }catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// return bomQty;
	// }

	// public double funGetOpenPOQty(String prodCode,String clientCode)
	// {
	// BigDecimal openPOQty=new BigDecimal(0);
	// double dblPOQty=0;
	// String sql="select a.dblOrdQty - ifnull(b.POQty,0) as BalanceQty "
	// +
	// "from tblpurchaseorderdtl a left outer join (SELECT b.strCode AS POCode, b.strProdCode, SUM(b.dblQty) AS POQty "
	// +
	// "FROM tblGRNHd a INNER JOIN tblGRNDtl b ON a.strGRNCode = b.strGRNCode "
	// +
	// "WHERE (a.strAgainst = 'Purchase Order') GROUP BY POCode, b.strProdCode) b on a.strPOCode = b.POCode "
	// +
	// "and a.strProdCode = b.strProdCode left outer join tblproductmaster c on a.strProdCode=c.strProdCode "
	// +
	// "where a.dblOrdQty > ifnull(b.POQty,0) and a.strProdCode='"+prodCode+"' and a.strClientCode='"+clientCode+"'";
	// List list=objGlobalFunctionsService.funGetList(sql, "sql");
	// if(list.size()>0)
	// {
	// // openPOQty = (double) list.get(0);
	// Object ob = list.get(0);
	// openPOQty =(BigDecimal) ob;
	// dblPOQty=openPOQty.doubleValue();
	// }
	//
	// return dblPOQty;
	// }

	// public String funGetProdInfo(String prodCode,String clientCode)
	// {
	// String prodInfo="";
	// String sql="select a.strProdCode,a.strProdName,a.dblCostRM,a.dblWeight "
	// +
	// " from tblproductmaster a where a.strProdCode='"+prodCode+"' and a.strClientCode='"+clientCode+"' ";
	//
	// List list=objGlobalFunctionsService.funGetList(sql, "sql");
	// if(list.size()>0)
	// {
	// Object[] arrObj=(Object[])list.get(0);
	// prodInfo=arrObj[0]+"#"+arrObj[1]+"#"+arrObj[2]+"#"+arrObj[3];
	// }
	//
	// return prodInfo;
	// }

	/*-----------------------------------End For Child Product Shown in Grid----------------------------------- */

	@RequestMapping(value = "/loadAgainstSO", method = RequestMethod.GET)
	public @ResponseBody List<clsSalesOrderDtl> funAssignFields1(@RequestParam("SOCode") String codes, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		List<clsSalesOrderDtl> listSaleDtl = new ArrayList<clsSalesOrderDtl>();
		;

		String[] SOCode = codes.split(",");

		List objSales = objSalesOrderService.funGetMultipleSODtl(SOCode, clientCode);
		if (null != objSales) {
			for (int i = 0; i < objSales.size(); i++) {
				Object[] obj = (Object[]) objSales.get(i);
				clsSalesOrderDtl saleDtl = new clsSalesOrderDtl();
				saleDtl.setStrProdCode(obj[0].toString());
				saleDtl.setStrProdName(obj[1].toString());
				saleDtl.setStrProdType(obj[2].toString());
				saleDtl.setDblQty(Double.parseDouble(obj[3].toString()));
				saleDtl.setDblUnitPrice(Double.parseDouble(obj[4].toString()));
				saleDtl.setDblWeight(Double.parseDouble(obj[5].toString()));
				saleDtl.setStrClientCode(clientCode);
				saleDtl.setIntindex(i);

				listSaleDtl.add(saleDtl);
			}

		}

		// for(int loop =0; loop<SOCode.length;loop++)
		// {
		//
		// List <Object>
		// objSales=objSalesOrderService.funGetSalesOrder(SOCode[loop],clientCode);
		// clsSalesOrderHdModel objSalesOrderHdModel = null;
		// for(int i=0;i<objSales.size();i++)
		// {
		// Object[] ob = (Object[])objSales.get(i);
		// objSalesOrderHdModel = (clsSalesOrderHdModel) ob[0];
		//
		// if(objSalesOrderHdModel.getStrCloseSO().equals("N"))
		// {
		// List<Object>
		// objSalesDtlModelList=objSalesOrderService.funGetSalesOrderDtl(objSalesOrderHdModel.getStrSOCode(),clientCode);
		// for(int cnt=0;cnt<objSalesDtlModelList.size();cnt++)
		// {
		// Object[] obj = (Object[])objSalesDtlModelList.get(cnt);
		// clsSalesOrderDtl saleDtl = (clsSalesOrderDtl) obj[0];
		// clsProductMasterModel prodmast =(clsProductMasterModel) obj[1];
		// saleDtl.setStrProdName(prodmast.getStrProdName());
		// saleDtl.setStrProdType(prodmast.getStrProdType());
		//
		// // String pCode=saleDtl.getStrProdCode();
		// // double qty=saleDtl.getDblQty();
		// //
		// // ListIterator<clsSalesOrderDtl> lisIte =
		// listSaleDtl.listIterator();
		// //
		// // try
		// // {
		// // while(lisIte.hasNext()){
		// //
		// // clsSalesOrderDtl saleITdtl = lisIte.next();
		// // if(lisIte.next() != null)
		// // {
		// // if(pCode.equals(saleITdtl.getStrProdCode()))
		// // {
		// // double oldQty =saleITdtl.getDblQty();
		// // saleITdtl.setDblQty(oldQty+qty);
		// // lisIte.set(saleITdtl);
		// // }
		// // else{
		// // listSaleDtl.add(saleDtl);
		// // }
		// // }
		// //
		// // }
		// // }catch(Exception ex)
		// // {
		// // ex.printStackTrace();
		// // }
		// // if(!(lisIte.hasNext()))
		// // {
		// listSaleDtl.add(saleDtl);
		// // }
		// //
		//
		//
		//
		//
		// }
		// }
		//
		//
		// }
		// }

		return listSaleDtl;
	}

	private Map<String, List<clsProductionOrderDtlModel>> funPrepardModelLocWise(clsProductionOrderBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest request) {
		Set<String> locSet = new HashSet<String>();
		Map<String, List<clsProductionOrderDtlModel>> hmLocProdDtl = new HashMap<String, List<clsProductionOrderDtlModel>>();
		String currentLoc = request.getSession().getAttribute("locationCode").toString();
		if (null != objBean.getListclsProductionOrderDtlModel()) {
			if (objBean.getListclsProductionOrderDtlModel().size() > 0) {
				List<clsProductionOrderDtlModel> listclsProductionOrderDtlModel = objBean.getListclsProductionOrderDtlModel();
				for (clsProductionOrderDtlModel ob : listclsProductionOrderDtlModel) {
					String pCode = ob.getStrProdCode();
					if (null != pCode) {
						String sqlProdWiseLoc = " select strLocCode from tblproductmaster where strProdCode='" + pCode + "' and strClientCode ='" + clientCode + "' ";
						List listProdWiseLoc = objGlobalFunctionsService.funGetDataList(sqlProdWiseLoc, "sql");

						if (null != listProdWiseLoc) {
							locSet.add(listProdWiseLoc.get(0).toString());
						}

					}
				}
				if (locSet.size() > 0) {
					for (String loc : locSet) {

						List<clsProductionOrderDtlModel> listMatchProdLocDtl = new ArrayList<clsProductionOrderDtlModel>();
						// String sqlLocWiseProd
						// =" select strProdCode from tblreorderlevel where strLocationCode='"+loc+"' and strClientCode ='"+clientCode+"' ";
						// List listLocWiseProd =
						// objGlobalFunctionsService.funGetDataList(sqlLocWiseProd,"sql");

						for (clsProductionOrderDtlModel ob : listclsProductionOrderDtlModel) {
							String pCode = ob.getStrProdCode();
							if (null != pCode) {
								String sqlProdWiseLoc = " select strLocCode from tblproductmaster where strProdCode='" + pCode + "' and strClientCode ='" + clientCode + "' ";
								List listProdWiseLoc = objGlobalFunctionsService.funGetDataList(sqlProdWiseLoc, "sql");

								if (null != listProdWiseLoc) {
									if (loc.equals(listProdWiseLoc.get(0).toString())) {
										listMatchProdLocDtl.add(ob);

									}

								}
							}

						}

						hmLocProdDtl.put(loc, listMatchProdLocDtl);
					}

				}

			}
		}

		return hmLocProdDtl;

	}

	private void funCallCustomerWiseLocationWiseSOReport(String dteFullfillment, HttpServletResponse resp, HttpServletRequest req, String type) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCustomerWiseLocationWiseSOReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		ArrayList fieldList = new ArrayList();

		String sqlQuery = " SELECT a.strCustCode,f.strPName,e.strLocCode,g.strLocName,d.strProdCode,e.strProdName," + " d.dblAcceptQty as dblQty,d.dblWeight FROM tblsalesorderhd a ,tblsalesorderdtl d  ,tblproductmaster e," + "  tblpartymaster f, tbllocationmaster g " + " WHERE a.strSOCode=d.strSOCode " + " and d.strProdCode=e.strProdCode " + " and e.strLocCode=g.strLocCode "
		// + " AND a.strCloseSO='Y' "
				+ " AND f.strPCode=a.strCustCode " + " AND a.strClientCode='" + clientCode + "' " + " AND e.strClientCode='" + clientCode + "' " + " AND d.strClientCode='" + clientCode + "' " + " AND f.strClientCode='" + clientCode + "' " + " AND g.strClientCode='" + clientCode + "' ";

		String fromDate = dteFullfillment;
		String toDate = dteFullfillment;
		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		sqlQuery = sqlQuery + "and date(a.dteFulmtDate) between '" + dteFromDate + "' and '" + dteToDate + "'  GROUP BY a.strCustCode,d.strProdCode   ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		try {
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
			jd.setQuery(newQuery);
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
			hm.put("dteFromDate", dteFromDate);
			hm.put("dteToDate", dteToDate);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptCustomerWiseLocationWiseSOReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void funCallProductionCompilationReport(String dteFullfillment, HttpServletResponse resp, HttpServletRequest req, String type) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCategoryWiseSalesOrderReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		// group code
		// String strGroupCodes = objBean.getStrGCode();
		// String tempstrGCode[] = strGroupCodes.split(",");
		// String strGCodes = "(";
		// for (int i = 0; i < tempstrGCode.length; i++)
		// {
		// if (i == 0)
		// {
		// strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
		// }
		// else
		// {
		// strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
		// }
		// }
		// strGCodes += ")";
		//
		// // subgroup code
		// String strSubGroupCodes = objBean.getStrSGCode();
		// String tempstrSGCode[] = strSubGroupCodes.split(",");
		// String strSGCodes = "(";
		// for (int i = 0; i < tempstrSGCode.length; i++)
		// {
		// if (i == 0)
		// {
		// strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
		// }
		// else
		// {
		// strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
		// }
		// }
		// strSGCodes += ")";

		ArrayList fieldList = new ArrayList();

		String sqlQuery = "select d.strSGName,c.strProdCode,c.strProdName,sum(b.dblAcceptQty),sum(b.dblWeight*b.dblAcceptQty),a.strSOCode,d.strSGCode " + "from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e " + "where a.strSOCode=b.strSOCode "
		// + " and a.strCloseSo='Y' "
				+ "and b.strProdCode=c.strProdCode " + "and c.strSGCode=d.strSGCode " + "and d.strGCode=e.strGCode ";
		// + "and e.strGCode IN " + strGCodes + " ";
		// if (objBean.getStrSGCode().length() > 0)
		// {
		// sqlQuery = sqlQuery + " and c.strSGCode IN " + strSGCodes + " ";
		// }
		// else
		// {
		// sqlQuery = sqlQuery + " and d.strSGCode=d.strSGCode ";
		// }

		String fromDate = dteFullfillment;
		String toDate = dteFullfillment;

		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		sqlQuery = sqlQuery + "and date(a.dteFulmtDate) between '" + dteFromDate + "' and '" + dteToDate + "' " + "group by b.strProdCode,e.strGName,d.strSGName " + "order by b.strProdCode,e.strGName,d.strSGName ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		for (int j = 0; j < listProdDtl.size(); j++) {
			clsProductionComPilationBean objProdCompilationBean = new clsProductionComPilationBean();
			Object[] prodArr = (Object[]) listProdDtl.get(j);
			{
				String prodCode = prodArr[1].toString();
				String soCode = prodArr[5].toString();
				objProdCompilationBean.setStrSGName(prodArr[0].toString());
				objProdCompilationBean.setStrProdCode(prodCode);
				objProdCompilationBean.setStrProdName(prodArr[2].toString());
				objProdCompilationBean.setDblAcceptQty(Double.parseDouble(prodArr[3].toString()));
				objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[4].toString()));
				objProdCompilationBean.setStrSGCode(prodArr[6].toString());
				String sql = " select strCharValue from tblsaleschar where strSOCode='" + soCode + "' and strProdCode ='" + prodCode + "' ";
				List listChar = objGlobalFunctionsService.funGetDataList(sql, "sql");
				String strChars = "";
				for (int k = 0; k < listChar.size(); k++) {
					strChars = strChars + listChar.get(k).toString() + "/";
				}
				objProdCompilationBean.setStrCharistics(strChars);

			}
			fieldList.add(objProdCompilationBean);
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
		hm.put("dteFromDate", dteFullfillment);
		hm.put("dteToDate", dteFullfillment);

		try {
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptProductionCompilationNormalReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallCustomerWiseCategoryWiseSalesOrderReport(String dteFullfillment, HttpServletResponse resp, HttpServletRequest req, String type) {
		try {

			// Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptCustomerWiseCategoryWiseSalesOrderReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			// // group code
			// String strGroupCodes = objBean.getStrGCode();
			// String tempstrGCode[] = strGroupCodes.split(",");
			// String strGCodes = "(";
			// for (int i = 0; i < tempstrGCode.length; i++)
			// {
			// if (i == 0)
			// {
			// strGCodes = strGCodes + "'" + tempstrGCode[i] + "'";
			// }
			// else
			// {
			// strGCodes = strGCodes + ",'" + tempstrGCode[i] + "'";
			// }
			// }
			// strGCodes += ")";
			//
			// // subgroup code
			// String strSubGroupCodes = objBean.getStrSGCode();
			// String tempstrSGCode[] = strSubGroupCodes.split(",");
			// String strSGCodes = "(";
			// for (int i = 0; i < tempstrSGCode.length; i++)
			// {
			// if (i == 0)
			// {
			// strSGCodes = strSGCodes + "'" + tempstrSGCode[i] + "'";
			// }
			// else
			// {
			// strSGCodes = strSGCodes + ",'" + tempstrSGCode[i] + "'";
			// }
			// }
			// strSGCodes += ")";

			ArrayList fieldList = new ArrayList();

			String sqlQuery = "select f.strPName,c.strProdCode,c.strProdName,e.strGName,d.strSGName,sum(b.dblAcceptQty),sum(b.dblWeight*b.dblAcceptQty),a.strSOCode " + "from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblsubgroupmaster d,tblgroupmaster e,tblpartymaster f " + "where a.strSOCode=b.strSOCode " + "and b.strProdCode=c.strProdCode " + "and c.strSGCode=d.strSGCode "
					+ "and d.strGCode=e.strGCode " + "and a.strCustCode=f.strPCode ";
			// if(objBean.getStrDocCode()!=null &&
			// objBean.getStrDocCode().length()>0)
			// {
			// sqlQuery=sqlQuery+"and a.strCustCode='"+objBean.getStrDocCode()+"' ";
			// }
			// else
			// {
			// //
			// }
			// if (objBean.getStrSGCode().length() > 0)
			// {
			// sqlQuery = sqlQuery + " and d.strSGCode IN " + strSGCodes + " ";
			// }
			// else
			// {
			// sqlQuery = sqlQuery + " and d.strSGCode=d.strSGCode ";
			// }

			String fromDate = dteFullfillment;
			String toDate = dteFullfillment;

			String fd = fromDate.split("-")[0];
			String fm = fromDate.split("-")[1];
			String fy = fromDate.split("-")[2];

			String td = toDate.split("-")[0];
			String tm = toDate.split("-")[1];
			String ty = toDate.split("-")[2];

			String dteFromDate = fy + "-" + fm + "-" + fd;
			String dteToDate = ty + "-" + tm + "-" + td;

			sqlQuery = sqlQuery + "and date(a.dteFulmtDate) between '" + dteFromDate + "' and '" + dteToDate + "'  " + "group by e.strGName,d.strSGName,f.strPName,b.strProdCode " + "order by f.strPName; ";

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

			for (int j = 0; j < listProdDtl.size(); j++) {
				clsProductionComPilationBean objProdCompilationBean = new clsProductionComPilationBean();
				Object[] prodArr = (Object[]) listProdDtl.get(j);
				{
					String prodCode = prodArr[1].toString();
					String soCode = prodArr[7].toString();
					objProdCompilationBean.setStrPName(prodArr[0].toString());
					objProdCompilationBean.setStrSGName(prodArr[4].toString());
					objProdCompilationBean.setStrProdCode(prodCode);
					objProdCompilationBean.setStrProdName(prodArr[2].toString());
					objProdCompilationBean.setDblAcceptQty(Double.parseDouble(prodArr[5].toString()));
					objProdCompilationBean.setDblWeight(Double.parseDouble(prodArr[6].toString()));

					String sql = " select strCharValue from tblsaleschar where strSOCode='" + soCode + "' and strProdCode ='" + prodCode + "' ";
					List listChar = objGlobalFunctionsService.funGetDataList(sql, "sql");
					String strChars = "";
					for (int k = 0; k < listChar.size(); k++) {
						strChars = strChars + listChar.get(k).toString() + "/";
					}
					objProdCompilationBean.setStrCharistics(strChars);

				}
				fieldList.add(objProdCompilationBean);
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
			hm.put("dteFromDate", dteFullfillment);
			hm.put("dteToDate", dteFullfillment);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			if (jp != null) {
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptCustomerWiseCategoryWiseSOReport.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/openLocationwiseCategorywiseSOReport", method = RequestMethod.GET)
	private void funCallReportOnSubmitLocationwiseCategorywiseSOReport(HttpServletResponse resp, HttpServletRequest req) {
		String OPCode = req.getParameter("rptOPCode").toString();
		req.getSession().removeAttribute("rptOPCode");
		String type = "pdf";
		String dteFullfillment = req.getSession().getAttribute("dteFullfillment").toString();

		// funCallReport(OPCode,type,resp,req);
		funCallLocationwiseCategorywiseSOReport(dteFullfillment, resp, req, type);
	}

	private void funCallLocationwiseCategorywiseSOReport(String dteFullfillment, HttpServletResponse resp, HttpServletRequest req, String type) {

		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null) {
			objSetup = new clsPropertySetupModel();
		}
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptLocationwiseCategorywiseSOReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

		String propNameSql = "select a.strPropertyName  from dbwebmms.tblpropertymaster a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' ";
		List listPropName = objGlobalFunctionsService.funGetDataList(propNameSql, "sql");
		String propName = "";
		if (listPropName.size() > 0) {
			propName = listPropName.get(0).toString();
		}

		ArrayList fieldList = new ArrayList();

		String sqlQuery = " SELECT h.strSGName,f.strPName,c.strLocCode,g.strLocName,d.strProdCode,e.strProdName," + "d.dblQty,d.dblWeight" + " FROM tblproductionorderhd c,tblproductionorderdtl d, tblproductmaster e,tblpartymaster f," + " tbllocationmaster g,tblSubgroupmaster h " + "WHERE c.strOPCode=d.strOPCode " + "AND d.strProdCode=e.strProdCode " + "AND e.strSGCode=h.strSGCode "
				+ "AND g.strLocCode=c.strLocCode " + "AND e.strClientCode='" + clientCode + "' " + "AND c.strClientCode='" + clientCode + "' " + "AND d.strClientCode='" + clientCode + "' " + "AND f.strClientCode='" + clientCode + "' " + "AND g.strClientCode='" + clientCode + "' ";

		String fromDate = dteFullfillment;
		String toDate = dteFullfillment;

		String fd = fromDate.split("-")[0];
		String fm = fromDate.split("-")[1];
		String fy = fromDate.split("-")[2];

		String td = toDate.split("-")[0];
		String tm = toDate.split("-")[1];
		String ty = toDate.split("-")[2];

		String dteFromDate = fy + "-" + fm + "-" + fd;
		String dteToDate = ty + "-" + tm + "-" + td;

		sqlQuery = sqlQuery + "and date(c.dtfulfilled) between '" + dteFromDate + "' and '" + dteToDate + "' group by d.strProdCode,c.strLocCode   ";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

		try {
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
			jd.setQuery(newQuery);
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
			hm.put("dteFromDate", dteFromDate);
			hm.put("dteToDate", dteToDate);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptLocationWiseCategoryWiseSOReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/ShowAutoGenProductionOrder", method = RequestMethod.GET)
	private @ResponseBody List funAutoAvgQtyForProduction(HttpServletResponse resp, HttpServletRequest req) {
		List retGenOPList = new ArrayList<>();
		String locCode = req.getParameter("strLocCode").toString();
		String dteFromDate = req.getParameter("dteFromDate").toString();
		String dteToDate = req.getParameter("dteToDate").toString();

		// String locCode =
		// req.getSession().getAttribute("locationCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String dteCurrDateTime = objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd");
		String[] dteCurrDate = dteCurrDateTime.split(" ");
		objGlobalFunctions.funInvokeStockFlash(startDate, locCode, dteCurrDate[0], dteCurrDate[0], clientCode, userCode, "Both", req, resp);

		long diffDay = funDayDifference(dteFromDate, dteToDate);

		if (diffDay == 0) {
			diffDay = 1;
		}

		String sqlMainData = " select c.strPName,f.strSGName,e.strProdName,sum(b.dblAcceptQty),d.dblClosingStk," + " b.strProdCode,e.strUOM,e.dblWeight,e.dblMRP " + " from tblsalesorderhd a,tblsalesorderdtl b,tblpartymaster c,tblcurrentstock d,tblproductmaster e,tblsubgroupmaster f " + " where a.strSOCode=b.strSOCode and a.strCustCode=c.strPCode "
				+ " and b.strProdCode=d.strProdCode and b.strProdCode=e.strProdCode " + " and e.strSGCode=f.strSGCode and date(a.dteSODate) " + " between '"
				+ dteFromDate
				+ "' and '"
				+ dteToDate
				+ "' "
				+ " and d.strUserCode='"
				+ userCode
				+ "' and a.strClientCode='"
				+ clientCode
				+ "' "
				+ " and b.strClientCode='"
				+ clientCode
				+ "' and c.strClientCode='"
				+ clientCode
				+ "' "
				+ " and c.strClientCode='"
				+ clientCode
				+ "' and d.strClientCode='"
				+ clientCode
				+ "' "
				+ " and e.strClientCode='"
				+ clientCode
				+ "' and f.strClientCode='"
				+ clientCode + "' " + " group by e.strProdCode,f.strSGCode,c.strPCode " + " order by c.strPName,f.strSGName,e.strProdName  ";
		List listsqlMainData = objGlobalFunctionsService.funGetList(sqlMainData);

		List ExportList = new ArrayList();

		String sqlCustNameHeader = "select b.strPName,a.strCustCode from tblsalesorderhd a,tblpartymaster b " + " where a.strCustCode=b.strPCode  " + " and a.dteSODate between '" + dteFromDate + "' and '" + dteToDate + "' " + " and a.strClientCode = '" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'" + " group by a.strCustCode ";
		List listCustNameHeader = objGlobalFunctionsService.funGetList(sqlCustNameHeader);

		int arrlen = 1;
		List<String> listHeader = new ArrayList<String>();
		listHeader.add("Product Name");

		for (int cnt = 0; cnt < listCustNameHeader.size(); cnt++) {
			Object[] arrObjCustNameHeader = (Object[]) listCustNameHeader.get(cnt);
			listHeader.add(arrObjCustNameHeader[0].toString());

		}
		listHeader.add("Total");
		listHeader.add("Stock");
		listHeader.add("Production Qty");
		String[] ExcelHeader = new String[listHeader.size()];
		ExcelHeader = listHeader.toArray(ExcelHeader);

		ExportList.add(ExcelHeader);

		HashMap<String, List<String>> hmSGProd = new HashMap<String, List<String>>();
		List ProdDtlList = new ArrayList<>();

		String sqlSG = " select f.strSGName,e.strSGCode,e.strProdName,b.strProdCode " + " from tblsalesorderhd a,tblsalesorderdtl b,tblpartymaster c," + " tblcurrentstock d,tblproductmaster e,tblsubgroupmaster f " + " where a.strSOCode=b.strSOCode and a.strCustCode=c.strPCode and b.strProdCode=d.strProdCode " + " and b.strProdCode=e.strProdCode and e.strSGCode=f.strSGCode "
				+ " and date(a.dteSODate) between '"
				+ dteFromDate
				+ "' and '"
				+ dteToDate
				+ "' "
				+ " and d.strUserCode='"
				+ userCode
				+ "'  "
				+ " and a.strClientCode='"
				+ clientCode
				+ "' and b.strClientCode='"
				+ clientCode
				+ "' "
				+ " and c.strClientCode='"
				+ clientCode
				+ "' and c.strClientCode='"
				+ clientCode
				+ "' "
				+ "  and d.strClientCode='"
				+ clientCode
				+ "' and e.strClientCode='"
				+ clientCode
				+ "' "
				+ "  and f.strClientCode='"
				+ clientCode + "' " + "  group by e.strProdCode,f.strSGCode " + " order by f.strSGName,e.strProdName  ";

		List listSG = objGlobalFunctionsService.funGetList(sqlSG);
		// List lst =new ArrayList<String>();
		for (int sg = 0; sg < listSG.size(); sg++) {
			Object[] arrObjSG = (Object[]) listSG.get(sg);
			String arrSG = arrObjSG[0].toString();
			if (hmSGProd.containsKey(arrSG)) {
				hmSGProd.get(arrSG).add(arrObjSG[2].toString());
			} else {
				List lst = new ArrayList<>();
				lst.add(arrObjSG[2].toString());
				hmSGProd.put(arrSG, lst);
			}

		}

		int cnttag = 0;
		List listSGName = new ArrayList<String>();
		HashMap<String, Double> hmSubGrouptotal = new HashMap<String, Double>();
		for (Map.Entry<String, List<String>> entry : hmSGProd.entrySet()) {
			double[] totSGQty = new double[listCustNameHeader.size()];
			String sgName = entry.getKey();

			listSGName.add(sgName);
			List DataList = new ArrayList<>();
			List DataListSg = new ArrayList<>();

			// Start// for subgroup Total of each customer
			if (cnttag == 0) {
				DataListSg.add("SUBGROUP#");
			} else {
				DataListSg.add("SUBGROUP TOTAL#");
			}
			int cntSGtolDisplay = 0;
			if (listSGName.size() == 1) {
				for (Object ObjcustName : listCustNameHeader) {

					DataListSg.add("");
					cntSGtolDisplay++;
				}
			} else {
				for (Object ObjcustName : listCustNameHeader) {
					Object[] arrObjCustName = (Object[]) ObjcustName;
					String custName = arrObjCustName[0].toString();
					String preSGName = sgName;
					if (listSGName.size() > 1) {
						System.out.print(listSGName.get(listSGName.size() - 2) + "==" + (listSGName.size() - 1));
						preSGName = listSGName.get(listSGName.size() - 2).toString();
					}
					String key1 = preSGName + "#" + custName;

					if (hmSubGrouptotal.containsKey(key1)) {
						double totalQty = hmSubGrouptotal.get(key1);
						DataListSg.add(totalQty);
					} else {
						DataListSg.add("");
					}
				}

			}
			DataListSg.add("");
			DataListSg.add("");
			DataListSg.add("");
			ProdDtlList.add(DataListSg);
			// End//////////////////////////////////////

			DataList.add(sgName + "#");
			// for(Object ObjcustName : listCustNameHeader)
			// {
			// Object[] arrObjCustName=(Object[]) ObjcustName;
			// String custName = arrObjCustName[0].toString();
			// String preSGName=sgName;
			// if(listSGName.size()>1)
			// {
			// System.out.print(listSGName.get(listSGName.size()-2)+"=="+(listSGName.size()-1));
			// preSGName=listSGName.get(listSGName.size()-2).toString();
			// }
			// String key1=preSGName+"#"+custName;
			//
			// if(hmSubGrouptotal.containsKey(key1))
			// {
			// double totalQty=hmSubGrouptotal.get(key1);
			// DataList.add(totalQty);
			// }
			// else
			// {
			DataList.add("");
			// }
			// }
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			ProdDtlList.add(DataList);
			List<String> listval = entry.getValue();
			for (String strSGProd : listval) {
				List prodDataList = new ArrayList<>();
				prodDataList.add(strSGProd);

				double dblTotCustQty = 0;

				int cntCust = 0;
				double[] totSGCustQtyL2 = new double[listCustNameHeader.size()];
				for (Object ObjcustName : listCustNameHeader) {
					double custTotRowQty = 0;
					double[] totSGCustQtyL3 = new double[listCustNameHeader.size()];
					Object[] arrObjCustName = (Object[]) ObjcustName;
					String custName = arrObjCustName[0].toString();
					boolean flgCustInsertcell = false;

					for (int md = 0; md < listsqlMainData.size(); md++) {
						Object[] arrObjMD = (Object[]) listsqlMainData.get(md);
						{
							String custNameMD = arrObjMD[0].toString();
							String sgNameMD = arrObjMD[1].toString();
							String prodNameMD = arrObjMD[2].toString();
							double qtyMD = Double.parseDouble(arrObjMD[3].toString());
							qtyMD = Math.round(qtyMD / diffDay);
							if (custName.equals(custNameMD)) {
								if (strSGProd.equals(prodNameMD)) {
									prodDataList.add(qtyMD);
									dblTotCustQty = dblTotCustQty + qtyMD;

									String key = sgNameMD + "#" + custName;
									double qty = 0;
									boolean flgAddqty = false;
									if (hmSubGrouptotal.containsKey(key)) {
										qty = qtyMD + hmSubGrouptotal.get(key);
										flgAddqty = true;
									}
									if (flgAddqty) {
										hmSubGrouptotal.put(key, qty);
										flgAddqty = false;
									} else {
										hmSubGrouptotal.put(key, qtyMD);
									}

									// if(cntCust==0)
									// {
									// totSGCustQtyL3[cntCust]=qtyMD;
									// }else
									// {
									// totSGCustQtyL3[cntCust]=totSGCustQtyL3[cntCust-1]+qtyMD;
									// }

									flgCustInsertcell = true;
									break;

								}

							}

						}

					}
					// sub
					totSGCustQtyL2[cntCust] = totSGCustQtyL3[cntCust];

					if (!flgCustInsertcell) {
						prodDataList.add("");
					}

					cntCust++;
				}
				// totSGQty=totSGCustQtyL3[cntCust];
				prodDataList.add(dblTotCustQty);
				double stkQtyMD = 0;
				for (int md = 0; md < listsqlMainData.size(); md++) {
					Object[] arrObjMD = (Object[]) listsqlMainData.get(md);
					{
						// String custNameMD = arrObjMD[0].toString();
						String prodNameMD = arrObjMD[2].toString();
						stkQtyMD = Double.parseDouble(arrObjMD[4].toString());
						if (stkQtyMD < 0) {
							stkQtyMD = 0;
						}
						if (strSGProd.equals(prodNameMD)) {
							prodDataList.add(stkQtyMD);
							break;
						}
					}

				}
				double opQty = dblTotCustQty - stkQtyMD;
				if (opQty < 0) {
					prodDataList.add(0);
				} else {
					prodDataList.add(opQty);
				}
				sgName = "";
				ProdDtlList.add(prodDataList);

				String sqlProdMaster = " select a.strProdCode,a.strUOM,a.dblWeight,a.dblMRP " + " from tblproductmaster a " + " where a.strProdName='" + prodDataList.get(0) + "' and a.strClientCode='" + clientCode + "'  ";
				List listProdDtl = objGlobalFunctionsService.funGetList(sqlProdMaster);
				List showAutoGenOPList = new ArrayList();
				if (!listProdDtl.isEmpty()) {
					Object[] arrObjProdDtl = (Object[]) listProdDtl.get(0);
					showAutoGenOPList.add(arrObjProdDtl[0].toString());
					showAutoGenOPList.add(prodDataList.get(0));
					showAutoGenOPList.add(arrObjProdDtl[1].toString());
					showAutoGenOPList.add(arrObjProdDtl[2].toString());
					showAutoGenOPList.add(arrObjProdDtl[3].toString());
					int size = prodDataList.size();

					showAutoGenOPList.add(prodDataList.get(size - 3));
					showAutoGenOPList.add(prodDataList.get(size - 2));

					showAutoGenOPList.add(prodDataList.get(size - 1));

					retGenOPList.add(showAutoGenOPList);

				}
			}

			cnttag++;
		}

		if (hmSGProd.size() == cnttag) {
			List DataListLast = new ArrayList<>();
			DataListLast.add("SUBGROUP TOTAL#");
			for (Object ObjcustName : listCustNameHeader) {
				Object[] arrObjCustName = (Object[]) ObjcustName;
				String custName = arrObjCustName[0].toString();
				String preSGName = "";
				if (listSGName.size() > 1) {
					System.out.print(listSGName.get(listSGName.size() - 2) + "==" + (listSGName.size() - 1));
					preSGName = listSGName.get(listSGName.size() - 1).toString();
				}
				String key1 = preSGName + "#" + custName;

				if (hmSubGrouptotal.containsKey(key1)) {
					double totalQty = hmSubGrouptotal.get(key1);
					DataListLast.add(totalQty);
				} else {
					DataListLast.add("");
				}
			}
			DataListLast.add("");
			DataListLast.add("");
			DataListLast.add("");
			ProdDtlList.add(DataListLast);

		}

		ExportList.add(ProdDtlList);

		req.getSession().setAttribute("sessAutoGenOPData", retGenOPList);

		return retGenOPList;

	}

	@SuppressWarnings("finally")
	private long funDayDifference(String fromDate, String toDate) {
		long diffDays = 0;
		try {

			// HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = null;
			Date d2 = null;

			d1 = format.parse(fromDate);
			d2 = format.parse(toDate);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			// long diffSeconds = diff / 1000 % 60;
			// long diffMinutes = diff / (60 * 1000) % 60;
			// long diffHours = diff / (60 * 60 * 1000) % 24;
			diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			// System.out.print(diffHours + " hours, ");
			// System.out.print(diffMinutes + " minutes, ");
			// System.out.print(diffSeconds + " seconds.");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return diffDays;
		}
	}

	@RequestMapping(value = "/frmAutoGenProductionOrder", method = RequestMethod.GET)
	public ModelAndView funOpenAutoGeneratedReq(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAutoGenProductionOrderBean objAutoBean = new clsAutoGenProductionOrderBean();

		return new ModelAndView("frmAutoGenProductionOrder", "command", objAutoBean);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillAutoGenOPData", method = RequestMethod.POST)
	public @ResponseBody List fun_LoadAutoReqData(HttpServletRequest req, HttpServletResponse res) {
		List<clsProductionOrderDtlModel> temp = (List<clsProductionOrderDtlModel>) req.getSession().getAttribute("sessAutoGenOPData");
		List<clsProductionOrderDtlModel> listOPDtl = new ArrayList<clsProductionOrderDtlModel>();
		for (clsProductionOrderDtlModel ob : temp) {
			if (null != ob.getStrChecked() && "true".equalsIgnoreCase(ob.getStrChecked())) {
				listOPDtl.add(ob);
			}
		}
		return listOPDtl;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/UpdateAutoGenOPListSelectAll", method = RequestMethod.POST)
	public @ResponseBody String fun_UpdateSelectAll(HttpServletRequest req, HttpServletResponse res) {
		List<List> temp = (List) req.getSession().getAttribute("sessAutoGenOPData");
		List<clsProductionOrderDtlModel> objDtlModel = new ArrayList<clsProductionOrderDtlModel>();
		for (List tempList : temp) {
			clsProductionOrderDtlModel obj = new clsProductionOrderDtlModel();
			obj.setStrProdCode(tempList.get(0).toString());
			obj.setStrProdName(tempList.get(1).toString());
			// obj.setDbl(Double.parseDouble(tempList.get(5).toString()));
			obj.setDblQty(Double.parseDouble(tempList.get(7).toString()));
			obj.setDblWeight(Double.parseDouble(tempList.get(3).toString()));
			obj.setDblUnitPrice(Double.parseDouble(tempList.get(4).toString()));
			obj.setDblstock(Double.parseDouble(tempList.get(6).toString()));
			obj.setStrChecked("true");
			objDtlModel.add(obj);
		}

		// for(clsRequisitionDtlModel ob:temp)
		// {
		// ob.setStrChecked("true");
		// }
		req.getSession().setAttribute("sessAutoGenOPData", objDtlModel);
		return "UpdateAll";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAutoGenOPSessionValue", method = RequestMethod.POST)
	public @ResponseBody String funUpdateAutoReqSessionValue(HttpServletRequest req) {
		int index = Integer.parseInt(req.getParameter("chkIndex").toString());
		List<List> temp = (List) req.getSession().getAttribute("sessAutoGenOPData");
		List<clsProductionOrderDtlModel> objDtlModel = new ArrayList<clsProductionOrderDtlModel>();
		for (List tempList : temp) {
			clsProductionOrderDtlModel obj = new clsProductionOrderDtlModel();
			obj.setStrProdCode(tempList.get(0).toString());
			obj.setStrProdName(tempList.get(1).toString());
			// obj.setDbl(Double.parseDouble(tempList.get(5).toString()));
			obj.setDblQty(Double.parseDouble(tempList.get(7).toString()));
			obj.setDblWeight(Double.parseDouble(tempList.get(3).toString()));
			obj.setDblUnitPrice(Double.parseDouble(tempList.get(4).toString()));
			obj.setDblstock(Double.parseDouble(tempList.get(6).toString()));

			objDtlModel.add(obj);
		}

		clsProductionOrderDtlModel objTemp = objDtlModel.get(index);
		if (objTemp.getStrChecked().equalsIgnoreCase("true")) {
			objTemp.setStrChecked("false");
		} else {
			objTemp.setStrChecked("true");
		}
		req.getSession().setAttribute("sessAutoGenOPData", temp);
		return "update";
	}

	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String OPCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsProductionOrderHdModel objclsProductionOrderHdModel = objProductionOrderService.funGetObject(OPCode, clientCode);
			objGlobal = objGlobalFunctions;
			if (objclsProductionOrderHdModel != null) {

				List listOPDtl = objProductionOrderService.funGetDtlList(OPCode, clientCode);

				if (null != listOPDtl && listOPDtl.size() > 0) {
					String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + OPCode + "'";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");

					if (!list.isEmpty()) {
						String count = list.get(0).toString();
						clsAuditHdModel model = funPrepairAuditHdModel(objclsProductionOrderHdModel);
						if (strTransMode.equalsIgnoreCase("Deleted")) {
							model.setStrTransCode(objclsProductionOrderHdModel.getStrOPCode());
						} else {
							model.setStrTransCode(objclsProductionOrderHdModel.getStrOPCode() + "-" + count);
						}

						model.setStrClientCode(clientCode);
						model.setStrTransMode(strTransMode);
						model.setStrUserAmed(userCode);
						model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
						model.setStrUserModified(userCode);
						objGlobalFunctionsService.funSaveAuditHd(model);
						for (int i = 0; i < listOPDtl.size(); i++) {
							Object[] ob1 = (Object[]) listOPDtl.get(i);
							clsProductionOrderDtlModel opDtl = (clsProductionOrderDtlModel) ob1[0];
							clsAuditDtlModel AuditMode = new clsAuditDtlModel();
							AuditMode.setStrTransCode(model.getStrTransCode());
							AuditMode.setStrProdCode(opDtl.getStrProdCode());
							AuditMode.setDblQty(opDtl.getDblQty());
							AuditMode.setDblUnitPrice(opDtl.getDblUnitPrice());
							AuditMode.setDblCStock(opDtl.getDblstock());
							AuditMode.setStrRemarks("");
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrCode("");
							AuditMode.setStrTaxType("");
							AuditMode.setStrPICode("");
							AuditMode.setStrClientCode(opDtl.getStrClientCode());
							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private clsAuditHdModel funPrepairAuditHdModel(clsProductionOrderHdModel objOpHd) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (objOpHd != null) {
			AuditHdModel.setStrTransCode(objOpHd.getStrOPCode());
			AuditHdModel.setDtTransDate(objOpHd.getDtOPDate());
			AuditHdModel.setStrTransType("Production Order");
			AuditHdModel.setStrLocCode(objOpHd.getStrLocCode());
			AuditHdModel.setStrUserCreated(objOpHd.getStrUserCreated());
			AuditHdModel.setDtDateCreated(objOpHd.getDtDateCreated());
			AuditHdModel.setStrAuthorise(objOpHd.getStrAuthorise());
			AuditHdModel.setStrNarration("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrCode(objOpHd.getStrCode());
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

}
