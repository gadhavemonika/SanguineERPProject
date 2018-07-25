package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.sanguine.webpos.bean.clsPOSModifierGroupMasterBean;

@Controller
public class clsPOSModifierGroupMasterController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSModifierGroupMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		ArrayList<String> lstSeqNo = new ArrayList<String>();
		for (int i = 1; i < 51; i++) {
			lstSeqNo.add(String.valueOf(i));
		}
		model.put("listSeqNo", lstSeqNo);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSModifierGroupMaster_1", "command", new clsPOSModifierGroupMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSModifierGroupMaster", "command", new clsPOSModifierGroupMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveModifierGroupMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSModifierGroupMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjModGroupMaster = new JSONObject();
			jObjModGroupMaster.put("ModifierGroupCode", objBean.getStrModifierGroupCode());
			jObjModGroupMaster.put("ModifierGroupName", objBean.getStrModifierGroupName());
			jObjModGroupMaster.put("ModifierGroupShortName", objBean.getStrModifierGroupShortName());
			jObjModGroupMaster.put("MinModifierSelection", objBean.getStrMinModifierSelection());

			jObjModGroupMaster.put("MinItemLimit", objBean.getStrMinItemLimit());
			jObjModGroupMaster.put("MaxModifierSelection", objBean.getStrMaxModifierSelection());
			jObjModGroupMaster.put("MaxItemLimit", objBean.getStrMaxItemLimit());
			jObjModGroupMaster.put("SequenceNo", objBean.getStrSequenceNo());

			jObjModGroupMaster.put("OperationType", objBean.getStrModGrpOperational());
			jObjModGroupMaster.put("User", webStockUserCode);
			jObjModGroupMaster.put("ClientCode", clientCode);
			jObjModGroupMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjModGroupMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveModifierGroupMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjModGroupMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSModifierGroupMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// loadPOSModifierGroupMasterData

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSModifierGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSModifierGroupMasterBean funSetSearchFields(@RequestParam("POSModifierGPCode") String ModGroupCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSModifierGroupMasterBean objPOSModifierGroupMasterBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetModifierMasterData" + "?modGroupCode=" + ModGroupCode + "&clientCode=" + clientCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSModifierGroupMaster");
		if (null != jArrSearchList) {
			objPOSModifierGroupMasterBean = new clsPOSModifierGroupMasterBean();
			objPOSModifierGroupMasterBean.setStrModifierGroupCode((String) jArrSearchList.get(0));
			objPOSModifierGroupMasterBean.setStrModifierGroupName((String) jArrSearchList.get(1));
			objPOSModifierGroupMasterBean.setStrModifierGroupShortName((String) jArrSearchList.get(2));
			objPOSModifierGroupMasterBean.setStrMinModifierSelection((String) jArrSearchList.get(3));
			objPOSModifierGroupMasterBean.setStrMaxModifierSelection((String) jArrSearchList.get(4));
			objPOSModifierGroupMasterBean.setStrMaxItemLimit(Long.parseLong(jArrSearchList.get(5).toString()));
			objPOSModifierGroupMasterBean.setStrMinItemLimit(Long.parseLong(jArrSearchList.get(6).toString()));
			objPOSModifierGroupMasterBean.setStrSequenceNo(Integer.parseInt(jArrSearchList.get(7).toString()));
			objPOSModifierGroupMasterBean.setStrModGrpOperational((String) jArrSearchList.get(8));
		}

		if (null == objPOSModifierGroupMasterBean) {
			objPOSModifierGroupMasterBean = new clsPOSModifierGroupMasterBean();
			objPOSModifierGroupMasterBean.setStrModifierGroupCode("Invalid Code");
		}

		return objPOSModifierGroupMasterBean;
	}

	@RequestMapping(value = "/checkModGrpName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckMenuName(@RequestParam("groupName") String grpName, @RequestParam("groupCode") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(grpName, code, clientCode, "POSModGroup");
		if (count > 0)
			return false;
		else
			return true;

	}
}
