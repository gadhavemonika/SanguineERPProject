package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
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
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsPOSUserCardBean;

@Controller
public class clsPOSUserCardController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	// Open POSUserCard
	@RequestMapping(value = "/frmPOSUserCardSwipe", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSUserCardSwipe_1", "command", new clsPOSUserCardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSUserCardSwipe", "command", new clsPOSUserCardBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/savePOSUserCardSwipe", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSUserCardBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			// urlHits=req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			// String
			// webStockUserCode=req.getSession().getAttribute("usercode").toString();

			JSONObject jObjUserCardSwipe = new JSONObject();
			jObjUserCardSwipe.put("UserCode", objBean.getStrUserCode());
			jObjUserCardSwipe.put("SwipeCard", objBean.getStrDebitCardString());
			jObjUserCardSwipe.put("SuperType", "op");
			jObjUserCardSwipe.put("SuperType", objBean.getStrSuperType());
			// jObjUserCardSwipe.put("User", webStockUserCode);
			jObjUserCardSwipe.put("ClientCode", clientCode);
			jObjUserCardSwipe.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjUserCardSwipe.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveUserCard";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjUserCardSwipe.toString().getBytes());
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

			return new ModelAndView("redirect:/frmGroup.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSUserCardSwipeData", method = RequestMethod.GET)
	public @ResponseBody clsPOSUserCardBean funSetSearchFields(@RequestParam("POSUserCode") String userCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSUserCardBean objPOSUserCardSwipe = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSSearchIntegration/funGetPOSSearchData" + "?masterName=POSUserCardSwipe&searchCode=" + userCode + "&clientCode=" + clientCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSUserCardSwipe");
		if (null != jArrSearchList) {
			objPOSUserCardSwipe = new clsPOSUserCardBean();
			objPOSUserCardSwipe.setStrUserCode((String) jArrSearchList.get(0));
			objPOSUserCardSwipe.setStrDebitCardString((String) jArrSearchList.get(1));
			objPOSUserCardSwipe.setStrSuperType((String) jArrSearchList.get(2));
			objPOSUserCardSwipe.setStrSuperType("U");

		}

		if (null == objPOSUserCardSwipe) {
			objPOSUserCardSwipe = new clsPOSUserCardBean();
			objPOSUserCardSwipe.setStrUserCode("Invalid Code");
		}

		return objPOSUserCardSwipe;
	}
}
