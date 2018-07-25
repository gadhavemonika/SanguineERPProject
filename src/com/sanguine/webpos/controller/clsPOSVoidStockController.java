package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
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
import com.sanguine.webpos.bean.clsMoveKOTBean;
import com.sanguine.webpos.bean.clsPOSImportDatabaseBean;
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;
import com.sanguine.webpos.bean.clsVoidStockBean;

@Controller
public class clsPOSVoidStockController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSVoidStock", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsVoidStockBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		JSONObject jObjReasonDetails = new JSONObject();
		JSONArray jArrReasonList = null;
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetReasonCode" + "?ReasonCode=All&Type=strVoidStkIn";
		jObjReasonDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrReasonList = (JSONArray) jObjReasonDetails.get("ReasonList");
		Map stkInMap = new HashMap();
		if (null != jArrReasonList) {
			for (int cnt = 0; cnt < jArrReasonList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrReasonList.get(cnt);

				stkInMap.put((String) jobj.get("ReasonCode"), (String) jobj.get("ReasonName"));
			}

		}
		posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetReasonCode" + "?ReasonCode=All&Type=strStkOut";
		jObjReasonDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrReasonList = (JSONArray) jObjReasonDetails.get("ReasonList");
		Map stkOutMap = new HashMap();
		if (null != jArrReasonList) {
			for (int cnt = 0; cnt < jArrReasonList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrReasonList.get(cnt);

				stkOutMap.put((String) jobj.get("ReasonCode"), (String) jobj.get("ReasonName"));
			}

		}

		posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetReasonCode" + "?ReasonCode=All&Type=strPSP";
		jObjReasonDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrReasonList = (JSONArray) jObjReasonDetails.get("ReasonList");
		Map PSPMap = new HashMap();
		if (null != jArrReasonList) {
			for (int cnt = 0; cnt < jArrReasonList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrReasonList.get(cnt);

				PSPMap.put((String) jobj.get("ReasonCode"), (String) jobj.get("ReasonName"));
			}

		}
		model.put("PSPReasonList", PSPMap);
		model.put("stkInReasonList", stkInMap);
		model.put("stkOutReasonList", stkOutMap);

		return new ModelAndView("frmPOSVoidStock");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fillVoidStockGridData", method = RequestMethod.GET)
	public @ResponseBody LinkedList fillVoidStockGridData(HttpServletRequest req) {

		String strPosCode = req.getSession().getAttribute("loginPOS").toString();
		String transType = req.getParameter("transType");
		LinkedList listFillGrid = new LinkedList();
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidStockController/funFillVoidStockGrid";
			JSONObject objRows = new JSONObject();

			objRows.put("transType", transType);
			objRows.put("strPosCode", strPosCode);

			JSONObject jObj = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			JSONArray jarr = (JSONArray) jObj.get("stkList");
			if (transType.equalsIgnoreCase("PS Posting")) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					LinkedList setFillGrid = new LinkedList();
					setFillGrid.add(jObjtemp.get("strPSPCode").toString());
					setFillGrid.add(jObjtemp.get("strStkInCode").toString());
					setFillGrid.add(jObjtemp.get("strStkOutCode").toString());
					setFillGrid.add(jObjtemp.get("strBillNo").toString());
					setFillGrid.add(Double.parseDouble(jObjtemp.get("dblStkInAmt").toString()));
					setFillGrid.add(Double.parseDouble(jObjtemp.get("dblSaleAmt").toString()));

					listFillGrid.add(setFillGrid);
				}
			} else {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					LinkedList setFillGrid = new LinkedList();
					setFillGrid.add(jObjtemp.get("strStkCode").toString());
					setFillGrid.add(jObjtemp.get("dteStkDate").toString());
					setFillGrid.add(jObjtemp.get("strReasonName").toString());
					setFillGrid.add(jObjtemp.get("strUserCreated").toString());
					listFillGrid.add(setFillGrid);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listFillGrid;

	}

	@RequestMapping(value = "/fillStockDtlData", method = RequestMethod.GET)
	public @ResponseBody JSONArray fillStockDtlData(HttpServletRequest req, @RequestParam("code") String code, @RequestParam("transType") String transType) {

		JSONArray jarr = null;
		try {
			String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/webPosVoidStockController/fillStockDtlData";
			JSONObject objRows = new JSONObject();

			objRows.put("transType", transType);
			objRows.put("code", code);

			JSONObject jObj = objGlobal.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			jarr = (JSONArray) jObj.get("stkDtl");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jarr;

	}

	@RequestMapping(value = "/voidStock", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsVoidStockBean objBean, BindingResult result, HttpServletRequest req, @RequestParam("stkCode") String stkCode) {

		try {
			String userCode = req.getSession().getAttribute("usercode").toString();
			String posURL = "";
			JSONObject jObjVoidStock = new JSONObject();
			String transType = objBean.getStrTransType();
			jObjVoidStock.put("transType", transType);
			jObjVoidStock.put("stockCode", stkCode);
			jObjVoidStock.put("userCode", userCode);
			if (transType.equalsIgnoreCase("Stock In")) {
				jObjVoidStock.put("voidResaonCode", objBean.getStrVoidStkInReasonCode());
				posURL = "http://localhost:8080/prjSanguineWebService/webPosVoidStockController/funVoidStockIn";
			}
			if (transType.equalsIgnoreCase("Stock Out")) {
				jObjVoidStock.put("voidResaonCode", objBean.getStrVoidStkOutReasonCode());
				posURL = "http://localhost:8080/prjSanguineWebService/webPosVoidStockController/funVoidStockOut";
			}
			if (transType.equalsIgnoreCase("PS Posting")) {
				jObjVoidStock.put("voidResaonCode", objBean.getStrVoidPSPReasonCode());
				posURL = "http://localhost:8080/prjSanguineWebService/webPosVoidStockController/funVoidPSPosting";
			}
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjVoidStock.toString().getBytes());
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

			return new ModelAndView("redirect://frmPOSVoidStock.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

}
