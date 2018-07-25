package com.sanguine.webpos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsPOSCounterMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuHeadBean;

@Controller
public class clsPOSCounterMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();
	Map map1 = new HashMap();

	// Map mapUserName = new HashMap<>();
	// Map mapPOSName = new HashMap<>();

	@RequestMapping(value = "/frmPOSCounterMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSCounterMasterBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<Object> posList = new ArrayList<Object>();
		map.put("All", "All");

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			// map.put(josnObjRet.get("strPosName"),
			// josnObjRet.get("strPosCode"));
			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}

		model.put("posList", map);

		// JSONArray jArrList=new JSONArray();
		// jArrList =objPOSGlobal.funGetAllPOSForMaster(clientCode);
		// // jArrList =objPOSGlobal.funGetAll
		// for(int i =0 ;i<jArrList.size();i++)
		// {
		// JSONObject josnObjRet = (JSONObject) jArrList.get(i);
		//
		// mapPOSName.put(josnObjRet.get("strPosCode"),
		// josnObjRet.get("strPosName"));
		//
		//
		// }
		// model.put("mapPOSName",mapPOSName);

		List userList = new ArrayList();
		map1.put("ALL", "ALL");

		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetAllUserName");
		JSONArray jArryPosList = (JSONArray) jObj.get("userList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			userList.add(josnObjRet.get("strUserName"));
			map1.put(josnObjRet.get("strUserCode"), josnObjRet.get("strUserName"));

		}
		model.put("userList", map1);

		// JSONObject jObj =
		// objGlobal.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetAllUserName");
		//
		// JSONArray jArryList=new JSONArray();
		// jArryList=(JSONArray) jObj.get("userList");
		//
		//
		// for(int i =0 ;i<jArryList.size();i++)
		// {
		// JSONObject josnObjRet = (JSONObject) jArryList.get(i);
		//
		//
		//
		// mapUserName.put(josnObjRet.get("strUserCode"),
		// josnObjRet.get("strUserName"));
		//
		//
		// }
		// model.put("mapUserName",mapUserName);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCounterMaster_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCounterMaster");
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savePOSCounterMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSCounterMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String posCode = "";
		String userCode = "";
		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjCounterMaster = new JSONObject();
			jObjCounterMaster.put("CounterCode", objBean.getStrCounterCode());
			jObjCounterMaster.put("CounterName", objBean.getStrCounterName());
			jObjCounterMaster.put("Operational", objBean.getStrOperational());

			String posName = objBean.getStrPOSCode();
			//
			// if(map.containsKey(posName))
			// {
			// posCode=(String) map.get(posName);
			//
			// }

			// jObjCounterMaster.put("POSName", posCode);
			jObjCounterMaster.put("POSName", posName);
			String userName = objBean.getStrUserCode();

			// if(map.containsKey(userName))
			// {
			// userCode=(String) map.get(userName);
			//
			// }
			jObjCounterMaster.put("UserName", userName);

			jObjCounterMaster.put("User", webStockUserCode);
			jObjCounterMaster.put("ClientCode", clientCode);
			jObjCounterMaster.put("dteDateCreated", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			jObjCounterMaster.put("dteDateEdited", objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

			// Menu Head Data

			List<clsPOSMenuHeadBean> list = objBean.getListMenuHeadDtl();
			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				clsPOSMenuHeadBean obj = new clsPOSMenuHeadBean();
				obj = (clsPOSMenuHeadBean) list.get(i);

				if (obj.getStrOperational() != null) {
					JSONObject jObjData = new JSONObject();

					jObjData.put("MenuCode", obj.getStrMenuHeadCode());
					// jObjData.put("MenuName",obj.getStrMenuHeadName());

					jArrList.add(jObjData);
				}

			}

			jObjCounterMaster.put("MenuHeadDetails", jArrList);

			String posURL = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funSaveCounterMaster";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjCounterMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSCounterMaster.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/loadCounterMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPOSCounterMasterBean funSetSearchFields(@RequestParam("counterCode") String counterCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSCounterMasterBean objPOSCounterMaster = null;
		String posName = "";
		String userName = "";

		List<clsPOSMenuHeadBean> listMenuHeadData = new ArrayList<clsPOSMenuHeadBean>();

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetCounterMasterData" + "?counterCode=" + counterCode + "&clientCode=" + clientCode;
		System.out.println(posUrl);

		jObjSearchDetails = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		// JSONArray jArrSearchList=(JSONArray)
		// jObjSearchDetails.get("POSCounterMaster");
		if (null != jObjSearchDetails) {
			objPOSCounterMaster = new clsPOSCounterMasterBean();
			objPOSCounterMaster.setStrCounterCode((String) jObjSearchDetails.get("strCounterCode"));
			objPOSCounterMaster.setStrCounterName((String) jObjSearchDetails.get("strCounterName"));
			objPOSCounterMaster.setStrOperational((String) jObjSearchDetails.get("strOperational"));
			objPOSCounterMaster.setStrUserCode((String) jObjSearchDetails.get("strUserCode"));
			objPOSCounterMaster.setStrPOSCode((String) jObjSearchDetails.get("strPOSCode"));

			// String userCode=(String) jArrSearchList.get(3);
			// userName=(String)getKeyFromValue(map1,userCode);
			// objPOSCounterMaster.setStrUserCode(userName);
			//
			//
			// String posCode=(String) jArrSearchList.get(4);
			// posName=(String)getKeyFromValue(map,posCode);
			// objPOSCounterMaster.setStrPOSCode(posName);

			// POS Menu Head Detailss
			JSONArray jArrMenuHeadList = (JSONArray) jObjSearchDetails.get("MenuDtl");
			if (null != jArrMenuHeadList) {
				for (int cnt = 0; cnt < jArrMenuHeadList.size(); cnt++) {
					JSONObject jobj = (JSONObject) jArrMenuHeadList.get(cnt);
					clsPOSMenuHeadBean objMenuHeadDtl = new clsPOSMenuHeadBean();
					objMenuHeadDtl.setStrMenuHeadCode((String) jobj.get("MenuCode"));

					if ((boolean) jobj.get("ApplicableYN")) {
						objMenuHeadDtl.setStrOperational("Y");
					} else {
						objMenuHeadDtl.setStrOperational("N");
					}

					listMenuHeadData.add(objMenuHeadDtl);
				}
			}
			objPOSCounterMaster.setListMenuHeadDtl(listMenuHeadData);

		}

		if (null == objPOSCounterMaster) {
			objPOSCounterMaster = new clsPOSCounterMasterBean();
			objPOSCounterMaster.setStrCounterCode("Invalid Code");
		}

		return objPOSCounterMaster;
	}

	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	@RequestMapping(value = "/checkCounterName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckFactoryName(@RequestParam("strCounterCode") String counterCode, @RequestParam("strCounterName") String counterName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		int count = objPOSGlobal.funCheckName(counterCode, counterName, clientCode, "POSCounterMaster");
		if (count > 0)
			return false;
		else
			return true;

	}

}