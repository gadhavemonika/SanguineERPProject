package com.sanguine.webclub.controller;

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

import antlr.collections.List;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubSubCategoryMasterBean;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;
import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel_ID;
import com.sanguine.webclub.service.clsWebClubSubCategoryMasterService;

@Controller
public class clsWebClubSubCategoryMasterController {

	@Autowired
	private clsWebClubSubCategoryMasterService objSubCategoryMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open SubCategoryMaster
	@RequestMapping(value = "/frmSubCategoryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubCategoryMaster_1", "command", new clsWebClubSubCategoryMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubCategoryMaster", "command", new clsWebClubSubCategoryMasterModel());
		} else {
			return null;
		}

	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmSubCategoryMaster1", method = RequestMethod.POST)
	public @ResponseBody clsWebClubSubCategoryMasterModel funLoadMasterData(HttpServletRequest request) {

		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("userCode").toString();
		clsWebClubSubCategoryMasterBean objBean = new clsWebClubSubCategoryMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = (List) objGlobalFunctionsService.funGetList(sql);
		clsWebClubSubCategoryMasterModel objSubCategoryMaster = new clsWebClubSubCategoryMasterModel();
		return objSubCategoryMaster;
	}

	// Save or Update SubCategoryMaster
	@RequestMapping(value = "/saveSubCategoryMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubSubCategoryMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsWebClubSubCategoryMasterModel objModel = funPrepareModel(objBean, userCode, propCode, clientCode);
			objSubCategoryMasterService.funAddUpdateSubCategoryMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Sub Category Code : ".concat(objModel.getStrSCCode()));
			return new ModelAndView("redirect:/frmSubCategoryMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmSubCategoryMaster.html?saddr=" + urlHits);
		}

	}

	// Convert bean to model function
	private clsWebClubSubCategoryMasterModel funPrepareModel(clsWebClubSubCategoryMasterBean objBean, String userCode, String propCode, String clientCode) {

		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubSubCategoryMasterModel objModel;
		if (objBean.getStrSCCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsubcategorymaster", "SubCategoryMaster", "intGId", clientCode);
			String sCCode = "SC" + String.format("%06d", lastNo);
			objModel = new clsWebClubSubCategoryMasterModel(new clsWebClubSubCategoryMasterModel_ID(sCCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setStrUserCreated(userCode);

			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWebClubSubCategoryMasterModel objSCModel = objSubCategoryMasterService.funGetSubCategoryMaster(objBean.getStrSCCode(), clientCode);
			if (null == objSCModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblsubcategorymaster", "SubCategoryMaster", "intGId", clientCode);
				String sCCode = "SC" + String.format("%06d", lastNo);
				objModel = new clsWebClubSubCategoryMasterModel(new clsWebClubSubCategoryMasterModel_ID(sCCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setStrUserCreated(userCode);

				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsWebClubSubCategoryMasterModel(new clsWebClubSubCategoryMasterModel_ID(objBean.getStrSCCode(), clientCode));

			}
		}

		objModel.setStrPropertyCode(propCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setStrSCName(objBean.getStrSCName());
		objModel.setStrSCDesc(objBean.getStrSCDesc());

		return objModel;

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebClubSubCategoryData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubSubCategoryMasterModel funAssignFields(@RequestParam("docCode") String sCCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubSubCategoryMasterModel objSCModel = objSubCategoryMasterService.funGetSubCategoryMaster(sCCode, clientCode);
		if (null == objSCModel) {
			objSCModel = new clsWebClubSubCategoryMasterModel();
			objSCModel.setStrSCCode("Invalid Code");
		}

		return objSCModel;
	}

}
