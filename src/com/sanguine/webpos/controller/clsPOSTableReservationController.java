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
import com.sanguine.webpos.bean.clsPOSOrderMasterBean;
import com.sanguine.webpos.bean.clsPOSTableReservationBean;

@Controller
public class clsPOSTableReservationController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@RequestMapping(value = "/frmPOSTableReservation", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSTableReservationBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {

		Map map = new HashMap();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<Object> posList = new ArrayList<Object>();
		posList.add("ALL");

		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			map.put(josnObjRet.get("strPosCode"), josnObjRet.get("strPosName"));
		}

		model.put("posList", map);

		return new ModelAndView("frmPOSTableReservation");

	}

	@RequestMapping(value = "/saveTableReservation", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPOSTableReservationBean objBean, BindingResult result, HttpServletRequest req) {

		String posCode = "";
		try {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String webStockUserCode = req.getSession().getAttribute("usercode").toString();

			JSONObject jObjAreaMaster = new JSONObject();
			jObjAreaMaster.put("resCode", objBean.getStrReservationCode());
			jObjAreaMaster.put("CustName", objBean.getStrCustName());
			jObjAreaMaster.put("CustCode", objBean.getStrCustCode());
			jObjAreaMaster.put("intPax", objBean.getIntPax());
			jObjAreaMaster.put("strSmokingYN", objBean.getStrSmokingYN());
			jObjAreaMaster.put("ContactNo", objBean.getStrContactNo());
			jObjAreaMaster.put("City", objBean.getStrCity());
			jObjAreaMaster.put("BldgCode", objBean.getStrBldgCode());
			jObjAreaMaster.put("BldgName", objBean.getStrBldgName());
			String[] date = objBean.getDteDate().split(" ");
			jObjAreaMaster.put("resDate", date[0]);

			String strHH = objBean.getStrHH();
			String strMM = objBean.getStrMM();
			String strAMPM = objBean.getStrAMPM();
			String resTime = strHH + ":" + strMM + ":00";

			jObjAreaMaster.put("resTime", resTime);
			jObjAreaMaster.put("strAMPM", strAMPM);
			jObjAreaMaster.put("strInfo", objBean.getStrInfo());
			jObjAreaMaster.put("strTableNo", objBean.getStrTableNo());

			jObjAreaMaster.put("POSCode", objBean.getStrPOS());
			jObjAreaMaster.put("User", webStockUserCode);
			jObjAreaMaster.put("ClientCode", clientCode);

			String posURL = "http://localhost:8080/prjSanguineWebService/WebTableReservationController/funSaveTableReservation";
			URL url = new URL(posURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(jObjAreaMaster.toString().getBytes());
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

			return new ModelAndView("redirect:/frmPOSTableReservation.html");
		} catch (Exception ex) {

			ex.printStackTrace();
			return new ModelAndView("redirect:/frmFail.html");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadReservationDefault", method = RequestMethod.GET)
	public @ResponseBody JSONObject funGetReservationDefault(HttpServletRequest req) {
		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();

		String date = req.getParameter("date");

		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebTableReservationController/funGetReservationDefault" + "?date=" + date + "&loginPosCode=" + loginPosCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONObject billHd = (JSONObject) jObjSettlementData.get("ReservationDtl");

		return billHd;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadTableReservationDtl", method = RequestMethod.GET)
	public @ResponseBody JSONArray funGetTableReservationDtl(HttpServletRequest req) {
		String loginPosCode = req.getSession().getAttribute("loginPOS").toString();
		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String fromTime = req.getParameter("fromTime");

		String toTime = req.getParameter("toTime");

		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebTableReservationController/funGetTableReservationDtl" + "?fromDate=" + fromDate + "&toDate=" + toDate + "&fromTime=" + fromTime + "&toTime=" + toTime + "&loginPosCode=" + loginPosCode;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONArray billHd = (JSONArray) jObjSettlementData.get("ReservationDtl");

		return billHd;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/funCancelTableReservation", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCancelTableReservation(HttpServletRequest req) {

		String reservationNo = req.getParameter("reservationNo");

		String tableNo = req.getParameter("tableNo");
		JSONObject jObjSettlementData = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/WebTableReservationController/funCancelTableReservation" + "?reservationNo=" + reservationNo + "&tableNo=" + tableNo;

		jObjSettlementData = objGlobal.funGETMethodUrlJosnObjectData(posUrl);

		JSONObject billHd = new JSONObject();
		return billHd;

	}

	@RequestMapping(value = "/loadPOSTableReservationData", method = RequestMethod.GET)
	public @ResponseBody clsPOSTableReservationBean funSetSearchFields(@RequestParam("resCode") String resCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsPOSTableReservationBean objPOSAreaMaster = null;
		String posName = "";

		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSSearchIntegration/funGetPOSSearchData" + "?masterName=POSTableReservation&searchCode=" + resCode + "&clientCode=" + clientCode;
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

		JSONArray jArrSearchList = (JSONArray) jObjSearchDetails.get("POSTableReservation");
		if (null != jArrSearchList) {
			objPOSAreaMaster = new clsPOSTableReservationBean();
			objPOSAreaMaster.setStrReservationCode((String) jArrSearchList.get(0));
			objPOSAreaMaster.setStrCustCode((String) jArrSearchList.get(1));
			objPOSAreaMaster.setStrCustName((String) jArrSearchList.get(2));
			objPOSAreaMaster.setStrBldgCode((String) jArrSearchList.get(3));
			objPOSAreaMaster.setStrBldgName((String) jArrSearchList.get(4));
			objPOSAreaMaster.setStrCity((String) jArrSearchList.get(5));
			objPOSAreaMaster.setStrContactNo((String) jArrSearchList.get(6));
			objPOSAreaMaster.setStrTableNo((String) jArrSearchList.get(7));
			objPOSAreaMaster.setDteDate((String) jArrSearchList.get(8));

			String resTime = (String) jArrSearchList.get(9);
			String[] time = resTime.split(":");
			String HH = time[0];

			String MM = time[1];

			objPOSAreaMaster.setStrHH(HH);
			objPOSAreaMaster.setStrMM(MM);
			objPOSAreaMaster.setIntPax((long) jArrSearchList.get(10));
			objPOSAreaMaster.setStrSmokingYN((String) jArrSearchList.get(11));
			objPOSAreaMaster.setStrInfo((String) jArrSearchList.get(12));
			objPOSAreaMaster.setStrTableName((String) jArrSearchList.get(14));

			objPOSAreaMaster.setStrAMPM((String) jArrSearchList.get(15));
			objPOSAreaMaster.setStrPOS((String) jArrSearchList.get(16));

		}

		if (null == objPOSAreaMaster) {
			objPOSAreaMaster = new clsPOSTableReservationBean();
			objPOSAreaMaster.setStrReservationCode("Invalid Code");
		}

		return objPOSAreaMaster;
	}

}
