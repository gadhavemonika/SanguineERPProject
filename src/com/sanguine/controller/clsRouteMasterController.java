package com.sanguine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.bean.clsRouteMasterBean;
import com.sanguine.bean.clsVehicleMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsRouteMasterModel;
import com.sanguine.model.clsRouteMasterModel_ID;
import com.sanguine.model.clsVehicleMasterModel;
import com.sanguine.model.clsVehicleMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsRouteMasterService;

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
public class clsRouteMasterController {

	@Autowired
	private clsRouteMasterService objRouteMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open RouteMaster
	@RequestMapping(value = "/frmRouteMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRouteMaster_1", "command", new clsRouteMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRouteMaster", "command", new clsRouteMasterModel());
		} else {
			return null;
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/LoadRouteMaster", method = RequestMethod.POST)
	public @ResponseBody clsRouteMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsRouteMasterBean objBean = new clsRouteMasterBean();
		String docCode = request.getParameter("docCode").toString();
		clsRouteMasterModel objRouteMaster = objRouteMasterService.funGetRouteMaster(docCode, clientCode);
		if (null == objRouteMaster) {
			objRouteMaster = new clsRouteMasterModel();
			objRouteMaster.setStrRouteCode("Invalid Code");
			return objRouteMaster;
		} else {
			return objRouteMaster;
		}

	}

	// Save or Update RouteMaster
	@RequestMapping(value = "/saveRouteMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsRouteMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsRouteMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objRouteMasterService.funAddUpdateRouteMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Route Code : ".concat(objModel.getStrRouteCode()));
			return new ModelAndView("redirect:/frmRouteMaster.html?saddr=" + urlHits);

		} else {

			return new ModelAndView("redirect:/frmRouteMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsRouteMasterModel funPrepareModel(clsRouteMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsRouteMasterModel objModel = null;

		if (objBean.getStrRouteCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblroutemaster", "routeMaster", "intId", clientCode);
			String rtCode = "RT" + String.format("%06d", lastNo);
			objModel = new clsRouteMasterModel(new clsRouteMasterModel_ID(rtCode, clientCode));
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsRouteMasterModel objRout = objRouteMasterService.funGetRouteMaster(objBean.getStrRouteCode(), clientCode);
			if (null == objRout) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblroutemaster", "routeMaster", "intId", clientCode);
				String rtCode = "RT" + String.format("%06d", lastNo);
				objModel = new clsRouteMasterModel(new clsRouteMasterModel_ID(rtCode, clientCode));
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsRouteMasterModel(new clsRouteMasterModel_ID(objBean.getStrRouteCode(), clientCode));
			}
		}
		objModel.setStrRouteName(objBean.getStrRouteName().toUpperCase());
		objModel.setStrDesc(objBean.getStrDesc());
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;

	}

}
