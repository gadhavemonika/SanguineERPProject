package com.sanguine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.bean.clsWSCityMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsWSCityMasterModel;
import com.sanguine.model.clsWSCityMasterModel_ID;
import com.sanguine.model.clsWSStateMasterModel;
import com.sanguine.model.clsWSStateMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsWSCityMasterService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class clsWSCityMasterController {

	@Autowired
	private clsWSCityMasterService objWSCityMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open WSCityMaster
	@RequestMapping(value = "/frmWSCityMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWSCityMaster_1", "command", new clsWSCityMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWSCityMaster", "command", new clsWSCityMasterModel());
		} else {
			return null;
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadWSCityCode", method = RequestMethod.POST)
	public @ResponseBody clsWSCityMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String docCode = request.getParameter("docCode").toString();
		clsWSCityMasterModel objCity = objWSCityMasterService.funGetWSCityMaster(docCode, clientCode);
		if (null == objCity) {
			objCity = new clsWSCityMasterModel();
			objCity.setStrCityCode("Invalid Code");
		}

		return objCity;
	}

	// Save or Update WSCityMaster
	@RequestMapping(value = "/saveWSCityMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWSCityMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsWSCityMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objWSCityMasterService.funAddUpdateWSCityMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "City Code : ".concat(objModel.getStrCityCode()));

			return new ModelAndView("redirect:/frmWSCityMaster.html");
		} else {
			return new ModelAndView("frmWSCityMaster");
		}
	}

	// Convert bean to model function
	private clsWSCityMasterModel funPrepareModel(clsWSCityMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWSCityMasterModel objModel = null;

		if (objBean.getStrStateCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcitymaster", "cityMaster", "intGId", clientCode);
			String ctCode = "CT" + String.format("%06d", lastNo);
			objModel = new clsWSCityMasterModel(new clsWSCityMasterModel_ID(ctCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWSCityMasterModel objCityModel = objWSCityMasterService.funGetWSCityMaster(objBean.getStrCityCode(), clientCode);
			if (null == objCityModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcitymaster", "cityMaster", "intGId", clientCode);
				String ctCode = "CT" + String.format("%06d", lastNo);
				objModel = new clsWSCityMasterModel(new clsWSCityMasterModel_ID(ctCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsWSCityMasterModel(new clsWSCityMasterModel_ID(objBean.getStrCityCode(), clientCode));
			}
		}
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrCityName(objBean.getStrCityName());
		objModel.setStrCountryCode(objBean.getStrCountryCode());
		objModel.setStrStateCode(objBean.getStrStateCode());
		objModel.setStrPropertyCode(objBean.getStrPropertyCode());

		return objModel;

	}

}
