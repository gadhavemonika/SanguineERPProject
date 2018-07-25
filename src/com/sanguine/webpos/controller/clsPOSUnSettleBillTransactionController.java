package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSCashManagmentTranscationBean;
import com.sanguine.webpos.bean.clsPOSUnsettleBillTransactionBean;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;

@Controller
public class clsPOSUnSettleBillTransactionController {

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
	@RequestMapping(value = "/frmUnsettleBill", method = RequestMethod.GET)
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
			return new ModelAndView("frmUnsettleBill_1", "command", new clsPOSUnsettleBillTransactionBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmUnsettleBill", "command", new clsPOSUnsettleBillTransactionBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/saveUnStettleBill", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSUnsettleBillTransactionBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String strReasoneName = null;

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String posCode = req.getSession().getAttribute("loginPOS").toString();
			String strReasonCode = objBean.getStrReasonCode();

			if (mapReason.containsKey(strReasonCode)) {
				strReasoneName = (String) mapReason.get(strReasonCode);
			}

			JSONObject JSONobj1 = objGlobalFun.funGetPOSDate(req);

			JSONObject jObjTransaction = new JSONObject();
			jObjTransaction.put("BillNo", objBean.getStrBillNo());
			jObjTransaction.put("posCode", posCode);
			jObjTransaction.put("posDate", (String) JSONobj1.get("POSDate"));
			jObjTransaction.put("ReasonCode", strReasonCode);
			jObjTransaction.put("ReasoneName", strReasoneName);
			jObjTransaction.put("User", webStockUserCode);
			jObjTransaction.put("ClientCode", clientCode);
			jObjTransaction.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjTransaction.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println("omg=" + jObjTransaction);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveUnStettleBill";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjTransaction.toString().getBytes());
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

			return new ModelAndView("redirect:/frmUnsettleBill.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

}
