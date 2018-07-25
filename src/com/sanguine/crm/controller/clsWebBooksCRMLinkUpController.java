package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsLinkUpBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsBrandMasterBean;
import com.sanguine.excise.bean.clsExciseSupplierMasterBean;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.webbooks.bean.clsSundryDebtorMasterBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsWebBooksCRMLinkUpController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsLinkUpService objARLinkUpService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;
	
	@Autowired
	private intfBaseService objBaseService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmCRMWebBooksLinkup", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsLinkUpBean objBean, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String urlHits = "1";
		Map<String, String> mapProperties = new HashMap<String, String>();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String sql = "";

		sql = " select a.strSGCode,a.strSGName,ifnull(b.strMasterDesc,''),ifnull(b.strAccountCode,'') " + " from tblsubgroupmaster a " + " left outer join tbllinkup b on a.strSGCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "'  " + " where a.strClientCode='" + clientCode + "' and b.strModuleType='Sale' " + " order by a.strSGName ";

		ArrayList list = (ArrayList) objGlobalFunctionsService.funGetDataList(sql, "sql");
		List listARLinkUp = new ArrayList<clsLinkUpHdModel>();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			clsLinkUpHdModel objModel = new clsLinkUpHdModel();
			Object[] arrObj = (Object[]) list.get(cnt);
			objModel.setStrMasterCode(arrObj[0].toString());
			objModel.setStrMasterName(arrObj[1].toString());
			objModel.setStrMasterDesc(arrObj[2].toString());
			objModel.setStrAccountCode(arrObj[3].toString());
			listARLinkUp.add(objModel);
		}

		objBean.setListSubGroupLinkUp(listARLinkUp);
		model.put("ARLinkUpList", objBean);

		mapProperties.put("All", "All");
		List<clsPropertyMaster> listProperties = objPropertyMasterService.funListProperty(clientCode);
		for (clsPropertyMaster property : listProperties) {
			mapProperties.put(property.getPropertyCode(), property.getPropertyName());
		}

		model.put("urlHits", urlHits);

		ModelAndView objModel = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModel = new ModelAndView("frmCRMWebBooksLinkup_1", "command", objBean);
			objModel.addObject("listProperty", mapProperties);
			return objModel;

		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModel = new ModelAndView("frmCRMWebBooksLinkup", "command", objBean);
			objModel.addObject("listProperty", mapProperties);
			return objModel;

		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCRMWebBooksLinkUpData", method = RequestMethod.POST)
	public @ResponseBody List funLoadLinkupData(@RequestParam("strDoc") String strDoc, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		String urlHits = "1";
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		// urlHits=request.getParameter("saddr").toString();
		String sql = "";
		if (strDoc.equals("SubGroup")) {
			sql = " select a.strSGCode,a.strSGName,ifnull(b.strAccountCode,''),ifnull(b.strMasterDesc,'') " + " from tblsubgroupmaster a " + " left outer join tbllinkup b on a.strSGCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " + " and b.strOperationType='SubGroup' and b.strModuleType='Sale' " + " where a.strClientCode='" + clientCode + "' order by a.strSGName ";
		} else if (strDoc.equals("Tax")) {
			sql = " select a.strTaxCode,a.strTaxDesc,ifnull(b.strAccountCode,''),ifnull(b.strMasterDesc,'') " + " from tbltaxhd a left outer join tbllinkup b on a.strTaxCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " + " and b.strOperationType='Tax' and b.strModuleType='Sale' " 
				+ " where  a.strClientCode='" + clientCode + "' and a.strTaxOnSP='Sales' ";
			if (!propertyCode.equalsIgnoreCase("All")) {
				sql += "and a.strPropertyCode='" + propertyCode + "' ";
			}
			sql += " order by a.strTaxCode  ";
		}

		else if (strDoc.equals("Customer")) {
			sql = " select a.strPCode,a.strPName,ifnull(b.strMasterDesc,''),ifnull(b.strAccountCode,''), ifnull(b.strWebBookAccCode,''),  ifnull(b.strWebBookAccName,'')" + " from tblpartymaster a left outer join tbllinkup b on a.strPCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " + " and b.strOperationType='Customer' and b.strModuleType='Sale' "
					+ " where  a.strPType='cust' and a.strClientCode='" + clientCode + "' ";
			if (!propertyCode.equalsIgnoreCase("All")) {
				sql += "and a.strPropCode='" + propertyCode + "' ";
			}

			sql += " order by a.strPCode ";
		} else if (strDoc.equals("Discount")) {
			sql = " SELECT '','', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where  b.strPropertyCode='" + propertyCode + "' and b.strMasterCode='Discount' " + " and b.strOperationType='Discount' and b.strModuleType='Sale' ";
		} else if (strDoc.equals("RoundOff")) {
			sql = " SELECT '','', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where  b.strPropertyCode='" + propertyCode + "' and b.strMasterCode='RoundOff' " + " and b.strOperationType='RoundOff' and b.strModuleType='Sale' ";
		} else if (strDoc.equals("ExtraCharge")) {
			sql = " SELECT '','', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where  b.strPropertyCode='" + propertyCode + "' and b.strMasterCode='ExtraCharge' " + " and b.strOperationType='ExtraCharge' and b.strModuleType='Sale' ";
		} else if (strDoc.equals("Settlement")) {
			sql = " SELECT a.strSettlementCode,a.strSettlementDesc, IFNULL(b.strAccountCode,''), IFNULL(b.strMasterDesc,'') "
				+ " FROM tblsettlementmaster a " 
				+" LEFT OUTER join tbllinkup b on a.strSettlementCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " 
				+" and b.strOperationType='Settlement' and b.strModuleType='Sale' " 
				+" where a.strClientCode='" + clientCode + "' and strSettlementType <> 'Credit' "
				+ " order by a.strSettlementDesc ";
		}

		ArrayList list = (ArrayList) objGlobalFunctionsService.funGetDataList(sql, "sql");
		List listARLinkUp = new ArrayList<clsLinkUpHdModel>();

		if (strDoc.equals("SubGroup")) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrMasterCode(arrObj[0].toString());
				objModel.setStrMasterName(arrObj[1].toString());
				objModel.setStrAccountCode(arrObj[2].toString());
				objModel.setStrMasterDesc(arrObj[3].toString());
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("saleSubGroup")) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrMasterCode(arrObj[0].toString());
				objModel.setStrMasterName(arrObj[1].toString());
				objModel.setStrAccountCode(arrObj[2].toString());
				objModel.setStrMasterDesc(arrObj[3].toString());
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("Tax")) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrMasterCode(arrObj[0].toString());
				objModel.setStrMasterName(arrObj[1].toString());
				objModel.setStrAccountCode(arrObj[2].toString());
				objModel.setStrMasterDesc(arrObj[3].toString());
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("Supplier")) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrMasterCode(arrObj[0].toString());
				objModel.setStrMasterName(arrObj[1].toString());
				objModel.setStrMasterDesc(arrObj[2].toString());
				objModel.setStrAccountCode(arrObj[3].toString());
				objModel.setStrExSuppCode(arrObj[4].toString());
				objModel.setStrExSuppName(arrObj[5].toString());
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("Customer")) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrMasterCode(arrObj[0].toString());
				objModel.setStrMasterName(arrObj[1].toString());
				objModel.setStrMasterDesc(arrObj[2].toString());
				objModel.setStrAccountCode(arrObj[3].toString());
//				objModel.setStrExSuppCode(arrObj[4].toString());
//				objModel.setStrExSuppName(arrObj[5].toString());
				objModel.setStrWebBookAccCode(arrObj[4].toString());
				objModel.setStrWebBookAccName(arrObj[5].toString());
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("Discount")) {
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					clsLinkUpHdModel objModel = new clsLinkUpHdModel();
					Object[] arrObj = (Object[]) list.get(cnt);
					objModel.setStrMasterCode("Discount");
					objModel.setStrMasterName("Discount");
					objModel.setStrMasterDesc(arrObj[2].toString());
					objModel.setStrAccountCode(arrObj[3].toString());
					objModel.setStrExSuppCode(arrObj[4].toString());
					objModel.setStrExSuppName(arrObj[5].toString());
					listARLinkUp.add(objModel);
				}
			} else {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				objModel.setStrMasterCode("Discount");
				objModel.setStrMasterName("Discount");
				objModel.setStrMasterDesc("");
				objModel.setStrAccountCode("");
				objModel.setStrExSuppCode("");
				objModel.setStrExSuppName("");
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("RoundOff")) {
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					clsLinkUpHdModel objModel = new clsLinkUpHdModel();
					Object[] arrObj = (Object[]) list.get(cnt);
					objModel.setStrMasterCode("RoundOff");
					objModel.setStrMasterName("RoundOff");
					objModel.setStrMasterDesc(arrObj[2].toString());
					objModel.setStrAccountCode(arrObj[3].toString());
					objModel.setStrExSuppCode(arrObj[4].toString());
					objModel.setStrExSuppName(arrObj[5].toString());
					listARLinkUp.add(objModel);
				}
			} else {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				objModel.setStrMasterCode("RoundOff");
				objModel.setStrMasterName("RoundOff");
				objModel.setStrMasterDesc("");
				objModel.setStrAccountCode("");
				objModel.setStrExSuppCode("");
				objModel.setStrExSuppName("");
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("ExtraCharge")) {
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					clsLinkUpHdModel objModel = new clsLinkUpHdModel();
					Object[] arrObj = (Object[]) list.get(cnt);
					objModel.setStrMasterCode("ExtraCharge");
					objModel.setStrMasterName("ExtraCharge");
					objModel.setStrMasterDesc(arrObj[2].toString());
					objModel.setStrAccountCode(arrObj[3].toString());
					objModel.setStrExSuppCode(arrObj[4].toString());
					objModel.setStrExSuppName(arrObj[5].toString());
					listARLinkUp.add(objModel);
				}
			} else {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				objModel.setStrMasterCode("ExtraCharge");
				objModel.setStrMasterName("ExtraCharge");
				objModel.setStrMasterDesc("");
				objModel.setStrAccountCode("");
				objModel.setStrExSuppCode("");
				objModel.setStrExSuppName("");
				listARLinkUp.add(objModel);
			}
		}
		else if (strDoc.equals("Settlement")) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objModel.setStrMasterCode(arrObj[0].toString());
				objModel.setStrMasterName(arrObj[1].toString());
				objModel.setStrAccountCode(arrObj[2].toString());
				objModel.setStrMasterDesc(arrObj[3].toString());
				listARLinkUp.add(objModel);
			}
		} 
		return listARLinkUp;
	}

	// Save or Update LinkUp
	@RequestMapping(value = "/saveCRMWebBooksLinkUp", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsLinkUpBean objBean, BindingResult result, HttpServletRequest req) {

		if (!result.hasErrors()) {

			objGlobal = new clsGlobalFunctions();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			// String propertyCode=objBean.getStrProperty();

			List<clsLinkUpHdModel> listSubLinkUp = objBean.getListSubGroupLinkUp();
			for (int cnt = 0; cnt < listSubLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listSubLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' and  strOperationType='Purchase' ";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("SubGroup");
					objModel.setStrModuleType("Sale");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}

			List<clsLinkUpHdModel> listTaxLinkUp = objBean.getListTaxLinkUp();
			if(listTaxLinkUp!=null)
			{
			for (int cnt = 0; cnt < listTaxLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listTaxLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("Tax");
					objModel.setStrModuleType("Sale");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}
		}

			List<clsLinkUpHdModel> listDiscLinkUp = objBean.getListDiscountLinkUp();
			if( listDiscLinkUp!=null)
			{
			for (int cnt = 0; cnt < listDiscLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listDiscLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("Discount");
					objModel.setStrModuleType("Sale");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}
			}
			List<clsLinkUpHdModel> listExtracharLinkUp = objBean.getListExtraCharLinkUp();
			if( listExtracharLinkUp!=null)
			{
			for (int cnt = 0; cnt < listExtracharLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listExtracharLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("ExtraCharge");
					objModel.setStrModuleType("Sale");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}
			}
			List<clsLinkUpHdModel> listRoundOFfLinkUp = objBean.getListRoundOffLinkUp();
			if( listRoundOFfLinkUp!=null)
			{
			for (int cnt = 0; cnt < listRoundOFfLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listRoundOFfLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("RoundOff");
					objModel.setStrModuleType("Sale");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}
			}
			/*
			 * List<clsLinkUpHdModel>
			 * listSupplierLinkUp=objBean.getListSupplierLinkUp(); for(int
			 * cnt=0;cnt<listSupplierLinkUp.size();cnt++) { clsLinkUpHdModel
			 * objModel=listSupplierLinkUp.get(cnt);
			 * if(objModel.getStrAccountCode().length()>0) { String
			 * delete=" delete from tbllinkup where strMasterCode='"
			 * +objModel.getStrMasterCode
			 * ()+"' and strClientCode='"+clientCode+"' and strPropertyCode='"
			 * +propertyCode+"' "; objARLinkUpService.funExecute(delete);
			 * objModel.setStrClientCode(clientCode);
			 * objModel.setDteCreatedDate(
			 * objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));;
			 * objModel.setDteLastModified
			 * (objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			 * objModel.setStrUserCreated(userCode);
			 * objModel.setStrUserEdited(userCode);
			 * objModel.setStrPropertyCode(propertyCode);
			 * objModel.setStrOperationType("Supplier");
			 * objModel.setStrModuleType("Purchase");
			 * objARLinkUpService.funAddUpdateARLinkUp(objModel); } }
			 */

			List<clsLinkUpHdModel> listCustomerLinkUp = objBean.getListCustomerLinkUp();
			for (int cnt = 0; cnt < listCustomerLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listCustomerLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("Customer");
					objModel.setStrModuleType("Sale");

					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}

			List<clsLinkUpHdModel> listSettlementLinkUp = objBean.getListSettlementLinkUp();
			if(listSettlementLinkUp!=null)
			{
			for (int cnt = 0; cnt < listSettlementLinkUp.size(); cnt++) {
				clsLinkUpHdModel objModel = listSettlementLinkUp.get(cnt);
				if (objModel.getStrAccountCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' "
							+ " and strOperationType='Settlement' and strModuleType='Sale'";
					objARLinkUpService.funExecute(delete);
					objModel.setStrExSuppCode("");
					objModel.setStrExSuppName("");
					objModel.setStrClientCode(clientCode);
					objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrUserCreated(userCode);
					objModel.setStrUserEdited(userCode);
					objModel.setStrPropertyCode(propertyCode);
					objModel.setStrOperationType("Settlement");
					objModel.setStrModuleType("Sale");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}
		}
			
			return new ModelAndView("redirect:/frmCRMWebBooksLinkup.html");
		} else {
			return new ModelAndView("frmARLinkUp");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCRMBrandDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsBrandMasterBean funLoadBrandDataFormWebService(@RequestParam("strBrandCode") String strBrandCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		String exciseUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funGetExciseBrandMasterData?strBrandCode=" + strBrandCode + "&clientCode=" + clientCode;
		System.out.println(exciseUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(exciseUrl);
		clsBrandMasterBean objBrand = new clsBrandMasterBean();

		objBrand.setStrBrandCode(jObj.get("strBrandCode").toString());
		objBrand.setStrBrandName(jObj.get("strBrandName").toString());

		if (null == objBrand) {
			objBrand = new clsBrandMasterBean();
			objBrand.setStrBrandCode("Invalid Code");
		}

		return objBrand;

	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCRMLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadSundryDataFormWebService(@RequestParam("strAccountCode") String strAccountCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();
		
		try
		{
			StringBuilder sbSql=new StringBuilder();
			sbSql.setLength(0);
			sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where  a.strAccountCode = '"+strAccountCode+"' and a.strClientCode = '"+clientCode+"' ") ;
					
			List list=objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
			if(list.size()>0)
			{
				for(int cn=0;cn<list.size();cn++)
				{
					Object[] arrObj=(Object[])list.get(cn);
					objDebtor.setStrDebtorCode(arrObj[0].toString());
					objDebtor.setStrFirstName(arrObj[1].toString());
				}
			}
			else
			{
				objDebtor.setStrDebtorCode("Invalid Code");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
//		
//		sql = " select a.strAccountCode , a.strAccountName "
//	    		+ " from tblacmaster a where  a.strAccountCode = '"+strAccountCode+"' and a.strClientCode = '"+clientCode+"'  " ;	
//
//		
//		String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksData?strAccountCode=" + strAccountCode + "&clientCode=" + clientCode;
//		System.out.println(webbookUrl);
//		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);
//		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();
//
//		objDebtor.setStrDebtorCode(jObj.get("strAccountCode").toString());
//		objDebtor.setStrFirstName(jObj.get("strAccountName").toString());
//
//		if (null == objDebtor) {
//			objDebtor = new clsSundryDebtorMasterBean();
//			objDebtor.setStrDebtorCode("Invalid Code");
//		}

		return objDebtor;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCRMSundryCreditorOrDebtorLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadSundryCreditorOrDebtorLinkupDataFormWebService(@RequestParam("strDocCode") String strDocCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSundryCreditorData?strDocCode=" + strDocCode + "&clientCode=" + clientCode;
		System.out.println(webbookUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);
		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();

		objDebtor.setStrDebtorCode(jObj.get("strCreditorCode").toString());
		objDebtor.setStrFirstName(jObj.get("strFirstName").toString());

		if (null == objDebtor) {
			objDebtor = new clsSundryDebtorMasterBean();
			objDebtor.setStrDebtorCode("Invalid Code");
		}

		return objDebtor;

	}

	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @RequestMapping(value = "/loadCRMExciseSuppMaster", method =
	 * RequestMethod.GET) public @ResponseBody clsExciseSupplierMasterBean
	 * funLoadExciseSupplierMasterData(@RequestParam("strSupplierCode") String
	 * strSupplierCode,HttpServletRequest request,HttpServletResponse response){
	 * String clientCode =
	 * request.getSession().getAttribute("clientCode").toString();
	 * 
	 * String exciseUrl = clsPOSGlobalFunctionsController.POSWSURL+
	 * "/ExciseIntegration/funGetExciseSupplierMasterData?strSupplierCode="
	 * +strSupplierCode+"&clientCode="+clientCode;
	 * System.out.println(exciseUrl); JSONObject jObj =
	 * objGlobalFunctions.funGETMethodUrlJosnObjectData(exciseUrl);
	 * clsExciseSupplierMasterBean objBrand = new clsExciseSupplierMasterBean();
	 * 
	 * objBrand.setStrSupplierCode(jObj.get("strSupplierCode").toString());
	 * objBrand.setStrSupplierName(jObj.get("strSupplierName").toString());
	 * 
	 * if(null==objBrand) { objBrand=new clsExciseSupplierMasterBean();
	 * objBrand.setStrSupplierCode("Invalid Code"); }
	 * 
	 * return objBrand;
	 * 
	 * }
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCRMSundryDebtorLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadSundryDebtorLinkupDataFormWebService(@RequestParam("strDocCode") String strDocCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();
		
		try
		{
			StringBuilder sbSql=new StringBuilder();
			sbSql.setLength(0);
			sbSql.append(" select a.strDebtorCode , a.strFirstName "
					+ " from tblsundaryDebtormaster a where  a.strDebtorCode = '"+strDocCode+"' and a.strClientCode = '"+clientCode+"'  ") ;
					
			List list=objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
			if(list.size()>0)
			{
				for(int cn=0;cn<list.size();cn++)
				{
					Object[] arrObj=(Object[])list.get(cn);
					objDebtor.setStrDebtorCode(arrObj[0].toString());
					objDebtor.setStrFirstName(arrObj[1].toString());
				}
			}
			else
			{
				objDebtor.setStrDebtorCode("Invalid Code");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
//		String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSundryDebtorData?strDocCode=" + strDocCode + "&clientCode=" + clientCode;
//		System.out.println(webbookUrl);
//		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);
//		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();
//
//		objDebtor.setStrDebtorCode(jObj.get("strDetorCode").toString());
//		objDebtor.setStrFirstName(jObj.get("strFirstName").toString());
//
//		if (null == objDebtor) {
//			objDebtor = new clsSundryDebtorMasterBean();
//			objDebtor.setStrDebtorCode("Invalid Code");
//		}

		return objDebtor;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCRMTaxLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadTaxLinkupDataFormWebService(@RequestParam("strDocCode") String strDocCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		clsSundryDebtorMasterBean objAc = new clsSundryDebtorMasterBean();
		
		try
		{
			StringBuilder sbSql=new StringBuilder();
			sbSql.setLength(0);
			sbSql.append(" select a.strAccountCode , a.strAccountName "
		    		+ " from tblacmaster a where  a.strAccountCode = '"+strDocCode+"' and a.strClientCode = '"+clientCode+"' and  a.strPropertyCode='"+propertyCode+"'  ") ;	
			List list=objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
			if(list.size()>0)
			{
				for(int cn=0;cn<list.size();cn++)
				{
					Object[] arrObj=(Object[])list.get(cn);
					objAc.setStrAccountCode(arrObj[0].toString());
					objAc.setStrAccountName(arrObj[1].toString());
				}
			}
			else
			{
				objAc.setStrAccountCode("Invalid Code");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		/*
		String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksTaxAccData?strDocCode=" + strDocCode + "&clientCode=" + clientCode + "&propertyCode=" + propertyCode;
		System.out.println(webbookUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);
		clsSundryDebtorMasterBean objAc = new clsSundryDebtorMasterBean();

		objAc.setStrAccountCode(jObj.get("strAccountCode").toString());
		objAc.setStrAccountName(jObj.get("strAccountName").toString());

		if (null == objAc) {
			objAc = new clsSundryDebtorMasterBean();
			objAc.setStrAccountCode("Invalid Code");
		}*/

		return objAc;
	}
	
	

}
