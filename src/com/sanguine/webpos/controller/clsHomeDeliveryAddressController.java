package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsHomeDeliveryAddressBean;
import com.sanguine.webpos.bean.clsMakeKotBillItemDtlBean;
import com.sanguine.webpos.bean.clsPOSCustomerMasterBean;
import com.sanguine.webpos.bean.clsPOSMakeKOTBean;

@Controller
public class clsHomeDeliveryAddressController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Vector vTableNo = new Vector();
	Map map = new HashMap();
	List openKOTList = new ArrayList();
	List openTableList = new ArrayList();

	@RequestMapping(value = "/frmHomeDeliveryAddress", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsHomeDeliveryAddressBean objBean, BindingResult result, Map<String, Object> model, @RequestParam(value = "strMobNo") String strMobNo, HttpServletRequest request) {

		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetCustomerAddress" + "?strMobNo=" + strMobNo;

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);
		objBean.setStrHomeAddress(jObj.get("strCustAddress").toString());
		objBean.setStrHomeCity(jObj.get("strCity").toString());
		objBean.setStrHomeCustomerName(jObj.get("strCustomerName").toString());
		objBean.setStrHomeLandmark(jObj.get("strLandmark").toString());
		objBean.setStrHomePinCode(jObj.get("intPinCode").toString());
		objBean.setStrHomeMobileNo(jObj.get("longMobileNo").toString());
		objBean.setStrHomeState(jObj.get("strState").toString());
		objBean.setStrHomeStreetName(jObj.get("strStreetName").toString());
		objBean.setStrOfficeCity(jObj.get("strOfficeCity").toString());
		objBean.setStrOfficeCustAddress(jObj.get("strOfficeAddress").toString());
		objBean.setStrOfficeLandmark(jObj.get("strOfficeLandmark").toString());
		objBean.setStrOfficePinCode(jObj.get("intOfficePinCode").toString());
		objBean.setStrOfficeState(jObj.get("strOfficeState").toString());
		objBean.setStrOfficeStreetName(jObj.get("strOfficeStreetName").toString());
		objBean.setStrTempCustAddress(jObj.get("strTempAddress").toString());
		objBean.setStrTempLandmark(jObj.get("strTempStreet").toString());
		objBean.setStrTempStreetName(jObj.get("strTempLandmark").toString());

		return new ModelAndView("frmHomeDeliveryAddress", "command", objBean);
	}

	@RequestMapping(value = "/updateHomeDeliveryAddress", method = RequestMethod.POST)
	public ModelAndView funUpdateHomeDeliveryAddress(@ModelAttribute("command") @Valid clsHomeDeliveryAddressBean objBean, BindingResult result, HttpServletRequest req) {
		JSONObject jObjMakeKOT = new JSONObject();
		try {
			jObjMakeKOT.put("strTempCustAddress", objBean.getStrTempCustAddress());
			jObjMakeKOT.put("strTempStreetName", objBean.getStrTempStreetName());
			jObjMakeKOT.put("strTempLandmark", objBean.getStrTempLandmark());

			jObjMakeKOT.put("strMobileNo", objBean.getStrHomeMobileNo());

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/updateHomeDeliveryAddress";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjMakeKOT.toString().getBytes());
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

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", " " + op);

			return new ModelAndView("redirect:/frmHomeDeliveryAddress.html?strMobNo=" + objBean.getStrHomeMobileNo());
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}

	}

}
