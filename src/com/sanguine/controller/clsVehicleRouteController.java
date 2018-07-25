package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.sanguine.bean.clsGroupMasterBean;
import com.sanguine.bean.clsRouteMasterBean;
import com.sanguine.bean.clsVehicleMasterBean;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsRouteMasterModel;
import com.sanguine.model.clsVehicleMasterModel;
import com.sanguine.model.clsVehicleRouteModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsRouteMasterService;
import com.sanguine.service.clsVehicleMasterService;

@Controller
public class clsVehicleRouteController {

	@Autowired
	private clsVehicleMasterService objVehicleMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsRouteMasterService objRouteMasterService;

	// Open VehicleMaster
	@RequestMapping(value = "/frmVehicleRouteMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVehicleRoute_1", "command", new clsVehicleMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmVehicleRoute", "command", new clsVehicleMasterBean());
		} else {
			return null;
		}
	}

	// Save or Update group master function to save or update record of master
	// into database and also validates all fields of form.
	@RequestMapping(value = "/saveVehicleRoute", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsVehicleMasterBean ObjBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			long lastNo = 0;
			if (null != ObjBean.getListclsVehicleRouteModel()) {
				objVehicleMasterService.funDeleteVehRouteDtl(ObjBean.getStrVehCode(), clientCode);
			}

			for (clsVehicleRouteModel ObjVehRoute : ObjBean.getListclsVehicleRouteModel()) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblvehroutedtl", "VehicleRoutedtl", "intId", clientCode);
				ObjVehRoute.setIntId(lastNo);
				clsVehicleMasterModel objVehMaster = objVehicleMasterService.funGetVehicleCode(ObjVehRoute.getStrVehNo(), clientCode);
				ObjVehRoute.setStrVehCode(objVehMaster.getStrVehCode());
				;

				clsRouteMasterModel objRouteModel = objRouteMasterService.funGetRouteNameMaster(ObjVehRoute.getStrRouteName(), clientCode);
				ObjVehRoute.setStrRouteCode(objRouteModel.getStrRouteCode());

				ObjVehRoute.setStrUserCreated(userCode);
				ObjVehRoute.setStrUserModified(userCode);
				ObjVehRoute.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				ObjVehRoute.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				ObjVehRoute.setDtFromDate(ObjVehRoute.getDtFromDate());
				ObjVehRoute.setDtToDate(ObjVehRoute.getDtToDate());
				ObjVehRoute.setStrClientCode(clientCode);
				objVehicleMasterService.funAddUpdateVehicleRouteMaster(ObjVehRoute);
			}

			// System.out.println(objVehicleMasterService.funGetListVehicleRouteModel("V000001",
			// clientCode));

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Vehicle Route Saved ");
			if ("2".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmVehicleRoute_1", "command", new clsVehicleMasterBean());
			} else if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmVehicleRoute", "command", new clsVehicleMasterBean());
			}
		}
		return new ModelAndView("frmVehicleRoute", "command", new clsVehicleMasterBean());
	}

	// Load Dtl Data On Form
	@SuppressWarnings("unused")
	@RequestMapping(value = "/VehRouteDtl", method = RequestMethod.POST)
	public @ResponseBody List funLoadVehRouteDtl(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsRouteMasterBean objBean = new clsRouteMasterBean();

		String vehCode = request.getParameter("vehCode").toString();

		List objVehRouteDtl = objVehicleMasterService.funGetListVehicleRouteModel(vehCode, clientCode);

		List objVehRout = new ArrayList<clsVehicleRouteModel>();

		if (null != objVehRouteDtl) {
			for (int i = 0; i < objVehRouteDtl.size(); i++) {
				clsVehicleRouteModel obj = (clsVehicleRouteModel) objVehRouteDtl.get(i);

				clsVehicleMasterModel objVehMaster = objVehicleMasterService.funGetVehicleMaster(obj.getStrVehCode(), clientCode);
				obj.setStrVehNo(objVehMaster.getStrVehNo());
				clsRouteMasterModel objRouteModel = objRouteMasterService.funGetRouteMaster(obj.getStrRouteCode(), clientCode);
				obj.setStrRouteName(objRouteModel.getStrRouteName());
				objVehRout.add(obj);
			}
		}

		return objVehRout;

	}

}
