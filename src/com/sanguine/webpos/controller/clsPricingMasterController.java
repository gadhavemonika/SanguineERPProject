package com.sanguine.webpos.controller;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSGroupMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuItemMasterBean;
import com.sanguine.webpos.bean.clsPricingMasterBean;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Controller
public class clsPricingMasterController {

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;
	@Autowired
	private clsGlobalFunctions objGlobal;

	// Open PricingMaster
	@RequestMapping(value = "/frmPOSPrice", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		// function to get all POS list
		JSONArray jPOSArr = objPOSGlobalFunctionsController.funGetAllPOSForMaster(clientCode);
		Map mapPOSName = new HashMap<>();
		mapPOSName.put("All", "All");
		for (int cnt = 0; cnt < jPOSArr.size(); cnt++) {
			JSONObject jObj = (JSONObject) jPOSArr.get(cnt);
			mapPOSName.put(jObj.get("strPosCode").toString(), jObj.get("strPosName").toString());
		}
		model.put("mapPOSName", mapPOSName);

		// function to get all Menu Head list
		JSONArray jMenuHeadArr = objPOSGlobalFunctionsController.funGetAllMenuHeadForMaster(clientCode);
		Map mapMenuHeadName = new HashMap<>();
		mapMenuHeadName.put("", "");
		for (int cnt = 0; cnt < jMenuHeadArr.size(); cnt++) {
			JSONObject jObj = (JSONObject) jMenuHeadArr.get(cnt);
			mapMenuHeadName.put(jObj.get("strMenuCode").toString(), jObj.get("strMenuName").toString());
		}
		model.put("mapMenuHeadName", mapMenuHeadName);

		// function to get all Sub Menu Head list
		JSONArray jSubMenuHeadArr = objPOSGlobalFunctionsController.funGetAllSubMenuHeadForMaster(clientCode);
		Map mapSubMenuHeadName = new HashMap<>();
		mapSubMenuHeadName.put("", "");
		for (int cnt = 0; cnt < jSubMenuHeadArr.size(); cnt++) {
			JSONObject jObj = (JSONObject) jSubMenuHeadArr.get(cnt);
			mapSubMenuHeadName.put(jObj.get("strSubMenuHeadCode").toString(), jObj.get("strSubMenuheadName").toString());
		}
		model.put("mapSubMenuHeadName", mapSubMenuHeadName);
		// function to fill all item colours
		Map mapColours = new HashMap<>();
		mapColours.put("", "");
		mapColours.put("WHITE", "WHITE");
		mapColours.put("Black", "Black");
		mapColours.put("Green", "Green");
		mapColours.put("Red", "Red");
		mapColours.put("BLUE", "BLUE");
		mapColours.put("CYAN", "CYAN");
		mapColours.put("ORANGE", "ORANGE");
		mapColours.put("PINK", "PINK");
		mapColours.put("YELLOW", "YELLOW");

		model.put("mapColours", mapColours);

		// function to get all Areas
		JSONArray jAreaArr = objPOSGlobalFunctionsController.funGetAllAreaForMaster(clientCode);
		Map mapAreaName = new HashMap<>();
		for (int cnt = 0; cnt < jAreaArr.size(); cnt++) {
			JSONObject jObj = (JSONObject) jAreaArr.get(cnt);
			mapAreaName.put(jObj.get("strAreaCode").toString(), jObj.get("strAreaName").toString());
		}
		model.put("mapAreaName", mapAreaName);

		// function to get all Cost Centers
		JSONArray jCostCenterArr = objPOSGlobalFunctionsController.funGetAllCostCentersForMaster(clientCode);
		Map mapCostCenterName = new HashMap<>();
		for (int cnt = 0; cnt < jCostCenterArr.size(); cnt++) {
			JSONObject jObj = (JSONObject) jCostCenterArr.get(cnt);
			mapCostCenterName.put(jObj.get("strCostCenterCode").toString(), jObj.get("strCostCenterName").toString());
		}
		model.put("mapCostCenterName", mapCostCenterName);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSPrice_1", "command", new clsPricingMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSPrice", "command", new clsPricingMasterBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/loadDataToCreateItemPrice", method = RequestMethod.GET)
	public @ResponseBody clsPOSMenuItemMasterBean funLoadDataToCreateItemPrice(@RequestParam("itemCode") String itemCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSMenuItemMasterBean objMenuItemMasterBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetMenuItemMasterData" + "?itemCode=" + itemCode + "&clientCode=" + clientCode;
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
			// System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSMenuItemMaster");
		if (null != jArrSearchList) {
			objMenuItemMasterBean = new clsPOSMenuItemMasterBean();
			objMenuItemMasterBean.setStrItemCode((String) jArrSearchList.get(0));
			objMenuItemMasterBean.setStrItemName((String) jArrSearchList.get(1));
		}
		if (null == objMenuItemMasterBean) {
			objMenuItemMasterBean = new clsPOSMenuItemMasterBean();
			objMenuItemMasterBean.setStrItemCode("Invalid Code");
		}

		return objMenuItemMasterBean;
	}

	@RequestMapping(value = "/loadDataToUpdateItemPrice", method = RequestMethod.GET)
	public @ResponseBody clsPricingMasterBean funLoadDataToUpdateItemPrice(@RequestParam("longPricingId") String longPricingId, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPricingMasterBean objPricingMasterBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetMenuItemPricingMaster" + "?pricingId=" + longPricingId + "&clientCode=" + clientCode;
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
			// System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSMenuItemPricingMaster");
		if (null != jArrSearchList) {
			objPricingMasterBean = new clsPricingMasterBean();
			objPricingMasterBean.setStrItemCode(jArrSearchList.get(0).toString());
			objPricingMasterBean.setStrItemName(jArrSearchList.get(1).toString());
			objPricingMasterBean.setStrPosCode(jArrSearchList.get(2).toString());
			objPricingMasterBean.setStrMenuCode(jArrSearchList.get(3).toString());
			if (jArrSearchList.get(4).toString().equalsIgnoreCase("Y")) {
				objPricingMasterBean.setStrPopular(true);
			} else {
				objPricingMasterBean.setStrPopular(false);
			}
			objPricingMasterBean.setStrPriceMonday(Double.parseDouble(jArrSearchList.get(5).toString()));
			objPricingMasterBean.setStrPriceTuesday(Double.parseDouble(jArrSearchList.get(6).toString()));
			objPricingMasterBean.setStrPriceWednesday(Double.parseDouble(jArrSearchList.get(7).toString()));
			objPricingMasterBean.setStrPriceThursday(Double.parseDouble(jArrSearchList.get(8).toString()));
			objPricingMasterBean.setStrPriceFriday(Double.parseDouble(jArrSearchList.get(9).toString()));
			objPricingMasterBean.setStrPriceSaturday(Double.parseDouble(jArrSearchList.get(10).toString()));
			objPricingMasterBean.setStrPriceSunday(Double.parseDouble(jArrSearchList.get(11).toString()));

			objPricingMasterBean.setDteFromDate(objGlobal.funGetDate("dd-MM-yyyy", jArrSearchList.get(12).toString()));
			objPricingMasterBean.setDteToDate(objGlobal.funGetDate("dd-MM-yyyy", jArrSearchList.get(13).toString()));

			objPricingMasterBean.setTmeTimeFrom(jArrSearchList.get(14).toString());
			objPricingMasterBean.setStrAMPMFrom(jArrSearchList.get(15).toString());
			objPricingMasterBean.setTmeTimeTo(jArrSearchList.get(16).toString());
			objPricingMasterBean.setStrAMPMTo(jArrSearchList.get(17).toString());
			objPricingMasterBean.setStrCostCenterCode(jArrSearchList.get(18).toString().trim());
			objPricingMasterBean.setStrTextColor(jArrSearchList.get(19).toString());
			objPricingMasterBean.setStrUserCreated(jArrSearchList.get(20).toString());
			objPricingMasterBean.setStrUserEdited(jArrSearchList.get(21).toString());
			objPricingMasterBean.setDteDateCreated(jArrSearchList.get(22).toString());
			objPricingMasterBean.setDteDateEdited(jArrSearchList.get(23).toString());
			objPricingMasterBean.setStrAreaCode(jArrSearchList.get(24).toString());
			objPricingMasterBean.setStrSubMenuHeadCode(jArrSearchList.get(25).toString());
			if (jArrSearchList.get(26).toString().equalsIgnoreCase("Yes")) {
				objPricingMasterBean.setStrHourlyPricing(true);
			} else {
				objPricingMasterBean.setStrHourlyPricing(false);
			}
			objPricingMasterBean.setLongPricingId(String.valueOf(jArrSearchList.get(27)));

		}
		if (null == objPricingMasterBean) {
			objPricingMasterBean = new clsPricingMasterBean();
			objPricingMasterBean.setStrItemCode("Invalid Code");
		}

		return objPricingMasterBean;
	}

	@RequestMapping(value = "/savePricingMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPricingMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			if (result.hasErrors()) {
				return new ModelAndView("frmPOSPrice", "command", objBean);
			} else {

				/* urlHits = req.getParameter("saddr").toString(); */
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();

				JSONObject jObjPricingMaster = new JSONObject();

				jObjPricingMaster.put("strItemCode", objBean.getStrItemCode());
				jObjPricingMaster.put("strItemName", objBean.getStrItemName());
				jObjPricingMaster.put("strPosCode", objBean.getStrPosCode());
				jObjPricingMaster.put("strMenuCode", objBean.getStrMenuCode());

				if (objBean.getStrPopular()) {
					jObjPricingMaster.put("strPopular", "Y");
				} else {
					jObjPricingMaster.put("strPopular", "N");
				}

				jObjPricingMaster.put("strPriceMonday", objBean.getStrPriceMonday());
				jObjPricingMaster.put("strPriceTuesday", objBean.getStrPriceThursday());
				jObjPricingMaster.put("strPriceWednesday", objBean.getStrPriceWednesday());
				jObjPricingMaster.put("strPriceThursday", objBean.getStrPriceThursday());
				jObjPricingMaster.put("strPriceFriday", objBean.getStrPriceFriday());
				jObjPricingMaster.put("strPriceSaturday", objBean.getStrPriceSaturday());
				jObjPricingMaster.put("strPriceSunday", objBean.getStrPriceSunday());

				jObjPricingMaster.put("dteFromDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteFromDate()));
				jObjPricingMaster.put("dteToDate", objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteToDate()));

				if (objBean.getStrHourlyPricing() && objBean.getTmeTimeFrom() != null) {
					String arrFromTime[] = objBean.getTmeTimeFrom().split(":");
					String fromHH = arrFromTime[0];
					String fromMM = arrFromTime[1];
					String fromAMPM = arrFromTime[2];

					objBean.setTmeTimeFrom(fromHH + ":" + fromMM);
					objBean.setStrAMPMFrom(fromAMPM);
				} else {
					objBean.setTmeTimeFrom("HH:MM");
					objBean.setStrAMPMFrom("AM");
				}

				if (objBean.getStrHourlyPricing() && objBean.getTmeTimeTo() != null) {
					String arrToTime[] = objBean.getTmeTimeTo().split(":");
					String toHH = arrToTime[0];
					String toMM = arrToTime[1];
					String toAMPM = arrToTime[2];

					objBean.setTmeTimeTo(toHH + ":" + toMM);
					objBean.setStrAMPMTo(toAMPM);
				} else {
					objBean.setTmeTimeTo("HH:MM");
					objBean.setStrAMPMTo("AM");
				}

				jObjPricingMaster.put("tmeTimeFrom", objBean.getTmeTimeFrom());
				jObjPricingMaster.put("strAMPMFrom", objBean.getStrAMPMFrom());
				jObjPricingMaster.put("tmeTimeTo", objBean.getTmeTimeTo());
				jObjPricingMaster.put("strAMPMTo", objBean.getStrAMPMTo());

				jObjPricingMaster.put("strCostCenterCode", objBean.getStrCostCenterCode());
				jObjPricingMaster.put("strTextColor", objBean.getStrTextColor());
				jObjPricingMaster.put("strUserCreated", userCode);
				jObjPricingMaster.put("strUserEdited", userCode);
				jObjPricingMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjPricingMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjPricingMaster.put("strAreaCode", objBean.getStrAreaCode());
				jObjPricingMaster.put("strSubMenuHeadCode", objBean.getStrSubMenuHeadCode());
				if (objBean.getStrHourlyPricing()) {
					jObjPricingMaster.put("strHourlyPricing", "YES");
				} else {
					jObjPricingMaster.put("strHourlyPricing", "NO");
				}
				jObjPricingMaster.put("longPricingId", objBean.getLongPricingId());

				String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveUpdatePricingMaster";
				URL url = new URL(posURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				OutputStream os = conn.getOutputStream();
				os.write(jObjPricingMaster.toString().getBytes());
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

				return new ModelAndView("redirect:/frmPOSPrice.html?saddr=" + urlHits);
			}
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/checkDuplicateItemPricing", method = RequestMethod.GET)
	public @ResponseBody String funCheckDuplicateItemPricing(@RequestParam("itemCode") String itemCode, @RequestParam("posCode") String posCode, @RequestParam("areaCode") String areaCode, @RequestParam("hourlyPricing") boolean hourlyPricing, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		JSONObject jObjPricingMaster = new JSONObject();

		jObjPricingMaster.put("strItemCode", itemCode);
		jObjPricingMaster.put("strPosCode", posCode);
		jObjPricingMaster.put("strAreaCode", areaCode);
		if (hourlyPricing) {
			jObjPricingMaster.put("strHourlyPricing", "YES");
		} else {
			jObjPricingMaster.put("strHourlyPricing", "NO");
		}

		String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funCheckDuplicateItemPricing";
		URL url;
		String output = "", op = "";
		try {
			url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjPricingMaster.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Result= " + op);
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			return op;
		}
	}
}
