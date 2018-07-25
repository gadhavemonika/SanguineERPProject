package com.sanguine.webpms.controller;

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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsRoomMasterBean;
import com.sanguine.webpms.bean.clsTaxGroupMasterBean;
import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.model.clsRoomMasterModel_ID;
import com.sanguine.webpms.model.clsTaxGroupMasterModel;
import com.sanguine.webpms.model.clsTaxGroupMasterModel_ID;
import com.sanguine.webpms.service.clsTaxGroupMasterService;

@Controller
public class clsTaxGroupMasterController {

	@Autowired
	private clsTaxGroupMasterService objTaxGroupMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open TaxGroupMaster
	@RequestMapping(value = "/frmTaxGroupMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmTaxGroupMaster", "command", new clsTaxGroupMasterBean());
		} else {
			return new ModelAndView("frmTaxGroupMaster_1", "command", new clsTaxGroupMasterBean());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadTaxGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsTaxGroupMasterModel funLoadMasterData(@RequestParam("taxGroupCode") String taxGroupCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsTaxGroupMasterModel objModel = objTaxGroupMasterService.funGetTaxGroupMaster(taxGroupCode, clientCode);
		if (objModel == null) {
			objModel = new clsTaxGroupMasterModel();
			objModel.setStrTaxGroupCode("Invalid Code");
		}

		return objModel;
	}

	// save/update tax group master
	@RequestMapping(value = "/saveTaxGroupMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsTaxGroupMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsTaxGroupMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);
			objTaxGroupMasterService.funAddUpdateTaxGroupMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Tax Group Code : ".concat(objModel.getStrTaxGroupCode()));

			return new ModelAndView("redirect:/frmTaxGroupMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmTaxGroupMaster.html?saddr=" + urlHits);
		}
	}

	private clsTaxGroupMasterModel funPrepareModel(clsTaxGroupMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsTaxGroupMasterModel objModel;
		if (objBean.getStrTaxGroupCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbltaxgroup", "TaxGroupMaster", "intGId", clientCode);
			String taxGroupCode = "TGC" + String.format("%06d", lastNo);
			objModel = new clsTaxGroupMasterModel(new clsTaxGroupMasterModel_ID(taxGroupCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsTaxGroupMasterModel(new clsTaxGroupMasterModel_ID(objBean.getStrTaxGroupCode(), clientCode));
		}
		objModel.setStrTaxGroupDesc(objBean.getStrTaxGroupDesc());
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return objModel;
	}
}
