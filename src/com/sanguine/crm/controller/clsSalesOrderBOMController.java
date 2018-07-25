package com.sanguine.crm.controller;

import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsSalesOrderBOMBean;
import com.sanguine.crm.model.clsSalesOrderBOMModel;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.service.clsSalesOrderBOMService;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProdProcessModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProcessMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsRecipeMasterService;

@Controller
public class clsSalesOrderBOMController {

	@Autowired
	private clsSalesOrderBOMService objSoBomService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSalesOrderService objSalesOrderService;

	@Autowired
	private clsProductMasterService objProdMasterService;

	@Autowired
	private clsProcessMasterService objProcessMasterService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsRecipeMasterService objRecipeMasterService;

	// Open Sales Order BOM Form
	@RequestMapping(value = "/frmSalesOrderBOM", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesOrderBOM_1", "command", new clsSalesOrderBOMBean());
		} else {
			return new ModelAndView("frmSalesOrderBOM", "command", new clsSalesOrderBOMBean());
		}
	}

	@RequestMapping(value = "/loadSalesOrderBOMImage", method = RequestMethod.GET)
	public @ResponseBody void funShowImage(@RequestParam("strAgainst") String strAgainst, @RequestParam("productCode") String productCode, HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// if(strAgainst.equalsIgnoreCase("salesOrder")){
		if (true) {
			clsProductMasterModel objeProductMaster = objProdMasterService.funGetObject(productCode, clientCode);
			if (objeProductMaster != null) {

				try {
					Blob image = null;
					byte[] imgData = null;
					image = objeProductMaster.getStrProductImage();
					if (null != image && image.length() > 0) {
						imgData = image.getBytes(1, (int) image.length());
						response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
						OutputStream o = response.getOutputStream();
						o.write(imgData);
						o.flush();
						o.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// clsProductMasterModel
			// objModel=objProductMasterService.funGetObject(objSalesOrder.g,clientCode);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadOrderTree", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> funLoadOrderTree(@RequestParam("strAgainst") String strAgainst, @RequestParam("orderCode") String orderCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		HashMap resMap = new HashMap<String, Object>();
		HashMap treeMap = new HashMap<String, Object>();
		if (strAgainst.equalsIgnoreCase("salesOrder")) {

			List orderList = objSalesOrderService.funGetSalesOrder(orderCode, clientCode);

			if (!(orderList.isEmpty())) {
				Object[] objArray = (Object[]) orderList.get(0);
				clsSalesOrderHdModel objModel = (clsSalesOrderHdModel) objArray[0];
				resMap.put("orderCode", orderCode);
				resMap.put("orderDate", objModel.getDteSODate());
			}
			HashMap<String, Object> parentdMap = new HashMap<String, Object>();

			List<Object> objSOModelList = objSoBomService.funGetListOfMainParent(orderCode, clientCode);
			for (int i = 0; i < objSOModelList.size(); i++) {

				Object[] ob = (Object[]) objSOModelList.get(i);
				// clsSalesOrderBOMModel soBOMModel = (clsSalesOrderBOMModel)
				// ob[0];
				clsProductMasterModel prodmast = (clsProductMasterModel) ob[1];

				HashMap isRecipeMap = funIsRecipe(orderCode, prodmast.getStrProdCode(), clientCode);
				if (isRecipeMap == null) {
					parentdMap.put(prodmast.getStrProdName() + "#" + prodmast.getStrProdCode(), prodmast.getStrProdName());
				} else {
					parentdMap.put(prodmast.getStrProdName() + "#" + prodmast.getStrProdCode(), isRecipeMap);
				}
			}

			treeMap.put(orderCode + "#" + orderCode, parentdMap);

		} else if (strAgainst.equalsIgnoreCase("productionOrder")) {

			// Logic For ProductionOrder

		} else if (strAgainst.equalsIgnoreCase("serviceOrder")) {

			// Logic For ServiceOrder

		}

		resMap.put("tree", treeMap);
		return resMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Object> funIsRecipe(String orderCode, String prodCode, String clientCode) {

		List<Object> objSOModelList = objSoBomService.funGetListOnProdCode(orderCode, prodCode, clientCode);

		HashMap<String, Object> childMap = new HashMap<String, Object>();

		if (objSOModelList.size() > 0) {

			for (int i = 0; i < objSOModelList.size(); i++) {

				Object obj[] = (Object[]) objSOModelList.get(i);

				// clsSalesOrderBOMModel soBOMModel = (clsSalesOrderBOMModel)
				// obj[0];
				clsProductMasterModel prodmast = (clsProductMasterModel) obj[1];
				HashMap isRecipeMap = funIsRecipe(orderCode, prodmast.getStrProdCode(), clientCode);
				if (isRecipeMap == null) {
					childMap.put(prodmast.getStrProdName() + "#" + prodmast.getStrProdCode(), prodmast.getStrProdName());
				} else {
					childMap.put(prodmast.getStrProdName() + "#" + prodmast.getStrProdCode(), isRecipeMap);
				}
			}

			return childMap;
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadOrderProducts", method = RequestMethod.GET)
	public @ResponseBody clsSalesOrderBOMBean funLoadOrderProducts(@RequestParam("strAgainst") String strAgainst, @RequestParam("orderCode") String orderCode, @RequestParam("productCode") String productCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsSalesOrderBOMBean objBean = new clsSalesOrderBOMBean();

		if (strAgainst.equalsIgnoreCase("salesOrder")) {

			clsProductMasterModel objProdMaster = objProdMasterService.funGetObject(productCode, clientCode);
			objBean.setStrParentCode(productCode);
			objBean.setStrParentName(objProdMaster.getStrProdName());

			List<Object> objSOModelList = objSoBomService.funGetListOnProdCode(orderCode, productCode, clientCode);

			List<clsSalesOrderBOMModel> listSOBOM = new ArrayList<clsSalesOrderBOMModel>();
			for (int i = 0; i < objSOModelList.size(); i++) {

				Object[] ob = (Object[]) objSOModelList.get(i);

				clsSalesOrderBOMModel soBOMModel = (clsSalesOrderBOMModel) ob[0];
				clsProductMasterModel prodmast = (clsProductMasterModel) ob[1];

				if (i <= 0) {
					clsProcessMasterModel objProcessMasterModel = objProcessMasterService.funGetProcessMaster(soBOMModel.getStrParentProcessCode(), clientCode);
					objBean.setStrParentProcessCode(objProcessMasterModel.getStrProcessCode());
					objBean.setStrParentProcessName(objProcessMasterModel.getStrProcessName());

				}
				soBOMModel.setStrChildName(prodmast.getStrProdName());
				listSOBOM.add(soBOMModel);
			}
			objBean.setListChildProduct(listSOBOM);
			;

		} else if (strAgainst.equalsIgnoreCase("productionOrder")) {

			// Logic For ProductionOrder

		} else if (strAgainst.equalsIgnoreCase("serviceOrder")) {

			// Logic For ServiceOrder

		}
		return objBean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/saveSOBOM", method = RequestMethod.POST)
	public ModelAndView funSaveSOBOMData(@ModelAttribute("command") @Valid clsSalesOrderBOMBean objSOBean, BindingResult result, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String orderCode = "";

		String urlHits = "1";
		Boolean success = false;

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			if (objSOBean.getStrParentCode() != null && objSOBean.getStrParentProcessCode() != null) {
				if (objSOBean.getStrParentCode().trim().length() > 0 && objSOBean.getStrParentProcessCode().trim().length() > 0) {

					objGlobal = new clsGlobalFunctions();
					orderCode = objSOBean.getStrSOCode();

					List<Object> objSOModelList = objSoBomService.funGetListOnProdCode(objSOBean.getStrSOCode(), objSOBean.getStrParentCode(), clientCode);

					clsSalesOrderBOMModel soBOMModel = null;

					String strSOCode = "";
					String strProdCode = "";
					String strProcessCode = "";

					if (objSOModelList.size() > 0) {

						Object obj[] = (Object[]) objSOModelList.get(0);
						soBOMModel = (clsSalesOrderBOMModel) obj[0];
						// clsProductMasterModel prodmast =
						// (clsProductMasterModel) obj[1];

						strSOCode = soBOMModel.getStrSOCode();
						strProdCode = soBOMModel.getStrProdCode();
						strProcessCode = soBOMModel.getStrProcessCode();

					} else {

						strSOCode = orderCode;
						strProdCode = objSOBean.getStrParentCode();

						List listParentProdObject = objProductMasterService.funGetProdProcessList(strProdCode, clientCode);
						if (listParentProdObject.size() != 0) {
							Object[] ob = (Object[]) listParentProdObject.get(0);
							clsProdProcessModel objParentProdProcess = (clsProdProcessModel) ob[0];
							strProcessCode = objParentProdProcess.getStrProcessCode();
						}

					}

					// Details Get From Old BOMMaster

					// Details Get From Bean

					String strParentCode = objSOBean.getStrParentCode();
					String strParentProcessCode = objSOBean.getStrParentProcessCode();

					objSoBomService.funDeleteSOBomOnParent(strSOCode, strParentCode, clientCode);

					Long i = new Long(0);

					for (clsSalesOrderBOMModel objMode : objSOBean.getListChildProduct()) {

						if (objMode.getStrChildCode() != null && objMode.getDblQty() > 0) {
							if (objMode.getStrChildCode().trim().length() > 0) {

								objMode.setStrSOCode(strSOCode);
								objMode.setStrProdCode(strProdCode);
								objMode.setStrProcessCode(strProcessCode);
								objMode.setStrParentCode(strParentCode);
								objMode.setStrParentProcessCode(strParentProcessCode);

								// objMode.setStrChildCode();
								// objMode.setDblQty(objInn.getQty());
								// objMode.setStrRemarks();

								// objMode.setDblWeight();

								objMode.setIntindex(i);
								objMode.setStrUserCreated(userCode);
								objMode.setStrUserModified(userCode);
								objMode.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
								objMode.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
								objMode.setStrClientCode(clientCode);

								success = objSoBomService.funAddUpdateSoBomHd(objMode);
								funPrepardBOMData(objMode.getStrProdCode(), objMode.getStrProcessCode(), objMode.getDblQty(), objMode.getDblWeight(), objMode.getStrSOCode(), objMode.getStrChildCode(), objMode.getStrRemarks(), clientCode, userCode);
								i++;

							}// Child Code Empty End

						}// Child Code Null End
					}// For loop End

				}
			}
		}

		if (success) {
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("orderCode", orderCode);
			return new ModelAndView("redirect:/frmSalesOrderBOM.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmSalesOrderBOM.html?saddr=" + urlHits);
		}
	}

	@SuppressWarnings("rawtypes")
	private void funPrepardBOMData(String strSOProduct, String strSOProcessCode, Double soQty, Double soWt, String strSOCode, String strParentProdCode, String strRemark, String clientCode, String userCode) {

		objSoBomService.funDeleteSOBomOnParent(strSOCode, strParentProdCode, clientCode);

		HashMap<String, ArrayList<clsInnerSalesBom>> mapSOBOMcls = funGetChild(soQty, soWt, strParentProdCode, clientCode);

		for (Map.Entry<String, ArrayList<clsInnerSalesBom>> entry : mapSOBOMcls.entrySet()) {

			if (mapSOBOMcls.containsKey(entry.getKey())) {
				List<clsInnerSalesBom> listSOBOM = entry.getValue();
				long i = 0;
				for (clsInnerSalesBom objInn : listSOBOM) {
					List listParentProdObject = objProductMasterService.funGetProdProcessList(entry.getKey(), clientCode);
					String strParentProdProcess = "";
					if (listParentProdObject.size() != 0) {
						Object[] ob = (Object[]) listParentProdObject.get(0);
						clsProdProcessModel objParentProdProcess = (clsProdProcessModel) ob[0];
						strParentProdProcess = objParentProdProcess.getStrProcessCode();
					}

					clsSalesOrderBOMModel SOBOMModel = new clsSalesOrderBOMModel();

					SOBOMModel.setStrSOCode(strSOCode);
					SOBOMModel.setStrProdCode(strSOProduct);
					SOBOMModel.setStrProcessCode(strSOProcessCode);
					SOBOMModel.setStrParentCode(entry.getKey());
					SOBOMModel.setStrParentProcessCode(strParentProdProcess);
					SOBOMModel.setStrChildCode(objInn.getProdCode());
					SOBOMModel.setDblQty(objInn.getQty());
					SOBOMModel.setDblWeight(objInn.getQty() * objInn.getWt());
					SOBOMModel.setIntindex(new Long(i));
					SOBOMModel.setStrRemarks(strRemark);
					SOBOMModel.setStrUserCreated(userCode);
					SOBOMModel.setStrUserModified(userCode);
					SOBOMModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					SOBOMModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					SOBOMModel.setStrClientCode(clientCode);
					i++;
					objSoBomService.funAddUpdateSoBomHd(SOBOMModel);

				}
			}

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap<String, ArrayList<clsInnerSalesBom>> funGetChild(double soQty, double soWt, String ProdCode, String clientCode) {
		HashMap<String, ArrayList<clsInnerSalesBom>> mapSOBOMcls = new HashMap<String, ArrayList<clsInnerSalesBom>>();
		List listBomCode = objRecipeMasterService.funGetBOMCode(ProdCode, clientCode);
		if (listBomCode.size() != 0) {
			clsBomHdModel objBomModel = (clsBomHdModel) listBomCode.get(0);

			List listBOMDtl = objRecipeMasterService.funGetBOMDtl(clientCode, objBomModel.getStrBOMCode());

			ArrayList arrListChildNodes = new ArrayList<String>();
			ArrayList objInnerSalesBom = new ArrayList<clsInnerSalesBom>();
			;
			for (int i = 0; i < listBOMDtl.size(); i++) {
				clsBomDtlModel objBOMDtl = (clsBomDtlModel) listBOMDtl.get(i);

				clsInnerSalesBom objSalesOrderBom = new clsInnerSalesBom();
				String childProdCode = objBOMDtl.getStrChildCode();
				double childProdQty = objBOMDtl.getDblQty();
				double childProdWeight = objBOMDtl.getDblWeight();
				objSalesOrderBom.setProdCode(childProdCode);
				objSalesOrderBom.setQty(childProdQty);
				objSalesOrderBom.setWt(childProdWeight);

				objInnerSalesBom.add(objSalesOrderBom);
				arrListChildNodes.add(childProdCode);

				funGetChild(childProdQty, childProdWeight, childProdCode, clientCode);

			}
			mapSOBOMcls.put(ProdCode, objInnerSalesBom);

		}
		return mapSOBOMcls;
	}

	private class clsInnerSalesBom {
		private String ProdCode;

		private double Qty;

		private double Wt;

		public String getProdCode() {
			return ProdCode;
		}

		public void setProdCode(String prodCode) {
			ProdCode = prodCode;
		}

		public double getQty() {
			return Qty;
		}

		public void setQty(double qty) {
			Qty = qty;
		}

		public double getWt() {
			return Wt;
		}

		public void setWt(double wt) {
			Wt = wt;
		}

	}

	/*
	 * private clsSalesOrderBean funPrepardHdBean(clsSalesOrderHdModel
	 * objSalesOrderHdModel,clsLocationMasterModel
	 * objLocationMasterModel,clsPartyMasterModel objPartyMasterModel) {
	 * 
	 * clsSalesOrderBean objBeanSale = new clsSalesOrderBean();
	 * 
	 * objBeanSale.setStrSOCode(objSalesOrderHdModel.getStrSOCode());
	 * objBeanSale.setDteSODate(objSalesOrderHdModel.getDteSODate());
	 * objBeanSale.setDteCPODate(objSalesOrderHdModel.getDteCPODate());
	 * objBeanSale.setStrCustCode(objSalesOrderHdModel.getStrCustCode());
	 * objBeanSale.setStrLocCode(objSalesOrderHdModel.getStrLocCode());
	 * objBeanSale.setStrAgainst(objSalesOrderHdModel.getStrAgainst());
	 * objBeanSale.setStrCurrency(objSalesOrderHdModel.getStrCurrency());
	 * objBeanSale.setStrPayMode(objSalesOrderHdModel.getStrPayMode());
	 * objBeanSale.setDblDisAmt(objSalesOrderHdModel.getDblDisAmt());
	 * objBeanSale.setStrNarration(objSalesOrderHdModel.getStrNarration());
	 * objBeanSale.setStrCloseSO(objSalesOrderHdModel.getStrCloseSO());
	 * objBeanSale.setDblSubTotal(objSalesOrderHdModel.getDblSubTotal());
	 * objBeanSale.setDblDisAmt(objSalesOrderHdModel.getDblDisAmt());
	 * objBeanSale.setDblDisRate(objSalesOrderHdModel.getDblDisRate());
	 * objBeanSale.setDblExtra(objSalesOrderHdModel.getDblExtra());
	 * objBeanSale.setDblTotal(objSalesOrderHdModel.getDblTotal());
	 * objBeanSale.setStrLocName(objLocationMasterModel.getStrLocName());
	 * objBeanSale.setStrcustName(objPartyMasterModel.getStrPName());
	 * objBeanSale.setDteFulmtDate(objSalesOrderHdModel.getDteFulmtDate());
	 * objBeanSale.setStrCustPONo(objSalesOrderHdModel.getStrCustPONo());
	 * objBeanSale.setStrCode(objSalesOrderHdModel.getStrCode());
	 * objBeanSale.setStrCloseSO(objSalesOrderHdModel.getStrCloseSO());
	 * 
	 * 
	 * objBeanSale.setStrBAdd1(objSalesOrderHdModel.getStrBAdd1());
	 * objBeanSale.setStrBAdd2(objSalesOrderHdModel.getStrBAdd2());
	 * objBeanSale.setStrBCity(objSalesOrderHdModel.getStrBCity());
	 * objBeanSale.setStrBCountry(objSalesOrderHdModel.getStrBCountry());
	 * objBeanSale.setStrBPin(objSalesOrderHdModel.getStrBPin());
	 * objBeanSale.setStrBState(objSalesOrderHdModel.getStrBState());
	 * 
	 * objBeanSale.setStrSAdd1(objSalesOrderHdModel.getStrSAdd1());
	 * objBeanSale.setStrSAdd2(objSalesOrderHdModel.getStrSAdd2());
	 * objBeanSale.setStrSCity(objSalesOrderHdModel.getStrSCity());
	 * objBeanSale.setStrSCountry(objSalesOrderHdModel.getStrSCountry());
	 * objBeanSale.setStrSPin(objSalesOrderHdModel.getStrSPin());
	 * objBeanSale.setStrSState(objSalesOrderHdModel.getStrSState()); return
	 * objBeanSale; }
	 * 
	 * @RequestMapping(value = "/loadTempTreeData", method = RequestMethod.GET)
	 * public @ResponseBody HashMap<String,Object>
	 * funLoadTempTreeData(@RequestParam HashMap<String,String>
	 * reqParam,HttpServletRequest req){
	 * 
	 * HashMap< String,Object> respObj = new HashMap<String, Object>();
	 * 
	 * HashMap< String,Object> childObj = new HashMap<String, Object>();
	 * 
	 * HashMap< String,Object> subObj = new HashMap<String, Object>();
	 * 
	 * subObj.put("Product_4", "Product_4"); subObj.put("Product_5",
	 * "Product_5"); subObj.put("Product_6", "Product_6");
	 * childObj.put("Product_1", "Product_1"); childObj.put("Product_2",
	 * "Product_2");
	 * 
	 * childObj.put("Product_3", subObj);
	 * 
	 * respObj.put("SO000001", childObj);
	 * 
	 * return respObj; }
	 */

}
