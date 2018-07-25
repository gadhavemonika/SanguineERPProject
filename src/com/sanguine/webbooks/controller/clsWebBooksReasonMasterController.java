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
import com.sanguine.webbooks.bean.clsWebBooksReasonMasterBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsWebBooksReasonMasterModel;
import com.sanguine.webbooks.service.clsWebBooksReasonMasterService;

@Controller
public class clsWebBooksReasonMasterController {

	@Autowired
	private clsWebBooksReasonMasterService objWebBooksReasonMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadWebBooksReasonMasterData", method = RequestMethod.GET)
	public @ResponseBody clsWebBooksReasonMasterModel funAssignFields(@RequestParam("reasonCode") String reasonCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebBooksReasonMasterModel objModel = objWebBooksReasonMasterService.funGetWebBooksReasonMaster(reasonCode, clientCode);
		if (null == objModel) {
			objModel = new clsWebBooksReasonMasterModel();
			objModel.setStrReasonCode("Invalid Code");
		}

		return objModel;
	}
}
