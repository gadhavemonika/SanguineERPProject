package com.sanguine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

public class clsPurchaseRegisterReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@RequestMapping(value = "/frmPurchaseRegisterReport", method = RequestMethod.GET)
	public ModelAndView funSalesRegisterReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseRegisterReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPurchaseRegisterReport", "command", new clsReportBean());
		}
	}

}
