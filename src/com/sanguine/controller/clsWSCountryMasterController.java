package com.sanguine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.bean.clsCountryMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsWSCountryMasterModel;
import com.sanguine.model.clsWSCountryMasterModel_ID;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.service.clsWSCountryMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.model.clsWebClubCountryMasterModel;

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
public class clsWSCountryMasterController {

	@Autowired
	private clsWSCountryMasterService objCountryMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open CountryMaster
	@RequestMapping(value = "/frmWSCountryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWSCountryMaster_1", "command", new clsCountryMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWSCountryMaster", "command", new clsCountryMasterBean());
		} else {
			return null;
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadWSCountryCode", method = RequestMethod.GET)
	public @ResponseBody clsWSCountryMasterModel funLoadMasterData(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String docCode = request.getParameter("docCode").toString();
		clsWSCountryMasterModel objCountry = objCountryMasterService.funGetCountryMaster(docCode, clientCode);
		if (null == objCountry) {
			objCountry = new clsWSCountryMasterModel();
			objCountry.setStrCountryCode("Invalid Code");
		}

		return objCountry;
	}

	// Save or Update CountryMaster
	@RequestMapping(value = "/saveWSCountryMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCountryMasterBean objBean, BindingResult result, HttpServletRequest req) {

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsWSCountryMasterModel objCountryModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objCountryMasterService.funAddUpdateCountryMaster(objCountryModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Country Code : ".concat(objCountryModel.getStrCountryCode()));

			return new ModelAndView("redirect:/frmWSCountryMaster.html");
		} else {
			return new ModelAndView("frmWSCountryMaster");
		}
	}

	// Convert bean to model function
	private clsWSCountryMasterModel funPrepareModel(clsCountryMasterBean objBean, String userCode, String clientCode, String propCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWSCountryMasterModel objCountry;
		if (objBean.getStrCountryCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcountrymaster", "CountryMaster", "intGId", clientCode);
			String countryCode = "CO" + String.format("%06d", lastNo);
			objCountry = new clsWSCountryMasterModel(new clsWSCountryMasterModel_ID(countryCode, clientCode));
			objCountry.setIntGId(lastNo);
			objCountry.setStrUserCreated(userCode);
			objCountry.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWSCountryMasterModel objCountryModel = objCountryMasterService.funGetCountryMaster(objBean.getStrCountryCode(), clientCode);
			if (null == objCountryModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcountrymaster", "CountryMaster", "intGId", clientCode);
				String countryCode = "CO" + String.format("%06d", lastNo);
				objCountry = new clsWSCountryMasterModel(new clsWSCountryMasterModel_ID(countryCode, clientCode));
				objCountry.setIntGId(lastNo);
				objCountry.setStrUserCreated(userCode);
				objCountry.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objCountry = new clsWSCountryMasterModel(new clsWSCountryMasterModel_ID(objBean.getStrCountryCode(), clientCode));
			}
		}
		objCountry.setStrCountryName(objBean.getStrCountryName().toUpperCase());
		objCountry.setStrUserModified(userCode);
		objCountry.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objCountry.setStrPropertyCode(propCode);
		return objCountry;

	}

}
