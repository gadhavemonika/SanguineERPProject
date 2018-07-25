package com.sanguine.controller;

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

import com.sanguine.bean.clsProcessMasterBean;
import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProcessMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProcessMasterService;

@Controller
public class clsProcessMasterController {

	@Autowired
	private clsProcessMasterService objProcessMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open ProcessMaster
	@RequestMapping(value = "/frmProcessMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProcessMaster_1", "command", new clsProcessMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProcessMaster", "command", new clsProcessMasterModel());
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProcessData1", method = RequestMethod.GET)
	public @ResponseBody clsProcessMasterModel funAssignProcess(@RequestParam("processCode") String processCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsProcessMasterModel objProcessMaster = objProcessMasterService.funGetProcessMaster(processCode, clientCode);
		if (null == objProcessMaster) {
			objProcessMaster = new clsProcessMasterModel();
			objProcessMaster.setStrProcessCode("Invalid Code");
		}

		return objProcessMaster;
	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmProcessMaster1", method = RequestMethod.POST)
	public @ResponseBody clsProcessMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String usercode = request.getSession().getAttribute("usercode").toString();
		clsProcessMasterBean objBean = new clsProcessMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsProcessMasterModel objProcessMaster = new clsProcessMasterModel();
		return objProcessMaster;
	}

	// Save or Update ProcessMaster
	@RequestMapping(value = "/saveProcessMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsProcessMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String usercode = req.getSession().getAttribute("usercode").toString();
			clsProcessMasterModel objModel = funPrepareModel(objBean, usercode, clientCode);
			objProcessMasterService.funAddUpdateProcessMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", " processCode: ".concat(objModel.getStrProcessCode()));

			return new ModelAndView("redirect:/frmProcessMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmProcessMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsProcessMasterModel funPrepareModel(clsProcessMasterBean objBean, String usercode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsProcessMasterModel process;
		if (objBean.getStrProcessCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblprocessMaster", "ProcessMaster", "intId", clientCode);
			String processCode = "PR" + String.format("%06d", lastNo);
			process = new clsProcessMasterModel(new clsProcessMasterModel_ID(processCode, clientCode));
			process.setIntId(lastNo);
			process.setStrUserCreated(usercode);
			process.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsProcessMasterModel obProcess = objProcessMasterService.funGetProcessMaster(objBean.getStrProcessCode(), clientCode);
			if (null == obProcess) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblprocessmaster", "processMaster", "intId", clientCode);
				String processCode = "PR" + String.format("%06d", lastNo);
				process = new clsProcessMasterModel(new clsProcessMasterModel_ID(processCode, clientCode));
				process.setIntId(lastNo);
				process.setStrUserCreated(usercode);
				process.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				process = new clsProcessMasterModel(new clsProcessMasterModel_ID(objBean.getStrProcessCode(), clientCode));

			}
		}
		process.setStrProcessName(objBean.getStrProcessName().toUpperCase());
		process.setStrDesc(objBean.getStrDesc());
		process.setStrUserModified(usercode);
		process.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		return process;

	}

}
