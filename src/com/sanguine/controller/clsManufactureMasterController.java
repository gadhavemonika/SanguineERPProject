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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsManufactureMasterBean;
import com.sanguine.model.clsManufactureMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsManufactureMasterService;

@Controller
public class clsManufactureMasterController {
	@Autowired
	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	clsManufactureMasterService objManufactureMasterService;

	@RequestMapping(value = "/frmManufactureMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmManufactureMaster_1", "command", new clsManufactureMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmManufactureMaster", "command", new clsManufactureMasterBean());
		} else {
			return null;
		}
	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveManufactureMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsManufactureMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			clsManufactureMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			objManufactureMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Manufacturer Code : ".concat(objModel.getStrManufacturerCode()));
			return new ModelAndView("redirect:/frmManufactureMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmManufactureMaster?saddr=" + urlHits, "command", new clsManufactureMasterBean());
		}

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadManufactureMasterData", method = RequestMethod.GET)
	public @ResponseBody clsManufactureMasterModel funAssignFields(@RequestParam("code") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsManufactureMasterModel objModel = objManufactureMasterService.funGetObject(code, clientCode);
		if (null == objModel) {
			objModel = new clsManufactureMasterModel();
			objModel.setStrManufacturerCode("Invalid Code");
			return objModel;
		} else {
			return objModel;
		}

	}

	private clsManufactureMasterModel funPrepareModel(clsManufactureMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;

		clsManufactureMasterModel objModel1 = objManufactureMasterService.funGetObject(objBean.getStrManufacturerCode(), clientCode);
		clsManufactureMasterModel objModel = new clsManufactureMasterModel();
		if (objModel1 != null) {

			objModel.setStrManufacturerCode(objModel1.getStrManufacturerCode());
			objModel.setIntGId(objModel1.getIntGId());
			objModel.setStrUserCreated(objModel1.getStrUserCreated());
			objModel.setDteDateCreated(objModel1.getDteDateCreated());
			objModel.setStrClientCode(clientCode);
		} else {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblmanufacturemaster", "ManufactureMaster", "intGId", clientCode);
			String strManufacturerCode = "M" + String.format("%06d", lastNo);
			objModel.setStrManufacturerCode(strManufacturerCode);
			objModel.setIntGId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrClientCode(clientCode);
		}

		objModel.setStrManufacturerName(objBean.getStrManufacturerName());
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		return objModel;
	}

}
