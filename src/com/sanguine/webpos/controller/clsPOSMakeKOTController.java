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

import com.google.gson.Gson;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsBillItemDtlBean;
import com.sanguine.webpos.bean.clsMakeKotBillItemDtlBean;
import com.sanguine.webpos.bean.clsMoveKOTBean;
import com.sanguine.webpos.bean.clsPOSMakeKOTBean;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@Controller
public class clsPOSMakeKOTController {

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;
	@Autowired
	private clsGlobalFunctions objGlobal;

	String gGetWebserviceURL = (String) clsPOSGlobalFunctionsController.hmPOSSetupValues.get("GetWebserviceURL");
	String gOutletUID = (String) clsPOSGlobalFunctionsController.hmPOSSetupValues.get("OutletUID");;

	@RequestMapping(value = "/frmPOSRestaurantBill", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		clsPOSMakeKOTBean objMakeKOTBean = new clsPOSMakeKOTBean();

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posClientCode = request.getSession().getAttribute("posClientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String posDate = request.getSession().getAttribute("POSDate").toString().split(" ")[0];
		String userCode = request.getSession().getAttribute("usercode").toString();

		// get and set menu item pricing for direct biller in json
		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funLoadTableDtl" + "?clientCode=" + clientCode + "&posCode=" + posCode;

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);
		JSONArray jsonArrForTableDtl = (JSONArray) jObj.get("tableDtl");

		objMakeKOTBean.setJsonArrForTableDtl(jsonArrForTableDtl);

		posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetButttonList" + "?transName=MakeKOT&posCode=" + posCode + "&posClientCode=" + posClientCode;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);

		JSONArray jsonArrForButtonList = (JSONArray) jObj.get("buttonList");

		objMakeKOTBean.setJsonArrForButtonList(jsonArrForButtonList);

		posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetWaiterList" + "?posCode=" + posCode;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);

		JSONArray jsonArrForWaiterDtl = (JSONArray) jObj.get("waiterList");

		objMakeKOTBean.setJsonArrForWaiterDtl(jsonArrForWaiterDtl);

		posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetMenuHeads" + "?posCode=" + posCode + "&userCode=" + userCode;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);

		JSONArray jsonArrForMenuHeads = (JSONArray) jObj.get("MenuHeads");

		objMakeKOTBean.setJsonArrForMenuHeads(jsonArrForMenuHeads);

		/*
		 * jObj =
		 * objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController
		 * .POSWSURL+"/APOSIntegration/funGetPOSDate" + "?POSCode="+posCode);
		 * String posDate=jObj.get("POSDate").toString().split(" ")[0];
		 */
		jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funPopularItem?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);

		JSONArray jsonArrForPopularItems = (JSONArray) jObj.get("PopularItems");

		objMakeKOTBean.setJsonArrForPopularItems(jsonArrForPopularItems);

		jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGetItemPricingDtl?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);

		JSONArray jsonArrForMenuItemPricing = (JSONArray) jObj.get("MenuItemPricingDtl");
		objMakeKOTBean.setJsonArrForMenuItemPricing(jsonArrForMenuItemPricing);

		model.put("gCheckDebitCardBalanceOnTrans", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("strCheckDebitCardBalOnTransactions"));
		model.put("gCMSIntegrationYN", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("CMSIntegrationYN"));
		model.put("gCMSMemberCodeForKOTJPOS", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("CMSMemberForKOTJPOS"));
		model.put("gCRMInterface", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("CRMInterface"));
		model.put("gGetWebserviceURL", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("GetWebserviceURL"));
		model.put("gCustAddressSelectionForBill", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("CustAddressSelectionForBill"));
		model.put("gOutletUID", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("OutletUID"));
		model.put("gPrintType", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("PrintType"));
		model.put("gSkipPax", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("SkipPax"));
		model.put("gSkipWaiter", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("SkipWaiter"));
		model.put("gSelectWaiterFromCardSwipe", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("SelectWaiterFromCardSwipe"));
		model.put("gMenuItemSortingOn", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("MenuItemSortingOn"));
		model.put("gMultiWaiterSelOnMakeKOT", clsPOSGlobalFunctionsController.hmPOSSetupValues.get("MultiWaiterSelectionOnMakeKOT"));

		return new ModelAndView("frmPOSRestaurantBill", "command", objMakeKOTBean);

	}

	@RequestMapping(value = "/funLoadModifiers", method = RequestMethod.GET)
	public @ResponseBody JSONObject funLoadModifiers(@RequestParam("itemCode") String itemCode, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funLoadModifiers?itemCode=" + itemCode);

		return jObj;
	}

	@RequestMapping(value = "/funFillTopModifierButtonList", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFillTopModifierButtonList(@RequestParam("itemCode") String itemCode, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funFillTopModifierButtonList?itemCode=" + itemCode);

		return jObj;
	}

	@RequestMapping(value = "/funCalculateTax", method = RequestMethod.POST)
	public @ResponseBody String funCalculateTax(@RequestParam("arrKOTItemDtlList") List<String> arrKOTItemDtlList, HttpServletRequest request) {
		String total = "";
		try {
			JSONArray jArr = new JSONArray();
			for (int i = 0; i < arrKOTItemDtlList.size(); i++) {
				jArr.add(arrKOTItemDtlList.get(i));
			}
			JSONObject obj = new JSONObject();

			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String posCode = request.getSession().getAttribute("loginPOS").toString();
			JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
			String posDate = jObj.get("POSDate").toString().split(" ")[0];

			obj.put("jArr", jArr);
			obj.put("posCode", posCode);
			obj.put("clientCode", clientCode);
			obj.put("posDate", posDate);

			jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funCalculateTax", obj);

			total = (String) jObj.get("Total");

			return total;
		} catch (Exception ex) {
			ex.printStackTrace();
			return total;
		}
	}

	@RequestMapping(value = "/funChekReservation", method = RequestMethod.GET)
	public @ResponseBody JSONObject funChekReservation(@RequestParam("strTableNo") String strTableNo, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funChekReservation?strTableNo=" + strTableNo);

		return jObj;
	}

	@RequestMapping(value = "/funFillMapWithHappyHourItems", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFillMapWithHappyHourItems(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String posDate = request.getSession().getAttribute("POSDate").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funFillMapWithHappyHourItems?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);

		return jObj;
	}

	@RequestMapping(value = "/funChekCustomerDtl", method = RequestMethod.GET)
	public @ResponseBody JSONObject funChekCustomerDtl(@RequestParam("strTableNo") String strTableNo, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funChekCustomerDtl?strTableNo=" + strTableNo + "&posCode=" + posCode + "&clientCode=" + clientCode);

		return jObj;
	}

	@RequestMapping(value = "/funChekCMSCustomerDtl", method = RequestMethod.GET)
	public @ResponseBody JSONObject funChekCMSCustomerDtl(@RequestParam("strTableNo") String strTableNo, HttpServletRequest request) {
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funChekCMSCustomerDtl?strTableNo=" + strTableNo);

		return jObj;
	}

	@RequestMapping(value = "/funChekCardDtl", method = RequestMethod.GET)
	public @ResponseBody JSONObject funChekCardDtl(@RequestParam("strTableNo") String strTableNo, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funChekCardDtl?strTableNo=" + strTableNo);

		return jObj;
	}

	@RequestMapping(value = "/funCheckMemeberBalance", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckMemeberBalance(@RequestParam("strCustomerCode") String strCustomerCode, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funCheckMemeberBalance?strCustomerCode=" + strCustomerCode);

		return jObj;
	}

	@RequestMapping(value = "/funFillOldKOTItems", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFillOldKOTItems(@RequestParam("strTableNo") String strTableNo, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
		String posDate = jObj.get("POSDate").toString().split(" ")[0];
		jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funFillOldKOTItems?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate + "&strTableNo=" + strTableNo);

		return jObj;
	}

	@RequestMapping(value = "/funCheckDebitCardString", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckDebitCardString(@RequestParam("debitCardNo") String debitCardNo, HttpServletRequest request) {
		String cardString = funGetSingleTrackData(debitCardNo);
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funCheckDebitCardString?debitCardNo=" + cardString + "&posCode=" + posCode + "&clientCode=" + clientCode);

		return jObj;
	}

	@RequestMapping(value = "/funLoadPopularItems", method = RequestMethod.GET)
	public @ResponseBody JSONObject funPopularItem(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
		String posDate = jObj.get("POSDate").toString().split(" ")[0];
		jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funPopularItem?posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);

		return jObj;
	}

	@RequestMapping(value = "/funFillTopButtonList", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFillTopButtonList(@RequestParam("menuHeadCode") String menuHeadCode, HttpServletRequest request) {

		String posCode = request.getSession().getAttribute("loginPOS").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
		String posDate = jObj.get("POSDate").toString().split(" ")[0];

		jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funFillTopButtonList?menuHeadCode=" + menuHeadCode + "&posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate);

		return jObj;
	}

	@RequestMapping(value = "/funCheckHomeDelivery", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckHomeDelivery(@RequestParam("strTableNo") String strTableNo, HttpServletRequest request) {

		String posCode = request.getSession().getAttribute("loginPOS").toString();
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funCheckHomeDelivery?posCode=" + posCode + "&strTableNo=" + strTableNo);

		return jObj;
	}

	@RequestMapping(value = "/funCheckCustomer", method = RequestMethod.GET)
	public @ResponseBody JSONObject fuCkeckCustomer(@RequestParam("strMobNo") String strMobNo, HttpServletRequest request) {
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funCheckCustomer?strMobNo=" + strMobNo);

		return jObj;
	}

	@RequestMapping(value = "/funCheckKOTSave", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckKOTSave(@RequestParam("strKOTNo") String strKOTNo, HttpServletRequest request) {
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funCheckKOTSave?strKOTNo=" + strKOTNo);

		return jObj;
	}

	@RequestMapping(value = "/funFillitemsSubMenuWise", method = RequestMethod.GET)
	public @ResponseBody JSONObject funFillitemsSubMenuWise(HttpServletRequest req) {
		String strMenuCode = req.getParameter("strMenuCode");
		String flag = req.getParameter("flag");
		String selectedButtonCode = req.getParameter("selectedButtonCode");

		String posCode = req.getSession().getAttribute("loginPOS").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
		String posDate = jObj.get("POSDate").toString().split(" ")[0];

		jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funFillitemsSubMenuWise?strMenuCode=" + strMenuCode + "&posCode=" + posCode + "&clientCode=" + clientCode + "&posDate=" + posDate + "&flag=" + flag + "&selectedButtonCode=" + selectedButtonCode);

		return jObj;
	}

	@RequestMapping(value = "/funGenerateKOTNo", method = RequestMethod.GET)
	public @ResponseBody JSONObject funGenerateKOTNo(HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funGenerateKOTNo");

		return jObj;
	}

	private String funGetSingleTrackData(String cardString) {
		String cardNo = "";

		if (cardString.contains("?") || cardString.contains(";")) {
			if (cardString.length() > 0) {
				StringBuilder sb = new StringBuilder(cardString);
				int percIndex = sb.indexOf("%");
				String allTracks = "";
				if (sb.toString().contains("?")) {
					allTracks = sb.substring(percIndex, sb.lastIndexOf("?") + 1);
				} else {
					allTracks = sb.toString();
				}
				String[] arrText = allTracks.split(";");
				String track1 = "", track2 = "", track3 = "";

				if (arrText.length > 0) {
					if (sb.toString().contains("?")) {
						track1 = arrText[0].substring(1, arrText[0].indexOf("?")).replaceAll("%", "");
						if (arrText.length > 1) {
							track2 = arrText[1].substring(1, arrText[1].indexOf("?")).replaceAll("%", "");
						}
						if (arrText.length > 2) {
							track3 = arrText[2].substring(1, arrText[2].indexOf("?")).replaceAll("%", "");
						}
					} else {
						track1 = arrText[0].replaceAll("%", "");
						track2 = arrText[1].replaceAll("%", "");
						track3 = arrText[2].replaceAll("%", "");
					}
				}

				if (!track1.isEmpty()) {
					cardNo = track1;
				} else if (!track2.isEmpty()) {
					cardNo = track2;
				} else if (!track3.isEmpty()) {
					cardNo = track2;
				}

			}
		} else {
			cardNo = cardString;
		}

		return cardNo;
	}

	// on direct biller action performed
	@RequestMapping(value = "/saveKOT", method = RequestMethod.POST)
	public ModelAndView funSaveKOT(@ModelAttribute("command") @Valid clsPOSMakeKOTBean objBean, BindingResult result, HttpServletRequest req, @RequestParam("ncKot") String strNCKotYN, @RequestParam("takeAway") String strTakeAwayYesNo, @RequestParam("globalDebitCardNo") String globalDebitCardNo, @RequestParam("cmsMemCode") String cmsMemCode, @RequestParam("cmsMemName") String cmsMemName,
			@RequestParam("reasonCode") String reasonCode, @RequestParam("homeDeliveryForTax") String homeDeliveryForTax, @RequestParam("arrListHomeDelDetails") List<String> arrListHomeDelDetails) {
		String posCode = req.getSession().getAttribute("loginPOS").toString();
		String webStockUserCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONObject obj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
		String posDate = obj.get("POSDate").toString().split(" ")[0];
		JSONObject jObjMakeKOT = new JSONObject();
		List<clsMakeKotBillItemDtlBean> listOfDirectBillerBillItemDtl = objBean.getListOfMakeKOTBillItemDtl();
		try {
			JSONArray jArr = new JSONArray();
			for (int i = 0; i < listOfDirectBillerBillItemDtl.size(); i++) {
				clsMakeKotBillItemDtlBean objBillItemDtlBean = listOfDirectBillerBillItemDtl.get(i);

				JSONObject jObj = new JSONObject();
				jObj.put("strItemName", objBillItemDtlBean.getStrItemName());
				jObj.put("strItemCode", objBillItemDtlBean.getStrItemCode());
				jObj.put("dblQuantity", objBillItemDtlBean.getDblQuantity());
				jObj.put("dblAmount", objBillItemDtlBean.getDblAmount());
				jObj.put("strSerialNo", objBillItemDtlBean.getStrSerialNo());

				jArr.add(jObj);

			}
			jObjMakeKOT.put("User", webStockUserCode);
			jObjMakeKOT.put("strClientCode", clientCode);
			jObjMakeKOT.put("billItemDtlList", jArr);
			jObjMakeKOT.put("intPaxNo", objBean.getIntPaxNo());
			jObjMakeKOT.put("KOTAmt", objBean.getTotal());
			jObjMakeKOT.put("strDeditCardBalance", objBean.getStrDeditCardBalance());
			jObjMakeKOT.put("strDeditCardNo", globalDebitCardNo);
			jObjMakeKOT.put("strKOTNo", objBean.getStrKOTNo());
			jObjMakeKOT.put("strTableNo", objBean.getStrTableNo());
			jObjMakeKOT.put("strWaiter", objBean.getStrWaiter());
			jObjMakeKOT.put("cmsMemberCode", cmsMemCode);
			jObjMakeKOT.put("strReasonCode", reasonCode);
			jObjMakeKOT.put("cmsMemberName", cmsMemName);
			String homeDelivery = "No", strDelBoyCode = "";
			if (homeDeliveryForTax.equalsIgnoreCase("Y")) {
				if (arrListHomeDelDetails.get(3).toString().equals("HomeDelivery")) {
					homeDelivery = "Yes";
				}
			}
			if (homeDelivery == "Yes") {
				strDelBoyCode = arrListHomeDelDetails.get(4);
			}
			jObjMakeKOT.put("strDelBoyCode", strDelBoyCode);

			jObjMakeKOT.put("strHomeDelivery", homeDelivery);
			jObjMakeKOT.put("strNCKotYN", strNCKotYN);
			jObjMakeKOT.put("strPOSCode", posCode);
			jObjMakeKOT.put("strTakeAwayYesNo", strTakeAwayYesNo);
			jObjMakeKOT.put("posDate", posDate);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funSaveUpdateKOT";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjMakeKOT.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSRestaurantBill.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/funCallWebService", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCallWebService(@RequestParam("strMobNo") String strMobNo, HttpServletRequest request) {

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/clsMakeKOTController/funChekCRMCustomerDtl?strMobNo=" + strMobNo);
		JSONObject obj = new JSONObject();
		String strCustomerCode = jObj.get("strCustomerCode").toString();
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String getWebServiceURL = gGetWebserviceURL;
			getWebServiceURL += "" + strMobNo + "/outlet/" + gOutletUID + "/";
			HttpGet getRequest = new HttpGet(getWebServiceURL);
			HttpResponse response = httpClient.execute(getRequest);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output = "", op = "";

			while ((output = br.readLine()) != null) {
				op += output;
			}
			// System.out.println(op);
			JSONParser p = new JSONParser();
			Object objJSON = p.parse(op);
			obj = (JSONObject) objJSON;

			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			return obj;
		}
	}

}
