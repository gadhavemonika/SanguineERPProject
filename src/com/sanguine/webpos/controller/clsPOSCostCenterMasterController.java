package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsCostCenterBean;
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;

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
public class clsPOSCostCenterMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	// Open CostCenterMaster
	@RequestMapping(value = "/frmPOSCostCenter", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		// List<String> printerList=new ArrayList<String>();
		//
		// printerList.add("All");
		//
		// JSONObject jObj =
		// objGlobal.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPrinterList");
		// printerList =(ArrayList) jObj.get("printerList");

		List<String> printerList = new ArrayList<String>();

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPrinterList");

		printerList = (ArrayList) jObj.get("printerList");
		// printerList.add(" ");

		model.put("printerList", printerList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCostCenterMaster_1", "command", new clsCostCenterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCostCenterMaster", "command", new clsCostCenterBean());
		} else {
			return new ModelAndView("frmPOSCostCenterMaster");
		}
	}

	// //Load Master Data On Form
	// @RequestMapping(value = "/frmCostCenterMaster1", method =
	// RequestMethod.POST)
	// public @ResponseBody clsCostCenterMasterModel
	// funLoadMasterData(HttpServletRequest request){
	// objGlobal=new clsGlobalFunctions();
	// String sql="";
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// String userCode=request.getSession().getAttribute("userCode").toString();
	// clsCostCenterMasterBean objBean=new clsCostCenterMasterBean();
	// String docCode=request.getParameter("docCode").toString();
	// List listModel=objGlobalFunctionsService.funGetList(sql);
	// clsCostCenterMasterModel objCostCenterMaster = new
	// clsCostCenterMasterModel();
	// return objCostCenterMaster;
	// }

	// Save or Update CostCenterMaster
	@RequestMapping(value = "/saveCostCenterMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCostCenterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjCostCenterMaster = new JSONObject();
			jObjCostCenterMaster.put("strCostCenterCode", objBean.getStrCostCenterCode());
			jObjCostCenterMaster.put("strCostCenterName", objBean.getStrCostCenterName());
			// jObjGroupMaster.put("Operational", objBean.getStrOperational());
			jObjCostCenterMaster.put("strPrinterPort", objBean.getStrPrinterPort());
			jObjCostCenterMaster.put("strSecondaryPrinterPort", objBean.getStrSecondaryPrinterPort());
			jObjCostCenterMaster.put("strPrintOnBothPrinters", objGlobal.funIfNull(objBean.getStrPrintOnBothPrinters(), "N", "Y"));
			jObjCostCenterMaster.put("strLabelOnKOT", objBean.getStrLabelOnKOT());
			jObjCostCenterMaster.put("User", webStockUserCode);
			jObjCostCenterMaster.put("ClientCode", clientCode);
			jObjCostCenterMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjCostCenterMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveCostCenterMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCostCenterMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSCostCenter.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadCostCenterMasterData", method = RequestMethod.GET)
	public @ResponseBody clsCostCenterBean funSetSearchFields(@RequestParam("POSCostCenterCode") String costCenterCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsCostCenterBean objPOSCostCenterMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetCostCenterMasterData" + "?costCenterCode=" + costCenterCode + "&clientCode=" + clientCode;
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
			objPOSCostCenterMaster = new clsCostCenterBean();
			objPOSCostCenterMaster.setStrCostCenterCode((String) jObjSearchDetails.get("strCostCenterCode"));
			objPOSCostCenterMaster.setStrCostCenterName((String) jObjSearchDetails.get("strCostCenterName"));
			objPOSCostCenterMaster.setStrPrinterPort((String) jObjSearchDetails.get("strPrinterPort"));
			objPOSCostCenterMaster.setStrSecondaryPrinterPort((String) jObjSearchDetails.get("strSecondaryPrinterPort"));
			objPOSCostCenterMaster.setStrPrintOnBothPrinters((String) jObjSearchDetails.get("strPrintOnBothPrinters"));
			objPOSCostCenterMaster.setStrLabelOnKOT((String) jObjSearchDetails.get("strLabelOnKOT"));

		}

		if (null == objPOSCostCenterMaster) {
			objPOSCostCenterMaster = new clsCostCenterBean();
			objPOSCostCenterMaster.setStrCostCenterCode("Invalid Code");
		}

		return objPOSCostCenterMaster;
	}

	@RequestMapping(value = "/checkCostCenterName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckFactoryName(@RequestParam("strCostCenterCode") String costCenterCode, @RequestParam("strCostCenterName") String costCenterName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(costCenterCode, costCenterName, clientCode, "POSCostCenterMaster");
		if (count > 0)
			return false;
		else
			return true;

	}
}
