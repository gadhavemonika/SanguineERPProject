package com.sanguine.webpms.controller;

import java.util.List;
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
import com.sanguine.webpms.bean.clsBillingInstructionsBean;
import com.sanguine.webpms.model.clsBillingInstructionsHdModel;
import com.sanguine.webpms.service.clsBillingInstructionsService;

@Controller
public class clsBillingInstructionsController {

	@Autowired
	private clsBillingInstructionsService objBillingInstructionsService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open BillingInstructions
	@RequestMapping(value = "/frmBillingInstructions", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillingInstructions_1", "command", new clsBillingInstructionsBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBillingInstructions", "command", new clsBillingInstructionsBean());
		} else {
			return null;
		}
	}

	// Save or Update BillingInstructions
	@RequestMapping(value = "/saveBillingInstructions", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsBillingInstructionsBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsBillingInstructionsHdModel objModel = objBillingInstructionsService.funPrepareModel(objBean, userCode, clientCode);
			objBillingInstructionsService.funAddUpdateBillingInstructions(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "BillingInstructions Code : ".concat(objModel.getStrBillingInstCode()));
			return new ModelAndView("redirect:/frmBillingInstructions.html");
		} else {
			return new ModelAndView("frmBillingInstructions");
		}
	}

	// Load Billing Instructions On Form
	@RequestMapping(value = "/loadBillingInstCode", method = RequestMethod.GET)
	public @ResponseBody clsBillingInstructionsHdModel funLoadAgentMaster(@RequestParam("billingInstructions") String billingInstructions, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsBillingInstructionsHdModel objModel = objBillingInstructionsService.funGetBillingInstructions(billingInstructions, clientCode);
		if (objModel == null) {
			objModel = new clsBillingInstructionsHdModel();
			objModel.setStrBillingInstCode("Invalid Code");
		}

		return objModel;
	}

}
