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
import com.sanguine.webbooks.bean.clsAreaMasterBean;
import com.sanguine.webbooks.model.clsAreaMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.service.clsAreaMasterService;

@Controller
public class clsAreaMasterController {

	@Autowired
	private clsAreaMasterService objAreaMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Load Master Data On Form
	@RequestMapping(value = "/loadAreaMasterData", method = RequestMethod.GET)
	public @ResponseBody clsAreaMasterModel funAssignFields(@RequestParam("areaCode") String areaCode, HttpServletRequest req) {
		clsAreaMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objModel = objAreaMasterService.funGetAreaMaster(areaCode, clientCode);
		if (null == objModel) {
			objModel = new clsAreaMasterModel();
			objModel.setStrAreaCode("Invalid Code");
		}
		return objModel;
	}

	// Save or Update AreaMaster
	@RequestMapping(value = "/saveAreaMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAreaMasterBean objBean, BindingResult result, HttpServletRequest req) {
		return null;
	}

	// Convert bean to model function
	private clsAreaMasterModel funPrepareModel(clsAreaMasterBean objBean, String userCode, String clientCode) {

		return null;

	}

}
