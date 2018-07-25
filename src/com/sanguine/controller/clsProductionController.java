package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsProductionBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductionDtlModel;
import com.sanguine.model.clsProductionHdModel;
import com.sanguine.model.clsProductionHdModel_ID;
import com.sanguine.model.clsWorkOrderHdModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductionService;
import com.sanguine.service.clsWorkOrderService;

@Controller
public class clsProductionController {
	@Autowired
	private clsProductionService objPDService;
	@Autowired
	private clsWorkOrderService objWoService;
	@Autowired
	private clsLocationMasterService objLocService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	clsGlobalFunctions objGlobal = null;

	/**
	 * Open Production Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmProduction", method = RequestMethod.GET)
	public ModelAndView funOpenPDForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
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
			docCode = request.getParameter("authorizationProductionCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationProductionCode", docCode);
		}
		model.put("urlHits", urlHits);
		objGlobal = new clsGlobalFunctions();
		clsProductionBean bean = new clsProductionBean();
		bean.setDtPDDate(objGlobal.funGetDate("yyyy/MM/dd", objGlobal.funGetCurrentDateTime("yyyy-MM-dd")));

		List list = objGlobalFunctions.funGetList("Select strProcessName from tblprocessmaster where strClientCode='" + clientCode + "'");
		if (list.size() > 0) {
			model.put("strProcess", list);
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProduction_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProduction", "command", bean);
		} else {
			return null;
		}
	}

	/**
	 * Save Production Form
	 * 
	 * @param PDBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/savePD", method = RequestMethod.POST)
	public ModelAndView funSaveProduction(@ModelAttribute("command") clsProductionBean PDBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		boolean flagDtlDataInserted = false;
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		if (!result.hasErrors()) {
			List<clsProductionDtlModel> listonPDDtl = PDBean.getListProductionDtl();
			if (null != listonPDDtl && listonPDDtl.size() > 0) {
				clsProductionHdModel objPDModel = funPrepareModelHd(PDBean, userCode, clientCode, propCode, startDate, req);
				objPDService.funAddPDHd(objPDModel);
				String strPdCode = objPDModel.getStrPDCode();
				objPDService.funDeleteDtl(strPdCode, clientCode);
				for (clsProductionDtlModel ob : listonPDDtl) {
					ob.setStrPDCode(strPdCode);
					ob.setStrClientCode(clientCode);
					objPDService.funAddUpdatePDDtl(ob);
					flagDtlDataInserted = true;
				}

				if (PDBean.getStrWOCode() != null) {
					String strStatus = objWoService.funGetWOStatusforProduct(PDBean.getStrWOCode(), clientCode);
					objPDService.funUpdateWorkOrderStatus(PDBean.getStrWOCode(), strStatus, userCode, objGlobal.funGetCurrentDateTime("yyyy-MM-dd"), clientCode);
				}

				if (flagDtlDataInserted) {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "PD OrderCode : ".concat(objPDModel.getStrPDCode()));
					req.getSession().setAttribute("rptOPCode", objPDModel.getStrPDCode());
				}

			}

			return new ModelAndView("redirect:/frmProduction.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmProduction.html?saddr=" + urlHits);
		}
	}

	/**
	 * Prepare Production HdModel
	 * 
	 * @param PDBean
	 * @param userCode
	 * @param clientCode
	 * @param propCode
	 * @param startDate
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsProductionHdModel funPrepareModelHd(clsProductionBean PDBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest request) {
		long lastNo = 0;
		clsProductionHdModel objPDHd;
		objGlobal = new clsGlobalFunctions();
		if (PDBean.getStrPDCode().trim().length() == 0) {
			String strPDCode = objGlobalFunctions.funGenerateDocumentCode("frmProduction", PDBean.getDtPDDate(), request);
			objPDHd = new clsProductionHdModel(new clsProductionHdModel_ID(strPDCode, clientCode));
			objPDHd.setIntid(lastNo);

			objPDHd.setStrUserCreated(userCode);
			objPDHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objPDHd = new clsProductionHdModel(new clsProductionHdModel_ID(PDBean.getStrPDCode(), clientCode));
		}

		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Production")) {
				res = true;
			}
		}
		if (res) {
			objPDHd.setStrAuthorise("No");
		} else {
			objPDHd.setStrAuthorise("Yes");
		}

		objPDHd.setStrLocCode(PDBean.getStrLocCode());
		objPDHd.setDtPDDate(objGlobal.funGetDate("yyyy-MM-dd", PDBean.getDtPDDate()));
		objPDHd.setStrUserModified(userCode);
		objPDHd.setStrWOCode(PDBean.getStrWOCode());
		objPDHd.setStrNarration(PDBean.getStrNarration());
		objPDHd.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objPDHd;
	}

	/**
	 * load Production HdData
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/loadProductionData", method = RequestMethod.GET)
	public @ResponseBody List<clsProductionHdModel> funOpenFormWithPDCode(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String PDCode = request.getParameter("PDCode").toString();
		List<clsProductionHdModel> ListPDHd = new ArrayList<clsProductionHdModel>();
		clsProductionHdModel objPDHd = objPDService.funGetObject(PDCode, clientCode);
		if (null == objPDHd) {
			objPDHd = new clsProductionHdModel();
			objPDHd.setStrPDCode("Invalid Code");
			ListPDHd.add(objPDHd);
		} else {
			clsLocationMasterModel objLocationFrom = objLocService.funGetObject(objPDHd.getStrLocCode(), clientCode);
			objPDHd.setStrLocationName(objLocationFrom.getStrLocName());
			objPDHd.setDtPDDate(objGlobal.funGetDate("yyyy/MM/dd", objPDHd.getDtPDDate()));
			ListPDHd.add(objPDHd);
		}

		return ListPDHd;
	}

	/**
	 * Load Production Dtl Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProductionDtlData", method = RequestMethod.GET)
	public @ResponseBody List<clsProductionDtlModel> funfillProdData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String PDCode = request.getParameter("PDCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List listProductionDtl = objPDService.funGetDtlList(PDCode, clientCode);
		List<clsProductionDtlModel> listProdDtl = new ArrayList<clsProductionDtlModel>();

		for (int i = 0; i < listProductionDtl.size(); i++) {
			Object[] ob = (Object[]) listProductionDtl.get(i);
			clsProductionDtlModel PDProdDtl = (clsProductionDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			clsProcessMasterModel prodProcess = (clsProcessMasterModel) ob[3];
			clsProductionDtlModel objProdDtl = new clsProductionDtlModel();
			objProdDtl.setStrPDCode(PDProdDtl.getStrPDCode());
			objProdDtl.setStrProdCode(prodMaster.getStrProdCode());
			objProdDtl.setStrProdName(prodMaster.getStrProdName());
			objProdDtl.setStrUOM(prodMaster.getStrUOM());
			objProdDtl.setDblQtyProd(PDProdDtl.getDblQtyProd());
			objProdDtl.setDblQtyRej(PDProdDtl.getDblQtyRej());
			objProdDtl.setDblWeight(PDProdDtl.getDblWeight());
			objProdDtl.setDblActTime(PDProdDtl.getDblActTime());
			objProdDtl.setDblPrice(PDProdDtl.getDblPrice());
			objProdDtl.setStrProcessCode(prodProcess.getStrProcessCode());
			objProdDtl.setStrProcessName(prodProcess.getStrProcessName());
			listProdDtl.add(objProdDtl);
		}
		return listProdDtl;
	}

	/**
	 * Load Work Order Data
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadWorkOrder", method = RequestMethod.GET)
	public @ResponseBody List funOpenFormWithWorkOrderCode(HttpServletRequest req) {
		String WorkOrderCode = req.getParameter("WoCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listWoDtl = objPDService.funGetWOHdData(WorkOrderCode, clientCode);
		List<clsProductionDtlModel> listPDDtl = new ArrayList<clsProductionDtlModel>();
		clsProductionDtlModel objProdHd = new clsProductionDtlModel();
		for (int i = 0; i < listWoDtl.size(); i++) {
			// Object[] ob = (Object[])listWoDtl.get(i);
			// clsWorkOrderHdModel objWoModel = (clsWorkOrderHdModel)ob[0];
			// clsProcessMasterModel ProcessMaster=(clsProcessMasterModel)ob[2];
			// clsProductMasterModel
			// objProductMaster=(clsProductMasterModel)ob[3];
			//
			// objProdHd.setStrProdCode(objWoModel.getStrProdCode());
			// objProdHd.setStrProdName(objProductMaster.getStrProdName());
			// objProdHd.setStrProcessCode(ProcessMaster.getStrProcessCode());
			// objProdHd.setStrProcessName(ProcessMaster.getStrProcessName());
			// objProdHd.setDblWeight(objProductMaster.getDblWeight());
			// Double
			// PdQty=objPDService.funGetPdProdQty(WorkOrderCode,objWoModel.getStrProdCode(),clientCode);
			// objProdHd.setDblQtyProd(objWoModel.getDblQty()-PdQty);
			// objProdHd.setStrUOM(objProductMaster.getStrUOM());
			// objProdHd.setDblPrice(objProductMaster.getDblCostRM());

			Object[] ob = (Object[]) listWoDtl.get(i);
			// clsWorkOrderHdModel objWoModel = (clsWorkOrderHdModel)ob[0];
			// clsProcessMasterModel ProcessMaster=(clsProcessMasterModel)ob[2];
			// clsProductMasterModel
			// objProductMaster=(clsProductMasterModel)ob[3];

			objProdHd.setStrProdCode(ob[0].toString());
			objProdHd.setStrProdName(ob[1].toString());
			objProdHd.setStrProcessCode(ob[2].toString());
			objProdHd.setStrProcessName(ob[3].toString());
			objProdHd.setDblWeight(Double.parseDouble(ob[4].toString()));
			Double PdQty = objPDService.funGetPdProdQty(WorkOrderCode, ob[0].toString(), clientCode);
			objProdHd.setDblQtyProd(Double.parseDouble(ob[5].toString()) - PdQty);
			objProdHd.setStrUOM(ob[6].toString());
			objProdHd.setDblPrice(Double.parseDouble(ob[7].toString()));

		}

		listPDDtl.add(objProdHd);

		return listPDDtl;
	}

	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String pdCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			// List
			// listStkTransHd=objStkTransService.funGetObject(pdCode,clientCode);
			clsProductionHdModel objPDHd = objPDService.funGetObject(pdCode, clientCode);
			if (objPDHd != null) {
				List listProductionDtls = objPDService.funGetDtlList(pdCode, clientCode);
				if (null != objPDHd) {
					if (null != listProductionDtls && listProductionDtls.size() > 0) {
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + objPDHd.getStrPDCode() + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");

						if (!list.isEmpty()) {
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(objPDHd);
							if (strTransMode.equalsIgnoreCase("Deleted")) {
								model.setStrTransCode(objPDHd.getStrPDCode());
							} else {
								model.setStrTransCode(objPDHd.getStrPDCode() + "-" + count);
							}
							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (int i = 0; i < listProductionDtls.size(); i++) {
								Object[] ob1 = (Object[]) listProductionDtls.get(i);
								clsProductionDtlModel stkTransDtl = (clsProductionDtlModel) ob1[0];
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(model.getStrTransCode());
								AuditMode.setStrProdCode(stkTransDtl.getStrProdCode());
								AuditMode.setDblQty(stkTransDtl.getDblQtyProd());
								AuditMode.setDblUnitPrice(stkTransDtl.getDblPrice());
								AuditMode.setStrRemarks(stkTransDtl.getStrProcessCode());
								AuditMode.setStrUOM("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode("");
								AuditMode.setStrTaxType("");
								AuditMode.setStrPICode("");
								AuditMode.setStrClientCode(stkTransDtl.getStrClientCode());
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

	private clsAuditHdModel funPrepairAuditHdModel(clsProductionHdModel productionDtlModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (productionDtlModel != null) {
			AuditHdModel.setStrTransCode(productionDtlModel.getStrPDCode());
			AuditHdModel.setDtTransDate(productionDtlModel.getDtPDDate());
			AuditHdModel.setStrTransType("Stock Transfer");
			// AuditHdModel.setStrTransMode("Edit");
			AuditHdModel.setStrLocBy(productionDtlModel.getStrLocCode());
			AuditHdModel.setStrLocOn(productionDtlModel.getStrLocCode());
			AuditHdModel.setStrAgainst("");
			AuditHdModel.setStrAuthorise(productionDtlModel.getStrAuthorise());
			AuditHdModel.setStrNarration(productionDtlModel.getStrNarration());
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrMaterialIssue("");
			AuditHdModel.setStrUserCreated(productionDtlModel.getStrUserCreated());
			AuditHdModel.setDtDateCreated(productionDtlModel.getDtCreatedDate());
			AuditHdModel.setStrCode(productionDtlModel.getStrWOCode());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrLocCode(productionDtlModel.getStrLocCode());
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode(productionDtlModel.getStrWOCode());
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
		}
		return AuditHdModel;
	}

}
