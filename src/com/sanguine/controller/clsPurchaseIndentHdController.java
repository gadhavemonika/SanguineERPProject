package com.sanguine.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsAutoGeneratePIHelpBean;
import com.sanguine.bean.clsPurchaseIndentHdBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsPurchaseIndentDtlModel;
import com.sanguine.model.clsPurchaseIndentHdModel;
import com.sanguine.model.clsPurchaseIndentHdModel_ID;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsRequisitionHdModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPurchaseIndentHdService;
import com.sanguine.service.clsRequisitionService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.util.clsReportBean;

@Configuration
@Controller
public class clsPurchaseIndentHdController {

	@Autowired
	ServletContext servletContext;
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsPurchaseIndentHdService objClsPurchaseIndentHdService;

	@Autowired
	private clsGroupMasterService objGrpMasterService;// service to add combobox
														// on Auto PI Help Fprm
														// :RP

	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;// service to add
															// combobox on Auto
															// PI Help Fprm:RP

	@Autowired
	private clsProductMasterService objProductMasterService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsRequisitionService objReqService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	private clsGlobalFunctions objGlobal = null;

	/**
	 * Open Purchase Indent Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmPurchaseIndent", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseIndentForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmPurchaseIndent");
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
			docCode = request.getParameter("authorizationPICode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationPICode", docCode);
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseIndent_1", "command", new clsPurchaseIndentHdBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseIndent", "command", new clsPurchaseIndentHdBean());
		} else {
			return null;
		}

	}

	/**
	 * Open Auto Generate Purchase Indent Form
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmAutoPIHelp", method = RequestMethod.GET)
	public ModelAndView funOpenAutoGeneratedPIHelp(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAutoGeneratePIHelpBean objAutoPIBean = new clsAutoGeneratePIHelpBean();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		objAutoPIBean.setGroup(mapGroup);
		objAutoPIBean.setSubGroup(mapSubGroup);
		return new ModelAndView("frmAutoGeneratePIHelp", "command", objAutoPIBean);
	}

	/**
	 * Load Subgroup Data
	 * 
	 * @param code
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadSubGroupCombo", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> SubGroupFroGroup(@RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Map<String, String> subGroup = objSubGrpMasterService.funGetSubgroups(code, clientCode);
		return subGroup;
	}

	/**
	 * Save Purchase Indent from
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/savepurchaseIndent", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPurchaseIndentHdBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		if (!result.hasErrors()) {
			List<clsPurchaseIndentDtlModel> listPurchaseIndentDtlModel = objBean.getListPurchaseIndentDtlModel();
			if (null != listPurchaseIndentDtlModel && listPurchaseIndentDtlModel.size() > 0) {
				clsPurchaseIndentHdModel objPurchaseIndentHdModel = funPrepareMaster(objBean, userCode, clientCode, propCode, startDate, req);
				objClsPurchaseIndentHdService.funAddUpdatePurchaseIndent(objPurchaseIndentHdModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "PI Code : ".concat(objPurchaseIndentHdModel.getStrPICode()));
				req.getSession().setAttribute("rptPICode", objPurchaseIndentHdModel.getStrPICode());
				req.getSession().removeAttribute("AutoPIData");
			}

			return new ModelAndView("redirect:/frmPurchaseIndent.html?saddr=" + urlHits);
		}

		return new ModelAndView("frmPurchaseIndent?saddr=" + urlHits);
	}

	/**
	 * Prepare Purchase Indent HdModel
	 * 
	 * @param objBean
	 * @param userCode
	 * @param clientCode
	 * @param propCode
	 * @param startDate
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsPurchaseIndentHdModel funPrepareMaster(clsPurchaseIndentHdBean objBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest req) {
		long lastNo = 0;
		clsPurchaseIndentHdModel objPurchaseIndentHdModel;
		clsGlobalFunctions ob = new clsGlobalFunctions();
		objGlobal = new clsGlobalFunctions();
		if (objBean.getStrPIcode().trim().length() == 0) {
			String PIcode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseIndent", objBean.getDtPIDate(), req);
			objPurchaseIndentHdModel = new clsPurchaseIndentHdModel(new clsPurchaseIndentHdModel_ID(PIcode, clientCode));
			lastNo = objGlobalFunctionsService.funGetLastNo("tblpurchaseindendhd", "PICode", "intId", clientCode);
			objPurchaseIndentHdModel.setIntId(lastNo);
			objPurchaseIndentHdModel.setStrUserCreated(userCode);
			objPurchaseIndentHdModel.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsPurchaseIndentHdModel objclsPurchaseIndentHdModel = objClsPurchaseIndentHdService.funGetObject(objBean.getStrPIcode(), clientCode);
			if (null == objclsPurchaseIndentHdModel) {
				String PIcode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseIndent", objBean.getDtPIDate(), req);
				objPurchaseIndentHdModel = new clsPurchaseIndentHdModel(new clsPurchaseIndentHdModel_ID(PIcode, clientCode));
				lastNo = objGlobalFunctionsService.funGetLastNo("tblpurchaseindendhd", "PICode", "intId", clientCode);
				objPurchaseIndentHdModel.setIntId(lastNo);
				objPurchaseIndentHdModel.setStrUserCreated(userCode);
				objPurchaseIndentHdModel.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmPurchaseIndent", req)) {
					funSaveAudit(objBean.getStrPIcode(), "Edit", req);
				}
				objPurchaseIndentHdModel = new clsPurchaseIndentHdModel(new clsPurchaseIndentHdModel_ID(objBean.getStrPIcode(), clientCode));
			}

		}
		objPurchaseIndentHdModel.setStrNarration(objBean.getStrNarration());
		objPurchaseIndentHdModel.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objPurchaseIndentHdModel.setStrUserModified(userCode);
		objPurchaseIndentHdModel.setStrLocCode(objBean.getStrLocCode());
		objPurchaseIndentHdModel.setDtPIDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtPIDate()));
		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Purchase Indent")) {
				res = true;
			}
		}
		if (res) {
			objPurchaseIndentHdModel.setStrAuthorise("No");

		} else {
			objPurchaseIndentHdModel.setStrAuthorise("Yes");
		}
		objPurchaseIndentHdModel.setDblTotal(objBean.getDblTotal());
		List<clsPurchaseIndentDtlModel> tempDetailList = objBean.getListPurchaseIndentDtlModel();
		Iterator<clsPurchaseIndentDtlModel> it = tempDetailList.iterator();
		while (it.hasNext()) {
			clsPurchaseIndentDtlModel Obj = it.next();
			if (null == Obj.getStrProdCode()) {
				it.remove();
			} else {
				if (null != Obj.getDtReqDate() && Obj.getDtReqDate().trim().length() != 0) {
					Obj.setDtReqDate(objGlobal.funGetDate("yyyy-MM-dd", Obj.getDtReqDate()));
				} else {
					Obj.setDtReqDate(null);
				}
			}
		}
		objPurchaseIndentHdModel.setClsPurchaseIndentDtlModel(tempDetailList);
		if (objBean.getStrClosePI() == null) {
			objPurchaseIndentHdModel.setStrClosePI("No");

		} else {

			objPurchaseIndentHdModel.setStrClosePI("Yes");
		}

		if (objBean.getStrDocCode() != null) {
			objPurchaseIndentHdModel.setStrDocCode(objBean.getStrDocCode());
		} else {
			objPurchaseIndentHdModel.setStrDocCode("");
		}

		if (objBean.getStrDocType() != null) {
			objPurchaseIndentHdModel.setStrAgainst(objBean.getStrDocType());
		} else {
			objPurchaseIndentHdModel.setStrAgainst("");
		}

		return objPurchaseIndentHdModel;
	}

	/**
	 * Load Purchase Indent Data
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmPurchaseIndent1", method = RequestMethod.GET)
	public @ResponseBody clsPurchaseIndentHdModel funLoadPIHdData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String PIcode = request.getParameter("PICode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		clsPurchaseIndentHdModel objPIHdLsit = objClsPurchaseIndentHdService.funGetObject(PIcode, clientCode);
		if (null != objPIHdLsit) {
			request.getSession().setAttribute("transname", "frmPurchaseIndent.jsp");
			request.getSession().setAttribute("formname", "Purchase Indent");
			request.getSession().setAttribute("code", PIcode);
		}

		if (null != objPIHdLsit) {
			objPIHdLsit.setDtPIDate(objGlobal.funGetDate("yyyy/MM/dd", objPIHdLsit.getDtPIDate()));
			clsLocationMasterModel objLocHd = objLocationMasterService.funGetObject(objPIHdLsit.getStrLocCode(), clientCode);
			objPIHdLsit.setStrLocName(objLocHd.getStrLocName());
			List<clsPurchaseIndentDtlModel> dtlList = new ArrayList<clsPurchaseIndentDtlModel>();
			for (clsPurchaseIndentDtlModel obj : objPIHdLsit.getClsPurchaseIndentDtlModel()) {
				obj.setStrProdName(objProductMasterService.funGetProductName(obj.getStrProdCode(), clientCode));
				obj.setDtReqDate(objGlobal.funGetDate("yyyy/MM/dd", obj.getDtReqDate()));
				dtlList.add(obj);
			}
			objPIHdLsit.setClsPurchaseIndentDtlModel(dtlList);
		}
		return objPIHdLsit;

	}

	/**
	 * Loading ReOrder Level Data
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadReorderLevel", method = RequestMethod.GET)
	public @ResponseBody clsProductReOrderLevelModel funGetReorderLevel(HttpServletRequest request) {
		String strProdCode = request.getParameter("prodCode").toString();
		String strLocCode = request.getParameter("strLocCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsProductReOrderLevelModel reOrder = objClsPurchaseIndentHdService.funGetReOrderLevel(strProdCode, strLocCode, clientCode);
		return reOrder;
	}

	/**
	 * Load Auto Generate Purchase Indent Data
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadAutoPIData", method = RequestMethod.GET)
	public @ResponseBody List funGetAutoReqProdData(HttpServletRequest req, HttpServletResponse resp) {
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

		String toDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String stockableItem = "Y";
		objGlobalFunctions.funGetStocks(LocCode, startDate, toDate, clientCode, userCode, stockableItem, req, resp);
		List listPIDtl = objReqService.funGenerateAutoReq(LocCode, clientCode, userCode, strGCode, strSGCode, strSuppCode);

		List<clsPurchaseIndentDtlModel> _clsPIDtlModel = new ArrayList<clsPurchaseIndentDtlModel>();
		for (int i = 0; i < listPIDtl.size(); i++) {
			Object[] ob = (Object[]) listPIDtl.get(i);
			clsPurchaseIndentDtlModel tempOb = new clsPurchaseIndentDtlModel();
			double dblOrderQty = (Double.valueOf(ob[5].toString()) - Double.valueOf(ob[7].toString()) - Double.valueOf(ob[8].toString())) + Double.valueOf(ob[6].toString());
			if (dblOrderQty > 0) {
				tempOb.setStrProdCode(ob[0].toString());
				tempOb.setStrProdName(ob[1].toString());
				tempOb.setDblRate(Double.valueOf(ob[3].toString()));
				tempOb.setStrInStock(Double.valueOf(ob[7].toString()));
				tempOb.setDblMinLevel(Double.valueOf(ob[5].toString()));
				tempOb.setDblReOrderQty(Double.valueOf(ob[6].toString()));
				tempOb.setDblQty(dblOrderQty);
				tempOb.setStrChecked("false");
				_clsPIDtlModel.add(tempOb);
			}
		}
		req.getSession().setAttribute("AutoPIData", _clsPIDtlModel);
		return _clsPIDtlModel;
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadAutoRequitionPIData", method = RequestMethod.GET)
	public @ResponseBody List funGetAutoRequitionProdData(HttpServletRequest req, HttpServletResponse resp) {
		objGlobal = new clsGlobalFunctions();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String LocCode = req.getParameter("LocCode").toString();
		String strGCode = req.getParameter("strGCode").toString();
		String strSGCode = req.getParameter("strSGCode").toString();
		String strSuppCode = req.getParameter("strSuppCode").toString();
		String strReqCode = req.getParameter("strReqCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String toDate = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String stockableItem = "Y";
		objGlobalFunctions.funGetStocks(LocCode, startDate, toDate, clientCode, userCode, stockableItem, req, resp);
		List<clsRequisitionDtlModel> listReqDtl = null;
		clsRequisitionHdModel objReqModel = objReqService.funGetObject(strReqCode, clientCode);
		if (objReqModel != null) {
			listReqDtl = objReqModel.getClsRequisitionDtlModel();
		}

		List<clsPurchaseIndentDtlModel> _clsPIDtlModel = new ArrayList<clsPurchaseIndentDtlModel>();
		List<clsProductMasterModel> stkProdListModel = objProductMasterService.funGetAllStockablProddSuppList(clientCode);
		for (int i = 0; i < listReqDtl.size(); i++) {
			clsRequisitionDtlModel ob = listReqDtl.get(i);
			for (clsProductMasterModel objstk : stkProdListModel) {
				if (ob.getStrProdCode().equals(objstk.getStrProdCode())) {
					clsProductMasterModel prodModel = objProductMasterService.funGetObject(ob.getStrProdCode(), clientCode);
					clsPurchaseIndentDtlModel tempOb = new clsPurchaseIndentDtlModel();
					tempOb.setStrProdCode(ob.getStrProdCode());
					tempOb.setStrProdName(prodModel.getStrProdName());
					tempOb.setDblRate(ob.getDblUnitPrice());
					tempOb.setStrInStock(0);
					tempOb.setDblMinLevel(0);
					tempOb.setDblReOrderQty(0);
					tempOb.setDblQty(ob.getDblQty());
					tempOb.setStrChecked("false");
					_clsPIDtlModel.add(tempOb);
				}
			}

		}
		req.getSession().setAttribute("AutoPIData", _clsPIDtlModel);
		return _clsPIDtlModel;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAutoPISessionValue", method = RequestMethod.POST)
	public @ResponseBody String funUpdateAutoReqSessionValue(HttpServletRequest req) {
		int index = Integer.parseInt(req.getParameter("chkIndex").toString());

		List<clsPurchaseIndentDtlModel> temp = (List<clsPurchaseIndentDtlModel>) req.getSession().getAttribute("AutoPIData");

		clsPurchaseIndentDtlModel objTemp = temp.get(index);
		if (objTemp.getStrChecked().equalsIgnoreCase("true")) {
			objTemp.setStrChecked("false");
		} else {
			objTemp.setStrChecked("true");
		}
		req.getSession().setAttribute("AutoPIData", temp);
		return "update";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/UpdateAutoPIListSelectAll", method = RequestMethod.POST)
	public @ResponseBody String fun_UpdateSelectAll(HttpServletRequest req, HttpServletResponse res) {
		List<clsPurchaseIndentDtlModel> temp = (List<clsPurchaseIndentDtlModel>) req.getSession().getAttribute("AutoPIData");
		for (clsPurchaseIndentDtlModel ob : temp) {
			ob.setStrChecked("true");
		}
		req.getSession().setAttribute("AutoPIData", temp);
		return "UpdateAll";

	}

	/**
	 * Fill Auto Requisition
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillAutoPIData", method = RequestMethod.POST)
	public @ResponseBody List fun_LoadAutoPIData(HttpServletRequest req, HttpServletResponse res) {
		List<clsPurchaseIndentDtlModel> temp = (List<clsPurchaseIndentDtlModel>) req.getSession().getAttribute("AutoPIData");
		List<clsPurchaseIndentDtlModel> listPIDtl = new ArrayList<clsPurchaseIndentDtlModel>();
		for (clsPurchaseIndentDtlModel ob : temp) {
			if (null != ob.getStrChecked() && "true".equalsIgnoreCase(ob.getStrChecked())) {
				listPIDtl.add(ob);
			}
		}
		return listPIDtl;
	}

	/**
	 * Auditing Function Start
	 * 
	 * @param PIModel
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String POCode, String strTransMode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsPurchaseIndentHdModel PIModel = objClsPurchaseIndentHdService.funGetObject(POCode, clientCode);
		List<clsPurchaseIndentDtlModel> listPIDtl = PIModel.getClsPurchaseIndentDtlModel();
		if (null != listPIDtl && listPIDtl.size() > 0) {
			String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + PIModel.getStrPICode() + "'";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (!list.isEmpty()) {
				String count = list.get(0).toString();
				clsAuditHdModel model = funPrepairAuditHdModel(PIModel);
				if (strTransMode.equalsIgnoreCase("Deleted")) {
					model.setStrTransCode(PIModel.getStrPICode() + "-" + count);
				} else {
					model.setStrTransCode(PIModel.getStrPICode() + "-" + count);
				}

				model.setStrClientCode(clientCode);
				model.setStrTransMode(strTransMode);
				model.setStrUserAmed(userCode);
				model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
				model.setStrUserModified(userCode);
				objGlobalFunctionsService.funSaveAuditHd(model);
				for (clsPurchaseIndentDtlModel ob : listPIDtl) {
					clsAuditDtlModel AuditMode = new clsAuditDtlModel();
					AuditMode.setStrTransCode(model.getStrTransCode());
					if (ob.getStrProdCode() != null) {
						AuditMode.setStrProdCode(ob.getStrProdCode());
						AuditMode.setDblQty(ob.getDblQty());
						AuditMode.setDblUnitPrice(ob.getDblRate());
						AuditMode.setDblTotalPrice(ob.getDblAmount());
						AuditMode.setStrRemarks(ob.getStrPurpose());
						AuditMode.setStrAgainst(ob.getStrAgainst());
						AuditMode.setDtReqDate(ob.getDtReqDate());
						AuditMode.setStrAgainst(ob.getStrAgainst());
						AuditMode.setStrPICode("");
						AuditMode.setStrUOM("");
						AuditMode.setStrCode("");
						AuditMode.setStrPICode("");
						AuditMode.setStrTaxType("");
						AuditMode.setDblCStock(ob.getStrInStock());
						AuditMode.setDblReOrderQty(ob.getDblReOrderQty());
						AuditMode.setStrClientCode(clientCode);
						objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
					}
				}
			}

		}
	}

	/**
	 * Prepare Audit HdModel
	 * 
	 * @param PIHdModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsPurchaseIndentHdModel PIHdModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (PIHdModel != null) {
			AuditHdModel.setStrTransCode(PIHdModel.getStrPICode());
			AuditHdModel.setDtTransDate(PIHdModel.getDtPIDate());
			AuditHdModel.setStrTransType("Purchase Indent");
			AuditHdModel.setStrAgainst("");
			AuditHdModel.setStrLocCode(PIHdModel.getStrLocCode());
			AuditHdModel.setStrNarration(PIHdModel.getStrNarration());
			AuditHdModel.setDblTotalAmt(PIHdModel.getDblTotal());
			AuditHdModel.setDtDateCreated(PIHdModel.getDtCreatedDate());
			AuditHdModel.setStrUserCreated(PIHdModel.getStrUserCreated());
			AuditHdModel.setStrAuthorise(PIHdModel.getStrAuthorise());
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrMInBy("");
			;
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrPayMode("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrCode("");
		}
		return AuditHdModel;

	}

	/**
	 * Open Purchase Indent Slip
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmPurchaseIndentSlip", method = RequestMethod.GET)
	public ModelAndView funOpenMISSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseIndentSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPurchaseIndentSlip", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/openRptPISlip", method = RequestMethod.GET)
	public void funOpenReport(HttpServletResponse resp, HttpServletRequest req) {

		String PICode = req.getParameter("rptPICode").toString();
		req.getSession().removeAttribute("rptPICode");
		String type = "pdf";
		funCallReport(PICode, type, resp, req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(String PIcode, String type, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPurchaseIndentHdModel objPIHdModel = objClsPurchaseIndentHdService.funGetObject(PIcode, clientCode);
			clsLocationMasterModel objLocHd = objLocationMasterService.funGetObject(objPIHdModel.getStrLocCode(), clientCode);
			objPIHdModel.setDtPIDate(objGlobal.funGetDate("yyyy/MM/dd", objPIHdModel.getDtPIDate()));
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			objPIHdModel.setStrLocName(objLocHd.getStrLocName());
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseIndentSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = " SELECT  st.strProdCode,p.strProdName,ifnull(rl.dblReOrderLevel,0) as dblReOrderLevel,st.dblQty, " + "st.strPurpose,date(st.dtReqDate) as dtReqDate ,st.strInStock, ifnull(st.strAgainst,'') as strAgainst, st.dblRate,st.dblAmount " + "from tblpurchaseindenddtl st " + "inner join tblproductmaster p ON st.strProdCode = p.strProdCode and p.strClientCode='" + clientCode + "' "
					+ "left outer join tblreorderlevel rl on rl.strProdCode=st.strProdCode " + "and rl.strLocationCode='" + objPIHdModel.getStrLocCode() + "' and rl.strClientCode='" + clientCode + "' " + "where strPICode='" + PIcode + "' " + "and st.strClientCode='" + clientCode + "'   ";

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPIdtl");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			String strPIDate = objGlobal.funGetDate("dd-MM-yyyy", objPIHdModel.getDtPIDate());

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strPICode", PIcode);
			hm.put("strPIDate", strPIDate);
			hm.put("strLocName", objPIHdModel.getStrLocName());
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
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptPISlip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPISlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");

			} else if (type.trim().equalsIgnoreCase("HTML")) {
				JRExporter exporterXLS = new JRHtmlExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPISlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/HTML");

			} else if (type.trim().equalsIgnoreCase("CSV")) {
				JRExporter exporterXLS = new JRCsvExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPISlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/CSV");

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

	/**
	 * Range PI Printing
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPISlip", method = RequestMethod.GET)
	public void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());

		String strFromPICode = objBean.getStrFromDocCode();
		String strToPICode = objBean.getStrToDocCode();
		String type = objBean.getStrDocType();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			String sql = "select a.strPIcode from tblpurchaseindendhd a where date(a.dtPIDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "'";

			if (strFromPICode.trim().length() > 0 && strToPICode.trim().length() > 0) {
				sql += "and a.strPIcode between '" + strFromPICode + "' and '" + strToPICode + "' ";
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
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptPISlip." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPISlip." + type.trim());
					exporterXLS.exportReport();
					resp.setContentType("application/xlsx");

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
	public JasperPrint funCallRangePrintReport(String PIcode, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		Connection con = objGlobal.funGetConnection(req);
		JasperPrint p = null;
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPurchaseIndentHdModel objPIHdModel = objClsPurchaseIndentHdService.funGetObject(PIcode, clientCode);
			clsLocationMasterModel objLocHd = objLocationMasterService.funGetObject(objPIHdModel.getStrLocCode(), clientCode);
			objPIHdModel.setDtPIDate(objGlobal.funGetDate("yyyy/MM/dd", objPIHdModel.getDtPIDate()));
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			objPIHdModel.setStrLocName(objLocHd.getStrLocName());
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseIndentSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = " SELECT  st.strProdCode,p.strProdName,ifnull(rl.dblReOrderLevel,0) as dblReOrderLevel,st.dblQty, " + "st.strPurpose,date(st.dtReqDate) as dtReqDate ,st.strInStock, ifnull(st.strAgainst,'') as strAgainst, st.dblRate,st.dblAmount " + "from tblpurchaseindenddtl st " + "inner join tblproductmaster p ON st.strProdCode = p.strProdCode and p.strClientCode='" + clientCode + "' "
					+ "left outer join tblreorderlevel rl on rl.strProdCode=st.strProdCode " + "and rl.strLocationCode='" + objPIHdModel.getStrLocCode() + "' and rl.strClientCode='" + clientCode + "' " + "where strPICode='" + PIcode + "' " + "and st.strClientCode='" + clientCode + "'   ";

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			
			String imgChefsCornerQualityPolicy ="";
			if(clientCode.equals("211.001"))//CHEFS CORNER
			{
				//imgChefsCornerQualityPolicy=servletContext.getRealPath("/resources/images/imgChefsCornerQualityPolicy.jpg");
				imgChefsCornerQualityPolicy=""
						+ "Quality Policy"
						+ "\n"
						+ "\n\t We at Chefs Corner are committed to provide Healthy,Quality,Tasty and Variety foods "
						+ "to our customer in hygienic environment at all times by continual improvement and thus achieve "
						+ "customer satisfaction by fulfilling all applicable requirements."
						+ "\n\n"
						+ "Quality Objectives"
						+ "\n"
						+ "\n> To Achieve Customer Satisfaction"
						+ "\n> To Reduce Customer Complaints"
						+ "\n> To Provide awareness to all employees"
						+ "\n> To Reduce Wastage"
						+ "\n";
			}
			
			
			
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPIdtl");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			String strPIDate = objGlobal.funGetDate("dd-MM-yyyy", objPIHdModel.getDtPIDate());

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("imgQualityPolicy", imgChefsCornerQualityPolicy);
			hm.put("strPICode", PIcode);
			hm.put("strPIDate", strPIDate);
			hm.put("strLocName", objPIHdModel.getStrLocName());
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());

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

}
