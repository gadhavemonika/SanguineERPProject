package com.sanguine.webpms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBookingTypeBean;
import com.sanguine.webpms.model.clsBookingTypeHdModel;
import com.sanguine.webpms.service.clsBookingTypeService;

@Controller
public class clsBookingTypeController {

	@Autowired
	private clsBookingTypeService objBookingTypeService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open BookingType
	@RequestMapping(value = "/frmBookingType", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBookingType_1", "command", new clsBookingTypeBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBookingType", "command", new clsBookingTypeBean());
		} else {
			return null;
		}
	}

	// Save or Update BookingType
	@RequestMapping(value = "/saveBookingType", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsBookingTypeBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsBookingTypeHdModel objModel = objBookingTypeService.funPrepareModel(objBean, userCode, clientCode);
			objBookingTypeService.funAddUpdateBookingType(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Booking Type Code : ".concat(objModel.getStrBookingTypeCode()));

			return new ModelAndView("redirect:/frmBookingType.html");
		} else {
			return new ModelAndView("frmBookingType");
		}
	}

	// Load Booking Type Data On Form
	@RequestMapping(value = "/loadBookingTypeCode", method = RequestMethod.GET)
	public @ResponseBody clsBookingTypeHdModel funLoadAgentMaster(@RequestParam("bookingType") String bookingType, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsBookingTypeHdModel objModel = objBookingTypeService.funGetBookingType(bookingType, clientCode);
		if (objModel == null) {
			objModel = new clsBookingTypeHdModel();
			objModel.setStrBookingTypeCode("Invalid Code");
		}

		return objModel;
	}
}
