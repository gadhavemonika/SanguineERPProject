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

import org.apache.commons.collections.map.HashedMap;
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
import com.sanguine.webpos.bean.clsPOSMenuItemMasterBean;

@Controller
public class clsPOSMenuItemMasterController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map<String, String> hmSubGroupName = null;
	Map<String, String> hmSubGroupCode = null;

	@RequestMapping(value = "/frmPOSMenuItem", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// ProcessTime
		List<String> lstProcessTime = new ArrayList<String>();
		for (int i = 1; i < 31; i++) {
			lstProcessTime.add(String.valueOf(i));
		}
		model.put("ProcessTime", lstProcessTime);

		// subgroup data
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		List list = funGetSubGroupGDetail(clientCode);
		model.put("subGroup", list);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMenuItemMaster_1", "command", new clsPOSMenuItemMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSMenuItemMaster", "command", new clsPOSMenuItemMasterBean());
		} else {
			return null;
		}
	}

	public ArrayList<String> funGetSubGroupGDetail(String clientCode) {

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllSubGroup?clientCode=" + clientCode);
		JSONArray jArr = (JSONArray) jObj.get("allSGData");
		JSONObject subJsonObject = new JSONObject();
		hmSubGroupName = new HashedMap();
		hmSubGroupCode = new HashedMap();
		ArrayList<String> lstSGData = new ArrayList<String>();
		if (null != jArr) {
			for (int i = 0; i < jArr.size(); i++) {
				subJsonObject = (JSONObject) jArr.get(i);

				hmSubGroupName.put(subJsonObject.get("strSubGroupName").toString(), subJsonObject.get("strSubGroupCode").toString());
				hmSubGroupCode.put(subJsonObject.get("strSubGroupCode").toString(), subJsonObject.get("strSubGroupName").toString());
				lstSGData.add(subJsonObject.get("strSubGroupName").toString());
			}
		}
		return lstSGData;
	}

	@RequestMapping(value = "/loadItemCode", method = RequestMethod.GET)
	public @ResponseBody clsPOSMenuItemMasterBean funSetSearchFields(@RequestParam("itemCode") String itemCode, HttpServletRequest req) {
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
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSMenuItemMaster");
		if (null != jArrSearchList) {
			objMenuItemMasterBean = new clsPOSMenuItemMasterBean();
			objMenuItemMasterBean.setStrItemCode((String) jArrSearchList.get(0));
			objMenuItemMasterBean.setStrItemName((String) jArrSearchList.get(1));
			objMenuItemMasterBean.setStrShortName((String) jArrSearchList.get(2));
			objMenuItemMasterBean.setStrExternalCode((String) jArrSearchList.get(3));
			objMenuItemMasterBean.setStrItemForSale((String) jArrSearchList.get(4));
			objMenuItemMasterBean.setStrItemType((String) jArrSearchList.get(5));
			if (null != hmSubGroupCode)
				objMenuItemMasterBean.setStrSubGroupCode(hmSubGroupCode.get((String) jArrSearchList.get(6)));
			objMenuItemMasterBean.setStrRawMaterial((String) jArrSearchList.get(7));
			objMenuItemMasterBean.setStrTaxIndicator((String) jArrSearchList.get(8));
			objMenuItemMasterBean.setDblPurchaseRate(Double.parseDouble(jArrSearchList.get(9).toString()));
			objMenuItemMasterBean.setStrRevenueHead((String) jArrSearchList.get(10));
			objMenuItemMasterBean.setDblSalePrice(Double.parseDouble(jArrSearchList.get(11).toString()));
			objMenuItemMasterBean.setIntProcDay(Integer.parseInt(jArrSearchList.get(12).toString()));
			objMenuItemMasterBean.setIntProcTimeMin(Integer.parseInt(jArrSearchList.get(13).toString()));
			objMenuItemMasterBean.setDblMinLevel(Double.parseDouble(jArrSearchList.get(14).toString()));
			objMenuItemMasterBean.setDblMaxLevel(Double.parseDouble(jArrSearchList.get(15).toString()));
			objMenuItemMasterBean.setStrStockInEnable((String) jArrSearchList.get(16));
			objMenuItemMasterBean.setStrOpenItem((String) jArrSearchList.get(17));
			objMenuItemMasterBean.setStrItemWiseKOTYN((String) jArrSearchList.get(18));
			objMenuItemMasterBean.setStrItemDetails((String) jArrSearchList.get(19));
			objMenuItemMasterBean.setStrDiscountApply((String) jArrSearchList.get(20));
			objMenuItemMasterBean.setStrUOM((String) jArrSearchList.get(21));
		}
		if (null == objMenuItemMasterBean) {
			objMenuItemMasterBean = new clsPOSMenuItemMasterBean();
			objMenuItemMasterBean.setStrItemCode("Invalid Code");
		}

		return objMenuItemMasterBean;
	}

	@RequestMapping(value = "/saveMenuItemMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSMenuItemMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjItemMaster = new JSONObject();
			jObjItemMaster.put("ItemCode", objBean.getStrItemCode());
			jObjItemMaster.put("ItemName", objBean.getStrItemName());
			jObjItemMaster.put("ShortName", objBean.getStrShortName());
			jObjItemMaster.put("ExternalCode", objBean.getStrExternalCode());
			jObjItemMaster.put("ItemForSale", objGlobal.funIfNull(objBean.getStrItemForSale(), "N", "Y"));

			jObjItemMaster.put("ItemType", objBean.getStrItemType());

			jObjItemMaster.put("SubGroupCode", hmSubGroupName.get(objBean.getStrSubGroupCode()));
			jObjItemMaster.put("RawMaterial", objGlobal.funIfNull(objBean.getStrRawMaterial(), "N", "Y"));
			jObjItemMaster.put("TaxIndicator", objBean.getStrTaxIndicator());
			jObjItemMaster.put("PurchaseRate", objBean.getDblPurchaseRate());
			jObjItemMaster.put("RevenueHead", objBean.getStrRevenueHead());

			jObjItemMaster.put("SalePrice", objBean.getDblSalePrice());
			jObjItemMaster.put("ProcDay", objBean.getIntProcDay());
			jObjItemMaster.put("ProcTimeMin", objBean.getIntProcTimeMin());
			jObjItemMaster.put("MinLevel", objBean.getDblMinLevel());
			jObjItemMaster.put("MaxLevel", objBean.getDblMaxLevel());

			jObjItemMaster.put("StockInEnable", objGlobal.funIfNull(objBean.getStrStockInEnable(), "N", "Y"));
			jObjItemMaster.put("OpenItem", objGlobal.funIfNull(objBean.getStrOpenItem(), "N", "Y"));
			jObjItemMaster.put("ItemWiseKOTYN", objGlobal.funIfNull(objBean.getStrItemWiseKOTYN(), "N", "Y"));
			jObjItemMaster.put("ItemDetails", objBean.getStrItemDetails());

			jObjItemMaster.put("ApplyDiscount", objGlobal.funIfNull(objBean.getStrDiscountApply(), "N", objBean.getStrDiscountApply()));

			jObjItemMaster.put("User", webStockUserCode);
			jObjItemMaster.put("ClientCode", clientCode);
			jObjItemMaster.put("UOM", objBean.getStrUOM());
			jObjItemMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjItemMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveMenuItemMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjItemMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSMenuItem.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/checkItemName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckMenuName(@RequestParam("itemName") String itemName, @RequestParam("itemCode") String itemCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(itemName, itemCode, clientCode, "POSMenuItem");

		if (count > 0)

			return false;
		else
			return true;

	}

}
