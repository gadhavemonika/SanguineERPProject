package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSTableMasterBean;
import com.sanguine.webpos.bean.clsReorderTimeBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsPOSMasterController {
	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPosMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		List<String> printerList = new ArrayList<String>();

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPrinterList");

		if (null != jObj.get("printerList"))
			printerList = (ArrayList) jObj.get("printerList");

		model.put("printerList", printerList);

		return new ModelAndView("frmPOSMaster");

	}

	@RequestMapping(value = "/savePOSMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSMasterBean objBean, BindingResult result, HttpServletRequest req) {

		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjPOSMaster = new JSONObject();
			jObjPOSMaster.put("PosCode", objBean.getStrPosCode());
			jObjPOSMaster.put("PosName", objBean.getStrPosName());
			jObjPOSMaster.put("PosType", objBean.getStrPosType());

			jObjPOSMaster.put("DebitCardTransactionYN", objBean.getStrDebitCardTransactionYN());
			jObjPOSMaster.put("PropertyPOSCode", objBean.getStrPropertyPOSCode());

			jObjPOSMaster.put("CounterWiseBilling", objGlobal.funIfNull(objBean.getStrCounterWiseBilling(), "N", "Y"));

			jObjPOSMaster.put("DelayedSettlementForDB", objGlobal.funIfNull(objBean.getStrDelayedSettlementForDB(), "N", "Y"));
			jObjPOSMaster.put("BillPrinterPort", objBean.getStrBillPrinterPort());
			jObjPOSMaster.put("AdvReceiptPrinterPort", objBean.getStrAdvReceiptPrinterPort());
			jObjPOSMaster.put("OperationalYN", objGlobal.funIfNull(objBean.getStrOperationalYN(), "N", "Y"));

			jObjPOSMaster.put("VatNo", objBean.getStrVatNo());
			jObjPOSMaster.put("PrintVatNo", objGlobal.funIfNull(objBean.getStrPrintVatNo(), "N", "Y"));
			jObjPOSMaster.put("ServiceTaxNo", objBean.getStrServiceTaxNo());
			jObjPOSMaster.put("PrintServiceTaxNo", objGlobal.funIfNull(objBean.getStrPrintServiceTaxNo(), "N", "Y"));

			jObjPOSMaster.put("EnableShift", 'N');
			jObjPOSMaster.put("RoundOff", objBean.getStrRoundOff());

			jObjPOSMaster.put("Tip", objBean.getStrTip());

			jObjPOSMaster.put("Discount", objBean.getStrDiscount());
			jObjPOSMaster.put("WSLocationCode", objBean.getStrWSLocationCode());
			jObjPOSMaster.put("ExciseLicenceCode", objBean.getStrExciseLicenceCode());

			jObjPOSMaster.put("User", webStockUserCode);
			jObjPOSMaster.put("ClientCode", clientCode);
			jObjPOSMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjPOSMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			// Settlement Data

			List<clsSettlementDetailsBean> list = objBean.getListSettlementDtl();
			JSONArray jArrList = new JSONArray();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					clsSettlementDetailsBean obj = new clsSettlementDetailsBean();
					obj = (clsSettlementDetailsBean) list.get(i);
					if (obj.getStrApplicableYN() != null) {
						JSONObject jObjData = new JSONObject();

						jObjData.put("SettlementCode", obj.getStrSettlementCode());
						jObjData.put("SettlementDesc", obj.getStrSettlementDesc());

						jArrList.add(jObjData);
					}

				}
			}
			jObjPOSMaster.put("SettlementDetails", jArrList);

			// Reorder Time

			List<clsReorderTimeBean> reorderTimeList = objBean.getListReorderTime();
			JSONArray jArrTimeList = new JSONArray();
			if (null != reorderTimeList) {
				for (int i = 0; i < reorderTimeList.size(); i++) {
					clsReorderTimeBean obj = new clsReorderTimeBean();
					obj = (clsReorderTimeBean) reorderTimeList.get(i);

					JSONObject jObjData = new JSONObject();

					jObjData.put("FromTime", obj.getTmeFromTime());
					jObjData.put("ToTime", obj.getTmeToTime());

					jArrTimeList.add(jObjData);

				}
			}

			jObjPOSMaster.put("ReorderTime", jArrTimeList);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funSavePOSMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjPOSMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPosMaster.html");

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSMasterBean funSetSearchFields(@RequestParam("posCode") String posCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSMasterBean objPOSMaster = null;
		List<clsSettlementDetailsBean> listSettleData = new ArrayList<clsSettlementDetailsBean>();
		List<clsReorderTimeBean> listReorderTime = new ArrayList<clsReorderTimeBean>();
		JSONObject jObjSearchDetails = new JSONObject();

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funGetPOSMasterData" + "?posCode=" + posCode + "&clientCode=" + clientCode;
		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		if (null != jObjSearchDetails) {
			objPOSMaster = new clsPOSMasterBean();
			objPOSMaster.setStrPosCode((String) jObjSearchDetails.get("strPosCode"));
			objPOSMaster.setStrPosName((String) jObjSearchDetails.get("strPosName"));
			objPOSMaster.setStrPosType((String) jObjSearchDetails.get("strPosType"));
			objPOSMaster.setStrDebitCardTransactionYN((String) jObjSearchDetails.get("strDebitCardTransactionYN"));
			objPOSMaster.setStrPropertyPOSCode((String) jObjSearchDetails.get("strPropertyPOSCode"));
			objPOSMaster.setStrCounterWiseBilling((String) jObjSearchDetails.get("strCounterWiseBilling"));
			objPOSMaster.setStrDelayedSettlementForDB((String) jObjSearchDetails.get("strDelayedSettlementForDB"));
			objPOSMaster.setStrBillPrinterPort((String) jObjSearchDetails.get("strBillPrinterPort"));
			objPOSMaster.setStrAdvReceiptPrinterPort((String) jObjSearchDetails.get("strAdvReceiptPrinterPort"));
			objPOSMaster.setStrOperationalYN((String) jObjSearchDetails.get("strOperationalYN"));
			objPOSMaster.setStrVatNo((String) jObjSearchDetails.get("strVatNo"));
			objPOSMaster.setStrPrintVatNo((String) jObjSearchDetails.get("strPrintVatNo"));

			objPOSMaster.setStrServiceTaxNo((String) jObjSearchDetails.get("strServiceTaxNo"));
			objPOSMaster.setStrPrintServiceTaxNo((String) jObjSearchDetails.get("strPrintServiceTaxNo"));
			objPOSMaster.setStrRoundOff((String) jObjSearchDetails.get("strRoundOff"));
			objPOSMaster.setStrTip((String) jObjSearchDetails.get("strTip"));
			objPOSMaster.setStrDiscount((String) jObjSearchDetails.get("strDiscount"));
			objPOSMaster.setStrWSLocationCode((String) jObjSearchDetails.get("strWSLocationCode"));
			objPOSMaster.setStrExciseLicenceCode((String) jObjSearchDetails.get("strExciseLicenceCode"));

			// POS Settlement Details
			JSONArray jArrSettlementList = (JSONArray) jObjSearchDetails.get("SettleData");
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
			objPOSMaster.setListSettlementDtl(listSettleData);

			// ReorderTime
			JSONArray jArrReorderTimeList = (JSONArray) jObjSearchDetails.get("ReorderTimeData");
			if (null != jArrReorderTimeList) {
				for (int cnt = 0; cnt < jArrReorderTimeList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrReorderTimeList.get(cnt);
					clsReorderTimeBean objReorderTime = new clsReorderTimeBean();
					objReorderTime.setTmeFromTime((String) jobj.get("FromTime"));
					objReorderTime.setTmeToTime((String) jobj.get("ToTime"));

					listReorderTime.add(objReorderTime);
				}
			}
			objPOSMaster.setListReorderTime(listReorderTime);
		}

		if (null == objPOSMaster) {
			objPOSMaster = new clsPOSMasterBean();
			objPOSMaster.setStrPosCode("Invalid Code");
		}

		return objPOSMaster;
	}

	@RequestMapping(value = "/checkPOSName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckPOSName(@RequestParam("name") String name, @RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(name, code, clientCode, "POSMaster");
		if (count > 0)
			return false;
		else
			return true;

	}
}
