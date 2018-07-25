package com.sanguine.excise.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsSubCategoryMasterBean;
import com.sanguine.excise.model.clsCategoryMasterModel;
import com.sanguine.excise.model.clsSubCategoryMasterModel;
import com.sanguine.excise.service.clsSubCategoryMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class clsSubCategoryMasterController {

	@Autowired
	private clsSubCategoryMasterService objSubCategoryMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open SubCategoryMaster
	@RequestMapping(value = "/frmExciseSubCategoryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List listType = new ArrayList<>();
		listType.add("Text");
		listType.add("Integer");
		listType.add("List");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseSubCategoryMaster_1", "command", new clsSubCategoryMasterModel());
		} else {
			return new ModelAndView("frmExciseSubCategoryMaster", "command", new clsSubCategoryMasterModel());
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseSubCategoryMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSubCategoryMasterModel funAssignFieldsForForm(@RequestParam("subcategoryCode") String subCategoryCode, HttpServletRequest req) {

		clsSubCategoryMasterModel objModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String isSubCategoryGlobal = "Custom";
		try {
			isSubCategoryGlobal = req.getSession().getAttribute("strSubCategory").toString();
		} catch (Exception e) {
			isSubCategoryGlobal = "Custom";
		}
		if (isSubCategoryGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		List objList = objSubCategoryMasterService.funGetObject(subCategoryCode, clientCode);

		if (objList.size() > 0) {
			Object obj[] = (Object[]) objList.get(0);
			clsSubCategoryMasterModel objModel1 = (clsSubCategoryMasterModel) obj[0];
			clsCategoryMasterModel objCategoryMasterModel = (clsCategoryMasterModel) obj[1];
			objModel = objModel1;
			objModel.setStrCategoryName(objCategoryMasterModel.getStrCategoryName());
		} else {
			objModel = new clsSubCategoryMasterModel();
			objModel.setStrSubCategoryCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update SubCategoryMaster
	@RequestMapping(value = "/saveExciseSubCategoryMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSubCategoryMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String isSubCategoryGlobal = "Custom";
		try {
			isSubCategoryGlobal = req.getSession().getAttribute("strSubCategory").toString();
		} catch (Exception e) {
			isSubCategoryGlobal = "Custom";
		}
		if (isSubCategoryGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		if (!result.hasErrors()) {
			clsSubCategoryMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			boolean success = objSubCategoryMasterService.funAddUpdateSubCategoryMaster(objModel);

			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Sub Category Name : ".concat(objModel.getStrSubCategoryName()));
				return new ModelAndView("redirect:/frmExciseSubCategoryMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseSubCategoryMaster.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseSubCategoryMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsSubCategoryMasterModel funPrepareModel(clsSubCategoryMasterBean objBean, String userCode, String clientCode) {

		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;

		clsSubCategoryMasterModel objModel = new clsSubCategoryMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrSubCategoryCode().isEmpty())) {
				List objList = objSubCategoryMasterService.funGetObject(objBean.getStrSubCategoryCode(), clientCode);
				Object obj[] = (Object[]) objList.get(0);
				clsSubCategoryMasterModel objModel1 = (clsSubCategoryMasterModel) obj[0];
				if (objModel1 != null) {
					objModel.setStrSubCategoryCode(objModel1.getStrSubCategoryCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblsubcategorymaster", "intId");
				String subCategoryCode = "SC" + String.format("%03d", lastNo);
				objModel.setStrSubCategoryCode(subCategoryCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrSubCategoryName(objBean.getStrSubCategoryName());
			objModel.setIntPegSize(objBean.getIntPegSize());
			objModel.setStrCategoryCode(objBean.getStrCategoryCode());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objModel;
	}

}
