package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONObject;
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

import com.sanguine.webpos.bean.clsMoveKOTItemsToTableBean;

@Controller
public class clsPOSKDSForKOTBookAndProcessController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSKDSForKOTBookAndProcess", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsMoveKOTItemsToTableBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("frmPOSKDSForKOTBookAndProcess");

	}

	@RequestMapping(value = "/funKOTOrderProcess", method = RequestMethod.POST)
	public ModelAndView funBillOrderProcess(@ModelAttribute("command") @Valid clsMoveKOTItemsToTableBean objBean, BindingResult result, HttpServletRequest req, @RequestParam("selectedBills") ArrayList<String> listOfKOTsToBeProcess) {
		String webStockUserCode = req.getSession().getAttribute("usercode").toString();
		try {

			JSONObject jObjMoveKOT = new JSONObject();

			jObjMoveKOT.put("listOfKOTsToBeProcess", listOfKOTsToBeProcess);
			jObjMoveKOT.put("userCode", webStockUserCode);

			String posURL = "http://localhost:8080/prjSanguineWebService/WebKDSForKOTBookAndProcessController/funKOTOrderProcess";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjMoveKOT.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSKDSForKOTBookAndProcess.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/funGetKOTHdDtl", method = RequestMethod.GET)
	public @ResponseBody JSONObject funGetBillHdDtl(HttpServletRequest req) {

		LinkedHashMap<String, ArrayList<JSONObject>> mapBillHd;
		mapBillHd = new LinkedHashMap<String, ArrayList<JSONObject>>();
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebKDSForKOTBookAndProcessController/funGetKOTHdDtl";

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONObject billHd = (JSONObject) jObjSettlementData.get("mapKOTHd");

		return billHd;

	}

	@RequestMapping(value = "/funGetNewKOTSize", method = RequestMethod.GET)
	public @ResponseBody long funGetNewBillSize(HttpServletRequest req) {
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebKDSForKOTBookAndProcessController/funGetNewKOTSize";

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		long newBillSize = (long) jObjSettlementData.get("newKOTSize");

		return newBillSize;

	}
}
