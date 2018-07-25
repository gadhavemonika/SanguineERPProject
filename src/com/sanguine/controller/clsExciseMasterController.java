package com.sanguine.controller;

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

import com.sanguine.bean.clsExciseMasterBean;
import com.sanguine.model.clsExciseMasterModel;
import com.sanguine.model.clsExciseMasterModel_ID;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.service.clsExciseMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseMasterController {

	@Autowired
	private clsExciseMasterService objExciseMasterService;

	clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmExciseMaster", method = RequestMethod.GET)
	public ModelAndView funOpenExciseMasterForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseMaster_1", "command", new clsExciseMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseMaster", "command", new clsExciseMasterBean());
		} else {
			return null;
		}

	}

	// Save or Update Excise master function to save or update record of master
	// into database and also validates all fields of form.
	@RequestMapping(value = "/saveExciseMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsExciseMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objExciseMasterService.funAddExcise(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "ExciseCode : ".concat(objModel.getStrExciseCode()));
			return new ModelAndView("redirect:/frmExciseMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmExciseMaster.html?saddr=" + urlHits);
		}
	}

	// Returns a single Excise master record by passing Excise code as primary
	// key. Also generates next Excise Code if transaction is for Save Master
	private clsExciseMasterModel funPrepareModel(clsExciseMasterBean exciseBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsExciseMasterModel exciseModel;
		if (exciseBean.getStrExciseCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblexcisemaster", "ExciseMaster", "strExciseCode", clientCode);
			String exciseCode = "Ex" + String.format("%06d", lastNo);
			exciseModel = new clsExciseMasterModel(new clsExciseMasterModel_ID(exciseCode, clientCode));
			exciseModel.setStrExciseCode(exciseCode);
			exciseModel.setStrUserCreated(userCode);
			exciseModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsExciseMasterModel objExcise = objExciseMasterService.funGetExcise(exciseBean.getStrExciseCode(), clientCode);
			if (null == objExcise) {
				lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblgroupmaster", "GroupMaster", "strExciseCode", clientCode);
				String exciseCode = "Ex" + String.format("%06d", lastNo);
				exciseModel = new clsExciseMasterModel(new clsExciseMasterModel_ID(exciseCode, clientCode));
				exciseModel.setStrUserCreated(userCode);
				exciseModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				exciseModel = new clsExciseMasterModel(new clsExciseMasterModel_ID(exciseBean.getStrExciseCode(), clientCode));
			}
		}
		exciseModel.setStrDesc(exciseBean.getStrDesc().toUpperCase());
		exciseModel.setStrSGCode(exciseBean.getStrSGCode());
		exciseModel.setStrRank(exciseBean.getStrRank());
		exciseModel.setStrCessTax(exciseBean.getStrCessTax());
		exciseModel.setStrCessTax(objGlobal.funIfNull(exciseBean.getStrCessTax(), "Y", "N"));
		exciseModel.setDblExcisePercent(exciseBean.getDblExcisePercent());
		exciseModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		exciseModel.setStrUserModified(userCode);
		exciseModel.setStrClientCode(exciseBean.getStrClientCode());
		return exciseModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExciseMasterModel funAssignFields(@RequestParam("exciseCode") String exciseCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsExciseMasterModel objExcise = objExciseMasterService.funGetExcise(exciseCode, clientCode);
		if (null == objExcise) {
			objExcise = new clsExciseMasterModel();
			objExcise.setStrExciseCode("Invalid Code");
		}

		return objExcise;
	}
}
