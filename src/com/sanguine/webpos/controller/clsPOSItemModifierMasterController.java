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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpos.bean.clsPOSItemModifierMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuItemMasterBean;

@Controller
public class clsPOSItemModifierMasterController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map<String, String> hmModifierGroupName = null;
	Map<String, String> hmModifierGroupCode = null;

	// Open ItemModifierMaster

	@RequestMapping(value = "/frmPOSItemModifier", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// load modifierGroup
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<String> lstModGroup = new ArrayList<String>();
		lstModGroup = funGetModGroupDetail(clientCode);
		model.put("ModifierGroup", lstModGroup);
		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSItemModifierMaster_1", "command", new clsPOSItemModifierMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSItemModifierMaster", "command", new clsPOSItemModifierMasterBean());
		} else {
			return null;
		}

	}

	public ArrayList<String> funGetModGroupDetail(String clientCode) {

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funLoadModifierGroupMaster?clientCode=" + clientCode);
		JSONArray jArr = (JSONArray) jObj.get("ModifierGroup");
		JSONObject subJsonObject = new JSONObject();
		hmModifierGroupName = new HashedMap();
		hmModifierGroupCode = new HashedMap();
		ArrayList<String> lstModGroup = new ArrayList<String>();
		if (null != jArr) {
			for (int i = 0; i < jArr.size(); i++) {
				subJsonObject = (JSONObject) jArr.get(i);
				hmModifierGroupName.put(subJsonObject.get("ModifierName").toString(), subJsonObject.get("ModifierCode").toString());
				hmModifierGroupCode.put(subJsonObject.get("ModifierCode").toString(), subJsonObject.get("ModifierName").toString());
				lstModGroup.add(subJsonObject.get("ModifierName").toString());
			}
		}
		return lstModGroup;
	}

	// load menu table
	@RequestMapping(value = "/LoadMenuDetails", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSItemModifierMasterBean> funGetMenuDetails(HttpServletRequest req) {
		List<clsPOSItemModifierMasterBean> lstMenuDtl = new ArrayList<clsPOSItemModifierMasterBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSItemModifierMasterBean objItemModifierMasterBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funLoadMenuHeadMaster" + "?masterName=" + "POSMenuHeadMaster" + "&clientCode=" + clientCode;

		/*
		 * "http://localhost:8080/prjSanguineWebService/APOSSearchIntegration/funGetPOSMenuDtl"
		 * + "?masterName="+"POSMenuHeadMaster"+"&clientCode="+clientCode;
		 */
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSMenuHeadMaster");
		JSONObject subJsonObject = new JSONObject();
		if (null != jArrSearchList) {
			for (int i = 0; i < jArrSearchList.size(); i++) {
				subJsonObject = (JSONObject) jArrSearchList.get(i);

				objItemModifierMasterBean = new clsPOSItemModifierMasterBean();

				objItemModifierMasterBean.setStrMenuCode((String) subJsonObject.get("strMenuCode"));
				objItemModifierMasterBean.setStrMenuName((String) subJsonObject.get("strMenuName"));

				lstMenuDtl.add(objItemModifierMasterBean);

			}
		}
		if (null == objItemModifierMasterBean) {
			objItemModifierMasterBean = new clsPOSItemModifierMasterBean();
			objItemModifierMasterBean.setStrMenuCode("Data not found");
		}
		return lstMenuDtl;
	}

	// load Menu wise Item Details
	@RequestMapping(value = "/loadMenuWiseItemDetail", method = RequestMethod.GET)
	public @ResponseBody List<clsPOSItemModifierMasterBean> funGetMenuWiseItemDetail(@RequestParam("MenuCode") String MenuCode, HttpServletRequest req) {
		List<clsPOSItemModifierMasterBean> lstItemDtl = new ArrayList<clsPOSItemModifierMasterBean>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSItemModifierMasterBean objItemModifierMasterBean = null;
		System.out.println(MenuCode);
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funLoadItemPricing" + "?MenuCode=" + MenuCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("MenuItemPricing");
		JSONObject subJsonObject = new JSONObject();
		if (null != jArrSearchList) {
			for (int i = 0; i < jArrSearchList.size(); i++) {
				subJsonObject = (JSONObject) jArrSearchList.get(i);

				objItemModifierMasterBean = new clsPOSItemModifierMasterBean();

				objItemModifierMasterBean.setStrItemCode((String) subJsonObject.get("ItemCode"));
				objItemModifierMasterBean.setStrItemName((String) subJsonObject.get("ItemName"));

				lstItemDtl.add(objItemModifierMasterBean);
			}
		}
		if (null == objItemModifierMasterBean) {
			objItemModifierMasterBean = new clsPOSItemModifierMasterBean();
			objItemModifierMasterBean.setStrItemCode("Data not found");
		}
		return lstItemDtl;
	}

	// load all data
	@RequestMapping(value = "/loadModifierCode", method = RequestMethod.GET)
	public @ResponseBody clsPOSItemModifierMasterBean funSetSearchFields(@RequestParam("modCode") String modCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSItemModifierMasterBean objPOSItemModifierMasterBean = null;

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetItemModifierMasterData" + "?modCode=" + modCode + "&clientCode=" + clientCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSItemModifierMaster");
		if (null != jArrSearchList) {
			objPOSItemModifierMasterBean = new clsPOSItemModifierMasterBean();
			objPOSItemModifierMasterBean.setStrModifierCode((String) jArrSearchList.get(0));
			objPOSItemModifierMasterBean.setStrModifierName((String) jArrSearchList.get(1));
			objPOSItemModifierMasterBean.setStrModifierDescription((String) jArrSearchList.get(2));
			objPOSItemModifierMasterBean.setStrModifierGroup(hmModifierGroupCode.get(jArrSearchList.get(3).toString()));
			objPOSItemModifierMasterBean.setDblRate(Double.parseDouble(jArrSearchList.get(4).toString()));
			objPOSItemModifierMasterBean.setStrApplicable((String) jArrSearchList.get(5));
			objPOSItemModifierMasterBean.setStrChargable((String) jArrSearchList.get(6));

		}

		if (null == objPOSItemModifierMasterBean) {
			objPOSItemModifierMasterBean = new clsPOSItemModifierMasterBean();
			objPOSItemModifierMasterBean.setStrModifierCode("Invalid Code");
		}

		return objPOSItemModifierMasterBean;
	}

	// Save or Update ItemModifierMaster
	@RequestMapping(value = "/saveItemModifierMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSItemModifierMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			List<clsPOSMenuItemMasterBean> listItem = objBean.getListObjItemBean();

			System.out.println(listItem.size() + " listItem :" + listItem);

			clsPOSMenuItemMasterBean objItem;
			JSONObject jObjData = new JSONObject();
			JSONArray jArrData = new JSONArray();
			// JSONArray jArrDataRow = new JSONArray();

			for (int i = 0; i < listItem.size(); i++) {
				try {
					objItem = listItem.get(i);
					if (objItem.getStrSelect().equals("Tick")) {

						JSONObject jObj = new JSONObject();
						jObj.put("strItemCode", objItem.getStrItemCode());
						jObj.put("strItemName", objItem.getStrItemName());
						jObj.put("dblRate", objItem.getDblPurchaseRate());
						jArrData.add(jObj);

					} else if (objItem.getStrSelect() == null) {
					}
				} catch (Exception e) {
				}

			}
			// jObjData.put("ModifierItems", jArrData);

			JSONObject jObjItemModifierMaster = new JSONObject();

			jObjItemModifierMaster.put("ModifierCode", objBean.getStrModifierCode());
			jObjItemModifierMaster.put("ModifierName", objBean.getStrModifierName());
			jObjItemModifierMaster.put("ModifierDescription", objBean.getStrModifierDescription());
			jObjItemModifierMaster.put("ModifierGroup", hmModifierGroupName.get(objBean.getStrModifierGroup()));
			jObjItemModifierMaster.put("Rate", objBean.getDblRate());
			jObjItemModifierMaster.put("Applicable", objGlobalFunctions.funIfNull(objBean.getStrApplicable(), "n", "y"));
			jObjItemModifierMaster.put("Chargable", objGlobalFunctions.funIfNull(objBean.getStrChargable(), "n", "y"));

			jObjItemModifierMaster.put("User", webStockUserCode);
			jObjItemModifierMaster.put("ClientCode", clientCode);
			jObjItemModifierMaster.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjItemModifierMaster.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));

			// add item details
			if (jArrData.size() > 0) {
				jObjItemModifierMaster.put("ItemDtls", jArrData);
			}

			System.out.println("jObjItemModifierMaster " + jObjItemModifierMaster);
			/*
			 * JSONObject jObjItemModifierAll=new JSONObject();
			 * 
			 * jObjItemModifierAll.put("ModifierItem",jObjData);
			 * jObjItemModifierAll
			 * .put("ModifierItemAll",jObjItemModifierMaster);
			 */

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveItemModifierMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjItemModifierMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSItemModifier.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmPOSItemModifier.html");
		}
	}

	@RequestMapping(value = "/checkModName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckMenuName(@RequestParam("modName") String modName, @RequestParam("modCode") String modCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(modName, modCode, clientCode, "POSModifier");
		if (count > 0)
			return false;
		else
			return true;
	}
}
