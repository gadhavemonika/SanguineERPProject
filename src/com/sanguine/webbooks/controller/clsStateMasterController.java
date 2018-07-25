package com.sanguine.webbooks.controller;

import java.util.List;

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
import com.sanguine.webbooks.bean.clsStateMasterBean;
import com.sanguine.webbooks.model.clsCityMasterModel;
import com.sanguine.webbooks.model.clsStateMasterModel;
import com.sanguine.webbooks.service.clsStateMasterService;

@Controller
public class clsStateMasterController {

	@Autowired
	private clsStateMasterService objStateMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadStateMasterData", method = RequestMethod.GET)
	public @ResponseBody clsStateMasterModel funAssignFields(@RequestParam("stateCode") String stateCode, HttpServletRequest req) {
		clsStateMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objModel = objStateMasterService.funGetStateMaster(stateCode, clientCode);
		if (null == objModel) {
			objModel = new clsStateMasterModel();
			objModel.setStrStateCode("Invalid Code");
		}
		return objModel;
	}
}
