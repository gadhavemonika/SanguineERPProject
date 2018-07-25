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
import com.sanguine.excise.bean.clsCategoryMasterBean;
import com.sanguine.excise.model.clsCategoryMasterModel;
import com.sanguine.excise.service.clsCategoryMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsCategoryMasterController {

	@Autowired
	private clsCategoryMasterService objCategoryMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open CategoryMaster
	@RequestMapping(value = "/frmExciseCategoryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseCategoryMaster_1", "command", new clsCategoryMasterModel());
		} else {
			return new ModelAndView("frmExciseCategoryMaster", "command", new clsCategoryMasterModel());
		}
	}

	// Save or Update CategoryMaster
	@RequestMapping(value = "/saveExciseCategoryMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCategoryMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String isCategoryGlobal = "Custom";
		try {
			isCategoryGlobal = req.getSession().getAttribute("strCategory").toString();
		} catch (Exception e) {
			isCategoryGlobal = "Custom";
		}
		if (isCategoryGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		if (!result.hasErrors()) {
			clsCategoryMasterModel objclsCategoryMasterModel = funPrepareModel(objBean, userCode, clientCode);
			boolean success = objCategoryMasterService.funAddUpdateCategoryMaster(objclsCategoryMasterModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Category Name : ".concat(objclsCategoryMasterModel.getStrCategoryName()));
				return new ModelAndView("redirect:/frmExciseCategoryMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseCategoryMaster.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseCategoryMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsCategoryMasterModel funPrepareModel(clsCategoryMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsCategoryMasterModel objModel = new clsCategoryMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrCategoryCode().isEmpty())) {
				clsCategoryMasterModel objModel1 = objCategoryMasterService.funGetObject(objBean.getStrCategoryCode(), clientCode);
				if (objModel1 != null) {
					objModel.setStrCategoryCode(objModel1.getStrCategoryCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblcategorymaster", "intId");
				String categoryCode = "C" + String.format("%03d", lastNo);

				objModel.setStrCategoryCode(categoryCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrCategoryName(objBean.getStrCategoryName());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseCategoryMasterData", method = RequestMethod.GET)
	public @ResponseBody clsCategoryMasterModel funAssignFieldsForForm(@RequestParam("categoryCode") String categoryCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String isCategoryGlobal = "Custom";
		try {
			isCategoryGlobal = req.getSession().getAttribute("strCategory").toString();
		} catch (Exception e) {
			isCategoryGlobal = "Custom";
		}
		if (isCategoryGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		clsCategoryMasterModel objModel = objCategoryMasterService.funGetObject(categoryCode, clientCode);
		if (null == objModel) {
			objModel = new clsCategoryMasterModel();
			objModel.setStrCategoryCode("Invalid Code");
		}

		return objModel;
	}

}
