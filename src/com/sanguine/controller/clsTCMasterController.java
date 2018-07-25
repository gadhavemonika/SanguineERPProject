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

import com.sanguine.bean.clsTCMasterBean;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsTCMasterService;

@Controller
public class clsTCMasterController {
	@Autowired
	private clsTCMasterService objTCMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsGlobalFunctions objGlobal;
	@Autowired
	clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmTCMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTCMaster_1", "command", new clsTCMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmTCMaster", "command", new clsTCMasterModel());
		} else {
			return null;
		}

	}

	// Save or Update group master function to save or update record of master
	// into database and also validates all fields of form.
	@RequestMapping(value = "/saveTCMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsTCMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsTCMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objTCMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "TC Code : ".concat(objModel.getStrTCCode()));
			return new ModelAndView("redirect:/frmTCMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmTCMaster?saddr=" + urlHits, "command", new clsTCMasterModel());
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadTCData", method = RequestMethod.GET)
	public @ResponseBody clsTCMasterModel funAssignFields(@RequestParam("tcCode") String tcCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsTCMasterModel objTCModel = objTCMasterService.funGetTCMaster(tcCode, clientCode);
		if (null == objTCModel) {
			objTCModel = new clsTCMasterModel();
			objTCModel.setStrTCCode("Invalid Code");
		}

		return objTCModel;
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsTCMasterModel funPrepareModel(clsTCMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsTCMasterModel objModel = new clsTCMasterModel();
		if (objBean.getStrTCCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbltcmaster", "TCMaster", "intId", clientCode);
			String tcCode = "TC" + String.format("%07d", lastNo);
			objModel.setIntId(lastNo);
			objModel.setStrTCCode(tcCode);
			objModel.setStrUserCreated(userCode);
			objModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrClientCode(clientCode);
		} else {
			clsTCMasterModel objTCModel = objTCMasterService.funGetTCMaster(objBean.getStrTCCode(), clientCode);
			if (null == objTCModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbltcmaster", "TCMaster", "intId", clientCode);
				String tcCode = "TC" + String.format("%07d", lastNo);
				objModel.setIntId(lastNo);
				objModel.setStrTCCode(tcCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrClientCode(clientCode);
			}
			objModel.setStrTCCode(objBean.getStrTCCode());
		}

		objModel.setStrTCName(objBean.getStrTCName());
		objModel.setStrApplicable(objGlobal.funIfNull(objBean.getStrApplicable(), "N", "Y"));
		objModel.setIntMaxLength(objBean.getIntMaxLength());
		objModel.setStrUserEdited(userCode);
		objModel.setDtDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrDataPostFlag("N");
		return objModel;
	}

	@RequestMapping(value = "/checkTCName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("TCName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmTCMaster");
		return count;

	}
}
