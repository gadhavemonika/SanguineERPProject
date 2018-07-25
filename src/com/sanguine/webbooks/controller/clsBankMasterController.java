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
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsBankMasterBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel_ID;
import com.sanguine.webbooks.service.clsBankMasterService;

@Controller
public class clsBankMasterController {

	@Autowired
	private clsBankMasterService objBankMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open BankMaster
	@RequestMapping(value = "/frmBankMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmBankMaster", "command", new clsBankMasterModel());
		} else {
			return new ModelAndView("frmBankMaster_1", "command", new clsBankMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadBankMasterData", method = RequestMethod.GET)
	public @ResponseBody clsBankMasterModel funAssignFields(@RequestParam("bankCode") String bankCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsBankMasterModel objModel = objBankMasterService.funGetBankMaster(bankCode, clientCode);
		if (null == objModel) {
			objModel = new clsBankMasterModel();
			objModel.setStrBankCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update BankMaster
	@RequestMapping(value = "/saveBankMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsBankMasterBean objBean, BindingResult result, HttpServletRequest req) {
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
			clsBankMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objBankMasterService.funAddUpdateBankMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Bank Code : ".concat(objModel.getStrBankCode()));

			return new ModelAndView("redirect:/frmBankMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmBankMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsBankMasterModel funPrepareModel(clsBankMasterBean objBean, String userCode, String clientCode, String propCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsBankMasterModel objModel;
		if (objBean.getStrBankCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblbankmaster", "BankMaster", "intGId", clientCode);
			String bankCode = String.format("%06d", lastNo);
			objModel = new clsBankMasterModel(new clsBankMasterModel_ID(bankCode, clientCode));
			objModel.setIntGId(lastNo);
		} else {
			objModel = new clsBankMasterModel(new clsBankMasterModel_ID(objBean.getStrBankCode(), clientCode));
		}

		objModel.setStrBankName(objBean.getStrBankName().toUpperCase());
		objModel.setStrBranch(objBean.getStrBranch());
		objModel.setStrMICR(objBean.getStrMICR());
		objModel.setStrUserCreated(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propCode);

		return objModel;

	}

}
