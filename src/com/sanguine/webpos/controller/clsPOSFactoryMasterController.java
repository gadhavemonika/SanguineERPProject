package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSFactoryMasterBean;

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
public class clsPOSFactoryMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSFactoryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSFactoryMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSFactoryMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSFactoryMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSFactoryMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSFactoryMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjFactoryMaster = new JSONObject();
			jObjFactoryMaster.put("FactoryCode", objBean.getStrFactoryCode());
			jObjFactoryMaster.put("FactoryName", objBean.getStrFactoryName());
			jObjFactoryMaster.put("User", webStockUserCode);
			jObjFactoryMaster.put("ClientCode", clientCode);
			jObjFactoryMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjFactoryMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveFactoryMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjFactoryMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSFactoryMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSFactoryMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSFactoryMasterBean funSetSearchFields(@RequestParam("POSFactoryCode") String factoryCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSFactoryMasterBean objPOSFactoryMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetFactoryMasterData" + "?factoryCode=" + factoryCode + "&clientCode=" + clientCode;
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
			objPOSFactoryMaster = new clsPOSFactoryMasterBean();
			objPOSFactoryMaster.setStrFactoryCode((String) jObjSearchDetails.get("strFactoryCode"));
			objPOSFactoryMaster.setStrFactoryName((String) jObjSearchDetails.get("strFactoryName"));

		}

		if (null == objPOSFactoryMaster) {
			objPOSFactoryMaster = new clsPOSFactoryMasterBean();
			objPOSFactoryMaster.setStrFactoryCode("Invalid Code");
		}

		return objPOSFactoryMaster;
	}

	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	@RequestMapping(value = "/checkFactoryName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckFactoryName(@RequestParam("strFactoryCode") String factoryCode, @RequestParam("strFactoryName") String factoryName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(factoryCode, factoryName, clientCode, "POSFactoryMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

}
