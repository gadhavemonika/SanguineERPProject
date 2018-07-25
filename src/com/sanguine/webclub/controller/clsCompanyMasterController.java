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
import com.sanguine.webclub.bean.clsCompanyMasterBean;
import com.sanguine.webclub.bean.clsWebClubGroupMasterBean;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel_ID;
import com.sanguine.webclub.service.clsCompanyMasterService;

@Controller
public class clsCompanyMasterController {

	@Autowired
	private clsCompanyMasterService objCompanyMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open CompanyMaster
	@RequestMapping(value = "/frmCompanyMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCompanyMaster_1", "command", new clsWebClubCompanyMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCompanyMaster", "command", new clsWebClubCompanyMasterModel());
		} else {
			return null;
		}

	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmCompanyMaster1", method = RequestMethod.POST)
	public @ResponseBody clsWebClubCompanyMasterModel funLoadMasterData(HttpServletRequest request) {

		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("userCode").toString();
		clsCompanyMasterBean objBean = new clsCompanyMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsWebClubCompanyMasterModel objCompanyMaster = new clsWebClubCompanyMasterModel();
		return objCompanyMaster;
	}

	// Save or Update CompanyMaster
	@RequestMapping(value = "/saveCompanyMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCompanyMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsWebClubCompanyMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, req);
			objCompanyMasterService.funAddUpdateCompanyMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Company Code : ".concat(objModel.getStrCompanyCode()));
			return new ModelAndView("redirect:/frmCompanyMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmCompanyMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsWebClubCompanyMasterModel funPrepareModel(clsCompanyMasterBean objBean, String userCode, String clientCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		long lastNo = 0;
		clsWebClubCompanyMasterModel objModel;
		if (objBean.getStrCompanyCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcompanymaster", "CompanyMaster", "intGId", clientCode);
			String companyCode = "CP" + String.format("%06d", lastNo);
			objModel = new clsWebClubCompanyMasterModel(new clsWebClubCompanyMasterModel_ID(companyCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setStrPropertyCode(propCode);
			objModel.setStrUserCreated(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWebClubCompanyMasterModel objCompanyModel = objCompanyMasterService.funGetCompanyMaster(objBean.getStrCompanyCode(), clientCode);
			if (null == objCompanyModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcompanymaster", "CompanyMaster", "intGId", clientCode);
				String companyCode = "CP" + String.format("%06d", lastNo);
				objModel = new clsWebClubCompanyMasterModel(new clsWebClubCompanyMasterModel_ID(companyCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setStrPropertyCode(propCode);
				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {

				objModel = new clsWebClubCompanyMasterModel(new clsWebClubCompanyMasterModel_ID(objBean.getStrCompanyCode(), clientCode));
				objModel.setStrPropertyCode(propCode);
			}
		}
		objModel.setStrUserCreated(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrCompanyName(objBean.getStrCompanyName());
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrCompanyType(objBean.getStrCompanyType());
		objModel.setDblAnnualTrunover(objBean.getDblAnnualTrunover());
		objModel.setDblCapital(objBean.getDblCapital());
		objModel.setStrMemberCode(objBean.getStrMemberCode());
		objModel.setStrCategoryCode(objBean.getStrCategoryCode());
		objModel.setStrActiveNominee(objBean.getStrActiveNominee());
		objModel.setStrAddress1(objBean.getStrAddress1());
		objModel.setStrAddress2(objBean.getStrAddress3());
		objModel.setStrAddress3(objBean.getStrAddress3());
		objModel.setStrLandmark(objBean.getStrLandmark());
		objModel.setStrAreaCode(objBean.getStrAreaCode());
		objModel.setStrCityCode(objBean.getStrCityCode());
		objModel.setStrStateCode(objBean.getStrStateCode());
		objModel.setStrRegionCode(objBean.getStrRegionCode());
		objModel.setStrCountryCode(objBean.getStrCountryCode());
		objModel.setStrPin(objBean.getStrPin());
		objModel.setStrTelephone1(objBean.getStrTelephone1());
		objModel.setStrTelephone2(objBean.getStrTelephone2());
		objModel.setStrFax1(objBean.getStrFax1());
		objModel.setStrFax2(objBean.getStrFax2());

		return objModel;

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebClubCompanyData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubCompanyMasterModel funAssignFields(@RequestParam("docCode") String companyCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubCompanyMasterModel objGroup = objCompanyMasterService.funGetCompanyMaster(companyCode, clientCode);
		if (null == objGroup) {
			objGroup = new clsWebClubCompanyMasterModel();
			objGroup.setStrCompanyCode("Invalid Code");
		}

		return objGroup;
	}

}
