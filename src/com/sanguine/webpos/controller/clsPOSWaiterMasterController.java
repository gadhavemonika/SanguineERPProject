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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;
import com.sanguine.webpos.bean.clsPOSWaiterMasterBean;

@Controller
public class clsPOSWaiterMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSWaiterMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSWaiterMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		Map map = new HashMap();
		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);

			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}

		model.put("posList", map);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSWaiterMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSWaiterMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSWaiterMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSWaiterMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjWaiterMaster = new JSONObject();
			jObjWaiterMaster.put("WaiterNo", objBean.getStrWaiterNo());
			jObjWaiterMaster.put("WaiterShortName", objBean.getStrWShortName());
			jObjWaiterMaster.put("WaiterFullName", objBean.getStrWFullName());

			jObjWaiterMaster.put("Operational", objGlobal.funIfNull(objBean.getStrOperational(), "N", "Y"));
			jObjWaiterMaster.put("DebitCardString", objBean.getStrDebitCardString());

			jObjWaiterMaster.put("POSCode", objBean.getStrPOSCode());

			jObjWaiterMaster.put("User", webStockUserCode);
			jObjWaiterMaster.put("ClientCode", clientCode);
			jObjWaiterMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjWaiterMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveWaiterMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjWaiterMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSWaiterMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSWaiterMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSWaiterMasterBean funSetSearchFields(@RequestParam("POSWaiterCode") String waiterCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSWaiterMasterBean objPOSWaiterMaster = null;
		String posName = "";
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetWaiterMasterData" + "?waiterNo=" + waiterCode + "&clientCode=" + clientCode;
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
			objPOSWaiterMaster = new clsPOSWaiterMasterBean();
			objPOSWaiterMaster.setStrWaiterNo((String) jObjSearchDetails.get("strWaiterNo"));
			objPOSWaiterMaster.setStrWShortName((String) jObjSearchDetails.get("strWShortName"));
			objPOSWaiterMaster.setStrWFullName((String) jObjSearchDetails.get("strWFullName"));
			objPOSWaiterMaster.setStrOperational((String) jObjSearchDetails.get("strOperational"));
			objPOSWaiterMaster.setStrDebitCardString((String) jObjSearchDetails.get("strDebitCardString"));
			objPOSWaiterMaster.setStrPOSCode((String) jObjSearchDetails.get("strPOSCode"));
		}
		if (null == objPOSWaiterMaster) {
			objPOSWaiterMaster = new clsPOSWaiterMasterBean();
			objPOSWaiterMaster.setStrWaiterNo("Invalid Code");
		}
		return objPOSWaiterMaster;
	}

	@RequestMapping(value = "/checkWaiterName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSWaiterMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

}
