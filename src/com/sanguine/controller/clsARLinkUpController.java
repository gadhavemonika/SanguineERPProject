package com.sanguine.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.sanguine.base.dao.intfBaseDao;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsLinkUpBean;
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
public class clsARLinkUpController {

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
	@RequestMapping(value = "/frmARLinkUp", method = RequestMethod.GET)
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

		sql = " select a.strSGCode,a.strSGName,ifnull(b.strMasterDesc,''),ifnull(b.strAccountCode,'') " + " from tblsubgroupmaster a " + " left outer join tbllinkup b on a.strSGCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "'  " + " where a.strClientCode='" + clientCode + "' and b.strModuleType='Purchase'  " + " order by a.strSGName ";

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
			objModel = new ModelAndView("frmARLinkUp_1", "command", objBean);
			objModel.addObject("listProperty", mapProperties);
			return objModel;

		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModel = new ModelAndView("frmARLinkUp", "command", objBean);
			objModel.addObject("listProperty", mapProperties);
			return objModel;

		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadARLinkUpData", method = RequestMethod.POST)
	public @ResponseBody List funLoadLinkupData(@RequestParam("strDoc") String strDoc, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		String urlHits = "1";
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		// urlHits=request.getParameter("saddr").toString();
		String sql = "";
		if (strDoc.equals("SubGroup")) {
			sql = " select a.strSGCode,a.strSGName,ifnull(b.strAccountCode,''),ifnull(b.strMasterDesc,'') " + " from tblsubgroupmaster a " + " left outer join tbllinkup b on a.strSGCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " + " and b.strOperationType='SubGroup' and b.strModuleType='Purchase' " + " where a.strClientCode='" + clientCode + "' order by a.strSGName ";
		} else if (strDoc.equals("Tax")) {
			sql = " select a.strTaxCode,a.strTaxDesc,ifnull(b.strAccountCode,''),ifnull(b.strMasterDesc,'') " 
				+ " from tbltaxhd a left outer join tbllinkup b on a.strTaxCode=b.strMasterCode "
				+ " and b.strPropertyCode='" + propertyCode + "' and b.strOperationType='Tax' and b.strModuleType='Purchase' " 
				+ " where  a.strClientCode='" + clientCode + "' and a.strTaxOnSP='Purchase' ";
			if (!propertyCode.equalsIgnoreCase("All")) {
				sql += "and a.strPropertyCode='" + propertyCode + "'";
			}

			sql += " order by a.strTaxCode  ";
		} else if (strDoc.equals("Supplier")) {
			sql = " select a.strPCode,a.strPName,ifnull(b.strMasterDesc,''),ifnull(b.strAccountCode,''), ifnull(b.strWebBookAccCode,''), " + " ifnull(b.strWebBookAccName,'')" + " from tblpartymaster a left outer join tbllinkup b on a.strPCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " + " and b.strOperationType='Supplier' and b.strModuleType='Purchase' "
					+ " where a.strPType='supp' and a.strClientCode='" + clientCode + "' ";
			if (!propertyCode.equalsIgnoreCase("All")) {
				sql += "and a.strPropCode='" + propertyCode + "' ";
			}

			sql += " order by a.strPCode ";
		} else if (strDoc.equals("Customer")) {
			sql = " select a.strPCode,a.strPName,ifnull(b.strMasterDesc,''),ifnull(b.strAccountCode,''), ifnull(b.strExSuppCode,''), " + " ifnull(b.strExSuppName,'')" + " from tblpartymaster a left outer join tbllinkup b on a.strPCode=b.strMasterCode and b.strPropertyCode='" + propertyCode + "' " + " and b.strOperationType='Customer' and b.strModuleType='Sales' "
					+ " where  a.strPType='cust' and a.strClientCode='" + clientCode + "' and a.strPropCode='" + propertyCode + "' " + " order by a.strPCode ";
		} else if (strDoc.equals("Discount")) {
			sql = " SELECT '','', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where b.strPropertyCode='" + propertyCode + "' and b.strMasterCode='PURDISC' " + " and b.strOperationType='Discount' and b.strModuleType='Purchase' ";
		} else if (strDoc.equals("RoundOff")) {
			sql = " SELECT '','', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where  b.strPropertyCode='" + propertyCode + "' and b.strMasterCode='PURROUNDOFF' " + " and b.strOperationType='RoundOff' and b.strModuleType='Purchase' ";
		} else if (strDoc.equals("ExtraCharge")) {
			sql = " SELECT '','', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where  b.strPropertyCode='" + propertyCode + "' and b.strMasterCode='PUREXTARACHARGES' " + " and b.strOperationType='ExtraCharge' and b.strModuleType='Purchase' ";
		}
		else if (strDoc.equals("OtherCharge")) {
			sql = " SELECT IFNULL(b.strMasterCode,''),'', IFNULL(b.strMasterDesc,''), IFNULL(b.strAccountCode,''), IFNULL(b.strExSuppCode,''), IFNULL(b.strExSuppName,'') " + " FROM tbllinkup b where  b.strPropertyCode='" + propertyCode + "' and b.strOperationType='OtherCharge' and b.strModuleType='Purchase' ";
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
				objModel.setStrWebBookAccCode(arrObj[4].toString());
				objModel.setStrWebBookAccName(arrObj[5].toString());
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
				objModel.setStrExSuppCode(arrObj[4].toString());
				objModel.setStrExSuppName(arrObj[5].toString());
				listARLinkUp.add(objModel);
			}
		} else if (strDoc.equals("Discount")) {
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					clsLinkUpHdModel objModel = new clsLinkUpHdModel();
					Object[] arrObj = (Object[]) list.get(cnt);
					objModel.setStrMasterCode("PURDISC");
					objModel.setStrMasterName("Purchase Discount");
					objModel.setStrMasterDesc(arrObj[2].toString());
					objModel.setStrAccountCode(arrObj[3].toString());
					objModel.setStrExSuppCode(arrObj[4].toString());
					objModel.setStrExSuppName(arrObj[5].toString());
					listARLinkUp.add(objModel);
				}
			} else {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				objModel.setStrMasterCode("PURDISC");
				objModel.setStrMasterName("Purchase Discount");
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
					objModel.setStrMasterCode("PURROUNDOFF");
					objModel.setStrMasterName("Purchase RoundOff");
					objModel.setStrMasterDesc(arrObj[2].toString());
					objModel.setStrAccountCode(arrObj[3].toString());
					objModel.setStrExSuppCode(arrObj[4].toString());
					objModel.setStrExSuppName(arrObj[5].toString());
					listARLinkUp.add(objModel);
				}
			} else {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				objModel.setStrMasterCode("PURROUNDOFF");
				objModel.setStrMasterName("Purchase RoundOff");
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
					objModel.setStrMasterCode("PUREXTARACHARGES");
					objModel.setStrMasterName("Purchase Extra Charges");
					objModel.setStrMasterDesc(arrObj[2].toString());
					objModel.setStrAccountCode(arrObj[3].toString());
					objModel.setStrExSuppCode(arrObj[4].toString());
					objModel.setStrExSuppName(arrObj[5].toString());
					listARLinkUp.add(objModel);
				}
			} else {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				objModel.setStrMasterCode("PUREXTARACHARGES");
				objModel.setStrMasterName("Purchase Extra Charges");
				objModel.setStrMasterDesc("");
				objModel.setStrAccountCode("");
				objModel.setStrExSuppCode("");
				objModel.setStrExSuppName("");
				listARLinkUp.add(objModel);
			}
		}
		else if (strDoc.equals("OtherCharge")) {
			if(list.size()>0){
			String charge="FreightInsuranceOther ChargeFOBTAXPAYABLE";
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsLinkUpHdModel objModel = new clsLinkUpHdModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				if(arrObj[0].toString().equals("Freight")){
					objModel.setStrMasterCode("Freight");
					objModel.setStrMasterName("Freight");
					charge=charge.replaceAll("Freight","");
				}else if(arrObj[0].toString().equals("Insurance")){
					objModel.setStrMasterCode("Insurance");
					objModel.setStrMasterName("Insurance");
					charge=charge.replaceAll("Insurance","");
				}else if(arrObj[0].toString().equals("Other Charge")){
					objModel.setStrMasterCode("Other Charge");
					objModel.setStrMasterName("Other Charge");
					charge=charge.replaceAll("Other Charge","");
				}else if(arrObj[0].toString().equals("FOB")){
					objModel.setStrMasterCode("FOB");
					objModel.setStrMasterName("FOB");
					charge=charge.replaceAll("FOB","");
				}else if(arrObj[0].toString().equals("TAXPAYABLE")){
					objModel.setStrMasterCode("TAXPAYABLE");
					objModel.setStrMasterName("TAXPAYABLE");
					charge=charge.replaceAll("TAXPAYABLE","");
				}
				objModel.setStrAccountCode(arrObj[3].toString());
				objModel.setStrMasterDesc(arrObj[2].toString());
				
				listARLinkUp.add(objModel);
			}
		 	clsLinkUpHdModel objModel = null;
			 
			for(int cnt =list.size();cnt<5;cnt++){
				//Object[] arrObj = (Object[]) list.get(cnt);
				objModel = new clsLinkUpHdModel();
				if(charge.contains("Freight")){
					objModel.setStrMasterCode("Freight");
					objModel.setStrMasterName("Freight");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
					charge=charge.replaceAll("Freight","");
			
				}else if(charge.contains("Insurance")){
					objModel.setStrMasterCode("Insurance");
					objModel.setStrMasterName("Insurance");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
					charge=charge.replaceAll("Insurance","");
				}else if(charge.contains("Other Charge")){
					objModel.setStrMasterCode("Other Charge");
					objModel.setStrMasterName("Other Charge");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
					charge=charge.replaceAll("Other Charge","");
				}else if(charge.contains("FOB")){
					objModel.setStrMasterCode("FOB");
					objModel.setStrMasterName("FOB");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
					charge=charge.replaceAll("FOB","");
				}else if(charge.equals("TAXPAYABLE")){
					objModel.setStrMasterCode("TAXPAYABLE");
					objModel.setStrMasterName("TAXPAYABLE");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
					charge=charge.replaceAll("TAXPAYABLE","");
				}
				listARLinkUp.add(objModel);
			}
		}
		else{
			clsLinkUpHdModel objModel = null;
			for(int cnt =0;cnt<5;cnt++){
				objModel = new clsLinkUpHdModel();
				if(cnt==0){
					objModel.setStrMasterCode("Freight");
					objModel.setStrMasterName("Freight");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
			
				}else if(cnt==1){
					objModel.setStrMasterCode("Insurance");
					objModel.setStrMasterName("Insurance");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
				}else if(cnt==2){
					objModel.setStrMasterCode("Other Charge");
					objModel.setStrMasterName("Other Charge");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
				}else if(cnt==3){
					objModel.setStrMasterCode("FOB");
					objModel.setStrMasterName("FOB");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
				}else if(cnt==4){
					objModel.setStrMasterCode("TAXPAYABLE");
					objModel.setStrMasterName("TAXPAYABLE");
					objModel.setStrAccountCode("");
					objModel.setStrMasterDesc("");
				}
					listARLinkUp.add(objModel);
				}
			}
		}
		return listARLinkUp;
	}

	// Save or Update LinkUp
	@RequestMapping(value = "/saveARLinkUp", method = RequestMethod.POST)
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
					objModel.setStrModuleType("Purchase");
					objModel.setStrWebBookAccCode("");
					objModel.setStrWebBookAccName("");
					
					
					objARLinkUpService.funAddUpdateARLinkUp(objModel);
				}
			}

			if (objBean.getListTaxLinkUp() != null) {
				List<clsLinkUpHdModel> listTaxLinkUp = objBean.getListTaxLinkUp();
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
						objModel.setStrModuleType("Purchase");
						objModel.setStrWebBookAccCode("");
						objModel.setStrWebBookAccName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					
					}
				}
			}
			if (objBean.getListDiscountLinkUp() != null) {
				List<clsLinkUpHdModel> listDiscLinkUp = objBean.getListDiscountLinkUp();
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
						objModel.setStrModuleType("Purchase");
						objModel.setStrWebBookAccCode("");
						objModel.setStrWebBookAccName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					}
				}
			}
			if (objBean.getListExtraCharLinkUp() != null) {
				List<clsLinkUpHdModel> listExtracharLinkUp = objBean.getListExtraCharLinkUp();
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
						objModel.setStrModuleType("Purchase");
						objModel.setStrWebBookAccCode("");
						objModel.setStrWebBookAccName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					}
				}
			}
			if (objBean.getListRoundOffLinkUp() != null) {
				List<clsLinkUpHdModel> listRoundOFfLinkUp = objBean.getListRoundOffLinkUp();
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
						objModel.setStrModuleType("Purchase");
						objModel.setStrWebBookAccCode("");
						objModel.setStrWebBookAccName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					}
				}
			}
			if (objBean.getListSupplierLinkUp() != null) {
				List<clsLinkUpHdModel> listSupplierLinkUp = objBean.getListSupplierLinkUp();
				for (int cnt = 0; cnt < listSupplierLinkUp.size(); cnt++) {
					clsLinkUpHdModel objModel = listSupplierLinkUp.get(cnt);
					if (objModel.getStrAccountCode().length() > 0) {
						String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
						objARLinkUpService.funExecute(delete);
						objModel.setStrClientCode(clientCode);
						objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						;
						objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objModel.setStrUserCreated(userCode);
						objModel.setStrUserEdited(userCode);
						objModel.setStrPropertyCode(propertyCode);
						objModel.setStrOperationType("Supplier");
						objModel.setStrModuleType("Purchase");
						objModel.setStrExSuppCode("");
						objModel.setStrExSuppName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					}
				}
			}
			if (objBean.getListCustomerLinkUp() != null) {
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
						objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objModel.setStrUserCreated(userCode);
						objModel.setStrUserEdited(userCode);
						objModel.setStrPropertyCode(propertyCode);
						objModel.setStrOperationType("Customer");
						objModel.setStrModuleType("Sale");
						objModel.setStrWebBookAccCode("");
						objModel.setStrWebBookAccName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					}
				}
			}
			if (objBean.getListOtherCharLinkUp() != null) {
				List<clsLinkUpHdModel> listOthercharLinkUp = objBean.getListOtherCharLinkUp();
				for (int cnt = 0; cnt < listOthercharLinkUp.size(); cnt++) {
					clsLinkUpHdModel objModel = listOthercharLinkUp.get(cnt);
					if (objModel.getStrAccountCode().length() > 0) {
						String delete = " delete from tbllinkup where strMasterCode='" + objModel.getStrMasterCode() + "' and strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
						objARLinkUpService.funExecute(delete);
						objModel.setStrExSuppCode("");
						objModel.setStrExSuppName("");
						objModel.setStrClientCode(clientCode);
						objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						objModel.setStrUserCreated(userCode);
						objModel.setStrUserEdited(userCode);
						objModel.setStrPropertyCode(propertyCode);
						objModel.setStrOperationType("OtherCharge");
						objModel.setStrModuleType("Purchase");
						objModel.setStrWebBookAccCode("");
						objModel.setStrWebBookAccName("");
						objARLinkUpService.funAddUpdateARLinkUp(objModel);
					}
				}
			}
			return new ModelAndView("redirect:/frmARLinkUp.html");
		} else {
			return new ModelAndView("frmARLinkUp");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadBrandDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsBrandMasterBean funLoadBrandDataFormWebService(@RequestParam("strBrandCode") String strBrandCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		//String exciseUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funGetExciseBrandMasterData?strBrandCode=" + strBrandCode + "&clientCode=" + clientCode;
		//System.out.println(exciseUrl);
		//JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(exciseUrl);
		 clsBrandMasterBean objBrand = new clsBrandMasterBean();
			
		try{
			StringBuilder sbsql =new StringBuilder(" select a.strBrandCode , a.strBrandName "
		    		+ " from tblbrandmaster a where  a.strBrandCode = '"+strBrandCode+"' and a.strClientCode = '"+clientCode+"'  " );	
			 List list=objBaseService.funGetListModuleWise(sbsql,"sql","WebBooks");
			 if(list.size()>0){
				 	Object[] obj=(Object[])list.get(0);
				 	objBrand.setStrBrandCode(obj[0].toString());
					objBrand.setStrBrandName(obj[1].toString());

			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (null == objBrand) {
			objBrand = new clsBrandMasterBean();
			objBrand.setStrBrandCode("Invalid Code");
		}

		return objBrand;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadSundryDataFormWebService(@RequestParam("strAccountCode") String strAccountCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		/*String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksData?strAccountCode=" + strAccountCode + "&clientCode=" + clientCode;
		System.out.println(webbookUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);*/
		
		
		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();

		try{
			StringBuilder sbsql =new StringBuilder(" select a.strAccountCode , a.strAccountName "
			    		+ " from tblacmaster a where  a.strAccountCode = '"+strAccountCode+"' and a.strClientCode = '"+clientCode+"'  ");	
			 List list=objBaseService.funGetListModuleWise(sbsql,"sql","WebBooks");
			 if(list.size()>0){
				 	Object[] obj=(Object[])list.get(0);
				 	objDebtor.setStrDebtorCode(obj[0].toString());
					objDebtor.setStrFirstName(obj[1].toString());

			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		if (null == objDebtor) {
			objDebtor = new clsSundryDebtorMasterBean();
			objDebtor.setStrDebtorCode("Invalid Code");
		}

		return objDebtor;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadSundryCreditorOrDebtorLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadSundryCreditorOrDebtorLinkupDataFormWebService(@RequestParam("strDocCode") String strDocCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		/*String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSundryCreditorData?strDocCode=" + strDocCode + "&clientCode=" + clientCode;
		System.out.println(webbookUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);
		*/
		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();
		
		try{
			StringBuilder sbsql =new StringBuilder("select a.strCreditorCode , a.strFirstName "
			    		+ " from tblsundarycreditormaster a where  a.strCreditorCode = '"+strDocCode+"' and a.strClientCode = '"+clientCode+"'  ");	
			 List list=objBaseService.funGetListModuleWise(sbsql,"sql","WebBooks");
			 if(list.size()>0){
				 	Object[] obj=(Object[])list.get(0);
				 	objDebtor.setStrDebtorCode(obj[0].toString());
					objDebtor.setStrFirstName(obj[1].toString());
					
					
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if (null == objDebtor) {
			objDebtor = new clsSundryDebtorMasterBean();
			objDebtor.setStrDebtorCode("Invalid Code");
		}

		return objDebtor;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadExciseSuppMaster", method = RequestMethod.GET)
	public @ResponseBody clsExciseSupplierMasterBean funLoadExciseSupplierMasterData(@RequestParam("strSupplierCode") String strSupplierCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		/*String exciseUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funGetExciseSupplierMasterData?strSupplierCode=" + strSupplierCode + "&clientCode=" + clientCode;
		System.out.println(exciseUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(exciseUrl);*/
		clsExciseSupplierMasterBean objBrand = new clsExciseSupplierMasterBean();


		try{
			StringBuilder sbsql =new StringBuilder("select a.strSupplierCode , a.strSupplierName "
		    		+ " from tblsuppliermaster a where  a.strSupplierCode = '"+strSupplierCode+"' and a.strClientCode = '"+clientCode+"'  ");	
			 List list=objBaseService.funGetListModuleWise(sbsql,"sql","WebBooks");
			 if(list.size()>0){
				 	Object[] obj=(Object[])list.get(0);

					objBrand.setStrSupplierCode(obj[0].toString());
					objBrand.setStrSupplierName(obj[1].toString());
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}

		if (null == objBrand) {
			objBrand = new clsExciseSupplierMasterBean();
			objBrand.setStrSupplierCode("Invalid Code");
		}

		return objBrand;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadSundryDebtorLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadSundryDebtorLinkupDataFormWebService(@RequestParam("strDocCode") String strDocCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

	/*	String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSundryDebtorData?strDocCode=" + strDocCode + "&clientCode=" + clientCode;
		System.out.println(webbookUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);*/
		clsSundryDebtorMasterBean objDebtor = new clsSundryDebtorMasterBean();

		

		try{
			StringBuilder sbsql =new StringBuilder(" select a.strDebtorCode , a.strFirstName "
			    		+ " from tblsundaryDebtormaster a where  a.strDebtorCode = '"+strDocCode+"' and a.strClientCode = '"+clientCode+"'  ");	
			 List list=objBaseService.funGetListModuleWise(sbsql,"sql","WebBooks");
			 if(list.size()>0){
				 	Object[] obj=(Object[])list.get(0);

				 	objDebtor.setStrDebtorCode(obj[0].toString());
					objDebtor.setStrFirstName(obj[1].toString());
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if (null == objDebtor) {
			objDebtor = new clsSundryDebtorMasterBean();
			objDebtor.setStrDebtorCode("Invalid Code");
		}

		return objDebtor;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadTaxLinkupDataFormWebService", method = RequestMethod.GET)
	public @ResponseBody clsSundryDebtorMasterBean funLoadTaxLinkupDataFormWebService(@RequestParam("strDocCode") String strDocCode, HttpServletRequest request, HttpServletResponse response) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();

		/*String webbookUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksTaxAccData?strDocCode=" + strDocCode + "&clientCode=" + clientCode + "&propertyCode=" + propertyCode;
		System.out.println(webbookUrl);
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(webbookUrl);*/
		clsSundryDebtorMasterBean objAc = new clsSundryDebtorMasterBean();

	

		try{
			StringBuilder sbsql =new StringBuilder(" select a.strAccountCode , a.strAccountName "
				    		+ " from tblacmaster a where  a.strAccountCode = '"+strDocCode+"' and a.strClientCode = '"+clientCode+"' and  a.strPropertyCode='"+propertyCode+"'  ");	
			 List list=objBaseService.funGetListModuleWise(sbsql,"sql","WebBooks");
			 if(list.size()>0){
				 	Object[] obj=(Object[])list.get(0);

					objAc.setStrAccountCode(obj[0].toString());
					objAc.setStrAccountName(obj[1].toString());
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (null == objAc) {
			objAc = new clsSundryDebtorMasterBean();
			objAc.setStrAccountCode("Invalid Code");
		}

		return objAc;

	}

}
