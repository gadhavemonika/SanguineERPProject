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

import com.sanguine.bean.clsWorkOrderBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductionDtlModel;
import com.sanguine.model.clsProductionHdModel;
import com.sanguine.model.clsProductionHdModel_ID;
import com.sanguine.model.clsStkTransferDtlModel;
import com.sanguine.model.clsStkTransferHdModel;
import com.sanguine.model.clsStkTransferHdModel_ID;
import com.sanguine.model.clsWorkOrderDtlModel;
import com.sanguine.model.clsWorkOrderHdModel;
import com.sanguine.model.clsWorkOrderHdModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsProductionOrderService;
import com.sanguine.service.clsProductionService;
import com.sanguine.service.clsStkTransferService;
import com.sanguine.service.clsWorkOrderService;

@Controller
public class clsWorkOrderController {
	@Autowired
	private clsWorkOrderService objWorkOrderService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	clsProductMasterService objProductMasterService;

	@Autowired
	private clsProductionService objPDService;

	@Autowired
	private clsProductMasterService objProdProcessService;

	@Autowired
	private clsStkTransferService objStkTransService;

	@Autowired
	private clsProductionOrderService objProductionOrderService;

	/**
	 * Open Work Order Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmWorkOrder", method = RequestMethod.GET)
	public ModelAndView funOpenWorkOrderForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		clsWorkOrderBean bean = new clsWorkOrderBean();
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWorkOrder_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWorkOrder", "command", bean);
		} else {
			return null;
		}

	}

	/**
	 * Load Process Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProcessData", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields(HttpServletRequest request) {
		String prodCode = request.getParameter("prodCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List listProdProcess = objWorkOrderService.funGetProdProcess(prodCode, clientCode);
		List<clsWorkOrderDtlModel> listProdDtl = new ArrayList<clsWorkOrderDtlModel>();

		for (int i = 0; i < listProdProcess.size(); i++) {
			Object[] ob = (Object[]) listProdProcess.get(i);
			clsProdProcessModel ProcessProdDtl = (clsProdProcessModel) ob[0];
			clsProcessMasterModel processMaster = (clsProcessMasterModel) ob[1];
			clsWorkOrderDtlModel objWODtl = new clsWorkOrderDtlModel();
			objWODtl.setStrProcessCode(ProcessProdDtl.getStrProcessCode());
			// System.out.println("ProcessProdDtl.getStrProcessCode()\t"+ProcessProdDtl.getStrProcessCode());
			objWODtl.setStrProcessName(processMaster.getStrProcessName());
			objWODtl.setStrStatus("	Work In Progress");
			objWODtl.setDblPendingQty(Double.valueOf("0.0"));
			listProdDtl.add(objWODtl);
		}
		return listProdDtl;
	}

	/**
	 * Save Work Order Data
	 * 
	 * @param WOBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveWorkOrder", method = RequestMethod.POST)
	public ModelAndView funSaveWorkOrder(@ModelAttribute("command") clsWorkOrderBean WOBean, BindingResult result, HttpServletRequest req) {
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
		String strStkCode = "";
		String against = "Production Order";
		if (!result.hasErrors()) {

			if (WOBean.getStrAgainst().equals("Production Order")) {
				strStkCode = funGenearteWOAgainstOPData(WOBean, req);

				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Stock Transfer Code : ".concat(strStkCode));
				req.getSession().setAttribute("rptStockTranferCode", strStkCode);
				return new ModelAndView("redirect:/frmWorkOrder.html?saddr=" + urlHits);

			} else {

				List<clsWorkOrderDtlModel> listonWoDtl = WOBean.getListclsWorkOrderDtlModel();
				if (null != listonWoDtl && listonWoDtl.size() > 0) {
					clsWorkOrderHdModel objWoModel = funPrepareModelHd(WOBean, userCode, clientCode, propCode, startDate, req, "");
					objWorkOrderService.funAddWorkOrderHd(objWoModel);
					String strWoCode = objWoModel.getStrWOCode();
					objWorkOrderService.funDeleteDtl(strWoCode, req.getSession().getAttribute("clientCode").toString());
					// boolean flagDtlDataInserted=false;
					// for (clsWorkOrderDtlModel ob : listonWoDtl)
					// {
					// ob.setStrWOCode(strWoCode);
					// //ob.setStrClientCode(req.getSession().getAttribute("clientCode").toString());
					// ob.setStrStatus("Work in Progress!");
					// objWorkOrderService.funAddUpdateWorkOrderDtl(ob);
					// flagDtlDataInserted=true;
					// }
					// if(flagDtlDataInserted)
					// {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "WO Code : ".concat(objWoModel.getStrWOCode()));
					// }
				}

			}
			return new ModelAndView("redirect:/frmWorkOrder.html?saddr=" + urlHits);

		} else {
			return new ModelAndView("redirect:/frmWorkOrder.html?saddr=" + urlHits);
		}

	}

	/**
	 * Prepare Work Order HdModel
	 * 
	 * @param WoBean
	 * @param userCode
	 * @param clientCode
	 * @param propCode
	 * @param startDate
	 * @param req
	 * @return
	 */
	private clsWorkOrderHdModel funPrepareModelHd(clsWorkOrderBean WoBean, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest req, String strLocCode) {
		long lastNo = 0;
		objGlobal = new clsGlobalFunctions();
		clsWorkOrderHdModel objWoHd;

		if (WoBean.getStrWOCode().length() == 0) {
			String strWOCode = objGlobalFunctions.funGenerateDocumentCode("frmWorkOrder", WoBean.getDtWODate(), req);
			objWoHd = new clsWorkOrderHdModel(new clsWorkOrderHdModel_ID(strWOCode, clientCode));
			objWoHd.setIntId(lastNo);
			objWoHd.setStrUserCreated(userCode);
			objWoHd.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objWoHd = new clsWorkOrderHdModel(new clsWorkOrderHdModel_ID(WoBean.getStrWOCode(), clientCode));
		}
		objWoHd.setStrProdCode(WoBean.getStrProdCode());
		objWoHd.setDblQty(WoBean.getDblQty());
		objWoHd.setDtWODate(objGlobal.funGetDate("yyyy-MM-dd", WoBean.getDtWODate()));
		objWoHd.setStrAuthorise("Y");
		if (WoBean.getStrSOCode() == null) {
			objWoHd.setStrSOCode("");
		} else {
			objWoHd.setStrSOCode(WoBean.getStrSOCode());
		}

		clsProductMasterModel objProductModel = objProductMasterService.funGetObject(WoBean.getStrProdCode(), clientCode);

		objWoHd.setStrProdName(objProductModel.getStrProdName());
		objWoHd.setStrParentWOCode("");

		objWoHd.setStrJOCode("");
		objWoHd.setStrUserModified(userCode);
		objWoHd.setStrAgainst("");
		objWoHd.setStrStatus("Work In Progress!");
		objWoHd.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objWoHd.setStrAgainst(WoBean.getStrAgainst());
		List<clsWorkOrderDtlModel> listWorkOrderDtlModel = new ArrayList<clsWorkOrderDtlModel>();
		if (null != WoBean.getListclsWorkOrderDtlModel()) {
			for (clsWorkOrderDtlModel objDtlModel : WoBean.getListclsWorkOrderDtlModel()) {
				// objDtlModel.setStrWOCode(objWoHd.getStrWOCode());
				// objDtlModel.setStrClientCode(req.getSession().getAttribute("clientCode").toString());
				objDtlModel.setStrStatus("Work in Progress!");
				listWorkOrderDtlModel.add(objDtlModel);
			}

		} else {
			clsWorkOrderDtlModel objDtlModel = new clsWorkOrderDtlModel();
			// objDtlModel.setStrWOCode(objWoHd.getStrWOCode());
			objDtlModel.setStrProdCode(WoBean.getStrProdCode());
			List listObjects = objProductMasterService.funGetProdProcessList(WoBean.getStrProdCode(), clientCode);
			String strProcessCode = "", processName = "";
			for (int i = 0; i < listObjects.size(); i++) {
				Object[] ob = (Object[]) listObjects.get(i);
				// clsProdProcessModel
				// objProdProcess=(clsProdProcessModel)ob[0];
				clsProcessMasterModel objProcess = (clsProcessMasterModel) ob[1];
				strProcessCode = objProcess.getStrProcessCode();
				processName = objProcess.getStrProcessName();
			}
			objDtlModel.setStrProdName(objProductModel.getStrProdName());
			objDtlModel.setStrProdName(processName);
			objDtlModel.setStrProcessCode(strProcessCode);
			objDtlModel.setStrStatus("Work in Progress!");
			listWorkOrderDtlModel.add(objDtlModel);
		}

		if (WoBean.getStrFromLocCode() == null) {
			objWoHd.setStrFromLocCode(WoBean.getStrFromLocCode());
			objWoHd.setStrToLocCode("");
		} else {
			objWoHd.setStrFromLocCode(strLocCode);
			objWoHd.setStrToLocCode("");
		}
		objWoHd.setStrAgainst("");
		objWoHd.setListWorkOrderDtlModel(listWorkOrderDtlModel);

		// for (clsWorkOrderDtlModel ob : listonWoDtl)
		// {
		// ob.setStrWOCode(objWoHd.getStrWOCode());
		// ob.setStrClientCode(req.getSession().getAttribute("clientCode").toString());
		// ob.setStrStatus("Work in Progress!");
		// objWorkOrderService.funAddUpdateWorkOrderDtl(ob);
		// flagDtlDataInserted=true;
		// }

		return objWoHd;
	}

	/**
	 * Load Work Order HdData
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/WorkOrderHdData", method = RequestMethod.GET)
	public @ResponseBody List<clsWorkOrderHdModel> funOpenFormWithWOCode(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String WOCode = req.getParameter("woCode").toString();
		List ListWOHd = objWorkOrderService.funGetWOHdData(WOCode, req.getSession().getAttribute("clientCode").toString());
		clsWorkOrderHdModel ObWOHd = new clsWorkOrderHdModel();
		List<clsWorkOrderHdModel> listProdDtl = new ArrayList<clsWorkOrderHdModel>();
		for (int i = 0; i < ListWOHd.size(); i++) {
			Object[] ob = (Object[]) ListWOHd.get(i);
			clsWorkOrderHdModel WoHdModel = (clsWorkOrderHdModel) ob[0];
			clsProductMasterModel prodModel = (clsProductMasterModel) ob[1];
			ObWOHd.setStrWOCode(WoHdModel.getStrWOCode());
			ObWOHd.setDtWODate(objGlobal.funGetDate("yyyy/MM/dd", WoHdModel.getDtWODate()));
			ObWOHd.setStrProdCode(WoHdModel.getStrProdCode());
			ObWOHd.setDblQty(WoHdModel.getDblQty());
			ObWOHd.setStrStatus(WoHdModel.getStrStatus());
			ObWOHd.setStrProdName(prodModel.getStrProdName());
			listProdDtl.add(ObWOHd);
		}
		return listProdDtl;
	}

	/**
	 * Get Process Status
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/GetProcessDet", method = RequestMethod.GET)
	public @ResponseBody List<clsWorkOrderDtlModel> funfillProdData(HttpServletRequest request) {
		String WOCode = request.getParameter("woCode").toString();
		String prodCode = request.getParameter("prodCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List listWoDtl = objWorkOrderService.funGetProcessDet(WOCode, clientCode);
		List<clsWorkOrderDtlModel> listProdDtl = new ArrayList<clsWorkOrderDtlModel>();

		Object[] ob1 = (Object[]) listWoDtl.get(0);
		clsWorkOrderDtlModel objWODtl = new clsWorkOrderDtlModel();
		objWODtl.setStrProcessCode(ob1[0].toString());
		objWODtl.setStrProcessName(ob1[1].toString());
		String Status = objWorkOrderService.funGetProdProcessStatus(prodCode, ob1[0].toString(), WOCode, clientCode);
		objWODtl.setStrStatus(Status);
		objWODtl.setDblPendingQty(Double.parseDouble(ob1[3].toString()));
		listProdDtl.add(objWODtl);

		return listProdDtl;
	}

	/**
	 * Generate Work Order Against Production Order
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/GenearteWOAgainstOPData", method = RequestMethod.GET)
	public @ResponseBody List<clsWorkOrderDtlModel> funGenearteWOAgainstOPData(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		boolean flgSavePD = false;
		// List<String> listWoCodes = new ArrayList<String>();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String against = req.getParameter("against").toString();
		String OPCode = req.getParameter("OPCode").toString();
		String strLocCode = req.getParameter("strLocCode").toString();
		String dteWoDate = "";
		String sqlSOCodes = "";
		if (against.equalsIgnoreCase("Production Order")) {
			sqlSOCodes = "select strCode from tblproductionorderhd where strOPCode='" + OPCode + "' and strAgainst='Sales Order' and strClientCode='" + clientCode + "' ";
		} else {
			sqlSOCodes = OPCode;
		}
		// List listType = objGlobalFunctionsService.funGetDataList(sqlSOCodes,
		// "sql");
		List listOPDtl = null;
		// if(null!=listType || listType.size()>0 )
		// {
		// String[] soCodes = sqlSOCodes.split(",");
		// for(int i=0 ; i<soCodes.length;i++)
		// {
		// listOPDtl=objWorkOrderService.funGenearteWOAgainstOPData(OPCode,soCodes[0],clientCode);
		// }
		//
		// }
		// else
		// {
		//
		// }
		listOPDtl = objWorkOrderService.funGenearteWOAgainstOPData(OPCode, "", clientCode, against);
		if (!listOPDtl.isEmpty()) {
			clsWorkOrderBean WOBean = new clsWorkOrderBean();
			for (int cnt = 0; cnt < listOPDtl.size(); cnt++) {
				Object[] arrObj = (Object[]) listOPDtl.get(cnt);
				String parentcode = arrObj[0].toString();
				Double Qty = Double.valueOf(arrObj[1].toString());
				System.out.println(arrObj[0].toString());
				System.out.println(arrObj[1].toString());
				WOBean.setStrFromLocCode(strLocCode);
				WOBean.setStrSOCode(OPCode);
				WOBean.setStrProdCode(parentcode);
				WOBean.setDblQty(Qty);
				WOBean.setStrWOCode("");
				WOBean.setDtWODate(objGlobal.funGetCurrentDate("dd-MM-yyyy"));
				clsWorkOrderHdModel objWoModel = funPrepareModelHd(WOBean, userCode, clientCode, propCode, startDate, req, strLocCode);
				objWorkOrderService.funAddWorkOrderHd(objWoModel);
				dteWoDate = WOBean.getDtWODate();
				// listWoCodes.add(objWoModel.getStrWOCode());
				// objWorkOrderService.funDeleteDtl(strWoCode,clientCode);
				// List
				// listProdProcess=objWorkOrderService.funGetProdProcess(parentcode,clientCode);
				// List<clsWorkOrderDtlModel> listProdDtl=new
				// ArrayList<clsWorkOrderDtlModel>();
				//
				// for(int i=0;i<listProdProcess.size();i++)
				// {
				// // Object[] ob = (Object[])listProdProcess.get(i);
				// // clsProdProcessModel
				// ProcessProdDtl=(clsProdProcessModel)ob[0];
				// // clsProcessMasterModel
				// processMaster=(clsProcessMasterModel)ob[1];
				// // clsWorkOrderDtlModel objWODtl=new clsWorkOrderDtlModel();
				// //
				// objWODtl.setStrProcessCode(ProcessProdDtl.getStrProcessCode());
				// //
				// objWODtl.setStrProcessName(processMaster.getStrProcessName());
				// // objWODtl.setStrStatus("	Work In Progress");
				// // objWODtl.setDblPendingQty(Double.valueOf("0.0"));
				// // listProdDtl.add(objWODtl);
				//
				//
				// }
				//
				// objWorkOrderService.funAddWorkOrderHd(objWoModel);
				//
				// for (clsWorkOrderDtlModel ob : listProdDtl)
				// {
				// //ob.setStrWOCode(strWoCode);
				// //ob.setStrClientCode(clientCode);
				// ob.setStrStatus("Work in Progress!");
				// objWorkOrderService.funAddUpdateWorkOrderDtl(ob);
				//
				// }
				// funGetRecipeList(OPCode,parentcode,Qty,req);

				// flgSavePD=funActulProduction(objWoModel,req);

			}
		}
		// if(flgSavePD)
		// {
		// funStockTransfer(OPCode,strLocCode,req,dteWoDate);
		// }

		List list = objWorkOrderService.funGetGeneratedWOAgainstOPData(OPCode, clientCode);

		return list;
	}

	/**
	 * Get Recipe List
	 * 
	 * @param OPCode
	 * @param parentcode
	 * @param Qty
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funGetRecipeList(String OPCode, String parentcode, Double Qty, HttpServletRequest req) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String Productcode = null;
		Double ProdQty = null;
		objGlobal = new clsGlobalFunctions();
		String against = "Production Order";
		List listRecipe = objWorkOrderService.funGetRecipeList(parentcode, clientCode);
		if (!listRecipe.isEmpty()) {

			clsWorkOrderBean WOBean = new clsWorkOrderBean();
			for (int cnt = 0; cnt < listRecipe.size(); cnt++) {
				Object[] arrObj = (Object[]) listRecipe.get(cnt);
				Productcode = arrObj[1].toString();
				ProdQty = Double.parseDouble(arrObj[2].toString());
				WOBean.setStrSOCode(OPCode);
				WOBean.setStrProdCode(Productcode);
				WOBean.setDblQty(ProdQty * Qty);
				WOBean.setStrWOCode("");
				WOBean.setDtWODate(objGlobal.funGetCurrentDate("dd-MM-yyyy"));
				clsWorkOrderHdModel objWoModel = funPrepareModelHd(WOBean, userCode, clientCode, propCode, startDate, req, "");

				objWorkOrderService.funAddWorkOrderHd(objWoModel);

				String strWoCode = objWoModel.getStrWOCode();

				objWorkOrderService.funDeleteDtl(strWoCode, clientCode);

				List listProdProcess = objWorkOrderService.funGetProdProcess(Productcode, clientCode);

				List<clsWorkOrderDtlModel> listProdDtl = new ArrayList<clsWorkOrderDtlModel>();

				for (int i = 0; i < listProdProcess.size(); i++) {
					Object[] ob = (Object[]) listProdProcess.get(i);
					clsProdProcessModel ProcessProdDtl = (clsProdProcessModel) ob[0];
					clsProcessMasterModel processMaster = (clsProcessMasterModel) ob[1];
					clsWorkOrderDtlModel objWODtl = new clsWorkOrderDtlModel();
					objWODtl.setStrProcessCode(ProcessProdDtl.getStrProcessCode());
					objWODtl.setStrProcessName(processMaster.getStrProcessName());
					objWODtl.setStrStatus("	Work In Progress");
					objWODtl.setDblPendingQty(Double.valueOf("0.0"));
					listProdDtl.add(objWODtl);
				}

				for (clsWorkOrderDtlModel ob : listProdDtl) {
					// ob.setStrWOCode(strWoCode);
					// ob.setStrClientCode(clientCode);
					ob.setStrStatus("Work in Progress!");
					objWorkOrderService.funAddUpdateWorkOrderDtl(ob);

				}
				funGetRecipeList(OPCode, Productcode, ProdQty, req);
			}
		}

	}

	/**
	 * Checking Production Order
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/checkProductionOrder", method = RequestMethod.GET)
	public @ResponseBody List<clsWorkOrderDtlModel> funCheckProductionOrder(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		List list = new ArrayList<>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String OPCode = req.getParameter("OPCode").toString();
		list = objWorkOrderService.funGetGeneratedWOAgainstOPData(OPCode, clientCode);
		if (!list.isEmpty()) {
			return list;
		} else {
			list.add("");
			return list;
		}

	}

	// private boolean funActulProduction(clsWorkOrderHdModel
	// objWoModel,HttpServletRequest req)
	// {
	// boolean flgSaveHd = false;
	// boolean flgSaveModels = false;
	// String userCode=req.getSession().getAttribute("usercode").toString();
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	// String propCode=req.getSession().getAttribute("propertyCode").toString();
	// String startDate=req.getSession().getAttribute("startDate").toString();
	//
	// String fromLocCode=objWoModel.getStrFromLocCode();
	// String dtePD=objWoModel.getDtWODate();
	// String woCode = objWoModel.getStrWOCode();
	//
	// clsProductionHdModel
	// objPDModel=funPrepareActuallProductionModelHd(fromLocCode,dtePD,woCode,userCode,clientCode,propCode,startDate,req);
	// flgSaveHd=objPDService.funAddPDHd(objPDModel);
	//
	// clsProductionDtlModel objPDDtl =new clsProductionDtlModel();
	// clsProductMasterModel objProdModel =
	// objProductMasterService.funGetObject(objWoModel.getStrProdCode(),
	// clientCode);
	// List<clsProdProcessModel> listproProcessModel =
	// objProdProcessService.funGetProdProcessList(objWoModel.getStrProdCode(),
	// clientCode);
	// clsProdProcessModel objProdProcess=null;
	// String processCode="";
	//
	// List
	// list=objGlobalFunctions.funGetList("Select strProcessCode from tblprodprocess where strProdCode='"+objWoModel.getStrProdCode()+"' and strClientCode='"+clientCode+"'");
	// if(list.size()>0)
	// {
	// processCode =list.get(0).toString();
	// }
	//
	//
	// if(flgSaveHd)
	// {
	// objPDDtl.setStrPDCode(objPDModel.getStrPDCode());
	// objPDDtl.setStrProdCode(objWoModel.getStrProdCode());
	// objPDDtl.setStrProcessCode(processCode);
	// objPDDtl.setDblQtyProd(objWoModel.getDblQty());
	// objPDDtl.setDblQtyRej(0.00);
	// objPDDtl.setDblWeight(objProdModel.getDblWeight());
	// objPDDtl.setDblPrice(objProdModel.getDblCostRM());
	// objPDDtl.setStrProdChar("");
	// objPDDtl.setDblActTime(0.00);
	// objPDDtl.setStrPartNo(objProdModel.getStrPartNo());
	// objPDDtl.setStrClientCode(clientCode);
	// objPDService.funAddUpdatePDDtl(objPDDtl);
	// flgSaveModels=true;
	//
	// }
	//
	// return flgSaveModels;
	//
	// }

	@SuppressWarnings({ "rawtypes", "unchecked", "unchecked" })
	private clsProductionHdModel funPrepareActuallProductionModelHd(String locCode, String dtrPD, String woCode, String userCode, String clientCode, String propCode, String startDate, HttpServletRequest req) {
		clsProductionHdModel objPDModel = null;

		long lastNo = 0;
		clsProductionHdModel objPDHd;
		objGlobal = new clsGlobalFunctions();
		String[] spDate = dtrPD.split("-");
		String dteActPDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String strPDCode = objGlobalFunctions.funGenerateDocumentCode("frmProduction", dteActPDate, req);
		objPDHd = new clsProductionHdModel(new clsProductionHdModel_ID(strPDCode, clientCode));
		objPDHd.setIntid(lastNo);

		objPDHd.setStrUserCreated(userCode);
		objPDHd.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {

			@SuppressWarnings("unchecked")
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Production")) {
				res = true;
			}
		}
		if (res) {
			objPDHd.setStrAuthorise("No");
		} else {
			objPDHd.setStrAuthorise("Yes");
		}

		objPDHd.setStrLocCode(locCode);
		objPDHd.setDtPDDate(dtrPD);
		objPDHd.setStrUserModified(userCode);
		objPDHd.setStrWOCode(woCode);
		objPDHd.setStrNarration("");
		objPDHd.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objPDHd;

	}

	// private boolean funStockTransfer(String OPCode,String
	// strLocCode,HttpServletRequest req,String dteWoDate)
	// {
	// String userCode=req.getSession().getAttribute("usercode").toString();
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	// String propCode=req.getSession().getAttribute("propertyCode").toString();
	// String startDate=req.getSession().getAttribute("startDate").toString();
	// boolean flgSaveStkTrnsHd=false;
	// boolean flgSaveStkTrnsHdDtl=false;
	// clsStkTransferHdModel objStkTransferHdModel =null;
	// List listStkTrnsData =
	// objStkTransService.funGetProdAgainstActualProduction(OPCode,clientCode);
	// if(null!=listStkTrnsData)
	// {
	// objStkTransferHdModel = funPrepareStKTrnsModel(userCode, clientCode,
	// startDate, req, dteWoDate, strLocCode);
	// objStkTransService.funAddUpdate(objStkTransferHdModel);
	// flgSaveStkTrnsHd=true;
	// }
	//
	// if(flgSaveStkTrnsHd)
	// {
	// for(int i =0; i<listStkTrnsData.size(); i++)
	// {
	// Object[] arrObj=(Object[])listStkTrnsData.get(i);
	// clsStkTransferDtlModel objDtl = new clsStkTransferDtlModel();
	// objDtl.setStrSTCode(objStkTransferHdModel.getStrSTCode());
	// objDtl.setStrProdCode(arrObj[0].toString());
	// objDtl.setDblQty(Double.parseDouble(arrObj[1].toString()));
	// objDtl.setDblWeight(Double.parseDouble(arrObj[2].toString()));
	// objDtl.setDblPrice(Double.parseDouble(arrObj[3].toString()));
	// objDtl.setDblTotalWt(0);
	// objDtl.setIntProdIndex(0);
	// objDtl.setStrRemark("");
	// objDtl.setStrProdChar("");
	// objDtl.setStrClientCode(clientCode);
	// objStkTransService.funAddUpdateDtl(objDtl);
	// flgSaveStkTrnsHdDtl=true;
	// }
	//
	// }
	//
	// return flgSaveStkTrnsHdDtl;
	// }
	//
	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// private clsStkTransferHdModel funPrepareStKTrnsModel(String
	// userCode,String clientCode,String startDate,HttpServletRequest
	// request,String dteWoDate,String toLocCode)
	// {
	// objGlobal=new clsGlobalFunctions();
	// long lastNo=0;
	// clsStkTransferHdModel objHdModel ;
	// String strFromLocCode =
	// request.getSession().getAttribute("locationCode").toString();;
	//
	// String
	// strStkTransCode=objGlobalFunctions.funGenerateDocumentCode("frmStockTransfer",
	// dteWoDate, request);
	// objHdModel=new clsStkTransferHdModel(new
	// clsStkTransferHdModel_ID(strStkTransCode,clientCode));
	// objHdModel.setIntId(lastNo);
	// objHdModel.setStrUserCreated(userCode);
	// objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
	//
	//
	//
	// boolean res=false;
	// if(null!=request.getSession().getAttribute("hmAuthorization"))
	// {
	// HashMap<String,Boolean>
	// hmAuthorization=(HashMap)request.getSession().getAttribute("hmAuthorization");
	// if(hmAuthorization.get("Stock Transfer"))
	// {
	// res=true;
	// }
	// }
	// if(res)
	// {
	// objHdModel.setStrAuthorise("No");
	// }
	// else
	// {
	// objHdModel.setStrAuthorise("Yes");
	// }
	// objHdModel.setStrNo("");
	// objHdModel.setDtSTDate(objGlobal.funGetDate("yyyy-MM-dd", dteWoDate));
	// objHdModel.setStrFromLocCode(strFromLocCode);
	// objHdModel.setStrToLocCode(toLocCode);
	// objHdModel.setStrAgainst(" ");
	// objHdModel.setStrNarration("");
	// objHdModel.setStrMaterialIssue("");
	// objHdModel.setStrUserModified(userCode);
	// objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
	// objHdModel.setStrWOCode(" ");
	// objHdModel.setDblTotalAmt("0.00");;
	// return objHdModel;
	// }

	private String funGenearteWOAgainstOPData(clsWorkOrderBean objBean, HttpServletRequest req) {

		objGlobal = new clsGlobalFunctions();
		String savePDCode = "";
		// List<String> listWoCodes = new ArrayList<String>();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String strStkTrnsCode = "";
		String OPCode = objBean.getStrSOCode();
		String strFromLocCode = objBean.getStrFromLocCode();
		String strToCode = objBean.getStrToLocCode();
		String dteWoDate = objBean.getDtWODate();

		String sqlSOCodes = "select strCode from tblproductionorderhd where strOPCode='" + OPCode + "' and strAgainst='Sales Order' and strClientCode='" + clientCode + "' ";
		List listType = objGlobalFunctionsService.funGetDataList(sqlSOCodes, "sql");
		List listOPDtl = null;

		savePDCode = funActulProduction1(objBean, req);

		if (savePDCode.length() > 0) {
			strStkTrnsCode = funStockTransfer1(OPCode, strFromLocCode, strToCode, req, dteWoDate, savePDCode);
		}

		return strStkTrnsCode;
	}

	private String funActulProduction1(clsWorkOrderBean objBean, HttpServletRequest req) {
		boolean flgSaveHd = false;
		String strPDCode = "";
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();

		String fromLocCode = objBean.getStrFromLocCode();
		String dtePD = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtWODate());
		// String woCode = objBean.getStrWOCode();

		if (objBean.getListclsWorkOrderDtlModel().size() > 0) {
			for (clsWorkOrderDtlModel objWoDtl : objBean.getListclsWorkOrderDtlModel()) {
				clsProductionHdModel objPDModel = funPrepareActuallProductionModelHd(fromLocCode, dtePD, objWoDtl.getStrWOCode(), userCode, clientCode, propCode, startDate, req);
				flgSaveHd = objPDService.funAddPDHd(objPDModel);

				clsProductionDtlModel objPDDtl = new clsProductionDtlModel();
				clsProductMasterModel objProdModel = objProductMasterService.funGetObject(objWoDtl.getStrProdCode(), clientCode);
				List<clsProdProcessModel> listproProcessModel = objProdProcessService.funGetProdProcessList(objWoDtl.getStrProdCode(), clientCode);
				clsProdProcessModel objProdProcess = null;
				String processCode = "";

				List list = objGlobalFunctions.funGetList("Select strProcessCode from tblprodprocess where strProdCode='" + objWoDtl.getStrProdCode() + "' and strClientCode='" + clientCode + "'");
				if (list.size() > 0) {
					processCode = list.get(0).toString();
				}

				if (flgSaveHd) {
					objPDDtl.setStrPDCode(objPDModel.getStrPDCode());
					objPDDtl.setStrProdCode(objWoDtl.getStrProdCode());
					objPDDtl.setStrProcessCode(processCode);
					objPDDtl.setDblQtyProd(objWoDtl.getDblQty());
					objPDDtl.setStrProdChar("");
					objPDDtl.setDblQtyRej(0.00);
					objPDDtl.setDblWeight(objProdModel.getDblWeight());
					objPDDtl.setDblPrice(objProdModel.getDblCostRM());
					objPDDtl.setDblActTime(0.00);
					objPDDtl.setStrPartNo(objProdModel.getStrPartNo());
					objPDDtl.setStrClientCode(clientCode);
					objPDService.funAddUpdatePDDtl(objPDDtl);
					strPDCode = objPDModel.getStrPDCode();

				}

			}

			if (strPDCode.length() > 0) {

				objProductionOrderService.funUpdateProductionOrderAginstMaterialProcution(objBean.getStrSOCode(), clientCode);

			}

		}

		return strPDCode;

	}

	private String funStockTransfer1(String OPCode, String strFromLocCode, String strToLocCode, HttpServletRequest req, String dteWoDate, String savePDCode) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		boolean flgSaveStkTrnsHd = false;
		boolean flgSaveStkTrnsHdDtl = false;
		clsStkTransferHdModel objStkTransferHdModel = null;
		String strStkTrnsCode = "";
		List listStkTrnsData = objStkTransService.funGetProdAgainstActualProduction(OPCode, clientCode);
		if (null != listStkTrnsData) {
			objStkTransferHdModel = funPrepareStKTrnsModel1(userCode, clientCode, startDate, req, dteWoDate, strFromLocCode, strToLocCode, savePDCode);
			objStkTransService.funAddUpdate(objStkTransferHdModel);
			strStkTrnsCode = objStkTransferHdModel.getStrSTCode();
			flgSaveStkTrnsHd = true;
		}

		if (flgSaveStkTrnsHd) {
			for (int i = 0; i < listStkTrnsData.size(); i++) {
				Object[] arrObj = (Object[]) listStkTrnsData.get(i);
				clsStkTransferDtlModel objDtl = new clsStkTransferDtlModel();
				objDtl.setStrSTCode(objStkTransferHdModel.getStrSTCode());
				objDtl.setStrProdCode(arrObj[0].toString());
				objDtl.setDblQty(Double.parseDouble(arrObj[1].toString()));
				objDtl.setDblWeight(Double.parseDouble(arrObj[2].toString()));
				objDtl.setDblPrice(Double.parseDouble(arrObj[3].toString()));
				objDtl.setDblTotalWt(0);
				objDtl.setIntProdIndex(0);
				objDtl.setStrRemark("");
				objDtl.setStrProdChar("");
				objDtl.setStrClientCode(clientCode);
				objStkTransService.funAddUpdateDtl(objDtl);
				flgSaveStkTrnsHdDtl = true;
			}

		}

		return strStkTrnsCode;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsStkTransferHdModel funPrepareStKTrnsModel1(String userCode, String clientCode, String startDate, HttpServletRequest request, String dteWoDate, String strFromLocCode, String toLocCode, String savePDCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsStkTransferHdModel objHdModel;

		String strStkTransCode = objGlobalFunctions.funGenerateDocumentCode("frmStockTransfer", dteWoDate, request);
		objHdModel = new clsStkTransferHdModel(new clsStkTransferHdModel_ID(strStkTransCode, clientCode));
		objHdModel.setIntId(lastNo);
		objHdModel.setStrUserCreated(userCode);
		objHdModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		boolean res = false;
		if (null != request.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Stock Transfer")) {
				res = true;
			}
		}
		if (res) {
			objHdModel.setStrAuthorise("No");
		} else {
			objHdModel.setStrAuthorise("Yes");
		}
		objHdModel.setStrNo("");
		objHdModel.setDtSTDate(objGlobal.funGetDate("yyyy-MM-dd", dteWoDate));
		objHdModel.setStrFromLocCode(strFromLocCode);
		objHdModel.setStrToLocCode(toLocCode);
		objHdModel.setStrAgainst(" ");
		objHdModel.setStrNarration("Auto Genrated by Matrial Production:" + savePDCode);
		objHdModel.setStrMaterialIssue("");
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objHdModel.setStrWOCode(" ");
		objHdModel.setDblTotalAmt("0.00");
		objHdModel.setStrReqCode("");
		
		return objHdModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/checkWorkOrder", method = RequestMethod.GET)
	public @ResponseBody String funCheckWorkOrder(HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		List list = new ArrayList<>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String woCode = req.getParameter("wOCode").toString();

		String[] strWOCodes = woCode.split(",");
		list = objPDService.funGetWorkOrdersComplete(strWOCodes, clientCode);
		if (!list.isEmpty()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String woCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			// List listWOHd=objStkAdjService.funGetObject(woCode,clientCode);
			List listWOHd = objWorkOrderService.funGetWOHdData(woCode, clientCode);
			objGlobal = objGlobalFunctions;
			if (!listWOHd.isEmpty()) {
				Object[] ob = (Object[]) listWOHd.get(0);
				clsWorkOrderHdModel woHd = (clsWorkOrderHdModel) ob[0];
				// List
				// listWorkDtl=objStkAdjService.funGetDtlList(woCode,clientCode);
				List listWorkDtl = objWorkOrderService.funGetProcessDet(woCode, clientCode);
				if (null != woHd) {
					if (null != listWorkDtl && listWorkDtl.size() > 0) {
						String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + woHd.getStrWOCode() + "'";
						List list = objGlobalFunctionsService.funGetList(sql, "sql");

						if (!list.isEmpty()) {
							String count = list.get(0).toString();
							clsAuditHdModel model = funPrepairAuditHdModel(woHd);
							if (strTransMode.equalsIgnoreCase("Deleted")) {
								model.setStrTransCode(woHd.getStrWOCode());
							} else {
								model.setStrTransCode(woHd.getStrWOCode() + "-" + count);
							}
							model.setStrClientCode(clientCode);
							model.setStrTransMode(strTransMode);
							model.setStrUserAmed(userCode);
							model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
							model.setStrUserModified(userCode);
							objGlobalFunctionsService.funSaveAuditHd(model);
							for (int i = 0; i < listWorkDtl.size(); i++) {
								Object[] arrObj = (Object[]) listWorkDtl.get(i);
								// clsWorkOrderDtlModel
								// woDtl=(clsWorkOrderDtlModel)ob1[0];
								clsAuditDtlModel AuditMode = new clsAuditDtlModel();
								AuditMode.setStrTransCode(woHd.getStrWOCode());
								AuditMode.setStrProdCode(arrObj[0].toString());
								AuditMode.setDblQty(Double.parseDouble(arrObj[3].toString()));
								AuditMode.setDblUnitPrice(0.0);
								AuditMode.setDblTotalPrice(0.0);
								AuditMode.setStrRemarks(arrObj[2].toString());
								AuditMode.setStrType("");
								AuditMode.setStrClientCode(clientCode);
								AuditMode.setStrUOM("");
								AuditMode.setStrAgainst("");
								AuditMode.setStrCode(arrObj[0].toString());
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

	private clsAuditHdModel funPrepairAuditHdModel(clsWorkOrderHdModel objOpHd) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (objOpHd != null) {
			AuditHdModel.setStrTransCode(objOpHd.getStrWOCode());
			AuditHdModel.setDtTransDate(objOpHd.getDtWODate());
			AuditHdModel.setStrTransType("Work Order");
			AuditHdModel.setStrLocCode(objOpHd.getStrFromLocCode());
			AuditHdModel.setStrUserCreated(objOpHd.getStrUserCreated());
			AuditHdModel.setDtDateCreated(objOpHd.getDtDateCreated());
			AuditHdModel.setStrAuthorise(objOpHd.getStrAuthorise());
			AuditHdModel.setStrNarration("");
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrCode(objOpHd.getStrSOCode());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode(objOpHd.getStrWOCode());
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(objOpHd.getDblQty());
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
			AuditHdModel.setStrLocBy(objOpHd.getStrFromLocCode());
			AuditHdModel.setStrLocOn(objOpHd.getStrToLocCode());
			AuditHdModel.setStrAgainst("");
		}
		return AuditHdModel;
	}

}
