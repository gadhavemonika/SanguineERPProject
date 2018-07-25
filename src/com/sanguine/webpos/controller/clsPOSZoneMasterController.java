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

import com.sanguine.webpos.bean.clsPOSCustomerTypeMasterBean;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;

@Controller
public class clsPOSZoneMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSZoneMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)

	{
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSZoneMaster_1", "command", new clsPOSZoneMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSZoneMaster", "command", new clsPOSZoneMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/checkZoneName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(@RequestParam("strZoneName") String Name, @RequestParam("strZoneCode") String code, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(Name, code, clientCode, "POSZoneMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

	@RequestMapping(value = "/savePOSZoneMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSZoneMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		System.out.println(objBean);
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjZoneMaster = new JSONObject();
			jObjZoneMaster.put("ZoneCode", objBean.getStrZoneCode());
			jObjZoneMaster.put("ZoneName", objBean.getStrZoneName());
			jObjZoneMaster.put("User", webStockUserCode);
			jObjZoneMaster.put("ClientCode", clientCode);
			jObjZoneMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjZoneMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			System.out.println("omg=" + jObjZoneMaster);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveZoneMaster";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjZoneMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSZoneMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSZoneMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSZoneMasterBean funSetSearchFields(@RequestParam("POSZoneCode") String ZoneCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSZoneMasterBean objPOSZoneMaster = null;

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoaddZoneMasterData" + "?searchCode=" + ZoneCode + "&clientCode=" + clientCode);

		if (null != jObjSearchDetails) {
			objPOSZoneMaster = new clsPOSZoneMasterBean();
			objPOSZoneMaster.setStrZoneCode(jObjSearchDetails.get("strZoneCode").toString());
			objPOSZoneMaster.setStrZoneName(jObjSearchDetails.get("strZoneName").toString());

		}

		if (null == objPOSZoneMaster) {
			objPOSZoneMaster = new clsPOSZoneMasterBean();
			objPOSZoneMaster.setStrZoneCode("Invalid Code");
		}

		return objPOSZoneMaster;
	}

}
