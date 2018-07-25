package com.sanguine.webbooks.apgl.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.webbooks.apgl.bean.clsAPGLBudgetBean;

@Controller
public class clsCreditorsOutStandingBillsReportController {

	// Open Buget Form
	@RequestMapping(value = "/frmCreditorsOutStandingBillsReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCreditorsOutStandingBillsReport_1", "command", new clsAPGLBudgetBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCreditorsOutStandingBillsReport", "command", new clsAPGLBudgetBean());
		} else {
			return null;
		}

	}
}
