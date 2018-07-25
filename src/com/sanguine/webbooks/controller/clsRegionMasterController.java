package com.sanguine.webbooks.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsRegionMasterBean;
import com.sanguine.webbooks.model.clsRegionMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.service.clsRegionMasterService;

@Controller
public class clsRegionMasterController {

	@Autowired
	private clsRegionMasterService objRegionMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadRegionMasterData", method = RequestMethod.GET)
	public @ResponseBody clsRegionMasterModel funLoadMasterData(@RequestParam("regionCode") String regionCode, HttpServletRequest req) {
		clsRegionMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objModel = objRegionMasterService.funGetRegionMaster(regionCode, clientCode);
		if (null == objModel) {
			objModel = new clsRegionMasterModel();
			objModel.setStrRegionCode("Invalid Code");
		}
		return objModel;
	}
}
