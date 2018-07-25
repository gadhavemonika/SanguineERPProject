package com.sanguine.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
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
import com.sanguine.bean.clsAutoGenReqBean;
import com.sanguine.bean.clsRequisitionBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsMRPIDtl;
import com.sanguine.model.clsMRPIDtl_ID;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsPurchaseIndentDtlModel;
import com.sanguine.model.clsPurchaseIndentHdModel;
import com.sanguine.model.clsPurchaseIndentHdModel_ID;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsRequisitionHdModel;
import com.sanguine.model.clsRequisitionHdModel_ID;
import com.sanguine.model.clsSessionMasterModel;
import com.sanguine.model.clsTransactionTimeModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPurchaseIndentHdService;
import com.sanguine.service.clsRequisitionService;
import com.sanguine.service.clsSessionMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTransactionTimeService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsMaterialReqController {

	@Autowired
	private clsRequisitionService objReqService;
	@Autowired
	private clsLocationMasterService objLocService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsProductMasterService objProductMasterService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsPurchaseIndentHdService objClsPurchaseIndentHdService;
	@Autowired
	private clsUOMService objclsUOMService;

	clsGlobalFunctions objGlobal;

	@Autowired
	private clsWhatIfAnalysisController objWhatIfAnalysis;

	@Autowired
	private clsSessionMasterService objSessionMasterService;
	@Autowired
	private clsTransactionTimeService objTransactionTimeService;

	Map<String, List<String>> mapChildNodes;
	List<List<String>> listChildNodes;

	/**
	 * Open Material requisition Form
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmMaterialReq", method = RequestMethod.GET)
	public ModelAndView funOpenMaterialReqForm(Map<String, Object> model, HttpServletRequest req) {
		clsRequisitionBean bean = new clsRequisitionBean();
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		req.getSession().setAttribute("formName", "frmMaterialReq");
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		/**
		 * Checking Authorization
		 */
		String authorizationPOCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationPOCode = req.getParameter("authorizationReqCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationReqCode", authorizationPOCode);
		}
		/**
		 * Set Process
		 */
		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmMaterialReq", propCode, clientCode);
		if (list.size() > 0) {
			model.put("strProcessList", list);
		} else {
			list = new ArrayList<String>();
			model.put("strProcessList", list);
		}

		/**
		 * Set Session List
		 */
		List<clsSessionMasterModel> listSession = objSessionMasterService.funListSession(clientCode);
		Map hmSession = new HashMap();
		for (clsSessionMasterModel obj : listSession) {
			hmSession.put(obj.getStrSessionCode(), obj.getStrSessionName());
		}
		if (hmSession.isEmpty()) {
			hmSession.put("", "");

		}
		model.put("hmSession", hmSession);

		/**
		 * Set UOM List
		 */
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialReq_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialReq", "command", bean);
		} else {
			return null;
		}

	}

	/**
	 * Open Auto generate form
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmAutoGenReq", method = RequestMethod.GET)
	public ModelAndView funOpenAutoGeneratedReq(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAutoGenReqBean objAutoReqBean = new clsAutoGenReqBean();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		objAutoReqBean.setGroup(mapGroup);
		objAutoReqBean.setSubGroup(mapSubGroup);
		return new ModelAndView("frmAutoGenReq", "command", objAutoReqBean);

	}

	/**
	 * Save Material Requisition
	 * 
	 * @param reqBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveMaterialReq", method = RequestMethod.POST)
	public ModelAndView funSaveReq(@ModelAttribute("command") clsRequisitionBean reqBean, BindingResult result, HttpServletRequest req) throws ParseException {

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
//		String loginLocationCode = req.getSession().getAttribute("locationCode").toString();
		String loginLocationCode =reqBean.getStrLocBy();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		List<clsTransactionTimeModel> listclsTransactionTimeModel = new ArrayList<clsTransactionTimeModel>();
		listclsTransactionTimeModel = objTransactionTimeService.funLoadTransactionTimeLocationWise(propCode, clientCode, reqBean.getStrLocBy());
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
						return new ModelAndView("redirect:/frmMaterialReq.html?saddr=" + urlHits);
					}else{
						
						List<clsRequisitionDtlModel> listonReqDtl = reqBean.getListReqDtl();
						if (null != listonReqDtl && listonReqDtl.size() > 0) {
							clsRequisitionHdModel objHdModel = funPrepareModelHd(reqBean, req);
							objReqService.funAddRequisionHd(objHdModel);
							/*
							 * if(reqBean.getStrSaveSatandard()!=null &&
							 * reqBean.getStrSaveSatandard().equalsIgnoreCase("Y")) {
							 * objReqService
							 * .funDeleteProductStandard(reqBean.getStrLocBy(),
							 * propCode, clientCode); List<clsProductStandardModel>
							 * listProdStdModel=
							 * funPrepardStandarBean(reqBean,clientCode);
							 * for(clsProductStandardModel objProdStandardModel :
							 * listProdStdModel) {
							 * objReqService.funAddProductStandard(listProdStdModel); //
							 * }
							 * 
							 * }
							 */

							req.getSession().setAttribute("success", true);
							req.getSession().setAttribute("successMessage", "Requsition Code : ".concat(objHdModel.getStrReqCode()));
							req.getSession().setAttribute("rptReqCode", objHdModel.getStrReqCode());
							req.getSession().removeAttribute("AutoReqData");

						}	
						
					}
				}
			} else {
				List<clsRequisitionDtlModel> listonReqDtl = reqBean.getListReqDtl();
				if (null != listonReqDtl && listonReqDtl.size() > 0) {
					clsRequisitionHdModel objHdModel = funPrepareModelHd(reqBean, req);
					objReqService.funAddRequisionHd(objHdModel);
					/*
					 * if(reqBean.getStrSaveSatandard()!=null &&
					 * reqBean.getStrSaveSatandard().equalsIgnoreCase("Y")) {
					 * objReqService
					 * .funDeleteProductStandard(reqBean.getStrLocBy(),
					 * propCode, clientCode); List<clsProductStandardModel>
					 * listProdStdModel=
					 * funPrepardStandarBean(reqBean,clientCode);
					 * for(clsProductStandardModel objProdStandardModel :
					 * listProdStdModel) {
					 * objReqService.funAddProductStandard(listProdStdModel); //
					 * }
					 * 
					 * }
					 */

					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Requsition Code : ".concat(objHdModel.getStrReqCode()));
					req.getSession().setAttribute("rptReqCode", objHdModel.getStrReqCode());
					req.getSession().removeAttribute("AutoReqData");

				}
			}
			return new ModelAndView("redirect:/frmMaterialReq.html?saddr=" + urlHits);

		} else {
			return new ModelAndView("redirect:/frmMaterialReq.html?saddr=" + urlHits);
		}

	}

	/**
	 * Prepare Material Requisition HdModel
	 * 
	 * @param reqHdBean
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsRequisitionHdModel funPrepareModelHd(clsRequisitionBean reqHdBean, HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String
		// propCode=req.getSession().getAttribute("propertyCode").toString();
		// String
		// startDate=req.getSession().getAttribute("startDate").toString();
		// String
		// res=req.getSession().getAttribute("MRAuthorization").toString();
		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Material Req")) {
				res = true;
			}
		}

		long lastNo = 0;
		String strReqCode = "";
		clsRequisitionHdModel objReqHd = null;
		boolean flagNewReq = false;
		objGlobal = new clsGlobalFunctions();
		if (reqHdBean.getStrReqCode().trim().length() == 0) {
			flagNewReq = true;
			/*
			 * lastNo=objGlobalFunctionsService.funGetLastNo("tblreqhd",
			 * "MaterailReq","intId", clientCode); String
			 * year=objGlobal.funGetSplitedDate(startDate)[2]; String
			 * cd=objGlobal.funGetTransactionCode("RE",propCode,year);
			 * strReqCode = cd + String.format("%06d", lastNo);
			 */
			strReqCode = objGlobalFunctions.funGenerateDocumentCode("frmMaterialReq", reqHdBean.getDtReqDate(), req);
			objReqHd = new clsRequisitionHdModel(new clsRequisitionHdModel_ID(strReqCode, clientCode));
			objReqHd.setIntId(lastNo);

			if (res) {
				objReqHd.setStrAuthorise("No");

			} else {
				objReqHd.setStrAuthorise("Yes");
			}

			objReqHd.setStrUserCreated(userCode);
			objReqHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {

			clsRequisitionHdModel objReqModel = objReqService.funGetObject(reqHdBean.getStrReqCode(), clientCode);
			if (null == objReqModel) {
				flagNewReq = true;
				/*
				 * lastNo=objGlobalFunctionsService.funGetLastNo("tblreqhd",
				 * "MaterailReq","intId", clientCode); String
				 * year=objGlobal.funGetSplitedDate(startDate)[2]; String
				 * cd=objGlobal.funGetTransactionCode("RE",propCode,year);
				 * strReqCode = cd + String.format("%06d", lastNo);
				 */
				strReqCode = objGlobalFunctions.funGenerateDocumentCode("frmMaterialReq", reqHdBean.getDtReqDate(), req);
				objReqHd = new clsRequisitionHdModel(new clsRequisitionHdModel_ID(strReqCode, clientCode));
				objReqHd.setIntId(lastNo);

				if (res) {
					objReqHd.setStrAuthorise("No");

				} else {
					objReqHd.setStrAuthorise("Yes");
				}

				objReqHd.setStrUserCreated(userCode);
				objReqHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				flagNewReq = false;
				if (objGlobalFunctions.funCheckAuditFrom("frmMaterialReq", req)) {
					String type = "Edit";
					funSaveAudit(reqHdBean.getStrReqCode(), type, req);
				}
				objReqHd = new clsRequisitionHdModel(new clsRequisitionHdModel_ID(reqHdBean.getStrReqCode(), clientCode));
				objReqHd.setIntId(objReqModel.getIntId());
				objReqHd.setStrAuthorise(reqHdBean.getStrAuthorise());

			}
		}
		// objReqHd.setStrAuthorise(reqHdBean.getStrAuthorise());
		objReqHd.setStrLocBy(reqHdBean.getStrLocBy());
		objReqHd.setStrLocOn(reqHdBean.getStrLocOn());
		objReqHd.setDblSubTotal(reqHdBean.getDblSubTotal());
		objReqHd.setStrAgainst(reqHdBean.getStrAgainst());

		if (reqHdBean.getDtReqDate() != "") {
			objReqHd.setDtReqDate(objGlobal.funGetDate("yyyy-MM-dd", reqHdBean.getDtReqDate()));
		} else {
			objReqHd.setDtReqDate(null);
		}

		objReqHd.setStrUserModified(userCode);
		objReqHd.setStrWoCode("");
		objReqHd.setDblWOQty(0);
		objReqHd.setStrNarration(reqHdBean.getStrNarration());
		objReqHd.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		objReqHd.setStrUserCreated(userCode);
		objReqHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objReqHd.setDtReqiredDate(objGlobal.funGetDate("yyyy-MM-dd", reqHdBean.getDtReqiredDate()));
		objReqHd.setStrSessionCode(reqHdBean.getStrSessionCode());

		List<clsRequisitionDtlModel> tempDetailList = reqHdBean.getListReqDtl();
		List<clsRequisitionDtlModel> tempListNonStockableItems = new ArrayList<clsRequisitionDtlModel>();
		Iterator<clsRequisitionDtlModel> it = tempDetailList.iterator();
		while (it.hasNext()) {
			clsRequisitionDtlModel ob = it.next();
			if (null == ob.getStrProdCode()) {
				it.remove();
			}
			if (flagNewReq) {
				if ("Yes".equalsIgnoreCase(ob.getStrNonStockableItem())) {
					tempListNonStockableItems.add(ob);
				}
			}
		}
		objReqHd.setClsRequisitionDtlModel(tempDetailList);
		if (flagNewReq && tempListNonStockableItems.size() > 0) {
			funGeneratePINonSklbleProduct(tempListNonStockableItems, reqHdBean, strReqCode, req);
		}

		if (reqHdBean.getStrCloseReq() == null) {
			objReqHd.setStrCloseReq("N");
		} else {
			objReqHd.setStrCloseReq("Y");
		}

		if (reqHdBean.getStrReqFrom() == null) {
			objReqHd.setStrReqFrom("System");
		} else {
			objReqHd.setStrReqFrom(reqHdBean.getStrReqFrom());
		}

		return objReqHd;
	}

	/**
	 * Generate Purchase Indent for Non Stockable Product
	 * 
	 * @param tempListNonStockableItems
	 * @param bean
	 * @param strReqCode
	 * @param req
	 */
	private void funGeneratePINonSklbleProduct(List<clsRequisitionDtlModel> tempListNonStockableItems, clsRequisitionBean bean, String strReqCode, HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objGlobal = new clsGlobalFunctions();
		clsPurchaseIndentHdModel objPurchaseIndentHdModel;
		clsGlobalFunctions ob = new clsGlobalFunctions();
		String PIcode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseIndent", bean.getDtReqDate(), req);
		objPurchaseIndentHdModel = new clsPurchaseIndentHdModel(new clsPurchaseIndentHdModel_ID(PIcode, clientCode));
		// objPurchaseIndentHdModel.setIntId(lastNo);
		objPurchaseIndentHdModel.setStrUserCreated(userCode);
		objPurchaseIndentHdModel.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objPurchaseIndentHdModel.setStrNarration("Purchase Indent Generated From Materail Requestion No:-" + strReqCode);
		objPurchaseIndentHdModel.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objPurchaseIndentHdModel.setStrUserModified(userCode);
		objPurchaseIndentHdModel.setStrLocCode(bean.getStrLocBy());
		objPurchaseIndentHdModel.setDtPIDate(objGlobal.funGetDate("yyyy-MM-dd", bean.getDtReqDate()));
		objPurchaseIndentHdModel.setStrAuthorise("No");
		objPurchaseIndentHdModel.setStrClosePI("No");
		objPurchaseIndentHdModel.setStrAgainst(bean.getStrAgainst());
		objPurchaseIndentHdModel.setStrDocCode("");
		List<clsPurchaseIndentDtlModel> listPINonStockable = new ArrayList<clsPurchaseIndentDtlModel>();

		for (clsRequisitionDtlModel objClsRequisitionDtlModel : tempListNonStockableItems) {
			clsPurchaseIndentDtlModel PIOb = new clsPurchaseIndentDtlModel();
			PIOb.setStrProdCode(objClsRequisitionDtlModel.getStrProdCode());
			PIOb.setDblQty(objClsRequisitionDtlModel.getDblQty());
			PIOb.setDtReqDate(objGlobal.funGetDate("yyyy-MM-dd", bean.getDtReqDate()));
			PIOb.setStrAgainst("Requisition".toString());
			PIOb.setStrPurpose("");
			PIOb.setDblMinLevel(0);
			PIOb.setDblReOrderQty(0);
			listPINonStockable.add(PIOb);
		}
		objPurchaseIndentHdModel.setClsPurchaseIndentDtlModel(listPINonStockable);
		objClsPurchaseIndentHdService.funAddUpdatePurchaseIndent(objPurchaseIndentHdModel);
		clsMRPIDtl MRPIDtlModel = new clsMRPIDtl(new clsMRPIDtl_ID(strReqCode, PIcode, clientCode));

		objReqService.funSaveMRPIDtl(MRPIDtlModel);
		req.getSession().setAttribute("success1", true);
		req.getSession().setAttribute("successMessage1", "Auto Generate PI Inserted. Code : ".concat(PIcode));
	}

	/**
	 * Load Material RequisitionHd Data
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadReqData", method = RequestMethod.GET)
	public @ResponseBody clsRequisitionHdModel funOpenFormWithReqCode(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String ReqCode = req.getParameter("ReqCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsRequisitionHdModel objReqModel = objReqService.funGetObject(ReqCode, clientCode);

		if (null == objReqModel) {
			objReqModel = new clsRequisitionHdModel();
			objReqModel.setStrReqCode("Invalid Requisition Code");
			return objReqModel;

		} else {
			objReqModel.setDtReqDate(objGlobal.funGetDate("yyyy/MM/dd", objReqModel.getDtReqDate()));
			clsLocationMasterModel objLocHdBy = objLocService.funGetObject(objReqModel.getStrLocBy(), clientCode);
			clsLocationMasterModel objLocHdOn = objLocService.funGetObject(objReqModel.getStrLocOn(), clientCode);
			objReqModel.setStrLocByName(objLocHdBy.getStrLocName());
			objReqModel.setStrLocOnName(objLocHdOn.getStrLocName());

			List<clsRequisitionDtlModel> dtlList = new ArrayList<clsRequisitionDtlModel>();

			for (clsRequisitionDtlModel tempList : objReqModel.getClsRequisitionDtlModel()) {
				clsProductMasterModel productDtl = objProductMasterService.funGetObject(tempList.getStrProdCode(), clientCode);
				tempList.setStrProdName(productDtl.getStrProdName());
				tempList.setStrUOM(productDtl.getStrIssueUOM());
				dtlList.add(tempList);
			}
			objReqModel.setClsRequisitionDtlModel(dtlList);
			return objReqModel;
		}

	}

	/**
	 * Load Auto Requisition
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadAutoReqData", method = RequestMethod.GET)
	public @ResponseBody List funGetAutoReqProdData(HttpServletRequest req, HttpServletResponse resp) {
		double openReq = 0.0;
		objGlobal = new clsGlobalFunctions();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String LocCode = req.getParameter("LocCode").toString();
		String strGCode = req.getParameter("strGCode").toString();
		String strSGCode = req.getParameter("strSGCode").toString();
		String strSuppCode = req.getParameter("strSuppCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		String fromDate = startDate;
		String toDate = objGlobal.funGetCurrentDate("yyyy-MM-dd");
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String stockableItem = "Y";
		objGlobalFunctions.funInvokeStockFlash(startDate, LocCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);

		List listReqDtl = objReqService.funGenerateAutoReq(LocCode, clientCode, userCode, strGCode, strSGCode, strSuppCode);
		List<clsRequisitionDtlModel> _clsRequisitionDtlModel = new ArrayList<clsRequisitionDtlModel>();
		for (int i = 0; i < listReqDtl.size(); i++) {
			Object[] ob = (Object[]) listReqDtl.get(i);
			clsRequisitionDtlModel tempOb = new clsRequisitionDtlModel();
			double dblOrderQty = (Double.valueOf(ob[5].toString()) - Double.valueOf(ob[7].toString()) - Double.valueOf(ob[8].toString())) + Double.valueOf(ob[6].toString());
			if (dblOrderQty > 0) {
				tempOb.setStrProdCode(ob[0].toString());
				tempOb.setStrProdName(ob[1].toString());
				tempOb.setStrPartNo(ob[2].toString());
				tempOb.setDblPrice(Double.valueOf(ob[3].toString()));
				tempOb.setAvailStock(Double.valueOf(ob[7].toString()));
				tempOb.setDblReOrderLevel(Double.valueOf(ob[5].toString()));
				tempOb.setDblReOderQty(Double.valueOf(ob[6].toString()));
				openReq = Double.valueOf(ob[8].toString());
				tempOb.setStrUOM(ob[9].toString());
				tempOb.setOpenReq(openReq);
				tempOb.setDblOrderQty(dblOrderQty);
				tempOb.setStrChecked("false");
				_clsRequisitionDtlModel.add(tempOb);
			}
		}
		req.getSession().setAttribute("AutoReqData", _clsRequisitionDtlModel);
		return _clsRequisitionDtlModel;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAutoReqSessionValue", method = RequestMethod.POST)
	public @ResponseBody String funUpdateAutoReqSessionValue(HttpServletRequest req) {
		int index = Integer.parseInt(req.getParameter("chkIndex").toString());

		List<clsRequisitionDtlModel> temp = (List<clsRequisitionDtlModel>) req.getSession().getAttribute("AutoReqData");

		clsRequisitionDtlModel objTemp = temp.get(index);
		if (objTemp.getStrChecked().equalsIgnoreCase("true")) {
			objTemp.setStrChecked("false");
		} else {
			objTemp.setStrChecked("true");
		}
		req.getSession().setAttribute("AutoReqData", temp);
		return "update";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/UpdateAutoReqListSelectAll", method = RequestMethod.POST)
	public @ResponseBody String fun_UpdateSelectAll(HttpServletRequest req, HttpServletResponse res) {
		List<clsRequisitionDtlModel> temp = (List<clsRequisitionDtlModel>) req.getSession().getAttribute("AutoReqData");
		for (clsRequisitionDtlModel ob : temp) {
			ob.setStrChecked("true");
		}
		req.getSession().setAttribute("AutoReqData", temp);
		return "UpdateAll";

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillAutoReqData", method = RequestMethod.POST)
	public @ResponseBody List fun_LoadAutoReqData(HttpServletRequest req, HttpServletResponse res) {
		List<clsRequisitionDtlModel> temp = (List<clsRequisitionDtlModel>) req.getSession().getAttribute("AutoReqData");
		List<clsRequisitionDtlModel> listReqDtl = new ArrayList<clsRequisitionDtlModel>();
		for (clsRequisitionDtlModel ob : temp) {
			if (null != ob.getStrChecked() && "true".equalsIgnoreCase(ob.getStrChecked())) {
				listReqDtl.add(ob);
			}
		}
		return listReqDtl;
	}

	/**
	 * Auditing Function
	 * 
	 * @param reqModel
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String strReqCode, String strTransMode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsRequisitionHdModel reqModel = objReqService.funGetObject(strReqCode, clientCode);
		if (null != reqModel) {
			List<clsRequisitionDtlModel> listReqDtl = reqModel.getClsRequisitionDtlModel();
			if (null != listReqDtl && listReqDtl.size() > 0) {
				String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + reqModel.getStrReqCode() + "' and strClientCode='" + clientCode + "'";
				List list = objGlobalFunctionsService.funGetList(sql, "sql");
				if (!list.isEmpty()) {
					String count = list.get(0).toString();
					clsAuditHdModel model = funPrepairAuditHdModel(reqModel);
					if (strTransMode.equalsIgnoreCase("Deleted")) {
						model.setStrTransCode(reqModel.getStrReqCode());
					} else {
						model.setStrTransCode(reqModel.getStrReqCode() + "-" + count);
					}

					model.setStrClientCode(clientCode);
					model.setStrTransMode(strTransMode);
					model.setStrUserAmed(userCode);
					model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
					model.setStrUserModified(userCode);
					objGlobalFunctionsService.funSaveAuditHd(model);
					for (clsRequisitionDtlModel ob : listReqDtl) {
						clsAuditDtlModel AuditMode = new clsAuditDtlModel();
						AuditMode.setStrTransCode(model.getStrTransCode());
						if (ob.getStrProdCode() != null) {
							AuditMode.setStrProdCode(ob.getStrProdCode());
							AuditMode.setDblQty(ob.getDblQty());
							AuditMode.setDblUnitPrice(ob.getDblUnitPrice());
							AuditMode.setDblTotalPrice(ob.getDblTotalPrice());
							AuditMode.setStrRemarks(ob.getStrRemarks());
							AuditMode.setStrPICode("");
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrCode("");
							AuditMode.setStrPICode("");
							AuditMode.setStrTaxType("");
							AuditMode.setStrClientCode(clientCode);
							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}
				}
			}
		}
	}

	/**
	 * Prepare AuditHd Model
	 * 
	 * @param reqModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsRequisitionHdModel reqModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (reqModel != null) {
			AuditHdModel.setStrTransCode(reqModel.getStrReqCode());
			AuditHdModel.setDtTransDate(reqModel.getDtReqDate());
			AuditHdModel.setStrTransType("Material Requisition");
			// AuditHdModel.setStrTransMode("Edit");
			AuditHdModel.setStrAgainst(reqModel.getStrAgainst());
			AuditHdModel.setStrLocBy(reqModel.getStrLocBy());
			AuditHdModel.setStrLocOn(reqModel.getStrLocOn());
			AuditHdModel.setStrCloseReq(reqModel.getStrCloseReq());
			AuditHdModel.setStrNarration(reqModel.getStrNarration());
			AuditHdModel.setStrWoCode(reqModel.getStrWoCode());
			AuditHdModel.setDblSubTotal(reqModel.getDblSubTotal());
			AuditHdModel.setDblWOQty(reqModel.getDblWOQty());
			AuditHdModel.setDtDateCreated(reqModel.getDtCreatedDate());
			AuditHdModel.setStrUserCreated(reqModel.getStrUserCreated());
			AuditHdModel.setStrAuthorise(reqModel.getStrAuthorise());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrLocCode("");
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setStrPayMode("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrCode("");
		}
		return AuditHdModel;

	}

	@RequestMapping(value = "/openRptMaterialReqSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String ReqCode = req.getParameter("rptReqCode").toString();
		req.getSession().removeAttribute("rptReqCode");
		String type = "pdf";
		funCallReport(ReqCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(String ReqCode, String type, HttpServletResponse resp, HttpServletRequest req) {
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
			clsRequisitionHdModel objReqModel = objReqService.funGetObject(ReqCode, clientCode);
			clsLocationMasterModel objLocHdBy = objLocService.funGetObject(objReqModel.getStrLocBy(), clientCode);
			clsLocationMasterModel objLocHdOn = objLocService.funGetObject(objReqModel.getStrLocOn(), clientCode);
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptRequisitionSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = "select a.strProdCode,p.strProdName,a.dblQty,a.dblUnitPrice,a.dblTotalPrice,a.strRemarks,p.strIssueUOM,p.strBinNo" + " from tblreqdtl a, tblproductmaster p where a.strProdCode=p.strProdCode " + "and a.strReqCode='" + ReqCode + "' and a.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsReqslp");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			String dtReqDate = objGlobal.funGetDate("dd-MM-yyyy", objReqModel.getDtReqDate());
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strReqCode", ReqCode);

			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());

			hm.put("dtReqDate", dtReqDate);
			hm.put("strAgainst", objReqModel.getStrAgainst());
			hm.put("strFromLoc", objLocHdBy.getStrLocName());
			hm.put("strToLoc", objLocHdOn.getStrLocName());
			hm.put("strNarration", objReqModel.getStrNarration());
			hm.put("reportTitle","Material Requisition Slip");
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptReqSlip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReqSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open Material Requisition Slip Form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmMaterialReqSlip", method = RequestMethod.GET)
	public ModelAndView funOpenReqSlipForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmWebStockHelpRequisitionSlip");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialReqSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMaterialReqSlip", "command", new clsReportBean());
		}

	}

	/**
	 * Range Requisition Printing
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptReqSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			String type = objBean.getStrDocType();
			String strFromReqCode = objBean.getStrFromDocCode();
			String strToReqCode = objBean.getStrToDocCode();
			String clientCode = req.getSession().getAttribute("clientCode").toString();

			String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String tempToLoc[] = objBean.getStrToLocCode().split(",");
			String tempFromLoc[] = objBean.getStrFromLocCode().split(",");
			String strToLocCodes = "";
			String strFromLocCodes = "";

			for (int i = 0; i < tempFromLoc.length; i++) {
				if (strFromLocCodes.length() > 0) {
					strFromLocCodes = strFromLocCodes + " or a.strLocBy='" + tempFromLoc[i] + "' ";
				} else {
					strFromLocCodes = "a.strLocBy='" + tempFromLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempToLoc.length; i++) {
				if (strToLocCodes.length() > 0) {
					strToLocCodes = strToLocCodes + " or a.strLocOn='" + tempToLoc[i] + "' ";
				} else {
					strToLocCodes = "a.strLocOn='" + tempToLoc[i] + "' ";

				}
			}

			String sql = "select a.strReqCode from tblreqhd a where date(a.dtReqDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "'";

			if (objBean.getStrFromLocCode().length() > 0) {
				sql = sql + " and (" + strFromLocCodes + ")  ";
			}
			if (objBean.getStrToLocCode().length() > 0) {
				sql = sql + " and (" + strToLocCodes + ")  ";
			}
			if (strFromReqCode.trim().length() > 0 && strToReqCode.trim().length() > 0) {
				sql = sql + " and a.strReqCode between '" + strFromReqCode + "' and '" + strToReqCode + "' ";
			}
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list != null && !list.isEmpty()) {
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int i = 0; i < list.size(); i++) {
					JasperPrint p = funCallRangePrintReport(list.get(i).toString(), resp, req);
					jprintlist.add(p);
				}

				if (type.trim().equalsIgnoreCase("pdf")) {

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptReqSlip." + type.trim());
					exporter.exportReport();

					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReqSlip." + type.trim());
					exporterXLS.exportReport();
					resp.setContentType("application/xlsx");

				} else if (type.trim().equalsIgnoreCase("HTML")) {
					JRExporter exporterhtml = new JRHtmlExporter();
					exporterhtml.setParameter(JRHtmlExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterhtml.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReqSlip." + type.trim().toLowerCase());
					exporterhtml.exportReport();
					resp.setContentType("application/html; charset=utf-8");

				} else if (type.trim().equalsIgnoreCase("CSV")) {
					JRExporter exporterXLS = new JRCsvExporter();
					exporterXLS.setParameter(JRCsvExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReqSlip." + type.trim().toLowerCase());
					exporterXLS.exportReport();
					resp.setContentType("application/csv");

				}

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	public JasperPrint funCallRangePrintReport(String ReqCode, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		JasperPrint p = null;
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			clsRequisitionHdModel objReqModel = objReqService.funGetObject(ReqCode, clientCode);
			clsLocationMasterModel objLocHdBy = objLocService.funGetObject(objReqModel.getStrLocBy(), clientCode);
			clsLocationMasterModel objLocHdOn = objLocService.funGetObject(objReqModel.getStrLocOn(), clientCode);
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptRequisitionSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = "select a.strProdCode,p.strProdName,a.dblQty,a.dblUnitPrice,a.dblTotalPrice,a.strRemarks,p.strIssueUOM,p.strBinNo" + " from tblreqdtl a, tblproductmaster p where a.strProdCode=p.strProdCode " + "and a.strReqCode='" + ReqCode + "' and a.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "' ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsReqslp");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			String dtReqDate = objGlobal.funGetDate("dd-MM-yyyy", objReqModel.getDtReqDate());
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strReqCode", ReqCode);

			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());

			hm.put("dtReqDate", dtReqDate);
			hm.put("strAgainst", objReqModel.getStrAgainst());
			hm.put("strFromLoc", objLocHdBy.getStrLocName());
			hm.put("strToLoc", objLocHdOn.getStrLocName());
			hm.put("strNarration", objReqModel.getStrNarration());
			p = JasperFillManager.fillReport(jr, hm, con);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			return p;
		}
	}

	// @RequestMapping(value = "/getOPParentList", method = RequestMethod.GET)
	// public @ResponseBody List funGetParentList(HttpServletRequest request)
	// {
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// String userCode=request.getSession().getAttribute("usercode").toString();
	// String OPCode=request.getParameter("OPCode").toString();
	//
	// objWhatIfAnalysis.listChildNodes1=new ArrayList<String>();
	// String
	// sql="select b.strProdCode,b.dblQty from tblproductionorderhd a,tblproductionorderdtl b "
	// +
	// " where a.strOPCode=b.strOPCode and a.strOPCode='"+OPCode+"' and a.strClientCode=b.strClientCode and a.strClientCode='"+clientCode+"'";
	//
	// List list=objGlobalFunctionsService.funGetList(sql,"sql");
	// if(!list.isEmpty())
	// {
	// Object[] arrObj=(Object[])list.get(0);
	// String parentCode=arrObj[0].toString();
	// double parentQty=Double.parseDouble(arrObj[1].toString());
	//
	// }
	// return list;
	// }

	@RequestMapping(value = "/getOPChildNodes", method = RequestMethod.GET)
	public @ResponseBody Map<String, List> funGetChildNodes(HttpServletRequest request) {
		Map<String, List> hmChildNodes = new HashMap<String, List>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String OPCode = request.getParameter("OPCode").toString();
		// String ProdCode=request.getParameter("ProdCode").toString();
		objWhatIfAnalysis.listChildNodes1 = new ArrayList<String>();
		String sql = "select b.strProdCode,b.dblQty from tblproductionorderhd a,tblproductionorderdtl b " + " where a.strOPCode=b.strOPCode and a.strOPCode='" + OPCode + "' "
		// + " and b.strProdCode='"+ProdCode+"' "
				+ " and a.strClientCode=b.strClientCode and a.strClientCode='" + clientCode + "'";

		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		if (!list.isEmpty()) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				String parentCode = arrObj[0].toString();
				double parentQty = Double.parseDouble(arrObj[1].toString());
				objWhatIfAnalysis.funGetBOMNodes(parentCode, 0, parentQty);
			}
		}
		// String param=request.getParameter("prodCode").toString();
		// System.out.println(param);
		// param=param.substring(1,param.length());
		// String[] sp=param.split(",");
		// for(int cn=0;cn<sp.length;cn++)
		// {
		// String sp1[]=sp[cn].split("!");
		// double reqdQty=Double.parseDouble(sp1[1]);
		// objWhatIfAnalysis.funGetBOMNodes(sp1[0],0,reqdQty);
		// }
		//
		for (int cnt = 0; cnt < objWhatIfAnalysis.listChildNodes1.size(); cnt++) {
			clsProductMasterModel objModel = null;
			List arrListBOMProducts = new ArrayList<String>();
			String temp = (String) objWhatIfAnalysis.listChildNodes1.get(cnt);
			String prodCode = temp.split(",")[0];
			double reqdQty = Double.parseDouble(temp.split(",")[1]);
			double openPOQty = objWhatIfAnalysis.funGetOpenPOQty(prodCode, clientCode);
			// double
			// currentStock=objGlobal.funGetCurrentStockForProduct(prodCode,
			// "L000001", clientCode, userCode, "2015-01-09", "2015-03-09");
			double currentStock = 0;
			double orderQty = reqdQty - (openPOQty + currentStock);
			if (orderQty < 0)
				orderQty = 0;
			System.out.println("Prod=" + prodCode + "\topenPO=" + openPOQty + "\tstk=" + currentStock + "\tOrder Qty=" + orderQty);
			String productInfo = objWhatIfAnalysis.funGetProdInfo(prodCode);
			String prodName = "", uom = "", unitprice = "0.00";

			if (productInfo.trim().length() > 0) {
				String[] spProd = productInfo.split("#");
				prodName = spProd[0];
				uom = spProd[1];

			} else {
				objModel = objProductMasterService.funGetObject(prodCode, clientCode);
				prodName = objModel.getStrProdName();
				uom = objModel.getStrRecipeUOM();
				unitprice = Double.toString(objModel.getDblCostRM());

			}

			if (null != hmChildNodes.get(prodCode)) {
				List arrListTemp = hmChildNodes.get(prodCode);

				orderQty = orderQty + Double.parseDouble(arrListTemp.get(3).toString());

				hmChildNodes.remove(prodCode);
			}

			arrListBOMProducts.add(prodCode);
			arrListBOMProducts.add(prodName);
			arrListBOMProducts.add(uom);
			arrListBOMProducts.add(orderQty);
			double uprice = 0.00;
			uprice = Double.parseDouble(unitprice);
			arrListBOMProducts.add(unitprice);
			arrListBOMProducts.add(orderQty * uprice);

			hmChildNodes.put(prodCode, arrListBOMProducts);
		}
		objWhatIfAnalysis.listChildNodes1 = null;
		return hmChildNodes;
	}

	private List<clsProductStandardModel> funPrepardStandarBean(clsRequisitionBean objBean, String clientCode) {
		List<clsProductStandardModel> listprodStandard = new ArrayList<clsProductStandardModel>();
		for (clsRequisitionDtlModel objDtl : objBean.getListReqDtl()) {
			clsProductStandardModel prodStdModel = new clsProductStandardModel();
			prodStdModel.setStrProdCode(objDtl.getStrProdCode());
			prodStdModel.setStrRemarks(objDtl.getStrRemarks());
			prodStdModel.setStrClientCode(clientCode);
			prodStdModel.setStrStandardType("RequestionStandard");
			prodStdModel.setDblQty(objDtl.getDblQty());
			prodStdModel.setDblUnitPrice(objDtl.getDblUnitPrice());
			prodStdModel.setDblTotalPrice(objDtl.getDblTotalPrice());
			listprodStandard.add(prodStdModel);
		}
		return listprodStandard;

	}

	/**
	 * Load Material Standard Data
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadLoadStandardProduct", method = RequestMethod.GET)
	public @ResponseBody List<clsRequisitionDtlModel> funLoadLoadStandardProduct(@RequestParam("strLocCode") String strLocCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();

		List<clsRequisitionDtlModel> dtlList = new ArrayList<clsRequisitionDtlModel>();
		List<clsProductStandardModel> stdList = objReqService.funGetProductStandartList(propertyCode, strLocCode, clientCode);
		for (clsProductStandardModel objStd : stdList) {
			clsRequisitionDtlModel tempList = new clsRequisitionDtlModel();
			clsProductMasterModel productDtl = objProductMasterService.funGetObject(objStd.getStrProdCode(), clientCode);
			tempList.setStrProdCode(objStd.getStrProdCode());
			tempList.setStrProdName(productDtl.getStrProdName());
			tempList.setStrUOM(productDtl.getStrIssueUOM());
			tempList.setStrRemarks(objStd.getStrRemarks());
			tempList.setDblQty(objStd.getDblQty());
			tempList.setDblUnitPrice(objStd.getDblUnitPrice());
			tempList.setDblTotalPrice(objStd.getDblTotalPrice());
			tempList.setDblWeight(0.00);
			tempList.setDblOrderQty(0.00);
			tempList.setDblReOderQty(0.00);
			dtlList.add(tempList);
		}
		return dtlList;

	}

}
