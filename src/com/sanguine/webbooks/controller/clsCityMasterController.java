package com.sanguine.webbooks.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.model.clsCityMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.service.clsCityMasterService;

@Controller(value = "webbooks")
public class clsCityMasterController {

	@Autowired
	private clsCityMasterService objCityMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadCityMasterData", method = RequestMethod.GET)
	public @ResponseBody clsCityMasterModel funAssignFields(@RequestParam("cityCode") String cityCode, HttpServletRequest req) {
		clsCityMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objModel = objCityMasterService.funGetCityMaster(cityCode, clientCode);
		if (null == objModel) {
			objModel = new clsCityMasterModel();
			objModel.setStrCityCode("Invalid Code");
		}
		return objModel;
	}

}
