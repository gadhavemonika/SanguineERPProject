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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSWiseItemIncentiveBean;
import com.sanguine.webpos.bean.clsPOSWiseItemIncentiveDtlBean;

@Controller
public class clsPOSWiseItemIncentiveController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();
	Map map1 = new HashMap();

	@RequestMapping(value = "/frmPOSWiseItemIncentive", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request)

	{
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		List poslist = new ArrayList();
		map1.put("ALL", "ALL");

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
			String POSName = josnObjRet.get("strPosName").toString();
			String POSCode = josnObjRet.get("strPosCode").toString();
			map1.put(POSCode, POSName);
			map.put(POSName, POSCode);
		}
		model.put("posList", map1);

		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSWiseItemIncentive_1", "command", new clsPOSWiseItemIncentiveBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSWiseItemIncentive", "command", new clsPOSWiseItemIncentiveBean());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/savePOSWiseItemIncentive", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSWiseItemIncentiveBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		System.out.println(objBean);
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjItemIncentive = new JSONObject();
			jObjItemIncentive.put("POSCode", objBean.getStrPOSName());
			jObjItemIncentive.put("User", webStockUserCode);
			jObjItemIncentive.put("ClientCode", clientCode);
			jObjItemIncentive.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjItemIncentive.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			List<clsPOSWiseItemIncentiveDtlBean> listdata = objBean.getListItemIncentive();

			clsPOSWiseItemIncentiveDtlBean obj;

			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < listdata.size(); i++) {

				obj = listdata.get(i);
				JSONObject jObjData = new JSONObject();
				jObjData.put("strItemCode", obj.getStrItemCode());
				jObjData.put("strItemName", obj.getStrItemName());
				jObjData.put("strIncentiveType", obj.getStrIncentiveType());
				jObjData.put("strIncentiveValue", obj.getStrIncentiveValue());
				jObjData.put("strPOSCode", obj.getStrPOSCode());

				jArrList.add(jObjData);

			}

			jObjItemIncentive.put("List", jArrList);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSavePOSWiseItemIncentive";

			URL url = new URL(posURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjItemIncentive.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSWiseItemIncentive.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadPOSWiseItemIncentiveData" }, method = RequestMethod.POST)
	@ResponseBody
	public Map FunLoadPOSwiseIncentive(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strPOSCode = req.getParameter("POSCode");

		resMap = FunGetData(clientCode, strPOSCode);
		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetData(String clientCode, String strPOSCode) {
		LinkedHashMap resMap = new LinkedHashMap();
		List colHeader = new ArrayList();

		JSONObject jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSSearchIntegration/funGetPOSSearchAll" + "?masterName=POSWiseItemIncentive&clientCode=" + strPOSCode);

		List list = new ArrayList();
		List totalList = new ArrayList();
		JSONArray jarr = (JSONArray) jObjSearchDetails.get("POSWiseItemIncentive");
		if (null != jarr) {
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				List arrList = new ArrayList();

				arrList.add(jObjtemp.get("strItemCode").toString());
				arrList.add(jObjtemp.get("strItemName").toString());
				arrList.add(jObjtemp.get("strIncentiveType").toString());
				arrList.add(jObjtemp.get("dblIncentiveValue").toString());
				// map.put(jObjtemp.get("strPOSCode").toString(),
				// jObjtemp.get("strPosName").toString());
				arrList.add(jObjtemp.get("strPOSCode").toString());
				list.add(arrList);
			}

		}

		resMap.put("list", list);

		return resMap;
	}

}
