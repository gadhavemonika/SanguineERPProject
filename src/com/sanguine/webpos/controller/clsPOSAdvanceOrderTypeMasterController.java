package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsAdvanceOrderTypeMasterBean;

@Controller
public class clsPOSAdvanceOrderTypeMasterController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSAdvanceOrderTypeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsAdvanceOrderTypeMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAdvanceOrderTypeMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAdvanceOrderTypeMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSAdvanceOrderMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAdvanceOrderTypeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();

			JSONObject jObjAreaMaster = new JSONObject();
			jObjAreaMaster.put("AdvOrderCode", objBean.getStrAdvOrderCode());
			jObjAreaMaster.put("AdvOrderName", objBean.getStrAdvOrderName());
			jObjAreaMaster.put("Operational", objBean.getStrOperational());
			jObjAreaMaster.put("strPosCode", strPosCode);
			jObjAreaMaster.put("User", webStockUserCode);
			jObjAreaMaster.put("ClientCode", clientCode);
			jObjAreaMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjAreaMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveAdvanceOrderMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjAreaMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSAdvanceOrderTypeMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSAdvOrderMasterData", method = RequestMethod.GET)
	public @ResponseBody clsAdvanceOrderTypeMasterBean funSetSearchFields(@RequestParam("POSAdvOrderCode") String advOrderCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAdvanceOrderTypeMasterBean objPOSAreaMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAdvOrderMasterData" + "?advOrderCode=" + advOrderCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != jObjSearchDetails) {
			objPOSAreaMaster = new clsAdvanceOrderTypeMasterBean();
			objPOSAreaMaster.setStrAdvOrderCode((String) jObjSearchDetails.get("strAdvOrderCode"));
			objPOSAreaMaster.setStrAdvOrderName((String) jObjSearchDetails.get("strAdvOrderDesc"));
			objPOSAreaMaster.setStrOperational((String) jObjSearchDetails.get("strOperational"));

		}

		if (null == objPOSAreaMaster) {
			objPOSAreaMaster = new clsAdvanceOrderTypeMasterBean();
			objPOSAreaMaster.setStrAdvOrderCode("Invalid Code");
		}

		return objPOSAreaMaster;
	}

	@RequestMapping(value = "/checkAdvOrderTypeName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAdvOrderName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSAdvanceOrderMaster");
		if (count > 0)
			return false;
		else
			return true;

	}
}
