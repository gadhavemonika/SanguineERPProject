package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.sanguine.webpos.bean.clsPOSCustomerMasterBean;
import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;

@Controller
public class clsPOSCustomerMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objGlobalFun;

	// Open POSCustomerMaster
	@RequestMapping(value = "/frmPOSCustomerMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List cityCode = new ArrayList();
		List cityName = new ArrayList();
		List stateName = new ArrayList();
		List CustomerType = new ArrayList();
		JSONArray jObj, jObj1, jObj2;
		JSONArray jArryList, jArryList1, jArryList2;

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		jObj = objGlobalFun.funGetAllCityForMaster(clientCode);
		jObj1 = objGlobalFun.funGetAllStateForMaster(clientCode);
		// jObj2 = objGlobalFun.funGetAllCountryForMaster(clientCode);
		jObj2 = objGlobalFun.funGetAllCustomerType(clientCode);
		jArryList = (JSONArray) jObj;
		jArryList1 = (JSONArray) jObj1;
		jArryList2 = (JSONArray) jObj2;

		for (int i = 0; i < jArryList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryList.get(i);
			// cityCode.add(josnObjRet.get("strCityCode"));
			cityName.add(josnObjRet.get("strCityName"));
		}
		for (int i = 0; i < jArryList1.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryList1.get(i);
			stateName.add(josnObjRet.get("strStateName"));

		}
		for (int i = 0; i < jArryList2.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryList2.get(i);
			CustomerType.add(josnObjRet.get("strCustomeTypeName"));

		}

		model.put("cityName", cityName);
		model.put("stateName", stateName);
		model.put("CustomerType", CustomerType);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerMaster_1", "command", new clsPOSCustomerMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCustomerMaster", "command", new clsPOSCustomerMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/checkExternalNo", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(@RequestParam("strCustCode") String strCustCode, @RequestParam("strExternalNo") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objGlobalFun.funCheckName(Name, strCustCode, clientCode, "POSCustomerMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

	@RequestMapping(value = "/savePOSCustomerMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSCustomerMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		JSONObject jSONObject = new JSONObject();
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjCustomerMaster = new JSONObject();
			jObjCustomerMaster.put("strCustomerCode", objBean.getStrCustomerCode());
			jObjCustomerMaster.put("strCustomerName", objBean.getStrCustomerName());
			jObjCustomerMaster.put("strBuldingCode", objBean.getStrBuldingCode());
			jObjCustomerMaster.put("strBuildingName", objBean.getStrBuildingName());
			jObjCustomerMaster.put("strStreetName", objBean.getStrStreetName());
			jObjCustomerMaster.put("strLandmark", objBean.getStrLandmark());
			jObjCustomerMaster.put("strArea", objBean.getStrArea());
			jObjCustomerMaster.put("strCity", objBean.getStrCity());
			jObjCustomerMaster.put("strState", objBean.getStrState());
			jObjCustomerMaster.put("intPinCode", objBean.getIntPinCode());
			jObjCustomerMaster.put("intlongMobileNo", objBean.getIntlongMobileNo());
			jObjCustomerMaster.put("strOfficeBuildingCode", objBean.getStrOfficeBuildingCode());
			jObjCustomerMaster.put("strOfficeBuildingName", objBean.getStrOfficeBuildingName());
			jObjCustomerMaster.put("strOfficeStreetName", objBean.getStrOfficeStreetName());
			// jObjCustomerMaster.put("strOfficeLandmark",
			// objBean.getStrOfficeLandmark());
			jObjCustomerMaster.put("strOfficeArea", objBean.getStrOfficeArea());
			jObjCustomerMaster.put("strOfficeCity", objBean.getStrOfficeCity());
			jObjCustomerMaster.put("strOfficePinCode", objBean.getStrOfficePinCode());
			jObjCustomerMaster.put("strOfficeState", objBean.getStrOfficeState());
			jObjCustomerMaster.put("strOfficeNo", objBean.getStrOfficeNo());
			// jObjCustomerMaster.put("strOfficeAddress",
			// objBean.getStrOfficeAddress());
			jObjCustomerMaster.put("strExternalCode", objBean.getStrExternalCode());
			jObjCustomerMaster.put("strCustomerType", objBean.getStrCustomerType());
			jObjCustomerMaster.put("dteDOB", objBean.getDteDOB());
			jObjCustomerMaster.put("strGender", objBean.getStrGender());
			jObjCustomerMaster.put("dteAnniversary", objBean.getDteAnniversary());
			jObjCustomerMaster.put("strEmailId", objBean.getStrEmailId());

			jObjCustomerMaster.put("User", webStockUserCode);
			jObjCustomerMaster.put("ClientCode", clientCode);
			jObjCustomerMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjCustomerMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println(objBean);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveCustomerMaster";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCustomerMaster.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";

			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Result= " + op);
			conn.disconnect();
			// jSONObject=objGlobal.funPOSTMethodUrlJosnObjectData(posURL,
			// jObjCustomerTypeMaster);
			// String result1=objGlobal.funPOSTMethodUrlJosnObjectData(posURL,
			// jObjCustomerTypeMaster);

			req.getSession().setAttribute("success", true);

			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmPOSCustomerMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSCustomerMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSCustomerMasterBean funSetSearchFields(@RequestParam("POSCustomerCode") String CustomerCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSCustomerMasterBean objPOSCustomerMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadCustomeMasterData" + "?searchCode=" + CustomerCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		if (null != jObjSearchDetails) {
			objPOSCustomerMaster = new clsPOSCustomerMasterBean();
			objPOSCustomerMaster.setStrCustomerCode(jObjSearchDetails.get("strCustomerCode").toString());
			objPOSCustomerMaster.setStrCustomerName(jObjSearchDetails.get("strCustomerName").toString());
			objPOSCustomerMaster.setIntlongMobileNo(jObjSearchDetails.get("longMobileNo").toString());
			objPOSCustomerMaster.setStrArea(jObjSearchDetails.get("strArea").toString());
			objPOSCustomerMaster.setStrBuldingCode(jObjSearchDetails.get("strBuldingCode").toString());
			objPOSCustomerMaster.setStrBuildingName(jObjSearchDetails.get("strBuildingName").toString());
			objPOSCustomerMaster.setStrStreetName(jObjSearchDetails.get("strStreetName").toString());
			objPOSCustomerMaster.setStrLandmark(jObjSearchDetails.get("strLandmark").toString());

			objPOSCustomerMaster.setStrCity(jObjSearchDetails.get("strCity").toString());
			objPOSCustomerMaster.setStrState(jObjSearchDetails.get("strState").toString());
			objPOSCustomerMaster.setIntPinCode(jObjSearchDetails.get("intPinCode").toString());

			objPOSCustomerMaster.setStrOfficeBuildingCode(jObjSearchDetails.get("strOfficeBuildingCode").toString());
			objPOSCustomerMaster.setStrOfficeBuildingName(jObjSearchDetails.get("strOfficeBuildingName").toString());
			objPOSCustomerMaster.setStrOfficeStreetName(jObjSearchDetails.get("strOfficeStreetName").toString());
			objPOSCustomerMaster.setStrOfficeArea(jObjSearchDetails.get("strOfficeArea").toString());
			objPOSCustomerMaster.setStrOfficeCity(jObjSearchDetails.get("strOfficeCity").toString());
			objPOSCustomerMaster.setStrOfficePinCode(jObjSearchDetails.get("strOfficePinCode").toString());
			objPOSCustomerMaster.setStrOfficeState(jObjSearchDetails.get("strOfficeState").toString());
			objPOSCustomerMaster.setStrOfficeNo(jObjSearchDetails.get("strOfficeNo").toString());
			objPOSCustomerMaster.setStrExternalCode(jObjSearchDetails.get("strExternalCode").toString());
			objPOSCustomerMaster.setStrCustomerType(jObjSearchDetails.get("strCustomerType").toString());
			objPOSCustomerMaster.setDteDOB(jObjSearchDetails.get("dteDOB").toString());
			objPOSCustomerMaster.setStrGender(jObjSearchDetails.get("strGender").toString());
			objPOSCustomerMaster.setDteAnniversary(jObjSearchDetails.get("dteAnniversary").toString());
			objPOSCustomerMaster.setStrEmailId(jObjSearchDetails.get("strEmailId").toString());

		}

		if (null == objPOSCustomerMaster) {
			objPOSCustomerMaster = new clsPOSCustomerMasterBean();
			objPOSCustomerMaster.setStrCustomerCode("Invalid Code");
		}

		return objPOSCustomerMaster;
	}

}
