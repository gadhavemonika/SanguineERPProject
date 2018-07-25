package com.sanguine.webbooks.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsCountryMasterBean;
import com.sanguine.webbooks.model.clsCountryMasterModel;
import com.sanguine.webbooks.service.clsCountryMasterService;

@Controller
public class clsCountryMasterController {

	@Autowired
	private clsCountryMasterService objCountryMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadCountryMasterData", method = RequestMethod.GET)
	public @ResponseBody clsCountryMasterModel funLoadMasterData(@RequestParam("countryCode") String countryCode, HttpServletRequest req) {
		clsCountryMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		objModel = objCountryMasterService.funGetCountryMaster(countryCode, clientCode);
		if (objModel == null) {
			objModel = new clsCountryMasterModel();
			objModel.setStrCountryCode("Invalid Code");
		}
		return objModel;
	}
}
