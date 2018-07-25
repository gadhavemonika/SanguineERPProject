package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;
import com.sanguine.webpos.bean.clsPOSShiftMasterBean;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

@Controller
public class clsPOSShiftMasterController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	Map mapPOS = new HashMap();

	// Open ShiftMaster

	@RequestMapping(value = "/frmPOSShiftMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");

		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			String strPOSName = (String) josnObjRet.get("strPosName");
			String strPOSCode = (String) josnObjRet.get("strPosCode");

			mapPOS.put(strPOSCode, strPOSName);

			// System.out.println("mapShift="+map);
		}
		model.put("posList", mapPOS);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSShiftMaster_1", "command", new clsPOSShiftMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSShiftMaster", "command", new clsPOSShiftMasterBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/savePOSShiftMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSShiftMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjShiftMaster = new JSONObject();
			String posName = objBean.getStrPOSCode();

			jObjShiftMaster.put("ShiftCode", objBean.getIntShiftCode());
			jObjShiftMaster.put("POSCode", objBean.getStrPOSCode());
			jObjShiftMaster.put("ShiftStart", objBean.getStrtimeShiftStart());
			jObjShiftMaster.put("ShiftEnd", objBean.getStrtimeShiftEnd());
			jObjShiftMaster.put("BillDateTimeType", objBean.getStrBillDateTimeType());
			jObjShiftMaster.put("AMPMStart", objBean.getStrAMPMStart());
			jObjShiftMaster.put("AMPMEnd", objBean.getStrAMPMEnd());
			jObjShiftMaster.put("User", webStockUserCode);
			jObjShiftMaster.put("ClientCode", clientCode);
			jObjShiftMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjShiftMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println(objBean);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveShiftMaster";
			// SONObject
			// objJson=objGlobal.funPOSTMethodUrlJosnObjectData(posURL,
			// jObjCustomerTypeMaster);
			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjShiftMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSShiftMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSShiftMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSShiftMasterBean funSetSearchFields(@RequestParam("POSShiftCode") String shiftCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSShiftMasterBean objPOSShiftMaster = null;
		String posName = null;

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadShiftMasterData" + "?searchCode=" + shiftCode + "&clientCode=" + clientCode);
		if (null != jObjSearchDetails) {
			objPOSShiftMaster = new clsPOSShiftMasterBean();
			objPOSShiftMaster.setIntShiftCode(jObjSearchDetails.get("intShiftCode").toString());

			String[] strShiftStart = (String[]) jObjSearchDetails.get("tmeShiftStart").toString().split(" ");
			String[] strShiftEnd = (String[]) jObjSearchDetails.get("tmeShiftEnd").toString().split(" ");
			objPOSShiftMaster.setStrPOSCode(jObjSearchDetails.get("strPOSCode").toString());
			objPOSShiftMaster.setStrtimeShiftStart((String) strShiftStart[0]);
			objPOSShiftMaster.setStrtimeShiftEnd((String) strShiftEnd[0]);
			objPOSShiftMaster.setStrBillDateTimeType(jObjSearchDetails.get("strBillDateTimeType").toString());
			objPOSShiftMaster.setStrAMPMStart((String) strShiftStart[1]);
			objPOSShiftMaster.setStrAMPMEnd((String) strShiftEnd[1]);

		}

		if (null == objPOSShiftMaster) {
			objPOSShiftMaster = new clsPOSShiftMasterBean();
			objPOSShiftMaster.setIntShiftCode("Invalid Code");
		}

		return objPOSShiftMaster;
	}

}
