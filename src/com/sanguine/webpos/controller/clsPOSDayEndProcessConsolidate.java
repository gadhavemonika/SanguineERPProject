package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsDayEndProcessBean;

@Controller
public class clsPOSDayEndProcessConsolidate {
	// frmPOSShiftEndProcessConsolidate
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;
	@Autowired
	private ServletContext servletContext;
	String strPOSCode = "";

	@RequestMapping(value = "/frmPOSShiftEndProcessConsolidate", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request, ModelMap modelmap) {

		clsDayEndProcessBean objDayEndProcessBean = new clsDayEndProcessBean();
		JSONArray jArrDayEnd = new JSONArray();
		JSONObject DayEndProcessData = new JSONObject();
		strPOSCode = request.getSession().getAttribute("loginPOS").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String strPOSCode = request.getSession().getAttribute("loginPOS").toString();
		String strPOSDate = request.getSession().getAttribute("POSDate").toString();

		JSONObject jObj = new JSONObject();
		jObj.put("strPOSDate", strPOSDate);
		jObj.put("strPOSCode", strPOSCode);
		jObj.put("strClientCode", strClientCode);
		jObj.put("userCode", userCode);
		jObj.put("strShiftNo", "1");

		JSONObject jsDayEnd = new JSONObject();
		JSONObject jsSettlement = new JSONObject();
		JSONObject jsSalesInProg = new JSONObject();
		JSONObject jsUnSettleBill = new JSONObject();

		DayEndProcessData = funLoadDayEndData(jObj);
		jsDayEnd = (JSONObject) DayEndProcessData.get("DayEnd");
		jsSettlement = (JSONObject) DayEndProcessData.get("Settlement");
		jsSalesInProg = (JSONObject) DayEndProcessData.get("SalesInProg");
		jsUnSettleBill = (JSONObject) DayEndProcessData.get("UnSettleBill");

		ArrayList al = new ArrayList<ArrayList<String>>();
		jArrDayEnd = (JSONArray) jsDayEnd.get("DayEndArr");
		objDayEndProcessBean.setjArrDayEnd(jArrDayEnd);
		objDayEndProcessBean.setTotalpax(jsDayEnd.get("totalPax").toString());
		objDayEndProcessBean.setjArrDayEndTotal((JSONArray) jsDayEnd.get("DayEndJArrTot"));

		objDayEndProcessBean.setjArrSettlement((JSONArray) jsSettlement.get("jArrSettlement"));
		objDayEndProcessBean.setjArrSettlementTotal((JSONArray) jsSettlement.get("jArrsettlementTot"));

		objDayEndProcessBean.setjArrSalesInProg((JSONArray) jsSalesInProg.get("SalesInProgress"));

		objDayEndProcessBean.setjArrUnSettlebill((JSONArray) jsUnSettleBill.get("UnSettleBill"));

		objDayEndProcessBean.setTotal(jsUnSettleBill.get("total").toString());

		String posURL = "http://localhost:8080/prjSanguineWebService/DayEndProcessTransaction/funGetAllParameterValuesPOSWise" + "?POSCode=" + strPOSCode + "&clientCode=" + strClientCode;
		JSONObject jsGlobaldata = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		// modelmap.addObject(modelName, modelObject)

		// model.put("gShiftEnd",jsGlobaldata.get("gShiftEnd").toString());
		// model.put("gDayEnd", jsGlobaldata.get("gDayEnd").toString());

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSShiftEndProcessConsolidate_1", "command", objDayEndProcessBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSShiftEndProcessConsolidate", "command", objDayEndProcessBean);
		} else {
			return null;
		}

	}

	public JSONObject funLoadDayEndData(JSONObject jObj) {
		JSONObject DayEndDataProcess = new JSONObject();
		JSONObject DayEndData = new JSONObject();
		JSONObject jsSettlement = new JSONObject();
		JSONObject jsSalesInProg = new JSONObject();
		JSONObject jsUnSettleBill = new JSONObject();
		JSONArray lastJson = new JSONArray();
		JSONArray jArr = new JSONArray();
		JSONArray jArrtmp = new JSONArray();

		JSONObject jsonObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSTransactions/funDayEndConsolidate", jObj);

		JSONObject DayEndDataWS = (JSONObject) jsonObj.get("jsonDayEnd");
		JSONObject SettlementWS = (JSONObject) jsonObj.get("jsonSettlement");
		JSONObject SalesInProgWS = (JSONObject) jsonObj.get("salesInProg");
		JSONObject UnSettleBillws = (JSONObject) jsonObj.get("UnSettleBill");

		/*
		 * jArr = (JSONArray) DayEndDataWS.get("tblDayEnd");
		 * lastJson=funGetJsonArrRow(jArr);
		 */

		DayEndData.put("DayEndArr", funGetJsonArrRowDayEnd((JSONArray) DayEndDataWS.get("tblDayEnd")));// lastJson);
		DayEndData.put("totalPax", DayEndDataWS.get("totalPax").toString());
		DayEndData.put("DayEndJArrTot", funGetJsonArrRowDayEnd((JSONArray) DayEndDataWS.get("TotalDayEnd")));

		jsSettlement.put("jArrSettlement", funGetJsonArrRowSettlement((JSONArray) SettlementWS.get("settlement")));
		jsSettlement.put("jArrsettlementTot", funGetJsonArrRowSettlement((JSONArray) SettlementWS.get("settlementTot")));

		jsSalesInProg.put("SalesInProgress", funGetJsonArrRowSalesOfProg((JSONArray) SalesInProgWS.get("salesInProg")));

		jsUnSettleBill.put("UnSettleBill", funGetJsonArrRowSettlement((JSONArray) UnSettleBillws.get("jArrUnSettle")));
		jsUnSettleBill.put("total", UnSettleBillws.get("ApproxSaleAmount").toString());
		DayEndDataProcess.put("DayEnd", DayEndData);
		DayEndDataProcess.put("Settlement", jsSettlement);
		DayEndDataProcess.put("SalesInProg", jsSalesInProg);
		DayEndDataProcess.put("UnSettleBill", jsUnSettleBill);

		return DayEndDataProcess;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/consolidateStartDayProcess", method = RequestMethod.GET)
	public @ResponseBody ModelAndView funConsolidateStartDayProcess(HttpServletRequest req) {
		JSONObject jsonDayStart = new JSONObject();
		jsonDayStart.put("DayStart", "Day Not Start");
		// String strDayStart="N";
		try {
			// funShiftStartProcess();
			String ShiftNo = "1";
			JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessConsolidate/funShiftStartProcess?shiftNo=" + ShiftNo);
			String shiftEnd = "", DayEnd = "", shiftNo = "";

			shiftEnd = jObj.get("shiftEnd").toString();
			DayEnd = jObj.get("DayEnd").toString();
			shiftNo = jObj.get("shiftNo").toString();

			// req.getSession().setAttribute("POSDate",POSDate);
			req.getSession().setAttribute("ShiftEnd", shiftEnd);
			req.getSession().setAttribute("DayEnd", DayEnd);
			jsonDayStart.put("DayStart", "Day Started Successfully");
			// strDayStart="Y";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("frmPOSShiftEndProcessConsolidate");// return
																	// jsonDayStart;
																	// //new
																	// clsDayEndProcessBean();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ConsolidateEndDayProcess", method = RequestMethod.GET)
	public @ResponseBody ModelAndView funConsolidateEndDayProcess(@RequestParam("emailReport") String EmailReport, HttpServletRequest req) {

		String userCode = req.getSession().getAttribute("usercode").toString();
		String strClientCode = req.getSession().getAttribute("clientCode").toString();
		String strPOSDate = req.getSession().getAttribute("POSDate").toString();
		String ShiftNo = "1";

		// JSONObject jObj =
		// objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessTransaction/funDayEndProcess?strPOSCode="+strPOSCode+"&shiftNo="+ShiftNo+"&strUserCode="+userCode+"&strClientCode="+strClientCode);
		String shiftEnd = "", DayEnd = "", strShiftNo = "";

		JSONObject jsonFilter = new JSONObject();
		jsonFilter.put("strPOSCode", strPOSCode);
		jsonFilter.put("ShiftNo", ShiftNo);
		jsonFilter.put("userCode", userCode);
		jsonFilter.put("strPOSDate", strPOSDate);
		jsonFilter.put("strClientCode", strClientCode);
		jsonFilter.put("EmailReport", EmailReport);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessConsolidate/funConsolidateDayEndProcess", jsonFilter);
		System.out.println(jObj);
		// shiftEnd=jObj.get("shiftEnd").toString();
		// DayEnd=jObj.get("DayEnd").toString();
		// /ShiftNo=jObj.get("shiftNo").toString();

		return new ModelAndView("frmPOSShiftEndProcessConsolidate");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ConsolidateCheckBillSettleBusyTable", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckBillSettleBusyTable(HttpServletRequest req) {
		JSONObject jsonObj = new JSONObject();
		try {

			// strPOSCode=req.getSession().getAttribute("loginPOS").toString();
			String strPOSDate = req.getSession().getAttribute("POSDate").toString();

			String ShiftNo = "1";
			JSONObject jOb = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessConsolidate/funCheckPendingBillsAndTables?POSDate=" + strPOSDate);

			JSONArray jsArr = (JSONArray) jOb.get("TableAndBill");
			for (int i = 0; i < jsArr.size(); i++) {
				JSONObject job = (JSONObject) jsArr.get(i);
				if (Boolean.parseBoolean(job.get("PendingBills").toString())) {
					jsonObj.put("PendingBills", true);
				}
				if (Boolean.parseBoolean(job.get("BusyTables").toString())) {
					jsonObj.put("BusyTables", true);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObj;
	}

	public JSONArray funGetJsonArrRowDayEnd(JSONArray jArr) {
		JSONArray lastJson = new JSONArray();
		for (int i = 0; i < jArr.size(); i++) {

			JSONObject JsOb = (JSONObject) jArr.get(i);
			String str = "";
			// JSONArray jArrtmp=new JSONArray();
			ArrayList al = new ArrayList<>();
			for (int j = 0; j < 11; j++) {
				str = String.valueOf(j);
				al.add(JsOb.get(str).toString());
			}
			lastJson.add(al);
		}
		return lastJson;
	}

	public JSONArray funGetJsonArrRowSettlement(JSONArray jArr) {
		JSONArray lastJson = new JSONArray();
		for (int i = 0; i < jArr.size(); i++) {

			JSONObject JsOb = (JSONObject) jArr.get(i);
			String str = "";
			JSONArray jArrtmp = new JSONArray();
			for (int j = 0; j < 3; j++) {
				str = String.valueOf(j);
				jArrtmp.add(JsOb.get(str));
			}
			lastJson.add(jArrtmp);
		}
		return lastJson;
	}

	public JSONArray funGetJsonArrRowSalesOfProg(JSONArray jArr) {
		JSONArray lastJson = new JSONArray();
		for (int i = 0; i < jArr.size(); i++) {

			JSONObject JsOb = (JSONObject) jArr.get(i);
			String str = "";
			JSONArray jArrtmp = new JSONArray();
			for (int j = 0; j < 2; j++) {
				str = String.valueOf(j);
				jArrtmp.add(JsOb.get(str));
			}
			lastJson.add(jArrtmp);
		}
		return lastJson;
	}

}
