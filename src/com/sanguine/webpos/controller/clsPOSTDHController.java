package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.mapping.KeyValue;
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
import com.sanguine.webpos.bean.clsPOSCustomerAreaMasterAmountBean;
import com.sanguine.webpos.bean.clsPOSTDHBean;
import com.sanguine.webpos.bean.clsPOSZoneMasterBean;
import com.sanguine.webpos.bean.clsTDHDtlBean;

@Controller
public class clsPOSTDHController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;
	Map mapCode = new HashMap();
	Map mapName = new HashMap();
	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSTDH", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)

	{
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		// return new ModelAndView("frmPOSGroupMaster");
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// JSONObject jObjSearchDetails=new JSONObject();

		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funloadPOSAllItemName" + "?clientCode=" + clientCode;

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONArray jArrSearchList = (JSONArray) jObj.get("ItemList");
		if (null != jArrSearchList) {

			for (int i = 0; i < jArrSearchList.size(); i++) {

				JSONObject jobj = (JSONObject) jArrSearchList.get(i);
				String strItemCode = jobj.get("strItemCode").toString();
				String strItemName = jobj.get("strItemName").toString();

				map.put(strItemCode, strItemName);
				mapCode.put(strItemName, strItemCode);
			}
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSTDH_1", "command", new clsPOSTDHBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSTDH", "command", new clsPOSTDHBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/checkTDHItem", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckAreaName(HttpServletRequest req) {
		String Name = "";
		String strItemCode = req.getParameter("strItemCode");
		String strTDHCode = req.getParameter("strTDHCode");
		if (mapCode.containsKey(strItemCode)) {
			Name = mapCode.get(strItemCode).toString();
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		int count = objPOSGlobal.funCheckName(Name, strTDHCode, clientCode, "POSTDHMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

	// Set Itemcose and ItemName in a Table
	@RequestMapping(value = "/loadPOSLoadTableData", method = RequestMethod.GET)
	public @ResponseBody LinkedHashMap funSearchFields(@RequestParam("strItemCode") String strItemCode, HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSZoneMasterBean objPOSZoneMaster = null;
		List arrList = null;
		String ItemName = "";

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadPOSTDHTableData" + "?searchCode=" + strItemCode + "&clientCode=" + clientCode);

		if (null != jObjSearchDetails) {
			ItemName = (jObjSearchDetails.get("strItemName").toString());
		}

		resMap.put("List", ItemName);
		return resMap;

	}

	@RequestMapping(value = "/loadPOSTDHOnItemData", method = RequestMethod.GET)
	public @ResponseBody LinkedHashMap funSetSearchFields(@RequestParam("strMenuCode") String strMenuCode, HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSZoneMasterBean objPOSZoneMaster = null;
		List arrListHeader = null;

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funloadPOSTDHOnItemData" + "?searchCode=" + strMenuCode + "&clientCode=" + clientCode);

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("ItemList");
		if (null != jArrSearchList) {
			arrListHeader = new ArrayList();
			for (int i = 0; i < jArrSearchList.size(); i++) {

				JSONObject jobj = (JSONObject) jArrSearchList.get(i);
				String strItemCode = jobj.get("strItemCode").toString();
				String strItemName = jobj.get("strItemName").toString();
				arrListHeader.add(jobj.get("strItemName"));
				// mapCode.put(strItemName,strItemCode);
				// mapName.put(strItemCode,strItemName);
			}
		}

		resMap.put("List", arrListHeader);
		return resMap;
	}

	@RequestMapping(value = "/savePOSTDH", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSTDHBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String ItemName = "";
		List<clsTDHDtlBean> listdata = null;
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String strItemCode = objBean.getStrTDHOnItem();
			if (mapCode.containsKey(strItemCode)) {
				ItemName = mapCode.get(strItemCode).toString();
			}
			JSONObject jObjTDH = new JSONObject();
			jObjTDH.put("strTDHCode", objBean.getStrTDHCode());
			jObjTDH.put("strDescription", objBean.getStrDescription());
			jObjTDH.put("strTDHOnMenuHead", objBean.getStrTDHOnMenuHead());
			jObjTDH.put("strTDHOnItem", ItemName);
			jObjTDH.put("strFreeQuantity", objBean.getStrFreeQuantity());
			jObjTDH.put("strchkApplicable", objGlobal.funIfNull(objBean.getStrchkApplicable(), "N", "Y"));
			jObjTDH.put("strMenuHead", objBean.getStrMenuHead());
			jObjTDH.put("strMaxItemQuantity", objBean.getStrMaxItemQuantity());
			jObjTDH.put("User", webStockUserCode);
			jObjTDH.put("ClientCode", clientCode);
			jObjTDH.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjTDH.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			listdata = objBean.getListTDHDtl();
			// System.out.println(listdata.size()+" listItem :"+listdata);
			clsTDHDtlBean obj;

			JSONArray jArrList = new JSONArray();
			if (null != listdata)

			{
				for (int i = 0; i < listdata.size(); i++) {

					obj = listdata.get(i);
					JSONObject jObjData = new JSONObject();
					jObjData.put("strItemCode", obj.getStrItemCode());
					jObjData.put("strItemName", obj.getStrItemName());
					jObjData.put("strDefaultYN", objGlobal.funIfNull(obj.getStrDefaultYN(), "N", "Y"));
					jObjData.put("intSubItemQty", obj.getIntSubItemQty());
					jObjData.put("strSubItemMenuCode", obj.getStrSubItemMenuCode());

					jArrList.add(jObjData);
				}
			}

			jObjTDH.put("List", jArrList);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveTDH";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjTDH.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSTDH.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadTableDataTDHOnItem", method = RequestMethod.GET)
	public @ResponseBody LinkedHashMap funLoadTableDataTDHOnItem(@RequestParam("strIteamName") String strIteamName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		LinkedHashMap resMap = new LinkedHashMap();
		List arrlist = null;

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadTableDataTDHOnItem" + "?searchCode=" + strIteamName + "&clientCode=" + clientCode);

		if (null != jObjSearchDetails) {

			String ItemName = "";
			// objPOSTDH = new clsPOSTDHBean();
			// objPOSTDH.setStrDescription();
			resMap.put("strDescription", jObjSearchDetails.get("strDescription").toString());
			if (map.containsKey(jObjSearchDetails.get("strItemCode").toString())) {
				ItemName = (String) map.get(jObjSearchDetails.get("strItemCode").toString());
			}
			resMap.put("strTDHOnItem", ItemName);
			resMap.put("strTDHOnMenuHead", jObjSearchDetails.get("strMenuCode").toString());
			resMap.put("strchkApplicable", jObjSearchDetails.get("strApplicable").toString());
			resMap.put("strMaxItemQuantity", jObjSearchDetails.get("intMaxQuantity").toString());

			/*
			 * objPOSTDH.setStrTDHOnMenuHead((String) jArrSearchList.get(1));
			 * if(map.containsKey(ItemCode)) { ItemName=(String)
			 * map.get(ItemCode); } objPOSTDH.setStrTDHOnItem(ItemName);
			 * objPOSTDH.setStrchkApplicable((String) jArrSearchList.get(3));
			 * objPOSTDH.setStrMaxItemQuantity((long)jArrSearchList.get(4));
			 */

			JSONArray jaarlist = (JSONArray) jObjSearchDetails.get("TDHDtlData");
			arrlist = new ArrayList();
			for (int i = 0; i < jaarlist.size(); i++) {
				List arrlistin = new ArrayList();
				JSONObject jobj = (JSONObject) jaarlist.get(i);
				arrlistin.add(jobj.get("strSubItemCode").toString());
				if (map.containsKey(jobj.get("strSubItemCode").toString())) {
					ItemName = (String) map.get(jobj.get("strSubItemCode").toString());
				}
				arrlistin.add(ItemName);
				arrlistin.add(jobj.get("strDefaultYN").toString());
				arrlistin.add(jobj.get("intSubItemQty").toString());
				arrlistin.add(jobj.get("strSubItemMenuCode").toString());
				arrlist.add(arrlistin);
			}
		}
		resMap.put("List", arrlist);

		return resMap;
	}

	@RequestMapping(value = "/loadPOSTDHData", method = RequestMethod.GET)
	public @ResponseBody LinkedHashMap funLoadTDHData(@RequestParam("strTDHCode") String strTDHCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		LinkedHashMap resMap = new LinkedHashMap();
		List arrlist = null;

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSMastersIntegration/funLoadTDHMasterData" + "?searchCode=" + strTDHCode + "&clientCode=" + clientCode);

		if (null != jObjSearchDetails) {

			String ItemName = "";
			// objPOSTDH = new clsPOSTDHBean();
			// objPOSTDH.setStrDescription();
			resMap.put("strDescription", jObjSearchDetails.get("strDescription").toString());
			if (map.containsKey(jObjSearchDetails.get("strItemCode").toString())) {
				ItemName = (String) map.get(jObjSearchDetails.get("strItemCode").toString());
			}
			resMap.put("strTDHOnItem", ItemName);
			resMap.put("strTDHOnMenuHead", jObjSearchDetails.get("strMenuCode").toString());
			resMap.put("strchkApplicable", jObjSearchDetails.get("strApplicable").toString());
			resMap.put("strMaxItemQuantity", jObjSearchDetails.get("intMaxQuantity").toString());

			/*
			 * objPOSTDH.setStrTDHOnMenuHead((String) jArrSearchList.get(1));
			 * if(map.containsKey(ItemCode)) { ItemName=(String)
			 * map.get(ItemCode); } objPOSTDH.setStrTDHOnItem(ItemName);
			 * objPOSTDH.setStrchkApplicable((String) jArrSearchList.get(3));
			 * objPOSTDH.setStrMaxItemQuantity((long)jArrSearchList.get(4));
			 */

			JSONArray jaarlist = (JSONArray) jObjSearchDetails.get("TDHDtlData");
			arrlist = new ArrayList();
			for (int i = 0; i < jaarlist.size(); i++) {
				List arrlistin = new ArrayList();
				JSONObject jobj = (JSONObject) jaarlist.get(i);
				arrlistin.add(jobj.get("strSubItemCode").toString());
				if (map.containsKey(jobj.get("strSubItemCode").toString())) {
					ItemName = (String) map.get(jobj.get("strSubItemCode").toString());
				}
				arrlistin.add(ItemName);
				arrlistin.add(jobj.get("strDefaultYN").toString());
				arrlistin.add(jobj.get("intSubItemQty").toString());
				arrlistin.add(jobj.get("strSubItemMenuCode").toString());
				arrlist.add(arrlistin);
			}
		}
		resMap.put("List", arrlist);

		return resMap;
	}

}
