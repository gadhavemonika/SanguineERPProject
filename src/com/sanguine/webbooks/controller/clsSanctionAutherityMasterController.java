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
import com.sanguine.webbooks.bean.clsInterfaceMasterBean;
import com.sanguine.webbooks.bean.clsSanctionAutherityMasterBean;
import com.sanguine.webbooks.model.clsInterfaceMasterModel;
import com.sanguine.webbooks.model.clsInterfaceMasterModel_ID;
import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel;
import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel_ID;
import com.sanguine.webbooks.service.clsSanctionAutherityMasterService;

@Controller
public class clsSanctionAutherityMasterController {

	@Autowired
	private clsSanctionAutherityMasterService objSanctionAutherityMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open SanctionAutherityMaster
	@RequestMapping(value = "/frmSanctionAutherityMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ArrayList<String> listOperational = new ArrayList<String>();
		listOperational.add("Yes");
		listOperational.add("No");

		model.put("urlHits", urlHits);
		model.put("listOperational", listOperational);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmSanctionAutherityMaster", "command", new clsSanctionAutherityMasterModel());
		} else {
			return new ModelAndView("frmSanctionAutherityMaster_1", "command", new clsSanctionAutherityMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadSanctionAutherityMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSanctionAutherityMasterModel funAssignFields(@RequestParam("sanctionCode") String sanctionCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsSanctionAutherityMasterModel objModel = objSanctionAutherityMasterService.funGetSanctionAutherityMaster(sanctionCode, clientCode);
		if (null == objModel) {
			objModel = new clsSanctionAutherityMasterModel();
			objModel.setStrSanctionCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update SanctionAutherityMaster
	@RequestMapping(value = "/saveSanctionAutherityMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSanctionAutherityMasterBean objBean, BindingResult result, HttpServletRequest req) {
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

			clsSanctionAutherityMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);
			objSanctionAutherityMasterService.funAddUpdateSanctionAutherityMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Sanction Autherity Code : ".concat(objModel.getStrSanctionCode()));

			return new ModelAndView("redirect:/frmSanctionAutherityMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmSanctionAutherityMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsSanctionAutherityMasterModel funPrepareModel(clsSanctionAutherityMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsSanctionAutherityMasterModel objModel;
		if (objBean.getStrSanctionCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsanctionauthmaster", "SanctionAutherityMaster", "intGId", clientCode);
			String sanctionCode = "SC" + String.format("%06d", lastNo);
			objModel = new clsSanctionAutherityMasterModel(new clsSanctionAutherityMasterModel_ID(sanctionCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsSanctionAutherityMasterModel(new clsSanctionAutherityMasterModel_ID(objBean.getStrSanctionCode(), clientCode));
		}
		objModel.setStrSanctionName(objBean.getStrSanctionName());
		objModel.setStrOperational(objGlobal.funIfNull(objBean.getStrOperational(), "No", objBean.getStrOperational()));

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;

	}

}
