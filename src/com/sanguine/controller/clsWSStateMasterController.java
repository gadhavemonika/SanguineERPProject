package com.sanguine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsWSStateMasterBean;
import com.sanguine.model.clsWSStateMasterModel;
import com.sanguine.model.clsWSStateMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsWSStateMasterService;

@Controller
public class clsWSStateMasterController {

	@Autowired
	private clsWSStateMasterService objWSStateMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open WSStateMaster
	@RequestMapping(value = "/frmWSStateMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWSStateMaster_1", "command", new clsWSStateMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWSStateMaster", "command", new clsWSStateMasterBean());
		} else {
			return null;
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadWSStateCode", method = RequestMethod.POST)
	public @ResponseBody clsWSStateMasterModel funLoadMasterData(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String docCode = request.getParameter("docCode").toString();
		clsWSStateMasterModel objState = objWSStateMasterService.funGetWSStateMaster(docCode, clientCode);
		if (null == objState) {
			objState = new clsWSStateMasterModel();
			objState.setStrStateCode("Invalid Code");
		}

		return objState;
	}

	// Save or Update WSStateMaster
	@RequestMapping(value = "/saveWSStateMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWSStateMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsWSStateMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objWSStateMasterService.funAddUpdateWSStateMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "State Code : ".concat(objModel.getStrStateCode()));
			return new ModelAndView("redirect:/frmWSStateMaster.html");
		} else {
			return new ModelAndView("frmWSStateMaster");
		}
	}

	// Convert bean to model function
	private clsWSStateMasterModel funPrepareModel(clsWSStateMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWSStateMasterModel objModel = null;

		if (objBean.getStrStateCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblstatemaster", "StateMaster", "intGId", clientCode);
			String stateCode = "ST" + String.format("%06d", lastNo);
			objModel = new clsWSStateMasterModel(new clsWSStateMasterModel_ID(stateCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWSStateMasterModel objStateModel = objWSStateMasterService.funGetWSStateMaster(objBean.getStrCountryCode(), clientCode);
			if (null == objStateModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblstatemaster", "StateMaster", "intGId", clientCode);
				String stateCode = "ST" + String.format("%06d", lastNo);
				objModel = new clsWSStateMasterModel(new clsWSStateMasterModel_ID(stateCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsWSStateMasterModel(new clsWSStateMasterModel_ID(objBean.getStrStateCode(), clientCode));
			}
		}
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrCountryCode(objBean.getStrCountryCode());
		objModel.setStrPropertyCode(objBean.getStrPropertyCode());
		objModel.setStrStateName(objBean.getStrStateName());
		objModel.setStrStateDesc(objBean.getStrStateDesc());

		return objModel;
	}

}
