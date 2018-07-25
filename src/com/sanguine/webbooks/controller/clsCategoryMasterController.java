package com.sanguine.webbooks.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.model.clsCategoryMasterModel;
import com.sanguine.webbooks.service.clsCategoryMasterService;

@Controller(value = "webBooksCategoryMaster")
public class clsCategoryMasterController {

	@Autowired
	@Qualifier("webBooksclsCategoryMasterService")
	private clsCategoryMasterService objCategoryMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadCategoryData", method = RequestMethod.GET)
	public @ResponseBody clsCategoryMasterModel funAssignFields(@RequestParam("categoryCode") String categoryCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsCategoryMasterModel objModel = objCategoryMasterService.funGetCategoryMaster(categoryCode, clientCode);
		if (null == objModel) {
			objModel = new clsCategoryMasterModel();
			objModel.setStrCatCode("Invalid Code");
		}

		return objModel;
	}
}
