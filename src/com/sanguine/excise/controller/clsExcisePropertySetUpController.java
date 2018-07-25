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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExcisePropertySetUpBean;
import com.sanguine.excise.model.clsExcisePropertySetUpModel;
import com.sanguine.excise.service.clsExcisePropertySetUpService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExcisePropertySetUpController {

	@Autowired
	private clsExcisePropertySetUpService objclsExcisePropertySetUpService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open CategoryMaster
	@RequestMapping(value = "/frmExcisePropertySetUp", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePropertySetUp_1", "command", new clsExcisePropertySetUpBean());
		} else {
			return new ModelAndView("frmExcisePropertySetUp", "command", new clsExcisePropertySetUpBean());
		}
	}

	// Save or Update CategoryMaster
	@RequestMapping(value = "/saveExcisePropertySetUp", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExcisePropertySetUpBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsExcisePropertySetUpModel objclsCategoryMasterModel = funPrepareModel(objBean, userCode, clientCode);
			boolean success = false;
			if (objclsCategoryMasterModel.getStrClientCode() != null && !(objclsCategoryMasterModel.getStrClientCode().isEmpty())) {
				success = objclsExcisePropertySetUpService.funAddUpdateSetUpMaster(objclsCategoryMasterModel);
			}

			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Data Save Successfully");
				return new ModelAndView("redirect:/frmExcisePropertySetUp.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExcisePropertySetUp.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExcisePropertySetUp.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsExcisePropertySetUpModel funPrepareModel(clsExcisePropertySetUpBean objBean, String userCode, String clientCode) {
		clsExcisePropertySetUpModel objModel = new clsExcisePropertySetUpModel();

		if (objBean != null) {
			clsExcisePropertySetUpModel objModel1 = objclsExcisePropertySetUpService.funGetObject(clientCode);
			if (objModel1 == null) {
				objModel.setStrClientCode(clientCode);
				if (objBean.getStrBrandMaster() == null) {
					objModel.setStrBrandMaster("Custom");
				} else {
					objModel.setStrBrandMaster(objBean.getStrBrandMaster());
				}
				if (objBean.getStrSizeMaster() == null) {
					objModel.setStrSizeMaster("Custom");
				} else {
					objModel.setStrSizeMaster(objBean.getStrSizeMaster());
				}
				if (objBean.getStrSubCategory() == null) {
					objModel.setStrSubCategory("Custom");
				} else {
					objModel.setStrSubCategory(objBean.getStrSubCategory());
				}
				if (objBean.getStrCategory() == null) {
					objModel.setStrCategory("Custom");
				} else {
					objModel.setStrCategory(objBean.getStrCategory());
				}
				if (objBean.getStrSupplier() == null) {
					objModel.setStrSupplier("Custom");
				} else {
					objModel.setStrSupplier(objBean.getStrSupplier());
				}
				if (objBean.getStrRecipe() == null) {
					objModel.setStrRecipe("Custom");
				} else {
					objModel.setStrRecipe(objBean.getStrRecipe());
				}
				if (objBean.getStrCity() == null) {
					objModel.setStrCity("Custom");
				} else {
					objModel.setStrCity(objBean.getStrCity());
				}
				if (objBean.getStrPermit() == null) {
					objModel.setStrPermit("Custom");
				} else {
					objModel.setStrPermit(objBean.getStrPermit());
				}

				objModel.setStrUserCreated(userCode);
				objModel.setStrUserEdited(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				// objModel.setStrClientCode(clientCode);
				// objModel.setStrBrandMaster(objBean.getStrBrandMaster());
				// objModel.setStrSizeMaster(objBean.getStrSizeMaster());
				// objModel.setStrSubCategory(objBean.getStrSubCategory());
				// objModel.setStrCategory(objBean.getStrCategory());
				// objModel.setStrSupplier(objBean.getStrSupplier());
				// objModel.setStrRecipe(objBean.getStrRecipe());
				// objModel.setStrCity(objBean.getStrCity());
				// objModel.setStrPermit(objBean.getStrPermit());
				// objModel.setStrUserCreated(userCode);
				// objModel.setStrUserEdited(userCode);
				// objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				// objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			}

		}

		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExcisePropertySetUp", method = RequestMethod.GET)
	public @ResponseBody clsExcisePropertySetUpModel funAssignFieldsForForm(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsExcisePropertySetUpModel objModel = objclsExcisePropertySetUpService.funGetObject(clientCode);
		if (null == objModel) {
			objModel = new clsExcisePropertySetUpModel();
		}

		return objModel;
	}

}
