package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//import com.apos.model.clsPosSettlementDetailsModel;
//import com.apos.model.clsPosSettlementDetailsModel_ID;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSDebitCardMasterBean;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSTaxMasterBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

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
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class clsPOSDebitCardMasterController {
	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	// Open POSTaxMaster
	@RequestMapping(value = "/frmPOSDebitCardMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDebitCardMaster_1", "command", new clsPOSDebitCardMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDebitCardMaster", "command", new clsPOSDebitCardMasterBean());
		} else {
			return null;
		}

	}

	// Save or Update DebitCardMaster
	@RequestMapping(value = "/savePOSDebitCardMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSDebitCardMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String expiryDate = objBean.getDteExpiryDt();
			String[] dateList = expiryDate.split("-");
			String day = dateList[0];
			String month = dateList[1];
			String year = dateList[2];
			String dteExpiryDate = year + "-" + month + "-" + day;

			JSONObject jObjDebitCardMaster = new JSONObject();
			jObjDebitCardMaster.put("CardTypeCode", objBean.getStrCardTypeCode());
			jObjDebitCardMaster.put("strCardName", objBean.getStrCardName());
			jObjDebitCardMaster.put("strDebitOnCredit", objGlobal.funIfNull(objBean.getStrDebitOnCredit(), "N", "Y"));

			jObjDebitCardMaster.put("strRoomCard", objGlobal.funIfNull(objBean.getStrRoomCard(), "N", "Y"));
			jObjDebitCardMaster.put("strComplementary", objGlobal.funIfNull(objBean.getStrComplementary(), "N", "Y"));
			jObjDebitCardMaster.put("strAutoTopUp", objGlobal.funIfNull(objBean.getStrAutoTopUp(), "N", "Y"));
			jObjDebitCardMaster.put("strRedeemableCard", objGlobal.funIfNull(objBean.getStrRedeemableCard(), "N", "Y"));
			jObjDebitCardMaster.put("strCardInUse", objGlobal.funIfNull(objBean.getStrCardInUse(), "N", "Y"));
			jObjDebitCardMaster.put("strEntryCharge", objGlobal.funIfNull(objBean.getStrEntryCharge(), "N", "Y"));
			jObjDebitCardMaster.put("strCoverCharge", objGlobal.funIfNull(objBean.getStrCoverCharge(), "N", "Y"));
			jObjDebitCardMaster.put("strDiplomate", objGlobal.funIfNull(objBean.getStrDiplomate(), "N", "Y"));
			jObjDebitCardMaster.put("strAllowTopUp", objGlobal.funIfNull(objBean.getStrAllowTopUp(), "N", "Y"));
			jObjDebitCardMaster.put("strExValOnTopUp", objGlobal.funIfNull(objBean.getStrExValOnTopUp(), "N", "Y"));
			jObjDebitCardMaster.put("strCustomerCompulsory", objGlobal.funIfNull(objBean.getStrCustomerCompulsory(), "N", "Y"));
			jObjDebitCardMaster.put("strSetExpiryDt", objGlobal.funIfNull(objBean.getStrSetExpiryDt(), "N", "Y"));

			jObjDebitCardMaster.put("dteExpiryDt", dteExpiryDate);

			jObjDebitCardMaster.put("strCurrentFinacialYr", objGlobal.funIfNull(objBean.getStrCurrentFinacialYr(), "N", "Y"));
			jObjDebitCardMaster.put("strCashCard", objGlobal.funIfNull(objBean.getStrCashCard(), "N", "Y"));

			jObjDebitCardMaster.put("strValidityDays", objBean.getStrValidityDays());

			jObjDebitCardMaster.put("strAuthorizeMemberCard", objGlobal.funIfNull(objBean.getStrAuthorizeMemberCard(), "N", "Y"));

			jObjDebitCardMaster.put("strCardValueFixed", objBean.getStrCardValueFixed());
			jObjDebitCardMaster.put("dblDepositAmt", objBean.getStrCardValueFixed());
			jObjDebitCardMaster.put("dblMaxVal", objBean.getStrCardValueFixed());
			jObjDebitCardMaster.put("dblMinVal", objBean.getStrCardValueFixed());
			jObjDebitCardMaster.put("dblMaxRefundAmt", objBean.getStrCardValueFixed());
			jObjDebitCardMaster.put("dblMinCharge", objBean.getStrCardValueFixed());
			jObjDebitCardMaster.put("strRedemptionLimitType", objBean.getStrRedemptionLimitType());
			jObjDebitCardMaster.put("strRedemptionLimitValue", objBean.getStrRedemptionLimitValue());

			jObjDebitCardMaster.put("User", webStockUserCode);
			jObjDebitCardMaster.put("ClientCode", clientCode);
			jObjDebitCardMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjDebitCardMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			// Settlement Data

			List<clsSettlementDetailsBean> list = objBean.getListSettlementDtl();
			JSONArray jArrList = new JSONArray();
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

			jObjDebitCardMaster.put("SettlementDetails", jArrList);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveDebitCardMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjDebitCardMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSDebitCardMaster.html?saddr=" + urlHits);

		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadPOSDebitCardMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSDebitCardMasterBean funSetSearchFields(@RequestParam("cardTypeCode") String cardTypeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSDebitCardMasterBean objPOSDebitCardMaster = null;
		List<clsSettlementDetailsBean> listSettleData = new ArrayList<clsSettlementDetailsBean>();

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetDebitCardMasterData" + "?cardTypeCode=" + cardTypeCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		// JSONArray jObjSearchDetails=(JSONArray)
		// jObjSearchDetails.get("POSDebitCardMaster");
		if (null != jObjSearchDetails) {
			objPOSDebitCardMaster = new clsPOSDebitCardMasterBean();
			objPOSDebitCardMaster.setStrCardTypeCode((String) jObjSearchDetails.get("strCardTypeCode"));
			objPOSDebitCardMaster.setStrCardName((String) jObjSearchDetails.get("strCardName"));
			objPOSDebitCardMaster.setStrDebitOnCredit((String) jObjSearchDetails.get("strDebitOnCredit"));
			objPOSDebitCardMaster.setStrRoomCard((String) jObjSearchDetails.get("strRoomCard"));
			objPOSDebitCardMaster.setStrComplementary((String) jObjSearchDetails.get("strComplementary"));
			objPOSDebitCardMaster.setStrAutoTopUp((String) jObjSearchDetails.get("strAutoTopUp"));
			objPOSDebitCardMaster.setStrRedeemableCard((String) jObjSearchDetails.get("strRedeemableCard"));
			objPOSDebitCardMaster.setStrCardInUse((String) jObjSearchDetails.get("strCardInUse"));
			objPOSDebitCardMaster.setStrEntryCharge((String) jObjSearchDetails.get("strEntryCharge"));
			objPOSDebitCardMaster.setStrCoverCharge((String) jObjSearchDetails.get("strCoverCharge"));
			objPOSDebitCardMaster.setStrDiplomate((String) jObjSearchDetails.get("strDiplomate"));
			objPOSDebitCardMaster.setStrAllowTopUp((String) jObjSearchDetails.get("strAllowTopUp"));
			objPOSDebitCardMaster.setStrExValOnTopUp((String) jObjSearchDetails.get("strExValOnTopUp"));
			objPOSDebitCardMaster.setStrCustomerCompulsory((String) jObjSearchDetails.get("strCustomerCompulsory"));
			// objPOSDebitCardMaster.setStrSetExpiryDt((String)
			// jObjSearchDetails.get(""));
			objPOSDebitCardMaster.setDteExpiryDt((String) jObjSearchDetails.get("dteExpiryDt"));
			objPOSDebitCardMaster.setStrCurrentFinacialYr((String) jObjSearchDetails.get("strCurrentFinacialYr"));
			objPOSDebitCardMaster.setStrCashCard((String) jObjSearchDetails.get("strCashCard"));
			objPOSDebitCardMaster.setStrValidityDays((Long) jObjSearchDetails.get("intValidityDays"));
			objPOSDebitCardMaster.setStrAuthorizeMemberCard((String) jObjSearchDetails.get("strAuthorizeMemberCard"));
			objPOSDebitCardMaster.setStrRedemptionLimitType((String) jObjSearchDetails.get(""));
			objPOSDebitCardMaster.setStrRedemptionLimitValue((long) jObjSearchDetails.get("dblRedemptionLimitValue"));
			objPOSDebitCardMaster.setStrRedemptionLimitType((String) jObjSearchDetails.get("strRedemptionLimitType"));

			// POS Settlement Detailss
			JSONArray jArrSettlementList = (JSONArray) jObjSearchDetails.get("SettleDtl");
			if (null != jArrSettlementList) {
				for (int cnt = 0; cnt < jArrSettlementList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrSettlementList.get(cnt);
					clsSettlementDetailsBean objSettlementDtl = new clsSettlementDetailsBean();
					objSettlementDtl.setStrSettlementCode((String) jobj.get("strSettlementCode"));
					// objSettlementDtl.setStrSettlementDesc((String)jobj.get("SettlementDesc"));
					objSettlementDtl.setStrApplicableYN((Boolean) jobj.get("strApplicable"));

					listSettleData.add(objSettlementDtl);
				}
			}
			objPOSDebitCardMaster.setListSettlementDtl(listSettleData);

		}

		if (null == objPOSDebitCardMaster) {
			objPOSDebitCardMaster = new clsPOSDebitCardMasterBean();
			objPOSDebitCardMaster.setStrCardTypeCode("Invalid Code");
		}

		return objPOSDebitCardMaster;
	}

	@RequestMapping(value = "/loadDebitCardTypeSettlmentDtlData", method = RequestMethod.GET)
	public @ResponseBody List<clsSettlementDetailsBean> funLoadSettlmentData(@RequestParam("cardTypeCode") String cardTypeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONArray jArrSettlementList = null;
		List<clsSettlementDetailsBean> listSettleData = new ArrayList<clsSettlementDetailsBean>();
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetDebitCardSettlementDtl" + "?clientCode=" + clientCode + "&cardTypeCode=" + cardTypeCode;

		System.out.println(posUrl);
		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		jArrSettlementList = (JSONArray) jObjSettlementData.get("SettlementDtl");

		if (null != jArrSettlementList) {
			for (int cnt = 0; cnt < jArrSettlementList.size(); cnt++) {
				JSONObject jobj = (JSONObject) jArrSettlementList.get(cnt);
				clsSettlementDetailsBean objSettlementDtl = new clsSettlementDetailsBean();
				objSettlementDtl.setStrSettlementCode((String) jobj.get("SettlementCode"));
				objSettlementDtl.setStrSettlementDesc((String) jobj.get("SettlementDesc"));
				objSettlementDtl.setStrApplicableYN(Boolean.valueOf(String.valueOf(jobj.get("ApplicableYN"))));

				listSettleData.add(objSettlementDtl);
			}
		}
		return listSettleData;
	}

	@RequestMapping(value = "/checkCardName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckFactoryName(@RequestParam("strCardName") String cardName, @RequestParam("strCardTypeCode") String cardCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(cardName, cardCode, clientCode, "POSDebitCardTypeMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

}