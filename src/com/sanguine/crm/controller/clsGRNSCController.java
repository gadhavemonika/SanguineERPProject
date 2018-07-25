package com.sanguine.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.util.clsReportBean;

@Controller
public class clsGRNSCController {
	@RequestMapping(value = "/frmGRNSCReport", method = RequestMethod.GET)
	public ModelAndView funGRNSCReport(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRNSCReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmGRNSCReport", "command", new clsReportBean());
		}

	}

}
