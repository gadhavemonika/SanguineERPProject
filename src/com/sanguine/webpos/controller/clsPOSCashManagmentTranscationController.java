package com.sanguine.webpos.controller;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSCashManagmentTranscationBean;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

@Controller
public class clsPOSCashManagmentTranscationController {

	/*
	 * @Autowired private clsPOSCashManagmentTranscationService
	 * objPOSCashManagmentTranscationService;
	 */
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsPOSGlobalFunctionsController objGlobalFun;

	Map mapReason = new HashMap();

	// Open POSCashManagmentTranscation
	@RequestMapping(value = "/frmPOSCashManagement", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) throws JSONException {
		String urlHits = "1";
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONArray jObj;

		jObj = objGlobalFun.funGetAllReasonMaster(clientCode);
		JSONArray jArryList = (JSONArray) jObj;

		for (int i = 0; i < jArryList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryList.get(i);

			String strReasoncode = (String) josnObjRet.get("strReasonCode");
			String strReasonName = (String) josnObjRet.get("strReasonName");

			mapReason.put(strReasoncode, strReasonName);
		}

		model.put("ReasonNameList", mapReason);
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCashManagement_1", "command", new clsPOSCashManagmentTranscationBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCashManagement", "command", new clsPOSCashManagmentTranscationBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/savePOSCashManagmentTranscation", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSCashManagmentTranscationBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String code = "";
		System.out.println(objBean);
		try {

			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String posCode = req.getSession().getAttribute("loginPOS").toString();
			double strAmount = objBean.getDblAmount();
			String Date = objBean.getDteTransDate();
			String DateFrom = Date.split("-")[2] + "-" + Date.split("-")[1] + "-" + Date.split("-")[0];

			JSONObject jObjCashManagementMaster = new JSONObject();
			jObjCashManagementMaster.put("strTransID", objBean.getStrTransID());
			jObjCashManagementMaster.put("strTransType", objBean.getStrTransType());
			jObjCashManagementMaster.put("strTransDate", DateFrom);
			jObjCashManagementMaster.put("strAmount", objBean.getDblAmount());
			jObjCashManagementMaster.put("strReaSonCode", objBean.getStrReasonCode());
			jObjCashManagementMaster.put("strCurrencyType", objBean.getStrCurrencyType());
			jObjCashManagementMaster.put("strRemarks", objBean.getStrRemarks());
			jObjCashManagementMaster.put("User", webStockUserCode);
			jObjCashManagementMaster.put("ClientCode", clientCode);
			jObjCashManagementMaster.put("posCode", posCode);
			jObjCashManagementMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjCashManagementMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println("omg=" + jObjCashManagementMaster);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSavePOSCashManagmentTranscation";
			// JSONObject
			// objJson=objGlobal.funPOSTMethodUrlJosnObjectData(posURL,
			// jObjCashManagementMaster);
			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCashManagementMaster.toString().getBytes());
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

			if (op.equalsIgnoreCase("0000")) {
				req.getSession().setAttribute("success", false);
			} else {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", " " + op);

			}
			return new ModelAndView("redirect:/frmPOSCashManagement.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}

	}
}
