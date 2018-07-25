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
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSSettlementMasterBean;
import com.sanguine.webpos.bean.clsPOSWaiterMasterBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSSettlementMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSSettlement", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSSettlementMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("frmPOSSettlement");

	}

	@RequestMapping(value = "/savePOSSettlementMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSSettlementMasterBean objBean, BindingResult result, HttpServletRequest req) {

		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjSettlementMaster = new JSONObject();
			jObjSettlementMaster.put("SettlementCode", objBean.getStrSettelmentCode());
			jObjSettlementMaster.put("SettlementName", objBean.getStrSettelmentDesc());
			jObjSettlementMaster.put("SettlementType", objBean.getStrSettelmentType());
			jObjSettlementMaster.put("Billing", objGlobal.funIfNull(objBean.getStrBilling(), "N", "Y"));
			jObjSettlementMaster.put("BillPrintOnSettlement", objGlobal.funIfNull(objBean.getStrBillPrintOnSettlement(), "N", "Y"));
			jObjSettlementMaster.put("AdvanceReceipt", objGlobal.funIfNull(objBean.getStrAdvanceReceipt(), "N", "Y"));
			jObjSettlementMaster.put("Applicable", objBean.getStrApplicable());
			jObjSettlementMaster.put("ConversionRatio", objBean.getDblConversionRatio());
			jObjSettlementMaster.put("AccountCode", objBean.getStrAccountCode());
			jObjSettlementMaster.put("User", webStockUserCode);
			jObjSettlementMaster.put("ClientCode", clientCode);
			jObjSettlementMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjSettlementMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "APOSMastersIntegration/funSaveSettlementMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjSettlementMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSSettlement.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSSettlementMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSSettlementMasterBean funSetSearchFields(@RequestParam("settlementCode") String settlementCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSSettlementMasterBean objPOSSettlementMaster = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetSettlementMasterData" + "?settlementCode=" + settlementCode + "&clientCode=" + clientCode;
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
			objPOSSettlementMaster = new clsPOSSettlementMasterBean();
			objPOSSettlementMaster.setStrSettelmentCode((String) jObjSearchDetails.get("strSettelmentCode"));
			objPOSSettlementMaster.setStrSettelmentDesc((String) jObjSearchDetails.get("strSettelmentDesc"));
			objPOSSettlementMaster.setStrSettelmentType((String) jObjSearchDetails.get("strSettelmentType"));
			objPOSSettlementMaster.setStrApplicable((String) jObjSearchDetails.get("strApplicable"));
			objPOSSettlementMaster.setStrBilling((String) jObjSearchDetails.get("strBilling"));
			objPOSSettlementMaster.setStrAdvanceReceipt((String) jObjSearchDetails.get("strAdvanceReceipt"));
			objPOSSettlementMaster.setStrBillPrintOnSettlement((String) jObjSearchDetails.get("strBillPrintOnSettlement"));
			objPOSSettlementMaster.setDblConversionRatio((long) jObjSearchDetails.get("dblConvertionRatio"));
			objPOSSettlementMaster.setStrAccountCode((String) jObjSearchDetails.get("strAccountCode"));

		}

		if (null == objPOSSettlementMaster) {
			objPOSSettlementMaster = new clsPOSSettlementMasterBean();
			objPOSSettlementMaster.setStrSettelmentCode("Invalid Code");
		}

		return objPOSSettlementMaster;
	}

	// Load All Settlement for table
	@RequestMapping(value = "/LoadSettlmentData", method = RequestMethod.GET)
	public @ResponseBody List<clsSettlementDetailsBean> funLoadSettlmentData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONArray jArrSettlementList = null;
		List<clsSettlementDetailsBean> listSettleData = new ArrayList<clsSettlementDetailsBean>();
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetSettlementDtl" + "?clientCode=" + clientCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("SettlementDtl");

		if (null != jArrSettlementList) {
			for (int cnt = 0; cnt < jArrSettlementList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(cnt);
				clsSettlementDetailsBean objSettlementDtl = new clsSettlementDetailsBean();
				objSettlementDtl.setStrSettlementCode((String) jobj.get("SettlementCode"));
				objSettlementDtl.setStrSettlementDesc((String) jobj.get("SettlementDesc"));
				objSettlementDtl.setStrApplicableYN((Boolean) jobj.get("ApplicableYN"));

				listSettleData.add(objSettlementDtl);
			}
		}
		return listSettleData;
	}

	@RequestMapping(value = "/checkSettlementName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSSettlementMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

}
