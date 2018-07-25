package com.sanguine.excise.controller;

import java.util.Map;

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
import com.sanguine.excise.bean.clsCityMasterBean;
import com.sanguine.excise.model.clsCityMasterModel;
import com.sanguine.excise.service.clsCityMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsCityMasterController {

	@Autowired
	private clsCityMasterService objCityMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open CityMaster
	@RequestMapping(value = "/frmExciseCityMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseCityMaster_1", "command", new clsCityMasterModel());
		} else {
			return new ModelAndView("frmExciseCityMaster", "command", new clsCityMasterModel());
		}
	}

	// Save or Update CityMaster
	@RequestMapping(value = "/saveExciseCityMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(Map<String, Object> model, @ModelAttribute("command") @Valid clsCityMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (!result.hasErrors()) {
			clsCityMasterModel objclsCityMasterModel = funPrepareModel(objBean, req);
			boolean success = objCityMasterService.funAddUpdateCityMaster(objclsCityMasterModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "City Name : ".concat(objclsCityMasterModel.getStrCityName()));
				return new ModelAndView("redirect:/frmExciseCityMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseCityMaster.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseCityMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsCityMasterModel funPrepareModel(clsCityMasterBean objBean, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsCityMasterModel objModel = new clsCityMasterModel();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String isCityGlobal = "Custom";
		try {
			isCityGlobal = req.getSession().getAttribute("strCity").toString();
		} catch (Exception e) {
			isCityGlobal = "Custom";
		}
		if (isCityGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		if (objBean != null) {
			if (!(objBean.getStrCityCode().isEmpty())) {
				clsCityMasterModel objModel1 = objCityMasterService.funGetObject(objBean.getStrCityCode(), clientCode);
				if (objModel1 != null) {
					objModel.setStrCityCode(objModel1.getStrCityCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblcitymaster", "intId");
				String categoryCode = "C" + String.format("%05d", lastNo);

				objModel.setStrCityCode(categoryCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrCityName(objBean.getStrCityName());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseCityMasterData", method = RequestMethod.GET)
	public @ResponseBody clsCityMasterModel funAssignFieldsForForm(@RequestParam("cityCode") String cityCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isCityGlobal = "Custom";
		try {
			isCityGlobal = req.getSession().getAttribute("strCity").toString();
		} catch (Exception e) {
			isCityGlobal = "Custom";
		}
		if (isCityGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		clsCityMasterModel objModel = objCityMasterService.funGetObject(cityCode, clientCode);
		if (null == objModel) {
			objModel = new clsCityMasterModel();
			objModel.setStrCityCode("Invalid Code");
		}

		return objModel;
	}

}
