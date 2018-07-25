package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsCostCenterBean;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSRegisterDebitCardBean;

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
public class clsPOSRegisterDebitCardController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();

	// Open POSRegisterDebitCard
	@RequestMapping(value = "/frmPOSRegisterDebitCard", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		// ModelAndView ob=new ModelAndView("frmPOSRegisterDebitCard");
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List cardTypeCode = new ArrayList();
		List cardName = new ArrayList();

		JSONArray jObj;
		JSONArray jArryList;

		jObj = objPOSGlobal.funGetAllDebitCardForMaster(clientCode);

		jArryList = (JSONArray) jObj;

		Map mapCardType = new HashMap<>();
		if (null != jArryList) {
			for (int i = 0; i < jArryList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArryList.get(i);
				cardTypeCode.add(josnObjRet.get("strCardTypeCode"));
				cardName.add(josnObjRet.get("strCardName"));

				mapCardType.put(josnObjRet.get("strCardTypeCode"), josnObjRet.get("strCardName"));

			}
		}
		model.put("mapCardType", mapCardType);

		Map mapCardOperation = new HashMap<>();

		mapCardOperation.put("Register", "Register");
		mapCardOperation.put("Delist", "Delist");

		model.put("mapCardOperation", mapCardOperation);

		// List<Object> cardTypeList= new ArrayList<Object>();
		// cardTypeList.add("");
		//
		// JSONArray jArrList=new JSONArray();
		// jArrList =objPOSGlobal.funGetAllDebitCardForMaster(clientCode);
		// for(int i =0 ;i<jArrList.size();i++)
		// {
		// JSONObject josnObjRet = (JSONObject) jArrList.get(i);
		// cardTypeList.add(josnObjRet.get("strCardName"));
		//
		//
		// map.put(josnObjRet.get("strCardName"),
		// josnObjRet.get("strCardTypeCode"));
		// }
		//
		// model.put("cardTypeList",cardTypeList);

		// Map<String, String> cardName=
		// objPOSGlobal.funGetAllDebitCardForMaster(clientCode);
		// if(cardName.isEmpty())
		// {
		// cardName.put(0, "");
		// }
		// ob.addObject("listFinancialYear",cardName);
		//
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSRegisterDebitCard_1", "command", new clsPOSRegisterDebitCardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSRegisterDebitCard", "command", new clsPOSRegisterDebitCardBean());
		} else {
			return new ModelAndView("frmPOSRegisterDebitCard");
		}
	}

	@RequestMapping(value = "/savePOSRegisterDebitCardMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSRegisterDebitCardBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String cardTypeCode = "";
		String operation = "";
		String posURL = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String posCode = req.getSession().getAttribute("loginPOS").toString();

			JSONObject jObjRegisterDebitCardMaster = new JSONObject();

			String cardName = objBean.getStrCardTypeCode();

			// if(map.containsKey(cardName))
			// {
			// cardTypeCode=(String) map.get(cardName);
			//
			// }
			//
			cardTypeCode = objBean.getStrCardTypeCode();
			operation = objBean.getStrOperation();
			String cardNo = objBean.getStrCardNo();
			Double redeemAmt = objBean.getDblRedeemAmt();
			String strStatus = objBean.getStrStatus();
			String cardString = objBean.getStrCardString();
			String customerCode = objBean.getStrCustomerCode();

			jObjRegisterDebitCardMaster.put("CardTypeCode", cardTypeCode);
			jObjRegisterDebitCardMaster.put("CardNo", cardNo);
			jObjRegisterDebitCardMaster.put("RedeemAmt", redeemAmt);
			jObjRegisterDebitCardMaster.put("StrStatus", strStatus);
			jObjRegisterDebitCardMaster.put("CardString", cardString);
			jObjRegisterDebitCardMaster.put("CustomerName", customerCode);
			jObjRegisterDebitCardMaster.put("User", webStockUserCode);
			jObjRegisterDebitCardMaster.put("ClientCode", clientCode);
			jObjRegisterDebitCardMaster.put("POSCode", posCode);
			jObjRegisterDebitCardMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjRegisterDebitCardMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjRegisterDebitCardMaster.put("CardOperation", operation);

			JSONObject jObj;

			if (operation.equals("Register")) {
				jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funRegisterCard", jObjRegisterDebitCardMaster);

			} else {
				jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funDelistCard", jObjRegisterDebitCardMaster);

			}

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", " ");

			return new ModelAndView("redirect:/frmPOSRegisterDebitCard.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/checkRegisterCardName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckFactoryName(@RequestParam("strCardTypeCode") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(Name, "", clientCode, "POSRegisterDebitCardMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

	// @RequestMapping(value ="/checkCustomerName" ,method =RequestMethod.GET)
	// public @ResponseBody boolean
	// funCheckCustomerName(@RequestParam("strCustomerCode") String
	// Name,HttpServletRequest req)
	// {
	// String clientCode
	// =req.getSession().getAttribute("clientCode").toString();
	// int
	// count=objPOSGlobal.funCheckName(Name,"",clientCode,"POSRegisterDebitCardMasterForCustomer");
	// if(count>0)
	// return false;
	// else
	// return true;
	//
	// }
	//

	// @RequestMapping(value ="/checkCustomerName" ,method =RequestMethod.GET)
	// public @ResponseBody boolean
	// funCheckCustomerName(@RequestParam("strCustomerCode") String
	// Name,HttpServletRequest req)
	// {
	// String clientCode
	// =req.getSession().getAttribute("clientCode").toString();
	// int
	// count=objPOSGlobal.funCheckName(Name,clientCode,"POSRegisterDebitCardMasterForCustomer");
	// if(count>0)
	// return false;
	// else
	// return true;
	//
	// }
	//

	@RequestMapping(value = "/checkCardString", method = RequestMethod.GET)
	public @ResponseBody clsPOSRegisterDebitCardBean funcheckCardString(@RequestParam("cardString") String cardString, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funCheckCardString" + "?cardString=" + cardString + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		clsPOSRegisterDebitCardBean objBean = new clsPOSRegisterDebitCardBean();

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
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			JSONObject jObj = new JSONObject();
			jObj = (JSONObject) obj;

			String message = (String) jObj.get("message");
			String strCardNo = "", strCardString = "", strCardTypeCode = "", strCustomerCode = "", strStatus = "", strCustomerName = "";

			objBean.setStrCardNo("");

			if (message.equalsIgnoreCase("Card Already Register")) {
				strCardNo = (String) jObj.get("cardNo");
				// strCardString = (String) jObj.get("cardString");
				strCardTypeCode = (String) jObj.get("cardTypeCode");
				strCustomerCode = (String) jObj.get("customerCode");
				strStatus = (String) jObj.get("status");
				strCustomerName = (String) jObj.get("customerName");

				objBean.setStrCardNo(strCardNo);
				objBean.setStrCardString(strCardString);
				objBean.setStrCardTypeCode(strCardTypeCode);
				objBean.setStrCustomerCode(strCustomerCode);
				objBean.setStrCustomerName(strCustomerName);
				objBean.setStrStatus(strStatus);
			}

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objBean;
	}

}
