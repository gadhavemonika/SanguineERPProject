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
import com.sanguine.webbooks.bean.clsNarrationMasterBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;
import com.sanguine.webbooks.model.clsNarrationMasterModel;
import com.sanguine.webbooks.model.clsNarrationMasterModel_ID;
import com.sanguine.webbooks.service.clsNarrationMasterService;

@Controller
public class clsNarrationMasterController {

	@Autowired
	private clsNarrationMasterService objNarrationMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open NarrationMaster
	@RequestMapping(value = "/frmNarrationMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmNarrationMaster", "command", new clsNarrationMasterModel());
		} else {
			return new ModelAndView("frmNarrationMaster_1", "command", new clsNarrationMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadNarrationMasterData", method = RequestMethod.GET)
	public @ResponseBody clsNarrationMasterModel funAssignFields(@RequestParam("remarkCode") String remarkCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsNarrationMasterModel objModel = objNarrationMasterService.funGetNarrationMaster(remarkCode, clientCode);
		if (null == objModel) {
			objModel = new clsNarrationMasterModel();
			objModel.setStrRemarkCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update NarrationMaster
	@RequestMapping(value = "/saveNarrationMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsNarrationMasterBean objBean, BindingResult result, HttpServletRequest req) {
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

			clsNarrationMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objNarrationMasterService.funAddUpdateNarrationMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Remark Code : ".concat(objModel.getStrRemarkCode()));

			return new ModelAndView("redirect:/frmNarrationMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmNarrationMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsNarrationMasterModel funPrepareModel(clsNarrationMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsNarrationMasterModel objModel;
		if (objBean.getStrRemarkCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblremarkmaster", "RemarkMaster", "intId", clientCode);
			String remarkCode = "RC" + String.format("%06d", lastNo);
			objModel = new clsNarrationMasterModel(new clsNarrationMasterModel_ID(remarkCode, clientCode));
			objModel.setIntId(lastNo);

		} else {
			objModel = new clsNarrationMasterModel(new clsNarrationMasterModel_ID(objBean.getStrRemarkCode(), clientCode));
		}
		objModel.setStrDescription(objBean.getStrDescription());
		objModel.setStrActiveYN(objGlobal.funIfNull(objBean.getStrActiveYN(), "No", objBean.getStrActiveYN()));

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;

	}

}
