package com.sanguine.excise.controller;

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

import com.sanguine.bean.clsPropertyMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertyMaster_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsPropertyMasterService;

@Controller
public class clsExcisePropertyMaster {
	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmExcisePropertyMaster", method = RequestMethod.GET)
	public ModelAndView funOpenMonthForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePropertyMaster_1", "command", new clsPropertyMaster());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePropertyMaster", "command", new clsPropertyMaster());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveExcisePropertyMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPropertyMasterBean propertyBean, BindingResult result, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {
			clsPropertyMaster obProperty = funPrepareModel(propertyBean, userCode, clientCode);
			objPropertyMasterService.funAddProperty(obProperty);
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("successMessage", "Property Code : ".concat(obProperty.getPropertyCode()));
			return new ModelAndView("redirect:/frmExcisePropertyMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmExcisePropertyMaster?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadExcisePropertyMasterData", method = RequestMethod.GET)
	public @ResponseBody clsPropertyMaster funAssignFields(@RequestParam("Code") String propertyCode, HttpServletRequest req) {
		clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propertyCode, req.getSession().getAttribute("clientCode").toString());
		if (null == objProperty) {
			objProperty = new clsPropertyMaster();
			objProperty.setPropertyCode("Invalid Code");
			return objProperty;
		} else {

			return objProperty;
		}
	}

	private clsPropertyMaster funPrepareModel(clsPropertyMasterBean propertyBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsPropertyMaster property;
		clsGlobalFunctions ob = new clsGlobalFunctions();
		if (propertyBean.getPropertyCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("dbwebmms.tblpropertymaster", "PropertyMaster", "intId", clientCode);
			String propertyCode = String.format("%02d", lastNo);
			property = new clsPropertyMaster(new clsPropertyMaster_ID(propertyCode, clientCode));
			property.setIntId(lastNo);
			property.setStrUserCreated(userCode);
			property.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsPropertyMaster objProperty = objPropertyMasterService.funGetProperty(propertyBean.getPropertyCode(), clientCode);
			if (null == objProperty) {
				lastNo = objGlobalFunctionsService.funGetLastNo("dbwebmms.tblpropertymaster", "PropertyMaster", "intId", clientCode);
				String propertyCode = String.format("%02d", lastNo);
				property = new clsPropertyMaster(new clsPropertyMaster_ID(propertyCode, clientCode));
				property.setIntId(lastNo);
				property.setStrUserCreated(userCode);
				property.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				property = new clsPropertyMaster(new clsPropertyMaster_ID(propertyBean.getPropertyCode(), clientCode));
			}
		}

		property.setPropertyName(propertyBean.getPropertyName());
		property.setStrUserModified(userCode);
		property.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));

		return property;
	}

	@RequestMapping(value = "/checkExcisePropertName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("porpName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmPropertyMaster");
		return count;

	}

}
