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
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsCheckInDetailsBean;
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsPOSReasonMasterBean;

@Controller
public class clsPOSReasonMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmPOSReasonMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSReasonMaster_1", "command", new clsPOSReasonMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSReasonMaster", "command", new clsPOSReasonMasterBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSReasonMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSReasonMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjReasonMaster = new JSONObject();
			jObjReasonMaster.put("ReasonCode", objBean.getStrReasonCode());
			jObjReasonMaster.put("ReasonName", objBean.getStrReasonName());
			jObjReasonMaster.put("TransferEntry", objBean.getStrTransferEntry());
			jObjReasonMaster.put("TransferType", objBean.getStrTransferType());
			jObjReasonMaster.put("StockIn", objGlobal.funIfNull(objBean.getStrStkIn(), "N", "Y"));
			jObjReasonMaster.put("StockOut", objGlobal.funIfNull(objBean.getStrStkOut(), "N", "Y"));
			jObjReasonMaster.put("VoidBill", objGlobal.funIfNull(objBean.getStrVoidBill(), "N", "Y"));
			jObjReasonMaster.put("ModifyBill", objGlobal.funIfNull(objBean.getStrModifyBill(), "N", "Y"));
			jObjReasonMaster.put("PSP", objGlobal.funIfNull(objBean.getStrPSP(), "N", "Y"));
			jObjReasonMaster.put("VoidKOT", objGlobal.funIfNull(objBean.getStrKot(), "N", "Y"));
			jObjReasonMaster.put("VoidStockIn", objGlobal.funIfNull(objBean.getStrVoidStkIn(), "N", "Y"));
			jObjReasonMaster.put("VoidStockOut", objGlobal.funIfNull(objBean.getStrVoidStkOut(), "N", "Y"));
			jObjReasonMaster.put("VoidAdvanceOrder", objGlobal.funIfNull(objBean.getStrVoidAdvOrder(), "N", "Y"));
			jObjReasonMaster.put("NCKOT", objGlobal.funIfNull(objBean.getStrNCKOT(), "N", "Y"));
			jObjReasonMaster.put("Complementary", objGlobal.funIfNull(objBean.getStrComplementary(), "N", "Y"));
			jObjReasonMaster.put("CashManagemnt", objGlobal.funIfNull(objBean.getStrCashMgmt(), "N", "Y"));
			jObjReasonMaster.put("Discount", objGlobal.funIfNull(objBean.getStrDiscount(), "N", "Y"));
			jObjReasonMaster.put("Reprint", objGlobal.funIfNull(objBean.getStrReprint(), "N", "Y"));
			jObjReasonMaster.put("UnsettleBill", objGlobal.funIfNull(objBean.getStrUnsettleBill(), "N", "Y"));
			jObjReasonMaster.put("User", webStockUserCode);
			jObjReasonMaster.put("ClientCode", clientCode);
			jObjReasonMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjReasonMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSaveReasonMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjReasonMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSReasonMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadPOSReasonMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSReasonMasterBean funSetSearchFields(@RequestParam("POSReasonCode") String reasonCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSReasonMasterBean objPOSReasonMaster = null;
		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadReasoneMasterData" + "?searchCode=" + reasonCode + "&clientCode=" + clientCode);

		if (null != jObjSearchDetails) {
			objPOSReasonMaster = new clsPOSReasonMasterBean();
			objPOSReasonMaster.setStrReasonCode(jObjSearchDetails.get("strReasonCode").toString());
			objPOSReasonMaster.setStrReasonName(jObjSearchDetails.get("strReasonName").toString());
			objPOSReasonMaster.setStrTransferEntry(jObjSearchDetails.get("strTransferEntry").toString());
			objPOSReasonMaster.setStrTransferType(jObjSearchDetails.get("strTransferType").toString());
			objPOSReasonMaster.setStrStkIn(jObjSearchDetails.get("strStkIn").toString());
			objPOSReasonMaster.setStrStkOut(jObjSearchDetails.get("strStkOut").toString());
			objPOSReasonMaster.setStrVoidBill(jObjSearchDetails.get("strVoidBill").toString());
			objPOSReasonMaster.setStrModifyBill(jObjSearchDetails.get("strModifyBill").toString());
			objPOSReasonMaster.setStrPSP(jObjSearchDetails.get("strPSP").toString());
			objPOSReasonMaster.setStrKot(jObjSearchDetails.get("strKot").toString());
			objPOSReasonMaster.setStrCashMgmt(jObjSearchDetails.get("strCashMgmt").toString());
			objPOSReasonMaster.setStrVoidStkIn(jObjSearchDetails.get("strVoidStkIn").toString());
			objPOSReasonMaster.setStrVoidStkOut(jObjSearchDetails.get("strVoidStkOut").toString());
			objPOSReasonMaster.setStrUnsettleBill(jObjSearchDetails.get("strUnsettleBill").toString());
			objPOSReasonMaster.setStrComplementary(jObjSearchDetails.get("strComplementary").toString());
			objPOSReasonMaster.setStrDiscount(jObjSearchDetails.get("strDiscount").toString());
			objPOSReasonMaster.setStrNCKOT(jObjSearchDetails.get("strNCKOT").toString());
			objPOSReasonMaster.setStrVoidAdvOrder(jObjSearchDetails.get("strVoidAdvOrder").toString());
			objPOSReasonMaster.setStrReprint(jObjSearchDetails.get("strReprint").toString());

		}
		return objPOSReasonMaster;
	}

}
