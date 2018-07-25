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
import com.sanguine.bean.clsMISBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsBatchHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMISHdModel_ID;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsRequisitionHdModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsMISService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsRequisitionService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsMISController {
	@Autowired
	ServletContext servletContext;

	@Autowired
	private clsMISService objMISService;
	@Autowired
	private clsLocationMasterService objLocService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsRequisitionService objReqService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	@Autowired
	private clsUOMService objclsUOMService;
	@Autowired
	clsProductMasterService objProductMasterService;

	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	/**
	 * Open MIS form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmMIS", method = RequestMethod.GET)
	public ModelAndView funOpenMISForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmMIS");
		clsMISBean bean = new clsMISBean();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		/**
		 * Code use when this form is open form authorization toll on click of
		 * view Button Ritesh 25 Feb 2015
		 */
		String authorizationMISCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationMISCode = request.getParameter("authorizationMISCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationMISCode", authorizationMISCode);
		}
		/**
		 * End of Code use when this form is open form authorization toll on
		 * click of view Button Ritesh 25 Feb 2015
		 */

		/**
		 * Set UOM List
		 */
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);
		model.put("urlHits", urlHits);

		/**
		 * Set Process
		 */
		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmMIS", propCode, clientCode);
		if (list.size() > 0) {
			model.put("strProcessList", list);
		} else {
			list = new ArrayList<String>();
			model.put("strProcessList", list);
		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMIS_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMIS", "command", bean);
		} else {
			return null;
		}

	}

	/**
	 * Open Pending Material Requisition
	 * 
	 * @return
	 */
	@RequestMapping(value = "/frmMISforMR", method = RequestMethod.GET)
	public ModelAndView funOpenMISforMR() {
		return new ModelAndView("frmMISforMR");

	}

	/**
	 * Save MIS form
	 * 
	 * @param MISBean
	 * @param result
	 * @param resp
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveMIS", method = RequestMethod.POST)
	public ModelAndView funSaveMIS(@ModelAttribute("command") clsMISBean MISBean, BindingResult result, HttpServletResponse resp, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			List<clsMISDtlModel> listonMISDtl = MISBean.getListMISDtl();
			if (null != listonMISDtl && listonMISDtl.size() > 0) {
				clsMISHdModel objMISHdModel = funPrepareModelHd(MISBean, req);
				objMISService.funAddMISHd(objMISHdModel);
				String reqCode[] = MISBean.getStrReqCode().split(",");
				for (String strReqCode : reqCode) {
					if (strReqCode.length() > 0) {
						clsRequisitionHdModel objReqModel = objReqService.funGetObject(strReqCode, clientCode);
						if (null == MISBean.getStrCloseReq()) {
							objReqModel.setStrCloseReq("N");
						} else {
							objReqModel.setStrCloseReq(MISBean.getStrCloseReq());
						}

						objReqService.funAddRequisionHd(objReqModel);
					}
				}

				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "MIS Code : ".concat(objMISHdModel.getStrMISCode()));
				req.getSession().setAttribute("rptMISCode", objMISHdModel.getStrMISCode());
				List<clsBatchHdModel> batchList = new ArrayList<clsBatchHdModel>();
				for (clsMISDtlModel ob : listonMISDtl)
					if (ob.getStrExpiry().equalsIgnoreCase("y") && ob.getStrExpiry() != null) {
						clsBatchHdModel BatchModel = new clsBatchHdModel();
						BatchModel.setStrTransCode(objMISHdModel.getStrMISCode());
						BatchModel.setStrProdCode(ob.getStrProdCode());
						BatchModel.setStrProdName(ob.getStrProdName());
						BatchModel.setDblQty(ob.getDblQty());
						batchList.add(BatchModel);
					}
				if (!batchList.isEmpty()) {
					req.getSession().setAttribute("BatchList", true);
					req.getSession().setAttribute("ManualBatchItemList", batchList);

				} else {
					req.getSession().setAttribute("BatchList", null);
					req.getSession().setAttribute("ManualBatchItemList", null);
				}
			}

			return new ModelAndView("redirect:/frmMIS.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmMIS.html?saddr=" + urlHits);
		}

	}

	/**
	 * Prepare MIS Model
	 * 
	 * @param MISHdBean
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsMISHdModel funPrepareModelHd(clsMISBean MISHdBean, HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("MIS")) {
				res = true;
			}
		}
		long lastNo = 0;
		clsMISHdModel objMISHd = null;

		if (MISHdBean.getStrMISCode().length() == 0) {
			String strMISCode = objGlobalFunctions.funGenerateDocumentCode("frmMIS", MISHdBean.getDtMISDate(), req);
			objMISHd = new clsMISHdModel(new clsMISHdModel_ID(strMISCode, clientCode));
			objMISHd.setIntid(lastNo);
			if (res) {
				objMISHd.setStrAuthorise("No");

			} else {
				objMISHd.setStrAuthorise("Yes");
			}

			objMISHd.setStrUserCreated(userCode);
			objMISHd.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsMISHdModel objclsMISHdModel = objMISService.funGetObject(MISHdBean.getStrMISCode(), clientCode);
			if (null == objclsMISHdModel) {
				String strMISCode = objGlobalFunctions.funGenerateDocumentCode("frmMIS", MISHdBean.getDtMISDate(), req);
				objMISHd = new clsMISHdModel(new clsMISHdModel_ID(strMISCode, clientCode));
				objMISHd.setIntid(lastNo);
				objMISHd.setStrUserCreated(userCode);
				objMISHd.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmMIS", req)) {
					funSaveAudit(MISHdBean.getStrMISCode(), "Edit", req);
				}
				objMISHd = new clsMISHdModel(new clsMISHdModel_ID(MISHdBean.getStrMISCode(), clientCode));
				objMISHd.setStrAuthorise(MISHdBean.getStrAuthorise());
			}
		}

		objMISHd.setStrLocFrom(MISHdBean.getStrLocFrom());
		objMISHd.setStrLocTo(MISHdBean.getStrLocTo());
		objMISHd.setStrAgainst(MISHdBean.getStrAgainst());
		objMISHd.setDtMISDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", MISHdBean.getDtMISDate()));
		objMISHd.setStrUserModified(userCode);
		objMISHd.setStrReqCode(MISHdBean.getStrReqCode());
		objMISHd.setStrNarration(MISHdBean.getStrNarration());
		objMISHd.setDtLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objMISHd.setStrUserCreated(userCode);
		objMISHd.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		List<clsMISDtlModel> tempDetailList = MISHdBean.getListMISDtl();
		objMISHd.setDblTotalAmt(MISHdBean.getDblTotalAmt());

		Iterator<clsMISDtlModel> it = tempDetailList.iterator();
		while (it.hasNext()) {
			clsMISDtlModel Obj = it.next();
			if (null == Obj.getStrProdCode()) {
				it.remove();
			} else if (null == Obj.getStrReqCode()) {
				Obj.setStrReqCode("");

				// for Issue Qty Conversion /////
				clsProductMasterModel objModel = objProductMasterService.funGetObject(Obj.getStrProdCode(), clientCode);
				double issueConversion = objModel.getDblIssueConversion();
				Obj.setDblQty(Obj.getDblQty() / issueConversion);
				// /////////////
			}
		}

		objMISHd.setClsMISDtlModel(tempDetailList);

		return objMISHd;
	}

	/**
	 * Load MIS HdData
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadMISHdData", method = RequestMethod.GET)
	public @ResponseBody clsMISHdModel funOpenFormWithMISCode(HttpServletRequest request) {
		
		String MISCode = request.getParameter("MISCode").toString();
		String startDate = request.getSession().getAttribute("startDate").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsMISHdModel objMISHd = objMISService.funGetObject(MISCode, clientCode);

		if (null == objMISHd) {
			objMISHd = new clsMISHdModel();
			objMISHd.setStrMISCode("Invalid MIS Code");
			return objMISHd;
		} else {
			request.getSession().setAttribute("transname", "frmMIS.jsp");
			request.getSession().setAttribute("formname", "MIS");
			request.getSession().setAttribute("code", MISCode);
			clsLocationMasterModel objLocationFrom = objLocService.funGetObject(objMISHd.getStrLocFrom(), clientCode);
			objMISHd.setStrLocFromName(objLocationFrom.getStrLocName());
			clsLocationMasterModel objLocationTo = objLocService.funGetObject(objMISHd.getStrLocTo(), clientCode);
			objMISHd.setStrLocToName(objLocationTo.getStrLocName());

			objMISHd.setDtMISDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", objMISHd.getDtMISDate()));

			List<clsMISDtlModel> dtlList = new ArrayList<clsMISDtlModel>();

			for (clsMISDtlModel ob : objMISHd.getClsMISDtlModel()) {
				List prodCodeDtl = (List<String>) objMISService.funGetProductDtl(ob.getStrProdCode(), clientCode);
				ob.setDblStock(objGlobalFunctions.funGetCurrentStockForProduct(ob.getStrProdCode(), objMISHd.getStrLocFrom(), clientCode, userCode, startDate, objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd")));
				Object[] obj = (Object[]) prodCodeDtl.get(0);
				ob.setStrProdName(obj[0].toString());
				ob.setStrUOM(obj[1].toString());
				ob.setStrExpiry(obj[2].toString());
				if (null == ob.getStrReqCode() || "NA".equalsIgnoreCase(ob.getStrReqCode()) || "null".equalsIgnoreCase(ob.getStrReqCode())) {
					ob.setStrReqCode("");
				}
				// for Issue Qty Conversion /////
				clsProductMasterModel objModel = objProductMasterService.funGetObject(ob.getStrProdCode(), clientCode);
				double issueConversion = objModel.getDblIssueConversion();
				ob.setDblQty(ob.getDblQty() * issueConversion);
				ob.setDblStock(ob.getDblStock() * issueConversion);
				// /////////////
				dtlList.add(ob);

			}
			objMISHd.setClsMISDtlModel(dtlList);
			return objMISHd;
		}

	}

	/**
	 * Load on form; Pending Material Requisition Data
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadMISforMR", method = RequestMethod.GET)
	public @ResponseBody List funOpenHelpMISforMR(HttpServletRequest req) {
		String strLocFrom = req.getParameter("strLocFrom").toString();
		String strLocTo = req.getParameter("strLocTo").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		return objMISService.funMISforMRDetails(strLocFrom, strLocTo, clientCode);
	}

	/**
	 * Load Pending Material Requisition Data
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/MISReqDtlData", method = RequestMethod.GET)
	public @ResponseBody List funFillReqDtlCode(HttpServletRequest req, HttpServletResponse resp) {
		 

		String reqCode = req.getParameter("ReqCode").toString();
		String locCode = req.getParameter("locCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String strEndDate = objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd");
		String toDate = objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd");
		List listMISDtl = new ArrayList();
		String sql = "select count(*) from tblreqdtl where strReqCode='" + reqCode + "' and strClientCode='" + clientCode + "'";
		List countlist = objGlobalFunctionsService.funGetList(sql, "sql");
		if (countlist.size() > 100) {
			String stockableItem = "Y";
			objGlobalFunctions.funInvokeStockFlash(startDate, locCode, strEndDate, toDate, clientCode, userCode, stockableItem, req, resp);
			List listReqDtl = objReqService.funGetReqDtlList(reqCode, clientCode, locCode, userCode);

			for (int i = 0; i < listReqDtl.size(); i++) {
				Object[] ob = (Object[]) listReqDtl.get(i);
				clsMISDtlModel objMISDtlModel = new clsMISDtlModel();
				objMISDtlModel.setStrProdCode(ob[0].toString());
				objMISDtlModel.setStrProdName(ob[2].toString());
				objMISDtlModel.setDblQty(Float.parseFloat(ob[3].toString()));
				objMISDtlModel.setDblUnitPrice(Float.parseFloat(ob[5].toString()));
				objMISDtlModel.setStrRemarks(ob[6].toString());
				objMISDtlModel.setStrUOM(ob[7].toString());
				objMISDtlModel.setDblStock(Math.round(Float.parseFloat(ob[8].toString()) * 100) / 100);
				listMISDtl.add(objMISDtlModel);
			}
		} else {
			List listReqDtl = objReqService.funGetReqDtlList(reqCode, clientCode, locCode, userCode);
			for (int i = 0; i < listReqDtl.size(); i++) {
				Object[] ob = (Object[]) listReqDtl.get(i);
				clsMISDtlModel objMISDtlModel = new clsMISDtlModel();
				objMISDtlModel.setStrProdCode(ob[0].toString());
				objMISDtlModel.setStrProdName(ob[2].toString());
				objMISDtlModel.setDblQty(Float.parseFloat(ob[3].toString()));
				objMISDtlModel.setDblUnitPrice(Float.parseFloat(ob[5].toString()));
				objMISDtlModel.setStrRemarks(ob[6].toString());
				objMISDtlModel.setStrUOM(ob[7].toString());
				// objMISDtlModel.setDblStock(Float.parseFloat(ob[8].toString()));
				double stock = objGlobalFunctions.funGetCurrentStockForProduct(objMISDtlModel.getStrProdCode(), locCode, clientCode, userCode, startDate, toDate);
				objMISDtlModel.setDblStock((double) Math.round(stock * 10000) / 10000);
				listMISDtl.add(objMISDtlModel);
			}
		}
		return listMISDtl;
	}

	/**
	 * Checking Stock for product
	 * 
	 * @param MISBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/checkStockForAllProduct", method = RequestMethod.POST)
	public @ResponseBody List<String> funcheckStockForAllProduct(@ModelAttribute("command") clsMISBean MISBean, BindingResult result, HttpServletRequest req) {
		List<String> ProdCodelist = new ArrayList<String>();
		if (!result.hasErrors()) {
			List<clsMISDtlModel> listonMISDtl = MISBean.getListMISDtl();
			for (int i = 0; i < listonMISDtl.size(); i++) {
				clsMISDtlModel ob = listonMISDtl.get(i);
				if (ob.getStrProdCode() != null) {
					if (ob.getDblQty() > ob.getDblStock()) {
						ProdCodelist.add(ob.getStrProdCode());
					}
				}
			}
		}
		return ProdCodelist;
	}

	/**
	 * Auditing Function Start
	 * 
	 * @param MISModel
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String strMISCode, String strTransMode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsMISHdModel MISModel = objMISService.funGetObject(strMISCode, clientCode);
		List<clsMISDtlModel> listMISDtl = MISModel.getClsMISDtlModel();
		if (null != listMISDtl && listMISDtl.size() > 0) {
			String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + MISModel.getStrMISCode() + "' and strClientCode='" + clientCode + "'";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (!list.isEmpty()) {
				String count = list.get(0).toString();
				clsAuditHdModel model = funPrepairAuditHdModel(MISModel);
				if (strTransMode.equalsIgnoreCase("Deleted")) {
					model.setStrTransCode(MISModel.getStrMISCode());
				} else {
					model.setStrTransCode(MISModel.getStrMISCode() + "-" + count);
				}
				model.setStrClientCode(clientCode);
				model.setStrTransMode(strTransMode);
				model.setStrUserAmed(userCode);
				model.setDtLastModified(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
				model.setStrUserModified(userCode);
				objGlobalFunctionsService.funSaveAuditHd(model);
				for (clsMISDtlModel ob : listMISDtl) {
					clsAuditDtlModel AuditMode = new clsAuditDtlModel();
					AuditMode.setStrTransCode(model.getStrTransCode());
					if (ob.getStrProdCode() != null) {
						AuditMode.setStrProdCode(ob.getStrProdCode());
						AuditMode.setStrUOM(ob.getStrUOM());
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

						objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
					}
				}
			}

		}
	}

	/**
	 * prepare Audit HdModel
	 * 
	 * @param reqModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsMISHdModel reqModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (reqModel != null) {
			AuditHdModel.setStrTransCode(reqModel.getStrMISCode());
			AuditHdModel.setDtTransDate(reqModel.getDtMISDate());
			AuditHdModel.setStrTransType("Material Issue Slip");
			AuditHdModel.setStrAgainst(reqModel.getStrAgainst());
			AuditHdModel.setStrLocBy(reqModel.getStrLocFrom());
			AuditHdModel.setStrLocOn(reqModel.getStrLocTo());
			AuditHdModel.setStrNarration(reqModel.getStrNarration());
			AuditHdModel.setDblTotalAmt(reqModel.getDblTotalAmt());
			AuditHdModel.setStrUserCreated(reqModel.getStrUserCreated());
			AuditHdModel.setDtDateCreated(reqModel.getDtCreatedDate());
			AuditHdModel.setStrAuthorise(reqModel.getStrAuthorise());
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrLocCode("");
			AuditHdModel.setStrMInBy("");
			;
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrLocCode("");
			AuditHdModel.setStrPayMode("");
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

	/**
	 * MIS Slip Report
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmMaterialIssueSlip", method = RequestMethod.GET)
	public ModelAndView funOpenMISSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialIssueSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMaterialIssueSlip", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/openRptMISSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String MISCode = req.getParameter("rptMISCode").toString();
		req.getSession().removeAttribute("rptMISCode");
		String type = "pdf";
		funCallReport(MISCode, type, resp, req);

	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void funCallReport(String MISCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		 
		Connection con = objGlobalFunctions.funGetConnection(req);
		try {
			String strMISCode = "", dtMISDate = "", strFromLoc = "";
			String strToLoc = "";
			String strAgainst = "";
			String strReqCode = "";
			String strOrderBy = "";
			String strNarration = "";
			String strUserCreated = "";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String sqlMisHd = "select a.strMISCode as strMISCode ,DATE_FORMAT(a.dtMISDate,'%d-%m-%Y') as dtMISDate,c.strLocName as strFromLoc ,d.strLocName as strToLoc," + " a.strAgainst as strAgainst,ifnull(a.strReqCode,'') as strReqCode, " + " ifnull(b.strUserCreated,'') as strOrderBy,a.strNarration as strNarration,a.strUserCreated "
					+ " from tblmishd a left outer join tblreqhd b on a.strReqCode=b.strReqCode " + " and b.strClientCode='" + clientCode + "' " + " left outer join  tbllocationmaster c on a.strLocFrom=c.strLocCode and c.strClientCode='" + clientCode + "'" + " left outer join tbllocationmaster d on a.strLocTo=d.strLocCode and d.strClientCode='" + clientCode + "'" + " where a.strMISCode='"
					+ MISCode + "' and a.strClientCode='" + clientCode + "' ";
			List list = objGlobalFunctionsService.funGetList(sqlMisHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strMISCode = arrObj[0].toString();
				dtMISDate = arrObj[1].toString();
				strFromLoc = arrObj[2].toString();
				strToLoc = arrObj[3].toString();
				strAgainst = arrObj[4].toString();
				strReqCode = arrObj[5].toString();
				strOrderBy = arrObj[6].toString();
				strNarration = arrObj[7].toString();
				strUserCreated = arrObj[8].toString();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMaterialIssueSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sql = "select a.strProdCode,p.strProdName,a.dblQty,a.dblUnitPrice,a.dblTotalPrice," + "a.strRemarks,p.strIssueUOM,p.strBinNo " + "from tblmisdtl a, tblproductmaster p " + "where a.strProdCode=p.strProdCode and a.strMISCode='" + MISCode + "' and a.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "'";

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsMISdtl");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			// System.out.println(imagePath);
			hm.put("strMISCode", MISCode);
			hm.put("dtMISDate", dtMISDate);
			hm.put("strAgainst", strAgainst);
			hm.put("strFromLoc", strFromLoc);
			hm.put("strToLoc", strToLoc);
			hm.put("strNarration", strNarration);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strShowValue", objSetup.getStrShowValMISSlip());
			hm.put("strReqCode", strReqCode);
			hm.put("strOrderBy", strOrderBy);
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptMISSlip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();

			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setContentType("application/vnd.ms-excel");
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptMISSlip." + type.trim());
				exporterXLS.exportReport();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
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
	 * Range MIS Printing
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptMISSlip", method = RequestMethod.POST)
	private @ResponseBody void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			 
			String dtFromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String dtToDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			/*
			 * String strFromLoc=objBean.getStrFromLocCode(); String
			 * strToLoc=objBean.getStrToLocCode();
			 */
			String strFromMISCode = objBean.getStrFromDocCode();
			String strToMISCode = objBean.getStrToDocCode();
			String type = objBean.getStrDocType();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String tempToLoc[] = objBean.getStrToLocCode().split(",");
			String tempFromLoc[] = objBean.getStrFromLocCode().split(",");
			String strToLocCodes = "";
			String strFromLocCodes = "";

			for (int i = 0; i < tempFromLoc.length; i++) {
				if (strFromLocCodes.length() > 0) {
					strFromLocCodes = strFromLocCodes + " or a.strLocFrom='" + tempFromLoc[i] + "' ";
				} else {
					strFromLocCodes = "a.strLocFrom='" + tempFromLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempToLoc.length; i++) {
				if (strToLocCodes.length() > 0) {
					strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
				} else {
					strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

				}
			}
			String sql = "select strMISCode from tblmishd a where date(a.dtMISDate) between '" + dtFromDate + "' and '" + dtToDate + "' and a.strClientCode='" + clientCode + "'";
			if (objBean.getStrFromLocCode().length() > 0) {
				sql = sql + " and (" + strFromLocCodes + ")  ";
			}
			if (objBean.getStrToLocCode().length() > 0) {
				sql = sql + " and (" + strToLocCodes + ")  ";
			}
			if (strFromMISCode.trim().length() > 0 && strToMISCode.trim().length() > 0) {
				sql = sql + " and a.strMISCode between '" + strFromMISCode + "' and '" + strToMISCode + "' ";
			}

			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			if (objBean.getStrToLocCode().equals("")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JasperPrint pconso = funCallRangePrintReport(list.get(0).toString(), resp, req, objBean);
				jprintlist.add(pconso);
				if (type.trim().equalsIgnoreCase("pdf")) {

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptMISSlip." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setContentType("application/vnd.ms-excel");
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptMISSlip." + type.trim());
					exporterXLS.exportReport();
				}
			} else {
				ServletOutputStream servletOutputStream = resp.getOutputStream();

				if (list != null && !list.isEmpty()) {

					for (int i = 0; i < list.size(); i++) {
						JasperPrint p = funCallRangePrintReport(list.get(i).toString(), resp, req, objBean);
						jprintlist.add(p);
					}

					if (type.trim().equalsIgnoreCase("pdf")) {

						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						resp.setHeader("Content-Disposition", "inline;filename=" + "rptMISSlip." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					} else if (type.trim().equalsIgnoreCase("xls")) {
						JRExporter exporterXLS = new JRXlsExporter();
						exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
						resp.setContentType("application/vnd.ms-excel");
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptMISSlip." + type.trim());
						exporterXLS.exportReport();
					}
				} else {
					// resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					// resp.getWriter().append("No Record Found");
				}
			}

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	public JasperPrint funCallRangePrintReport(String MISCode, HttpServletResponse resp, HttpServletRequest req, clsReportBean objBean) {
		 
		Connection con = objGlobalFunctions.funGetConnection(req);
		JasperPrint p = null;
		try {
			String tempToLoc[] = objBean.getStrToLocCode().split(",");
			String tempFromLoc[] = objBean.getStrFromLocCode().split(",");
			String dtFDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
			String dtTDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
			String dtFromDate = objBean.getDtFromDate();
			String dtToDate = objBean.getDtToDate();
			String strToLocCodes = "";
			String strFromLocCodes = "";
			String dtMISDate = "", strFromLoc = "";
			String strToLoc = "";
			String strAgainst = "";
			String strReqCode = "";
			String strOrderBy = "";
			String strNarration = "";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptMaterialIssueSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			String sql = "";
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String strLocationNames = "";
			if (objBean.getStrToLocCode().equals("")) {
				for (int i = 0; i < tempFromLoc.length; i++) {
					if (strFromLocCodes.length() > 0) {
						strFromLocCodes = strFromLocCodes + " or b.strLocFrom='" + tempFromLoc[i] + "' ";
						clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(tempFromLoc[i], clientCode);
						strLocationNames = strLocationNames + "," + objLocCode.getStrLocName();
					} else {
						strFromLocCodes = "b.strLocFrom='" + tempFromLoc[i] + "' ";
						clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(tempFromLoc[i], clientCode);
						strLocationNames = objLocCode.getStrLocName();

					}
				}

				dtMISDate = dtFromDate;
				strFromLoc = strLocationNames;
				strToLoc = dtToDate;
				strAgainst = "";
				strReqCode = "";
				strOrderBy = "";
				strNarration = "";
				MISCode = "";

				sql = " select a.strProdCode,p.strProdName,sum(a.dblQty) as dblQty,sum(a.dblTotalPrice)/sum(a.dblQty) as dblUnitPrice,sum(a.dblTotalPrice) as dblTotalPrice," + " a.strRemarks,p.strIssueUOM,p.strBinNo from tblmisdtl a, tblmishd b,tblproductmaster p " + " where a.strMISCode=b.strMISCode and a.strProdCode=p.strProdCode and date(b.dtMISDate) between '" + dtFDate + "' and '" + dtTDate
						+ "' and (" + strFromLocCodes + ") " + " and a.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "' group by a.strProdCode ; ";

			} else {

				String sqlMisHd = "select a.strMISCode as strMISCode ,DATE_FORMAT(a.dtMISDate,'%d-%m-%Y') as dtMISDate,c.strLocName as strFromLoc ,d.strLocName as strToLoc," + " a.strAgainst as strAgainst,ifnull(a.strReqCode,'') as strReqCode, " + " ifnull(b.strUserCreated,'') as strOrderBy,a.strNarration as strNarration,a.strUserCreated "
						+ " from tblmishd a left outer join tblreqhd b on a.strReqCode=b.strReqCode " + " and b.strClientCode='" + clientCode + "' " + " left outer join  tbllocationmaster c on a.strLocFrom=c.strLocCode and c.strClientCode='" + clientCode + "'" + " left outer join tbllocationmaster d on a.strLocTo=d.strLocCode and d.strClientCode='" + clientCode + "'" + " where a.strMISCode='"
						+ MISCode + "' and a.strClientCode='" + clientCode + "' ";
				List list = objGlobalFunctionsService.funGetList(sqlMisHd, "sql");
				if (!list.isEmpty()) {
					Object[] arrObj = (Object[]) list.get(0);
					dtMISDate = arrObj[1].toString();
					strFromLoc = arrObj[2].toString();
					strToLoc = arrObj[3].toString();
					strAgainst = arrObj[4].toString();
					strReqCode = arrObj[5].toString();
					strOrderBy = arrObj[6].toString();
					strNarration = arrObj[7].toString();
				}

				sql = "select a.strProdCode,p.strProdName,a.dblQty,a.dblUnitPrice,a.dblTotalPrice," + "a.strRemarks,p.strIssueUOM,p.strBinNo " + "from tblmisdtl a, tblproductmaster p " + "where a.strProdCode=p.strProdCode and a.strMISCode='" + MISCode + "' and a.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "'";

			}

			// getting multi copy of small data in table in Detail thats why we
			// add in subDataset
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsMISdtl");
			subDataset.setQuery(subQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			// System.out.println(imagePath);
			hm.put("strMISCode", MISCode);
			hm.put("dtMISDate", dtMISDate);
			hm.put("strAgainst", strAgainst);
			hm.put("strFromLoc", strFromLoc);
			hm.put("strToLoc", strToLoc);
			hm.put("strNarration", strNarration);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strShowValue", objSetup.getStrShowValMISSlip());
			hm.put("strReqCode", strReqCode);
			hm.put("strOrderBy", strOrderBy);
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

	@RequestMapping(value = "/frmMaterialIssueRegisterReport", method = RequestMethod.GET)
	public ModelAndView funOpenMISRegisterReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMaterialIssueRegisterReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMaterialIssueRegisterReport", "command", new clsReportBean());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rptMISRegisterReport", method = RequestMethod.POST)
	private ModelAndView funCallGRNRegisterReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
		String fDate = objBean.getDtFromDate();
		String tDate = objBean.getDtToDate();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String locationCode = objBean.getStrLocationCode();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String tempFrmLoc[] = objBean.getStrFromLoc().split(",");
		String tempToLoc[] = objBean.getStrToLoc().split(",");
		String strFrmLocCodes = "";
		String strToLocCodes = "";

		for (int i = 0; i < tempFrmLoc.length; i++) {
			if (strFrmLocCodes.length() > 0) {
				strFrmLocCodes = strFrmLocCodes + " or a.strLocFrom='" + tempFrmLoc[i] + "' ";
			} else {
				strFrmLocCodes = "a.strLocFrom='" + tempFrmLoc[i] + "' ";

			}
		}

		for (int i = 0; i < tempToLoc.length; i++) {
			if (strToLocCodes.length() > 0) {
				strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
			} else {
				strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

			}
		}

		String header = "MISCode ,MISDate,ReqCode,FrmLocCode,FrmLocName,ToLocCode,ToLocName,ProdCode, ProdName, ProdClass ," + " GroupCode, GroupName, " + "SGCode, SubGroupName, Qty, UnitPrice, TotalPrice ";

		List ExportList = new ArrayList();

		ExportList.add("MISRegisterReport_" + fDate + "to" + fDate + "_" + userCode);

		List titleData = new ArrayList<>();
		titleData.add("Material Issue Slip Register Report");
		ExportList.add(titleData);

		List filterData = new ArrayList<>();
		filterData.add("From Date");
		filterData.add(fDate);
		filterData.add("To Date");
		filterData.add(fDate);

		ExportList.add(filterData);

		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		String sqlDtl = " select a.strMISCode as MISCode,Date(a.dtMISDate) as MISDate,a.strReqCode as ReqCode, " + " a.strLocFrom as FormLocationCode,d.strLocName as LocName, " + " a.strLocTo as ToLocationCode,(select f.strLocName as toLoc from tbllocationmaster f where a.strLocTo=f.strLocCode ) as ToLocName, " + " b.strProdCode as ProdCode, c.strProdName as ProdName, "
				+ " CASE c.strClass WHEN '' THEN 'NA' ELSE c.strClass END AS strClass," + " e.strGCode as GroupCode, " + " g.strGName as GroupName ,e.strSGCode as SubGroupCode,e.strSGName as SubGroupName, " + " b.dblQty As Qty, " + " b.dblUnitPrice as UnitPrice,b.dblTotalPrice as TotalPrice " + " from tblmishd a,tblmisdtl b ,tblproductmaster c ,tbllocationmaster d, tblsubgroupmaster e, "
				+ " tblgroupmaster g " + " where a.strMISCode=b.strMISCode and b.strProdCode=c.strProdCode " + " and a.strLocFrom=d.strLocCode and c.strSGCode=e.strSGCode and e.strGCode=g.strGCode " + " and DATE(a.dtMISDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' ";

		sqlDtl = sqlDtl + " and " + "(" + strFrmLocCodes + ")" + "and " + "(" + strToLocCodes + ") ";

		sqlDtl += " and a.strClientCode='" + clientCode + "' " + " AND a.strClientCode=b.strClientCode " + " AND b.strClientCode=c.strClientCode " + " and c.strClientCode=d.strClientCode " + " and d.strClientCode=e.strClientCode " + " and e.strClientCode=g.strClientCode " + " group by a.strMISCode,a.strLocFrom,a.strLocTo,b.strProdCode ";

		List list = objGlobalFunctionsService.funGetList(sqlDtl.toString(), "sql");
		List OpeningStklist = new ArrayList();
		double totalAmt = 0.0;
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);

			List DataList = new ArrayList<>();
			DataList.add(ob[0].toString());
			DataList.add(ob[1].toString());
			DataList.add(ob[2].toString());
			DataList.add(ob[3].toString());
			DataList.add(ob[4].toString());
			DataList.add(ob[5].toString());
			DataList.add(ob[6].toString());

			DataList.add(ob[7].toString());
			DataList.add(ob[8].toString());
			DataList.add(ob[9].toString());
			DataList.add(ob[10].toString());
			DataList.add(ob[11].toString());
			DataList.add(ob[12].toString());
			DataList.add(ob[13].toString());
			DataList.add(ob[14].toString());

			DataList.add(ob[15].toString());
			DataList.add(ob[16].toString());
			totalAmt = totalAmt + (Double.parseDouble(ob[16].toString()));

			OpeningStklist.add(DataList);
		}
		List totalRow = new ArrayList<>();
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("Total");
		totalRow.add(totalAmt);
		OpeningStklist.add(totalRow);

		for (int j = 0; j < 4; j++) {

			if (j == 3) {
				String dateTime[] = objGlobalFunctions.funGetCurrentDateTime("dd-MM-yyyy").split(" ");
				List footer = new ArrayList<>();

				footer.add("Created on :");
				footer.add(dateTime[0]);
				footer.add("AT :");
				footer.add(dateTime[1]);
				footer.add("By :");
				footer.add(userCode);
				OpeningStklist.add(footer);

			} else {
				List blank = new ArrayList<>();
				blank.add("");
				OpeningStklist.add(blank);
			}

		}
		ExportList.add(OpeningStklist);

		// return new ModelAndView("excelView", "stocklist", ExportList);
		return new ModelAndView("excelViewFromDateTodateWithReportName", "listFromDateTodateWithReportName", ExportList);

	}

	@RequestMapping(value = "/frmMISSummaryReport", method = RequestMethod.GET)
	private ModelAndView funOpenFormMISSummaryReport(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMISSummaryReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmMISSummaryReport", "command", new clsReportBean());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rptMISSummaryReport", method = RequestMethod.POST)
	private ModelAndView funCallMISSummaryReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
		String fDate = objBean.getDtFromDate();
		String tDate = objBean.getDtToDate();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String tempFrmLoc[] = objBean.getStrFromLocCode().split(",");
		String tempToLoc[] = objBean.getStrToLocCode().split(",");
		String tempGCodes[] = objBean.getStrGCode().split(",");
		String tempSGCodes[] = objBean.getStrSGCode().split(",");

		String strFrmLocCodes = "";
		String strToLocCodes = "";
		String strGCodes = "";
		String strSGCodes = "";

		for (int i = 0; i < tempFrmLoc.length; i++) {
			if (strFrmLocCodes.length() > 0) {
				strFrmLocCodes = strFrmLocCodes + " or a.strLocFrom='" + tempFrmLoc[i] + "' ";
			} else {
				strFrmLocCodes = "a.strLocFrom='" + tempFrmLoc[i] + "' ";

			}
		}

		for (int i = 0; i < tempToLoc.length; i++) {
			if (strToLocCodes.length() > 0) {
				strToLocCodes = strToLocCodes + " or a.strLocTo='" + tempToLoc[i] + "' ";
			} else {
				strToLocCodes = "a.strLocTo='" + tempToLoc[i] + "' ";

			}
		}

		for (int i = 0; i < tempGCodes.length; i++) {
			if (strGCodes.length() > 0) {
				strGCodes = strGCodes + " or e.strGCode='" + tempGCodes[i] + "' ";
			} else {
				strGCodes = "e.strGCode='" + tempGCodes[i] + "' ";

			}
		}

		for (int i = 0; i < tempSGCodes.length; i++) {
			if (strSGCodes.length() > 0) {
				strSGCodes = strSGCodes + " or c.strSGCode='" + tempSGCodes[i] + "' ";
			} else {
				strSGCodes = "c.strSGCode='" + tempSGCodes[i] + "' ";

			}
		}

		String header = " FrmLocName,ToLocName,GroupName,SubGroupName,Amount";

		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add("MISSummaryReport_" + fDate + "to" + tDate + "_" + userCode);

		List titleData = new ArrayList<>();
		titleData.add("MIS Summary Report");
		ExportList.add(titleData);

		List filterData = new ArrayList<>();
		filterData.add("From Date");
		filterData.add(fDate);
		filterData.add("To Date");
		filterData.add(tDate);

		ExportList.add(filterData);

		ExportList.add(ExcelHeader);
		String sqlDtl = " select d.strLocName as FrmLocName,(select f.strLocName as toLoc from tbllocationmaster f where a.strLocTo=f.strLocCode ) as ToLocName, " + " g.strGName as GroupName ,e.strSGName as SubGroupName , " + " sum(b.dblTotalPrice) as Amount  " + " from tblmishd a,tblmisdtl b ,tblproductmaster c ,tbllocationmaster d, tblsubgroupmaster e, " + " tblgroupmaster g "
				+ " where a.strMISCode=b.strMISCode and b.strProdCode=c.strProdCode " + " and a.strLocFrom=d.strLocCode and c.strSGCode=e.strSGCode and e.strGCode=g.strGCode " + " and DATE(a.dtMISDate) BETWEEN '" + fromDate + "' AND '" + toDate + "' ";

		sqlDtl = sqlDtl + " and " + "(" + strFrmLocCodes + ")" + "and " + "(" + strToLocCodes + ") ";

		sqlDtl = sqlDtl + " and " + "(" + strSGCodes + ")" + "and " + "(" + strGCodes + ") ";

		sqlDtl += " and a.strClientCode='" + clientCode + "' " + " AND a.strClientCode=b.strClientCode " + " AND b.strClientCode=c.strClientCode " + " and c.strClientCode=d.strClientCode " + " and d.strClientCode=e.strClientCode " + " and e.strClientCode=g.strClientCode " + " group by a.strLocFrom,a.strLocTo,e.strGCode,e.strSGCode ";

		List list = objGlobalFunctionsService.funGetList(sqlDtl.toString(), "sql");
		List listdata = new ArrayList();
		double totalAmt = 0.00;
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);

			List DataList = new ArrayList<>();
			DataList.add(ob[0].toString());
			DataList.add(ob[1].toString());
			DataList.add(ob[2].toString());
			DataList.add(ob[3].toString());
			DataList.add(ob[4].toString());
			totalAmt = totalAmt + (Double.parseDouble(ob[4].toString()));

			listdata.add(DataList);
		}
		List totalRow = new ArrayList<>();
		totalRow.add("");
		totalRow.add("");
		totalRow.add("");
		totalRow.add("Total");
		totalRow.add(totalAmt);
		listdata.add(totalRow);

		for (int j = 0; j < 4; j++) {

			if (j == 3) {
				String dateTime[] = objGlobalFunctions.funGetCurrentDateTime("dd-MM-yyyy").split(" ");
				List footer = new ArrayList<>();

				footer.add("Created on :");
				footer.add(dateTime[0]);
				footer.add("AT :");
				footer.add(dateTime[1]);
				footer.add("By :");
				footer.add(userCode);
				listdata.add(footer);

			} else {
				List blank = new ArrayList<>();
				blank.add("");
				listdata.add(blank);
			}

		}

		ExportList.add(listdata);
		return new ModelAndView("excelViewFromDateTodateWithReportName", "listFromDateTodateWithReportName", ExportList);

	}
	@RequestMapping(value = "/loadMISDataFromNotification", method = RequestMethod.GET)
	private ModelAndView funLoadMISDataFromNotification(@ModelAttribute("command") clsMISBean objBean,Map<String, Object> model,@RequestParam("strMRCode") String strMRCode,@RequestParam("strLocOn") String strLocOn,  HttpServletResponse resp, HttpServletRequest req) {
		{
			String urlHits = "1";
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String locCode= req.getSession().getAttribute("locationCode").toString();
			try {
				urlHits = req.getParameter("saddr").toString();
			} catch (NullPointerException e) {
				urlHits = "1";
			}
			List<String> uomList = new ArrayList<String>();
			uomList = objclsUOMService.funGetUOMList(clientCode);
			model.put("uomList", uomList);
			model.put("urlHits", urlHits);

			/**
			 * Set Process
			 */
			List<String> list = objGlobalFunctions.funGetSetUpProcess("frmMIS", propCode, clientCode);
			if (list.size() > 0) {
				model.put("strProcessList", list);
			} else {
				list = new ArrayList<String>();
				model.put("strProcessList", list);
			}
			objBean.setStrReqCode(strMRCode);
			objBean.setStrLocTo(strLocOn);
			objBean.setStrAgainst("Requisition");
			objBean.setStrLocFrom(locCode);
			if ("2".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmMIS_1", "command", objBean);
			} else if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmMIS", "command", objBean);
			} else {
				return null;
			}
		}
	
	
	}
	
	

}
