package com.sanguine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.bean.clsVehicleMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.model.clsVehicleMasterModel;
import com.sanguine.model.clsVehicleMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsVehicleMasterService;

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
public class clsVehicleMasterController {

	@Autowired
	private clsVehicleMasterService objVehicleMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open VehicleMaster
	@RequestMapping(value = "/frmVehicleMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVehicleMaster_1", "command", new clsVehicleMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVehicleMaster", "command", new clsVehicleMasterModel());
		} else {
			return null;
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/LoadVehicleMaster", method = RequestMethod.GET)
	public @ResponseBody clsVehicleMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsVehicleMasterBean objBean = new clsVehicleMasterBean();
		String docCode = request.getParameter("docCode").toString();

		clsVehicleMasterModel objVeh = objVehicleMasterService.funGetVehicleMaster(docCode, clientCode);
		if (null == objVeh) {
			objVeh = new clsVehicleMasterModel();
			objVeh.setStrVehCode("Invalid Code");
			return objVeh;
		} else {
			return objVeh;
		}

	}

	// Save or Update VehicleMaster
	@RequestMapping(value = "/saveVehicleMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsVehicleMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsVehicleMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objVehicleMasterService.funAddUpdateVehicleMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Vehicle Code : ".concat(objModel.getStrVehCode()));
			return new ModelAndView("redirect:/frmVehicleMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmVehicleMaster.html?saddr=" + urlHits);
		}

	}

	// Convert bean to model function
	private clsVehicleMasterModel funPrepareModel(clsVehicleMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsVehicleMasterModel objModel = null;

		if (objBean.getStrVehCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblvehiclemaster", "VehicleMaster", "intId", clientCode);
			String vCode = "V" + String.format("%06d", lastNo);
			objModel = new clsVehicleMasterModel(new clsVehicleMasterModel_ID(vCode, clientCode));
			objModel.setIntId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsVehicleMasterModel objVeh = objVehicleMasterService.funGetVehicleMaster(objBean.getStrVehCode(), clientCode);
			if (null == objVeh) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblvehiclemaster", "VehicleMaster", "intId", clientCode);
				String vCode = "V" + String.format("%06d", lastNo);
				objModel = new clsVehicleMasterModel(new clsVehicleMasterModel_ID(vCode, clientCode));
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsVehicleMasterModel(new clsVehicleMasterModel_ID(objBean.getStrVehCode(), clientCode));
			}
		}
		objModel.setStrVehNo(objBean.getStrVehNo().toUpperCase());
		objModel.setStrDesc(objBean.getStrDesc());
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;

	}

}
