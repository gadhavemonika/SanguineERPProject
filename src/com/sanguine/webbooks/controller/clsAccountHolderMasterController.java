package com.sanguine.webbooks.controller;

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
import com.sanguine.webbooks.bean.clsAccountHolderMasterBean;
import com.sanguine.webbooks.model.clsAccountHolderMasterModel;
import com.sanguine.webbooks.model.clsAccountHolderMasterModel_ID;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;
import com.sanguine.webbooks.service.clsAccountHolderMasterService;

@Controller
public class clsAccountHolderMasterController {

	@Autowired
	private clsAccountHolderMasterService objAccountHolderMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open AccountHolderMaster
	@RequestMapping(value = "/frmAccountHolderMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmAccountHolderMaster", "command", new clsAccountHolderMasterModel());
		} else {
			return new ModelAndView("frmAccountHolderMaster_1", "command", new clsAccountHolderMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadACHolderMasterData", method = RequestMethod.GET)
	public @ResponseBody clsAccountHolderMasterModel funAssignFields(@RequestParam("acHolderCode") String acHolderCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAccountHolderMasterModel objMpdel = objAccountHolderMasterService.funGetAccountHolderMaster(acHolderCode, clientCode);
		if (null == objMpdel) {
			objMpdel = new clsAccountHolderMasterModel();
			objMpdel.setStrACHolderCode("Invalid Code");
		}

		return objMpdel;
	}

	// Save or Update AccountHolderMaster
	@RequestMapping(value = "/saveAccountHolderMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAccountHolderMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();

			clsAccountHolderMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objAccountHolderMasterService.funAddUpdateAccountHolderMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "AC Holder Code : ".concat(objModel.getStrACHolderCode()));

			return new ModelAndView("redirect:/frmAccountHolderMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmAccountHolderMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsAccountHolderMasterModel funPrepareModel(clsAccountHolderMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsAccountHolderMasterModel objModel;
		if (objBean.getStrACHolderCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblacholdermaster", "ACHolderMaster", "intGId", clientCode);
			String acHolderCode = "AHC" + String.format("%06d", lastNo);
			objModel = new clsAccountHolderMasterModel(new clsAccountHolderMasterModel_ID(acHolderCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsAccountHolderMasterModel(new clsAccountHolderMasterModel_ID(objBean.getStrACHolderCode(), clientCode));
		}
		objModel.setStrACHolderName(objBean.getStrACHolderName());
		objModel.setStrDesignation(objBean.getStrDesignation());
		objModel.setIntMobileNumber(objBean.getIntMobileNumber());
		objModel.setStrEmailId(objBean.getStrEmailId());

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;

	}

}
