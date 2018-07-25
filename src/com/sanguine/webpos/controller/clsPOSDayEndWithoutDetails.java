package com.sanguine.webpos.controller;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
public class clsPOSDayEndWithoutDetails {
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;
	@Autowired
	private ServletContext servletContext;
	String strPOSCode = "";

	@RequestMapping(value = "/frmPOSDayEndWithoutDetails", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request, ModelMap modelMap) {
		clsDayEndProcessBean objDayEndProcessBean = new clsDayEndProcessBean();
		strPOSCode = request.getSession().getAttribute("loginPOS").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDayEndWithoutDetails_1", "command", objDayEndProcessBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDayEndWithoutDetails", "command", objDayEndProcessBean);
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/startDayProcessWithoutDetails", method = RequestMethod.GET)
	public @ResponseBody ModelAndView funStartDayProcessWithoutDetails(HttpServletRequest req) {
		JSONObject jsonDayStart = new JSONObject();
		jsonDayStart.put("DayStart", "Day Not Start");
		// String strDayStart="N";
		try {
			// funShiftStartProcess();
			String ShiftNo = "1";
			JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessConsolidate/funStartDayProcessWithoutDetails?strPOSCode=" + strPOSCode + "&shiftNo=" + ShiftNo);
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

		return new ModelAndView("frmPOSDayEndWithoutDetails");// return
																// jsonDayStart;
																// //new
																// clsDayEndProcessBean();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/CheckBillSettleBusyTableWithoutDtl", method = RequestMethod.GET)
	public @ResponseBody JSONObject funCheckBillSettleBusyTable(HttpServletRequest req) {
		JSONObject jObj = new JSONObject();
		try {

			strPOSCode = req.getSession().getAttribute("loginPOS").toString();
			String strPOSDate = req.getSession().getAttribute("POSDate").toString();

			String ShiftNo = "1";
			jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessTransaction/funCheckPendingBillsAndTables?strPOSCode=" + strPOSCode + "&POSDate=" + strPOSDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jObj;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/blankDayEndDayProcess", method = RequestMethod.GET)
	public @ResponseBody ModelAndView funBlankDayEndDayProcess(HttpServletRequest req) {

		String userCode = req.getSession().getAttribute("usercode").toString();
		String strClientCode = req.getSession().getAttribute("clientCode").toString();
		String strPOSDate = req.getSession().getAttribute("POSDate").toString();
		String ShiftNo = "1";

		String shiftEnd = "", DayEnd = "", strShiftNo = "";

		JSONObject jsonFilter = new JSONObject();
		jsonFilter.put("strPOSCode", strPOSCode);
		jsonFilter.put("ShiftNo", ShiftNo);
		jsonFilter.put("userCode", userCode);
		jsonFilter.put("strPOSDate", strPOSDate);
		jsonFilter.put("strClientCode", strClientCode);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/DayEndProcessConsolidate/funDayEndProcessWithoutDetails", jsonFilter);
		System.out.println(jObj);
		// shiftEnd=jObj.get("shiftEnd").toString();
		// DayEnd=jObj.get("DayEnd").toString();
		// /ShiftNo=jObj.get("shiftNo").toString();

		return new ModelAndView("frmPOSDayEndWithoutDetails");
	}
}
