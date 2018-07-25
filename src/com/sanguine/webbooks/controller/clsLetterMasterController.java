package com.sanguine.webbooks.controller;

import java.util.ArrayList;
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
import com.sanguine.webbooks.bean.clsBankMasterBean;
import com.sanguine.webbooks.bean.clsLetterMasterBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;
import com.sanguine.webbooks.model.clsLetterMasterModel;
import com.sanguine.webbooks.model.clsLetterMasterModel_ID;
import com.sanguine.webbooks.service.clsLetterMasterService;

@Controller
public class clsLetterMasterController {

	@Autowired
	private clsLetterMasterService objLetterMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open LetterMaster
	@RequestMapping(value = "/frmLetterMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		ArrayList<String> listReminderLetter = new ArrayList<>();
		listReminderLetter.add("Reminder1");
		listReminderLetter.add("Reminder2");
		listReminderLetter.add("Reminder3");
		listReminderLetter.add("Reminder4");
		listReminderLetter.add("Reminder5");

		ArrayList<String> listLetterProcessOn = new ArrayList<>();
		listLetterProcessOn.add("Billing To Debtor Detail");
		listLetterProcessOn.add("Debtor Detail");
		listLetterProcessOn.add("ESIC");
		listLetterProcessOn.add("WCT");

		List listVMemberDebtorDtlColumnNames = objGlobalFunctionsService.funGetDataList("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'vwdebtormemberdtl'", "sql");

		model.put("urlHits", urlHits);
		model.put("listReminderLetter", listReminderLetter);
		model.put("listLetterProcessOn", listLetterProcessOn);
		model.put("listVMemberDebtorDtlColumnNames", listVMemberDebtorDtlColumnNames);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmLetterMaster", "command", new clsLetterMasterModel());
		} else {
			return new ModelAndView("frmLetterMaster_1", "command", new clsLetterMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadLetterMasterData", method = RequestMethod.GET)
	public @ResponseBody clsLetterMasterModel funLoadMasterData(@RequestParam("letterCode") String letterCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		clsLetterMasterModel objModel = objLetterMasterService.funGetLetterMaster(letterCode, clientCode);
		if (null == objModel) {
			objModel = new clsLetterMasterModel();
			objModel.setStrLetterCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update Letter Master
	@RequestMapping(value = "/saveLetterMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsLetterMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsLetterMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);
			objLetterMasterService.funAddUpdateLetterMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Letter Code : ".concat(objModel.getStrLetterCode()));

			return new ModelAndView("redirect:/frmLetterMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmLetterMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsLetterMasterModel funPrepareModel(clsLetterMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsLetterMasterModel objModel;
		if (objBean.getStrLetterCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbllettermaster", "LettrMaster", "intGId", clientCode);
			String letterCode = String.format("%03d", lastNo);
			objModel = new clsLetterMasterModel(new clsLetterMasterModel_ID(letterCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsLetterMasterModel(new clsLetterMasterModel_ID(objBean.getStrLetterCode(), clientCode));
		}
		objModel.setStrLetterName(objBean.getStrLetterName().toUpperCase());
		objModel.setStrReminderYN(objBean.getStrReminderYN());
		objModel.setStrReminderLetter(objBean.getStrReminderLetter());
		objModel.setStrIsCircular(objBean.getStrIsCircular());
		objModel.setStrView(objBean.getStrView());
		objModel.setStrArea(objBean.getStrArea());

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;
	}

}
