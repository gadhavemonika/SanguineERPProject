package com.sanguine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class clsHelpModuleWindow {

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmHelpModuleWindow", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmHelpModuleWindow");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmHelpModuleWindow");
		} else {
			return null;
		}

	}
}
