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
import com.sanguine.webbooks.bean.clsInterfaceMasterBean;
import com.sanguine.webbooks.model.clsInterfaceMasterModel;
import com.sanguine.webbooks.model.clsInterfaceMasterModel_ID;
import com.sanguine.webbooks.model.clsNarrationMasterModel;
import com.sanguine.webbooks.model.clsNarrationMasterModel_ID;
import com.sanguine.webbooks.service.clsInterfaceMasterService;

@Controller
public class clsInterfaceMasterController {

	@Autowired
	private clsInterfaceMasterService objInterfaceMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open InterfaceMaster
	@RequestMapping(value = "/frmInterfaceMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmInterfaceMaster", "command", new clsInterfaceMasterModel());
		} else {
			return new ModelAndView("frmInterfaceMaster_1", "command", new clsInterfaceMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadInterfaceMasterData", method = RequestMethod.GET)
	public @ResponseBody clsInterfaceMasterModel funAssignFields(@RequestParam("interfaceCode") String interfaceCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsInterfaceMasterModel objModel = objInterfaceMasterService.funGetInterfaceMaster(interfaceCode, clientCode);
		if (null == objModel) {
			objModel = new clsInterfaceMasterModel();
			objModel.setStrInterfaceCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update InterfaceMaster
	@RequestMapping(value = "/saveInterfaceMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsInterfaceMasterBean objBean, BindingResult result, HttpServletRequest req) {
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

			clsInterfaceMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);
			objInterfaceMasterService.funAddUpdateInterfaceMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Interface Code : ".concat(objModel.getStrInterfaceCode()));

			return new ModelAndView("redirect:/frmInterfaceMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmInterfaceMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsInterfaceMasterModel funPrepareModel(clsInterfaceMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsInterfaceMasterModel objModel;
		if (objBean.getStrInterfaceCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblposlinkupmaster", "InterfaceMaster", "intGId", clientCode);
			String interfaceCode = "IC" + String.format("%06d", lastNo);
			objModel = new clsInterfaceMasterModel(new clsInterfaceMasterModel_ID(interfaceCode, clientCode));
			objModel.setIntGId(lastNo);
		} else {
			objModel = new clsInterfaceMasterModel(new clsInterfaceMasterModel_ID(objBean.getStrInterfaceCode(), clientCode));
		}
		objModel.setStrInterfaceName(objBean.getStrInterfaceName());
		objModel.setStrAccountCode(objBean.getStrAccountCode());
		objModel.setStrAccountName(objBean.getStrAccountName());

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;
	}

}
