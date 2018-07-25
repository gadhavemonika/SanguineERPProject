package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSOrderMasterBean;

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
public class clsPOSOrderMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	// Open POSOrderMaster
	@RequestMapping(value = "/frmPOSOrderMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSOrderMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		Map map = new HashMap();

		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		map.put("All", "All");
		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);

			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}

		model.put("posList", map);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSOrderMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSOrderMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSOrderMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSOrderMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjAreaMaster = new JSONObject();
			jObjAreaMaster.put("OrderCode", objBean.getStrOrderCode());
			jObjAreaMaster.put("OrderDesc", objBean.getStrOrderDesc());
			String strHH = objBean.getStrHH();
			String strMM = objBean.getStrMM();
			String strAMPM = objBean.getStrAMPM();
			String uptoTime = strHH + ":" + strMM + " " + strAMPM;

			jObjAreaMaster.put("uptoTime", uptoTime);

			String posName = objBean.getStrPOSName();

			jObjAreaMaster.put("POSCode", posName);
			jObjAreaMaster.put("User", webStockUserCode);
			jObjAreaMaster.put("ClientCode", clientCode);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveOrderMaster";
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

			return new ModelAndView("redirect:/frmPOSOrderMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSOrderMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSOrderMasterBean funSetSearchFields(@RequestParam("orderCode") String orderCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSOrderMasterBean objPOSAreaMaster = null;
		String posName = "";

		JSONObject jObjSearchDetails = new JSONObject();
		/*
		 * String posUrl =
		 * "http://localhost:8080/prjSanguineWebService/APOSSearchIntegration/funGetPOSSearchData"
		 * + "?masterName=POSOrderMaster&searchCode="+orderCode+"&clientCode="+
		 * clientCode;
		 */
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetOrderMasterData" + "?orderCode=" + orderCode + "&clientCode=" + clientCode;
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
			objPOSAreaMaster = new clsPOSOrderMasterBean();
			objPOSAreaMaster.setStrOrderCode((String) jObjSearchDetails.get("strOrderCode"));
			objPOSAreaMaster.setStrOrderDesc((String) jObjSearchDetails.get("strOrderDesc"));

			String posCode = (String) jObjSearchDetails.get("strPOSCode");

			String uptoTime = (String) jObjSearchDetails.get("tmeUpToTime");
			String[] time = uptoTime.split(":");
			String HH = time[0];
			String[] time1 = time[1].split("\\s");
			String MM = time1[0];
			String AMPM = time1[1];
			objPOSAreaMaster.setStrHH(HH);
			objPOSAreaMaster.setStrMM(MM);
			objPOSAreaMaster.setStrAMPM(AMPM);

			objPOSAreaMaster.setStrPOSName(posCode);

		}

		if (null == objPOSAreaMaster) {
			objPOSAreaMaster = new clsPOSOrderMasterBean();
			objPOSAreaMaster.setStrOrderCode("Invalid Code");
		}

		return objPOSAreaMaster;
	}

	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	@RequestMapping(value = "/checkOrderName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSOrderMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

}
