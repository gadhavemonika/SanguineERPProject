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
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsPOSSubGroupMasterBean;

@Controller
public class clsPOSSubGroupMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	Map<String, String> mapGroup = new HashMap<String, String>();

	@RequestMapping(value = "/frmPOSSubGroup", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List listGroup = new ArrayList<String>();
		// listGroup.add("ALL");
		JSONArray jGroupArry = funGetAllGroup(strClientCode);
		for (int i = 0; i < jGroupArry.size(); i++) {
			JSONObject jObjtemp = (JSONObject) jGroupArry.get(i);
			listGroup.add(jObjtemp.get("strGroupName").toString());
			mapGroup.put(jObjtemp.get("strGroupName").toString(), jObjtemp.get("strGroupCode").toString());
		}
		model.put("listGroupName", listGroup);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSubGroup_1", "command", new clsPOSSubGroupMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSSubGroup", "command", new clsPOSSubGroupMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSSubGroup", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSSubGroupMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			// urlHits=req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjSubGroupMaster = new JSONObject();
			jObjSubGroupMaster.put("SubGroupCode", objBean.getStrSubGroupCode());
			jObjSubGroupMaster.put("SubGroupName", objBean.getStrSubGroupName());
			jObjSubGroupMaster.put("GroupCode", objBean.getStrGroupCode());
			jObjSubGroupMaster.put("Incentives", objBean.getStrIncentives());
			jObjSubGroupMaster.put("User", webStockUserCode);
			jObjSubGroupMaster.put("ClientCode", clientCode);
			jObjSubGroupMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjSubGroupMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveSubGroupMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjSubGroupMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSSubGroup.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmPOSSubGroup.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSSubGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSSubGroupMasterBean funSetSearchFields(@RequestParam("POSSubGroupCode") String subgroupCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSSubGroupMasterBean objPOSSubGroupMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetSubGroupMasterData" + "?subgroupCode=" + subgroupCode + "&clientCode=" + clientCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSSubGroupMaster");
		if (null != jArrSearchList) {
			objPOSSubGroupMaster = new clsPOSSubGroupMasterBean();
			objPOSSubGroupMaster.setStrSubGroupCode((String) jArrSearchList.get(0));
			objPOSSubGroupMaster.setStrSubGroupName((String) jArrSearchList.get(1));
			objPOSSubGroupMaster.setStrGroupCode((String) jArrSearchList.get(2));
			objPOSSubGroupMaster.setStrIncentives((String) jArrSearchList.get(3));
		}

		if (null == objPOSSubGroupMaster) {
			objPOSSubGroupMaster = new clsPOSSubGroupMasterBean();
			objPOSSubGroupMaster.setStrSubGroupCode("Invalid Code");
		}

		return objPOSSubGroupMaster;
	}

	public JSONArray funGetAllGroup(String strClientCode) {
		List sglist = new ArrayList<String>();
		JSONArray jArry = new JSONArray();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllGroup";
		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strClientCode", strClientCode);

			JSONObject jObj = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			jArry = (JSONArray) jObj.get("allGroupData");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jArry;
	}

}
