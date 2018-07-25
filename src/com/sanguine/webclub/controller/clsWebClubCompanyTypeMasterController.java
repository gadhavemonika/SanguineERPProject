package com.sanguine.webclub.controller;

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
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubCompanyTypeMasterBean;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;
import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel;
import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel_ID;
import com.sanguine.webclub.service.clsWebClubCompanyTypeMasterService;

@Controller
public class clsWebClubCompanyTypeMasterController {

	@Autowired
	private clsWebClubCompanyTypeMasterService objWebClubCompanyTypeMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open WebClubCompanyTypeMaster
	@RequestMapping(value = "/frmCompanyTypeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCompanyTypeMaster_1", "command", new clsWebClubCompanyTypeMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCompanyTypeMaster", "command", new clsWebClubCompanyTypeMasterModel());
		} else {
			return null;
		}

	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmCompanyTypeMaster1", method = RequestMethod.POST)
	public @ResponseBody clsWebClubCompanyTypeMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsWebClubCompanyTypeMasterBean objBean = new clsWebClubCompanyTypeMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsWebClubCompanyTypeMasterModel objWebClubCompanyTypeMaster = new clsWebClubCompanyTypeMasterModel();
		return objWebClubCompanyTypeMaster;
	}

	// Save or Update WebClubCompanyTypeMaster
	@RequestMapping(value = "/saveWebClubCompanyTypeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubCompanyTypeMasterBean objBean, BindingResult result, HttpServletRequest req) {

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
			clsWebClubCompanyTypeMasterModel objModel = funPrepareModel(objBean, userCode, propCode, clientCode);
			objWebClubCompanyTypeMasterService.funAddUpdateWebClubCompanyTypeMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Company Code : ".concat(objModel.getStrCompanyTypeCode()));
			return new ModelAndView("redirect:/frmCompanyTypeMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmCompanyTypeMaster.html?saddr=" + urlHits);
		}

	}

	// Convert bean to model function
	private clsWebClubCompanyTypeMasterModel funPrepareModel(clsWebClubCompanyTypeMasterBean objBean, String userCode, String propCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubCompanyTypeMasterModel objModel;

		if (objBean.getStrCompanyTypeCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcompanytypemaster", "CompanyMaster", "intGId", clientCode);
			String comTypeCode = "COT" + String.format("%06d", lastNo);
			objModel = new clsWebClubCompanyTypeMasterModel(new clsWebClubCompanyTypeMasterModel_ID(comTypeCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setStrUserCreated(userCode);

			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWebClubCompanyTypeMasterModel objComTypeModel = objWebClubCompanyTypeMasterService.funGetWebClubCompanyTypeMaster(objBean.getStrCompanyTypeCode(), clientCode);
			if (null == objComTypeModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcompanytypemaster", "CompanyMaster", "intGId", clientCode);
				String comTypeCode = "COT" + String.format("%06d", lastNo);
				objModel = new clsWebClubCompanyTypeMasterModel(new clsWebClubCompanyTypeMasterModel_ID(comTypeCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setStrUserCreated(userCode);

				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsWebClubCompanyTypeMasterModel(new clsWebClubCompanyTypeMasterModel_ID(objBean.getStrCompanyTypeCode(), clientCode));

			}
		}
		objModel.setStrPropertyCode(propCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setStrAnnualTurnOver(objBean.getStrAnnualTurnOver());
		objModel.setStrCapitalAndReserved(objBean.getStrCapitalAndReserved());
		objModel.setStrCompanyName(objBean.getStrCompanyName());

		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebClubCompanyTypeData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubCompanyTypeMasterModel funAssignFields(@RequestParam("docCode") String comTypeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubCompanyTypeMasterModel objCompanyTypeModel = objWebClubCompanyTypeMasterService.funGetWebClubCompanyTypeMaster(comTypeCode, clientCode);
		if (null == objCompanyTypeModel) {
			objCompanyTypeModel = new clsWebClubCompanyTypeMasterModel();
			objCompanyTypeModel.setStrCompanyTypeCode("Invalid Code");
		}

		return objCompanyTypeModel;

	}

}
