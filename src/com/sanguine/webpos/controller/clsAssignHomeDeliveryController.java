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
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsCostCenterBean;
import com.sanguine.webpos.bean.clsPOSAreaMasterBean;
import com.sanguine.webpos.bean.clsPOSAssignHomeDeliveryBean;
import com.sanguine.webpos.bean.clsPOSBillHdDtl;
import com.sanguine.webpos.bean.clsPOSCounterMasterBean;
import com.sanguine.webpos.bean.clsPOSDeliveryPersonMaster;
import com.sanguine.webpos.bean.clsPOSMasterBean;
import com.sanguine.webpos.bean.clsPOSMenuHeadBean;
import com.sanguine.webpos.bean.clsPOSWaiterMasterBean;
import com.sanguine.webpos.bean.clsSettlementDetailsBean;

@Controller
public class clsAssignHomeDeliveryController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	Map map = new HashMap();
	JSONObject josnObjRet;

	@RequestMapping(value = "/frmPOSAssignHomeDelivery", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSAssignHomeDeliveryBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();

		List customerAreaList1 = new ArrayList();
		customerAreaList1.add("All");
		JSONArray jArryList = new JSONArray();

		jArryList = objPOSGlobal.funGetAllCustomerAreaForMaster(clientCode);

		for (int i = 0; i < jArryList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryList.get(i);
			customerAreaList1.add(josnObjRet.get("strBuildingCode"));
			map.put(josnObjRet.get("strBuildingCode"), josnObjRet.get("strBuildingName"));
		}
		model.put("customerAreaList1", customerAreaList1);

		JSONArray jObj;
		Map mapZoneCode = new HashMap<>();

		mapZoneCode.put("All", "All");
		jObj = objPOSGlobal.funGetAllZoneForMaster(clientCode);
		jArryList = (JSONArray) jObj;

		for (int i = 0; i < jArryList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryList.get(i);

			mapZoneCode.put(josnObjRet.get("strZoneCode"), josnObjRet.get("strZoneName"));

		}
		model.put("mapZoneCode", mapZoneCode);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAssignHomeDelivery_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSAssignHomeDelivery");
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/saveDelBoyBillDtl", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSAssignHomeDeliveryBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";

		try {
			urlHits = req.getParameter("saddr").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();
			String areaCode = objBean.getStrArea();
			String zoneCode = objBean.getStrZone();
			String posCode = req.getSession().getAttribute("loginPOS").toString();
			JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode);
			String posDate = jObj.get("POSDate").toString().split(" ")[0];

			JSONObject jObjAssignHomeDelivery = new JSONObject();

			List<clsPOSBillHdDtl> list = objBean.getListBillNoDtl();
			JSONArray jArrList = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				clsPOSBillHdDtl obj = new clsPOSBillHdDtl();
				obj = (clsPOSBillHdDtl) list.get(i);

				JSONObject jObjData = new JSONObject();
				// String billDtl=jObjData.getString()
				jObjData.put("BillNo", obj.getStrBillNo());
				// jObjData.put("MenuName",obj.getStrMenuHeadName());

				jArrList.add(jObjData);

			}

			jObjAssignHomeDelivery.put("BillNoDtl", jArrList);
			// List<clsPOSDeliveryPersonMaster>
			// list1=objBean.getListDelBoyDtl();
			// JSONArray jArrList1 = new JSONArray();
			// for(int i=0; i<list1.size(); i++)
			// {
			// clsPOSDeliveryPersonMaster obj= new clsPOSDeliveryPersonMaster();
			// obj=(clsPOSDeliveryPersonMaster)list.get(i);
			//
			//
			// JSONObject jObjData = new JSONObject();
			//
			// jObjData.put("DPCode",obj.getStrDPCode());
			// // jObjData.put("MenuName",obj.getStrMenuHeadName());
			//
			// jArrList1.add(jObjData);
			//
			//
			// }
			//
			// jObjAssignHomeDelivery.put("DelBoyDtl",jArrList1 );
			String dpCode = "";
			List<clsPOSDeliveryPersonMaster> poslist = objBean.getListDelBoyDtl();
			JSONArray jArrPosList = new JSONArray();
			for (int i = 0; i < poslist.size(); i++) {
				clsPOSDeliveryPersonMaster obj = new clsPOSDeliveryPersonMaster();
				obj = (clsPOSDeliveryPersonMaster) poslist.get(i);

				JSONObject jObjData = new JSONObject();

				String dpName = obj.getStrDPCode();

				if (map.containsKey(dpName)) {
					dpCode = (String) map.get(dpName);

				}
				// jObjData.put("DPCode",obj.getStrDPCode());
				jObjData.put("DPCode", dpCode);

				jArrPosList.add(jObjData);

			}

			jObjAssignHomeDelivery.put("DelBoyDtl", jArrPosList);
			jObjAssignHomeDelivery.put("clientCode", clientCode);
			jObjAssignHomeDelivery.put("areaCode", areaCode);
			jObjAssignHomeDelivery.put("zoneCode", zoneCode);
			jObjAssignHomeDelivery.put("posDate", posDate);

			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSaveDelBoyBillDtl";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjAssignHomeDelivery.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSAssignHomeDelivery.html?saddr=" + urlHits);
		} catch (Exception ex) {
			urlHits = "1";
			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@RequestMapping(value = "/fillCustomerAreaCombo", method = RequestMethod.GET)
	public @ResponseBody Map funfillCustomerAreaCombo(@RequestParam("zoneCode") String zoneCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		List custAreaName = new ArrayList();
		Map customerAreaList1 = new HashMap<>();
		customerAreaList1.put("All", "All");
		JSONArray jArryList = new JSONArray();
		if (zoneCode.equals("All")) {
			jArryList = objPOSGlobal.funGetAllCustomerAreaForMaster(clientCode);

			for (int i = 0; i < jArryList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArryList.get(i);

				customerAreaList1.put(josnObjRet.get("strBuildingCode"), josnObjRet.get("strBuildingName"));
			}
		} else {
			jArryList = objPOSGlobal.funFillCustomerAreaForMaster(clientCode, zoneCode);

			for (int i = 0; i < jArryList.size(); i++) {
				JSONObject josnObjRet = (JSONObject) jArryList.get(i);

				customerAreaList1.put(josnObjRet.get("strBuildingCode"), josnObjRet.get("strBuildingName"));
			}
		}
		return customerAreaList1;
	}

	@RequestMapping(value = "/funSetBillAmountAndLooseCash", method = RequestMethod.GET)
	public @ResponseBody JSONObject funSetBillAmountAndLooseCash(@RequestParam("billNo") String billNo, HttpServletRequest req) {
		JSONObject jObj = new JSONObject();

		billNo = req.getParameter("billNo");
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funSetBillAmountAndLooseCash" + "?billNo=" + billNo;

		jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONObject jObjRet = new JSONObject();
		jObjRet.put("dblGrandTotal", (long) jObj.get("dblGrandTotal"));
		return jObjRet;
	}

	@RequestMapping(value = "/loadBillAndDelBoyData", method = RequestMethod.GET)
	public @ResponseBody JSONObject funloadBillAndDelBoyData(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String zoneCode = req.getParameter("zoneCode");
		String areaCd = req.getParameter("areaCode");
		String areaCode = "";
		if (areaCd.equalsIgnoreCase("All")) {
			areaCode = "All";
		} else {
			if (map.containsKey(areaCd)) {
				areaCode = (String) map.get(areaCd);
			}
		}
		JSONArray jArrBillAndDeliveryBoyList = null;
		clsPOSAssignHomeDeliveryBean objPOSCounterMaster = null;
		JSONObject jObjBillAndDeliveryBoyData = new JSONObject();

		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetOpenBillAndDeliveryBoyDtl" + "?zoneCode=" + zoneCode + "&areaCode=" + areaCode + "&clientCode=" + clientCode;

		jObjBillAndDeliveryBoyData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);
		List<clsPOSDeliveryPersonMaster> listMenuHeadData = new ArrayList<clsPOSDeliveryPersonMaster>();

		// long countDelBoy = (long)
		// jObjBillAndDeliveryBoyData.get("countDelBoy");
		// long countBill = (long) jObjBillAndDeliveryBoyData.get("countBill");

		jArrBillAndDeliveryBoyList = (JSONArray) jObjBillAndDeliveryBoyData.get("DeliveryBoyDtl");

		for (int i = 0; i < jArrBillAndDeliveryBoyList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrBillAndDeliveryBoyList.get(i);

			map.put(josnObjRet.get("strDPName"), josnObjRet.get("strDPCode"));
		}
		// if(null!=jArrBillAndDeliveryBoyList)
		// {
		// for(int cnt=0;cnt<jArrBillAndDeliveryBoyList.size();cnt++)
		// {
		// JSONObject jobj=(JSONObject) jArrBillAndDeliveryBoyList.get(cnt);
		// clsPOSDeliveryPersonMaster objMenuHeadDtl = new
		// clsPOSDeliveryPersonMaster();
		// objMenuHeadDtl.setStrDPCode((String)jobj.get("strDPCode"));
		// // objMenuHeadDtl.setStrDPName((String)jobj.get("strDPName"));
		// listMenuHeadData.add(objMenuHeadDtl);
		// }
		//
		// }
		// objPOSCounterMaster.setListDelBoyDtl(listMenuHeadData);

		JSONArray jArrKOTList = (JSONArray) jObjBillAndDeliveryBoyData.get("BillNoDtl");

		JSONArray jArrDelBoyList = (JSONArray) jObjBillAndDeliveryBoyData.get("delBoy");

		// JSONObject jObjDelBoyStatus = (JSONObject)
		// jObjBillAndDeliveryBoyData.get("delBoy");
		// String delBoyStatus = (String)
		// jObjDelBoyStatus.get("statusDelBoyBillNo");

		JSONObject jObjHomeDeliveryData = new JSONObject();

		jObjHomeDeliveryData.put("BillNoDtl", jArrKOTList);
		jObjHomeDeliveryData.put("DeliveryBoyDtl", jArrBillAndDeliveryBoyList);
		jObjHomeDeliveryData.put("delBoyDtl", jArrDelBoyList);
		// jObjHomeDeliveryData.put("delBoyDtl",jArrDelBoyList);
		// jObjHomeDeliveryData.put("countDelBoy", countDelBoy);
		// jObjHomeDeliveryData.put("countBill", countBill);
		return jObjHomeDeliveryData;

	}
	// @RequestMapping(value = "/loadDeliverPersonData", method =
	// RequestMethod.GET)
	// public @ResponseBody JSONArray funGetTableDtl(HttpServletRequest req)
	// {
	// String areaCode=req.getParameter("areaCode");
	//
	//
	// JSONArray jArrSettlementList=null;
	//
	// JSONObject jObjSettlementData=new JSONObject();
	// String posUrl =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funGetTableDtl"
	// + "?areaCode="+areaCode;
	//
	// jObjSettlementData =objGlobal.funGETMethodUrlJosnObjectData(posUrl);
	//
	// jArrSettlementList=(JSONArray) jObjSettlementData.get("TableDtl");
	//
	//
	// return jArrSettlementList;
	// }

	// @RequestMapping(value = "/checkBillAndDelBoyData", method =
	// RequestMethod.GET)
	// public @ResponseBody JSONObject
	// funCheckBillAndDelBoyData(HttpServletRequest req)
	// {
	// JSONObject jObjCheckBillAndDeliveryBoyData=new JSONObject();
	// String clientCode=req.getSession().getAttribute("clientCode").toString();
	//
	// String posUrl =
	// "http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funCheckBillAndDeliveryBoyDtl"
	// +"&clientCode="+clientCode;
	//
	// jObjCheckBillAndDeliveryBoyData
	// =objGlobal.funGETMethodUrlJosnObjectData(posUrl);
	// return jObjCheckBillAndDeliveryBoyData;
	//
	// }

}