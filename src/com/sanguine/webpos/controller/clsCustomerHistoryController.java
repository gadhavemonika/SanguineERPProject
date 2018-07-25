package com.sanguine.webpos.controller;

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
import com.sanguine.webpos.bean.clsCustomerHistoryFlashBean;

@Controller
public class clsCustomerHistoryController {
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	private String strCustCode;

	@RequestMapping(value = "/frmCustomerHistory", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsCustomerHistoryFlashBean objBean, BindingResult result, Map<String, Object> model, @RequestParam(value = "strCustCode") String custCode, HttpServletRequest request) {
		strCustCode = custCode;
		return new ModelAndView("frmCustomerHistory");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadCustomerHistory", method = RequestMethod.GET)
	public @ResponseBody JSONArray funGetTableReservationDtl(HttpServletRequest req) {
		String fromDate = req.getParameter("fromDate");
		String toDate = req.getParameter("toDate");

		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSTransactions/funGetCustomerHistory" + "?strCustCode=" + strCustCode + "&fromDate=" + fromDate + "&toDate=" + toDate;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posURL);
		JSONArray jArryList = (JSONArray) jObj.get("CustomerHistory");

		return jArryList;

	}
}
