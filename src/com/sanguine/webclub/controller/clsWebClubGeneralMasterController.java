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
import com.sanguine.webclub.bean.clsWebClubGeneralMasterBean;
import com.sanguine.webclub.model.clsWebClubAreaMasterModel;
import com.sanguine.webclub.model.clsWebClubAreaMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubCityMasterModel;
import com.sanguine.webclub.model.clsWebClubCityMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel;
import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubCountryMasterModel;
import com.sanguine.webclub.model.clsWebClubCountryMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel;
import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubDesignationMasterModel;
import com.sanguine.webclub.model.clsWebClubDesignationMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubEducationMasterModel;
import com.sanguine.webclub.model.clsWebClubEducationMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel;
import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubMaritalStatusModel;
import com.sanguine.webclub.model.clsWebClubMaritalStatusModel_ID;
import com.sanguine.webclub.model.clsWebClubProfessionMasterModel;
import com.sanguine.webclub.model.clsWebClubProfessionMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubProfileMasterModel;
import com.sanguine.webclub.model.clsWebClubProfileMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubReasonMasterModel;
import com.sanguine.webclub.model.clsWebClubReasonMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubRegionMasterModel;
import com.sanguine.webclub.model.clsWebClubRegionMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubRelationMasterModel;
import com.sanguine.webclub.model.clsWebClubRelationMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubSalutationMasterModel;
import com.sanguine.webclub.model.clsWebClubSalutationMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubStaffMasterModel;
import com.sanguine.webclub.model.clsWebClubStaffMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubTitleMasterModel;
import com.sanguine.webclub.model.clsWebClubTitleMasterModel_ID;
import com.sanguine.webclub.service.clsWebClubRelationMasterService;
import com.sanguine.webclub.model.clsWebClubStateMasterModel;
import com.sanguine.webclub.model.clsWebClubStateMasterModel_ID;
import com.sanguine.webclub.service.clsWebClubAreaMasterService;
import com.sanguine.webclub.service.clsWebClubCityMasterService;
import com.sanguine.webclub.service.clsWebClubCommitteeMemberRoleMasterService;
import com.sanguine.webclub.service.clsWebClubCountryMasterService;
import com.sanguine.webclub.service.clsWebClubCurrencyDetailsMasterService;
import com.sanguine.webclub.service.clsWebClubDesignationMasterService;
import com.sanguine.webclub.service.clsWebClubEducationMasterService;
import com.sanguine.webclub.service.clsWebClubGeneralMasterService;
import com.sanguine.webclub.service.clsWebClubInvitedByMasterService;
import com.sanguine.webclub.service.clsWebClubItemCategoryMasterService;
import com.sanguine.webclub.service.clsWebClubMaritalStatusService;
import com.sanguine.webclub.service.clsWebClubProfessionMasterService;
import com.sanguine.webclub.service.clsWebClubProfileMasterService;
import com.sanguine.webclub.service.clsWebClubReasonMasterService;
import com.sanguine.webclub.service.clsWebClubRegionMasterService;
import com.sanguine.webclub.service.clsWebClubSalutationMasterService;
import com.sanguine.webclub.service.clsWebClubStaffMasterService;
import com.sanguine.webclub.service.clsWebClubStateMasterService;
import com.sanguine.webclub.service.clsWebClubTitleMasterService;

@Controller
public class clsWebClubGeneralMasterController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsWebClubAreaMasterService objAreaMasterService;

	@Autowired
	private clsWebClubRegionMasterService objRegionMasterService;

	@Autowired
	private clsWebClubStateMasterService objStateMasterService;

	@Autowired
	private clsWebClubCountryMasterService objCountryMasterService;

	@Autowired
	private clsWebClubCityMasterService objCityMasterService;

	@Autowired
	private clsWebClubGeneralMasterService objGeneralMasterService;

	@Autowired
	private clsWebClubDesignationMasterService objDesignationMasterService;

	@Autowired
	private clsWebClubEducationMasterService objEducationMasterService;

	@Autowired
	private clsWebClubMaritalStatusService objMaritalStatusService;

	@Autowired
	private clsWebClubProfessionMasterService objProfessionMasterSerice;

	@Autowired
	private clsWebClubReasonMasterService objReasonMasterService;

	@Autowired
	private clsWebClubCommitteeMemberRoleMasterService objCommitteeMemberRoleService;

	@Autowired
	private clsWebClubRelationMasterService objRelationMasterService;

	@Autowired
	private clsWebClubStaffMasterService objStaffMasterService;

	@Autowired
	private clsWebClubCurrencyDetailsMasterService objCurrencyDetailsMasterService;

	@Autowired
	private clsWebClubInvitedByMasterService objInvitedByMasterService;

	@Autowired
	private clsWebClubItemCategoryMasterService objItemCategoryMasterService;

	@Autowired
	private clsWebClubProfileMasterService objProfileMasterService;

	@Autowired
	private clsWebClubSalutationMasterService objSalutationMasterService;

	@Autowired
	private clsWebClubTitleMasterService objTitleMasterService;

	// Open WebClubGeneralMaster
	@RequestMapping(value = "/frmGeneralMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGeneralMaster_1", "command", new clsWebClubGeneralMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGeneralMaster", "command", new clsWebClubGeneralMasterBean());
		} else {
			return null;
		}

	}

	// Save or Update WebClubGeneralMaster
	@RequestMapping(value = "/saveWebClubGeneralMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubGeneralMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();

			String masterID = funCheckBeanForModel(objBean);
			Object objGenricModel = funAddPreparedModelAccordingToMasterID(masterID, objBean, clientCode, userCode, req);

			if (masterID.equalsIgnoreCase("Area")) {
				clsWebClubAreaMasterModel objAreaModel = (clsWebClubAreaMasterModel) objGenricModel;
				objAreaModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objAreaModel.setStrUserModified(userCode);
				objAreaModel.setStrPropertyCode(propCode);

				objAreaMasterService.funAddUpdateWebClubAreaMaster(objAreaModel);
				;
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Area Code : ".concat(objAreaModel.getStrAreaCode()));

			}

			if (masterID.equalsIgnoreCase("Region")) {
				clsWebClubRegionMasterModel objRegionModel = (clsWebClubRegionMasterModel) objGenricModel;
				objRegionModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objRegionModel.setStrUserModified(userCode);
				objRegionModel.setStrPropertyCode(propCode);

				objRegionMasterService.funAddUpdateWebClubRegionMaster(objRegionModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Region Code : ".concat(objRegionModel.getStrRegionCode()));
			}

			if (masterID.equalsIgnoreCase("State")) {
				clsWebClubStateMasterModel objStateModel = (clsWebClubStateMasterModel) objGenricModel;
				objStateModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objStateModel.setStrUserModified(userCode);
				objStateModel.setStrPropertyCode(propCode);

				objStateMasterService.funAddUpdateWebClubStateMaster(objStateModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Region Code : ".concat(objStateModel.getStrStateCode()));
			}

			if (masterID.equalsIgnoreCase("City")) {
				clsWebClubCityMasterModel objCityModel = (clsWebClubCityMasterModel) objGenricModel;
				objCityModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objCityModel.setStrUserModified(userCode);
				objCityModel.setStrPropertyCode(propCode);

				objCityMasterService.funAddUpdateWebClubCityMaster(objCityModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "City Code : ".concat(objCityModel.getStrCityCode()));

			}

			if (masterID.equalsIgnoreCase("Country")) {
				clsWebClubCountryMasterModel objCountryModel = (clsWebClubCountryMasterModel) objGenricModel;
				objCountryModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objCountryModel.setStrUserModified(userCode);
				objCountryModel.setStrPropertyCode(propCode);

				objCountryMasterService.funAddUpdateWebClubCountryMaster(objCountryModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Country Code : ".concat(objCountryModel.getStrCountryCode()));

			}

			if (masterID.equalsIgnoreCase("Designation")) {
				clsWebClubDesignationMasterModel objDesModel = (clsWebClubDesignationMasterModel) objGenricModel;
				objDesModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objDesModel.setStrUserModified(userCode);
				objDesModel.setStrPropertyCode(propCode);

				objDesignationMasterService.funAddUpdateWebClubDesignationMaster(objDesModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Desgination Code : ".concat(objDesModel.getStrDesignationCode()));

			}

			if (masterID.equalsIgnoreCase("Education")) {
				clsWebClubEducationMasterModel objEduModel = (clsWebClubEducationMasterModel) objGenricModel;
				objEduModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objEduModel.setStrUserModified(userCode);
				objEduModel.setStrPropertyCode(propCode);

				objEducationMasterService.funAddUpdateWebClubEducationMaster(objEduModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Education Code : ".concat(objEduModel.getStrEducationCode()));

			}

			if (masterID.equalsIgnoreCase("Marital")) {
				clsWebClubMaritalStatusModel objMsModel = (clsWebClubMaritalStatusModel) objGenricModel;
				objMsModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objMsModel.setStrUserModified(userCode);
				objMsModel.setStrPropertyCode(propCode);

				objMaritalStatusService.funAddUpdateWebClubMaritalStatus(objMsModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "MaritalStatus Code : ".concat(objMsModel.getStrMaritalCode()));

			}

			if (masterID.equalsIgnoreCase("Profession")) {
				clsWebClubProfessionMasterModel objProModel = (clsWebClubProfessionMasterModel) objGenricModel;
				objProModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objProModel.setStrUserModified(userCode);
				objProModel.setStrPropertyCode(propCode);

				objProfessionMasterSerice.funAddUpdateWebClubProfessionMaster(objProModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Profession Code : ".concat(objProModel.getStrProfessionCode()));

			}

			if (masterID.equalsIgnoreCase("Reason")) {
				clsWebClubReasonMasterModel objReasonModel = (clsWebClubReasonMasterModel) objGenricModel;
				objReasonModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objReasonModel.setStrUserModified(userCode);
				objReasonModel.setStrPropertyCode(propCode);

				objReasonMasterService.funAddUpdateWebClubReasonMaster(objReasonModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Profession Code : ".concat(objReasonModel.getStrReasonCode()));

			}

			if (masterID.equalsIgnoreCase("CommitteeMemberRole")) {
				clsWebClubCommitteeMemberRoleMasterModel objCommRoleModel = (clsWebClubCommitteeMemberRoleMasterModel) objGenricModel;
				objCommRoleModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objCommRoleModel.setStrUserModified(userCode);
				objCommRoleModel.setStrPropertyCode(propCode);

				objCommitteeMemberRoleService.funAddUpdateWebClubCommitteeMemberRoleMaster(objCommRoleModel);
				;
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Committee Role Code : ".concat(objCommRoleModel.getStrRoleCode()));

			}

			if (masterID.equalsIgnoreCase("Relation")) {
				clsWebClubRelationMasterModel objRelationModel = (clsWebClubRelationMasterModel) objGenricModel;
				objRelationModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objRelationModel.setStrUserModified(userCode);
				objRelationModel.setStrPropertyCode(propCode);

				objRelationMasterService.funAddUpdateWebClubRelationMaster(objRelationModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Relation Code : ".concat(objRelationModel.getStrRelationCode()));

			}

			if (masterID.equalsIgnoreCase("Staff")) {
				clsWebClubStaffMasterModel objStaffModel = (clsWebClubStaffMasterModel) objGenricModel;
				objStaffModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objStaffModel.setStrUserModified(userCode);
				objStaffModel.setStrPropertyCode(propCode);

				objStaffMasterService.funAddUpdateWebClubStaffMaster(objStaffModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Staff Code : ".concat(objStaffModel.getStrStaffCode()));

			}

			if (masterID.equalsIgnoreCase("CurrencyDetails")) {
				clsWebClubCurrencyDetailsMasterModel objCurrencyModel = (clsWebClubCurrencyDetailsMasterModel) objGenricModel;
				objCurrencyModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objCurrencyModel.setStrUserModified(userCode);
				objCurrencyModel.setStrPropertyCode(propCode);

				objCurrencyDetailsMasterService.funAddUpdateWebClubCurrencyDetailsMaster(objCurrencyModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Currency Code : ".concat(objCurrencyModel.getStrCurrCode()));

			}

			if (masterID.equalsIgnoreCase("InvitedBy")) {
				clsWebClubInvitedByMasterModel objInvitedByModel = (clsWebClubInvitedByMasterModel) objGenricModel;
				objInvitedByModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objInvitedByModel.setStrUserModified(userCode);
				objInvitedByModel.setStrPropertyCode(propCode);

				objInvitedByMasterService.funAddUpdateWebClubInvitedByMaster(objInvitedByModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invitation Code : ".concat(objInvitedByModel.getStrInvCode()));

			}
			if (masterID.equalsIgnoreCase("ItemCategory")) {
				clsWebClubItemCategoryMasterModel objItemCategoryModel = (clsWebClubItemCategoryMasterModel) objGenricModel;
				objItemCategoryModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objItemCategoryModel.setStrUserModified(userCode);
				objItemCategoryModel.setStrPropertyCode(propCode);

				objItemCategoryMasterService.funAddUpdateWebClubItemCategoryMaster(objItemCategoryModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Item Category Code : ".concat(objItemCategoryModel.getStrItemCategoryCode()));

			}
			if (masterID.equalsIgnoreCase("Profile")) {
				clsWebClubProfileMasterModel objProfileModel = (clsWebClubProfileMasterModel) objGenricModel;
				objProfileModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objProfileModel.setStrUserModified(userCode);
				objProfileModel.setStrPropertyCode(propCode);

				objProfileMasterService.funAddUpdateWebClubProfileMaster(objProfileModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Profile Code : ".concat(objProfileModel.getStrProfileCode()));

			}
			if (masterID.equalsIgnoreCase("Salutation")) {
				clsWebClubSalutationMasterModel objSalutationModel = (clsWebClubSalutationMasterModel) objGenricModel;
				objSalutationModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objSalutationModel.setStrUserModified(userCode);
				objSalutationModel.setStrPropertyCode(propCode);

				objSalutationMasterService.funAddUpdateWebClubSalutationMaster(objSalutationModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Salutation Code : ".concat(objSalutationModel.getStrSalutationCode()));

			}
			if (masterID.equalsIgnoreCase("Title")) {
				clsWebClubTitleMasterModel objTitleModel = (clsWebClubTitleMasterModel) objGenricModel;
				objTitleModel.setDteLastModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objTitleModel.setStrUserModified(userCode);
				objTitleModel.setStrPropertyCode(propCode);

				objTitleMasterService.funAddUpdateWebClubTitleMaster(objTitleModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Title Code : ".concat(objTitleModel.getStrTitleCode()));

			}
			return new ModelAndView("redirect:/frmGeneralMaster.html?saddr=" + urlHits);
		}

		else {
			return new ModelAndView("redirect:/frmGeneralMaster.html");
		}

	}

	// //Convert bean to model function
	// private clsWebClubGeneralMasterModel
	// funPrepareModel(clsWebClubGeneralMasterBean objBean,String
	// userCode,String clientCode){
	// objGlobal=new clsGlobalFunctions();
	// long lastNo=0;
	// clsWebClubGeneralMasterModel objModel;
	// return objModel;
	//
	// }
	//
	private String funCheckBeanForModel(clsWebClubGeneralMasterBean objBean) {
		String masterID = "";
		if (objBean == null) {
			masterID = "";
		} else {
			if (objBean.getStrMasterID().equalsIgnoreCase("Area")) {
				masterID = "Area";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Region")) {
				masterID = "Region";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("State")) {
				masterID = "State";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Country")) {
				masterID = "Country";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("City")) {
				masterID = "City";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Designation")) {
				masterID = "Designation";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Education")) {
				masterID = "Education";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Marital")) {
				masterID = "Marital";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Profession")) {
				masterID = "Profession";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Reason")) {
				masterID = "Reason";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("CommitteeMemberRole")) {
				masterID = "CommitteeMemberRole";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Relation")) {
				masterID = "Relation";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Staff")) {
				masterID = "Staff";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("CurrencyDetails")) {
				masterID = "CurrencyDetails";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("InvitedBy")) {
				masterID = "InvitedBy";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("ItemCategory")) {
				masterID = "ItemCategory";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Profile")) {
				masterID = "Profile";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Salutation")) {
				masterID = "Salutation";
			}

			if (objBean.getStrMasterID().equalsIgnoreCase("Title")) {
				masterID = "Title";
			}
		}

		return masterID;
	}

	private Object funAddPreparedModelAccordingToMasterID(String masterID, clsWebClubGeneralMasterBean objBean, String clientCode, String userCode, HttpServletRequest req) {

		switch (masterID) {
		case "Area": {
			clsWebClubAreaMasterModel areaMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrAreaCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblareamaster", "AreaMaster", "intGId", clientCode);
				String areaCode = "A" + String.format("%06d", lastNo);
				areaMasterModel = new clsWebClubAreaMasterModel(new clsWebClubAreaMasterModel_ID(areaCode, clientCode));
				areaMasterModel.setIntGId(lastNo);
				areaMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				areaMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubAreaMasterModel aMModel = objAreaMasterService.funGetWebClubAreaMaster(objBean.getStrAreaCode(), clientCode);
				if (null == aMModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblareamaster", "AreaMaster", "intGId", clientCode);
					String areaCode = "A" + String.format("%06d", lastNo);
					areaMasterModel = new clsWebClubAreaMasterModel(new clsWebClubAreaMasterModel_ID(areaCode, clientCode));
					areaMasterModel.setIntGId(lastNo);
					areaMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					areaMasterModel.setStrUserCreated(userCode);

				} else {
					areaMasterModel = new clsWebClubAreaMasterModel(new clsWebClubAreaMasterModel_ID(objBean.getStrAreaCode(), clientCode));
				}
			}

			areaMasterModel.setStrAreaName(objBean.getStrAreaName());
			areaMasterModel.setStrCityCode(objBean.getStrCityCode());

			return areaMasterModel;

		}

		case "Region": {
			clsWebClubRegionMasterModel regionMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrRegionCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblregionmaster", "RegionMaster", "intGId", clientCode);
				String regionCode = "RE" + String.format("%06d", lastNo);
				regionMasterModel = new clsWebClubRegionMasterModel(new clsWebClubRegionMasterModel_ID(regionCode, clientCode));
				regionMasterModel.setIntGId(lastNo);
				regionMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				regionMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubRegionMasterModel rmModel = objRegionMasterService.funGetWebClubRegionMaster(objBean.getStrRegionCode(), clientCode);
				if (null == rmModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblregionmaster", "RegionMaster", "intGId", clientCode);
					String regionCode = "RE" + String.format("%06d", lastNo);
					regionMasterModel = new clsWebClubRegionMasterModel(new clsWebClubRegionMasterModel_ID(regionCode, clientCode));
					regionMasterModel.setIntGId(lastNo);
					regionMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					regionMasterModel.setStrUserCreated(userCode);

				} else {
					regionMasterModel = new clsWebClubRegionMasterModel(new clsWebClubRegionMasterModel_ID(objBean.getStrRegionCode(), clientCode));
				}
			}

			regionMasterModel.setStrRegionName(objBean.getStrRegionName());
			return regionMasterModel;
		}

		case "State": {
			clsWebClubStateMasterModel stateMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrStateCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblstatemaster", "StateMaster", "intGId", clientCode);
				String stateCode = "ST" + String.format("%06d", lastNo);
				stateMasterModel = new clsWebClubStateMasterModel(new clsWebClubStateMasterModel_ID(stateCode, clientCode));
				stateMasterModel.setIntGId(lastNo);
				stateMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				stateMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubStateMasterModel stateModel = objStateMasterService.funGetWebClubStateMaster(objBean.getStrStateCode(), clientCode);
				if (null == stateModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblstatemaster", "StateMaster", "intGId", clientCode);
					String stateCode = "ST" + String.format("%06d", lastNo);
					stateMasterModel = new clsWebClubStateMasterModel(new clsWebClubStateMasterModel_ID(stateCode, clientCode));
					stateMasterModel.setIntGId(lastNo);
					stateMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					stateMasterModel.setStrUserCreated(userCode);

				} else {
					stateMasterModel = new clsWebClubStateMasterModel(new clsWebClubStateMasterModel_ID(objBean.getStrStateCode(), clientCode));
				}
			}

			stateMasterModel.setStrStateName(objBean.getStrStateName());
			stateMasterModel.setStrStateDesc(objBean.getStrStateDesc());
			stateMasterModel.setStrRegionCode(objBean.getStrRegionCode());
			stateMasterModel.setStrCountryCode(objBean.getStrCountryCode());
			return stateMasterModel;
		}

		case "Country": {
			clsWebClubCountryMasterModel countryMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrCountryCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcountrymaster", "CountryMaster", "intGId", clientCode);
				String countryCode = "CO" + String.format("%06d", lastNo);
				countryMasterModel = new clsWebClubCountryMasterModel(new clsWebClubCountryMasterModel_ID(countryCode, clientCode));
				countryMasterModel.setIntGId(lastNo);
				countryMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				countryMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubCountryMasterModel countryModel = objCountryMasterService.funGetWebClubCountryMaster(objBean.getStrCountryCode(), clientCode);
				if (null == countryModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblcountrymaster", "CountryMaster", "intGId", clientCode);
					String countryCode = "CO" + String.format("%06d", lastNo);
					countryMasterModel = new clsWebClubCountryMasterModel(new clsWebClubCountryMasterModel_ID(countryCode, clientCode));
					countryMasterModel.setIntGId(lastNo);
					countryMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					countryMasterModel.setStrUserCreated(userCode);

				} else {
					countryMasterModel = new clsWebClubCountryMasterModel(new clsWebClubCountryMasterModel_ID(objBean.getStrCountryCode(), clientCode));
				}
			}

			countryMasterModel.setStrCountryName(objBean.getStrCountryName());
			return countryMasterModel;
		}

		case "City": {
			clsWebClubCityMasterModel cityMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrCityCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcitymaster", "CityMaster", "intGId", clientCode);
				String cityCode = "CT" + String.format("%06d", lastNo);
				cityMasterModel = new clsWebClubCityMasterModel(new clsWebClubCityMasterModel_ID(cityCode, clientCode));
				cityMasterModel.setIntGId(lastNo);
				cityMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				cityMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubCityMasterModel cityModel = objCityMasterService.funGetWebClubCityMaster(objBean.getStrCityCode(), clientCode);
				if (null == cityModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblcitymaster", "CityMaster", "intGId", clientCode);
					String cityCode = "CT" + String.format("%06d", lastNo);
					cityMasterModel = new clsWebClubCityMasterModel(new clsWebClubCityMasterModel_ID(cityCode, clientCode));
					cityMasterModel.setIntGId(lastNo);
					cityMasterModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					cityMasterModel.setStrUserCreated(userCode);

				} else {
					cityMasterModel = new clsWebClubCityMasterModel(new clsWebClubCityMasterModel_ID(objBean.getStrCityCode(), clientCode));
				}
			}

			cityMasterModel.setStrCityName(objBean.getStrCityName());
			cityMasterModel.setStrCountryCode(objBean.getStrCountryCode());
			cityMasterModel.setStrSTDCode(objBean.getStrStdCode());
			cityMasterModel.setStrStateCode(objBean.getStrStateCode());
			return cityMasterModel;
		}

		case "Designation": {
			clsWebClubDesignationMasterModel designationMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrDesignationCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbldesignationmaster", "DesignationMaster", "intGId", clientCode);
				String designationCode = "DG" + String.format("%06d", lastNo);
				designationMasterModel = new clsWebClubDesignationMasterModel(new clsWebClubDesignationMasterModel_ID(designationCode, clientCode));
				designationMasterModel.setIntGId(lastNo);
				designationMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				designationMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubDesignationMasterModel desModel = objDesignationMasterService.funGetWebClubDesignationMaster(objBean.getStrDesignationCode(), clientCode);
				if (null == desModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tbldesignationmaster", "DesignationMaster", "intGId", clientCode);
					String designationCode = "DG" + String.format("%06d", lastNo);
					designationMasterModel = new clsWebClubDesignationMasterModel(new clsWebClubDesignationMasterModel_ID(designationCode, clientCode));
					designationMasterModel.setIntGId(lastNo);
					designationMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					designationMasterModel.setStrUserCreated(userCode);

				} else {
					designationMasterModel = new clsWebClubDesignationMasterModel(new clsWebClubDesignationMasterModel_ID(objBean.getStrDesignationCode(), clientCode));
				}
			}

			designationMasterModel.setStrDesignationName(objBean.getStrDesignationName());

			return designationMasterModel;
		}

		case "Education": {
			clsWebClubEducationMasterModel eduMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrEducationCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbleducationmaster", "EducationMaster", "intGId", clientCode);
				String eduCode = "ED" + String.format("%06d", lastNo);
				eduMasterModel = new clsWebClubEducationMasterModel(new clsWebClubEducationMasterModel_ID(eduCode, clientCode));
				eduMasterModel.setIntGId(lastNo);
				eduMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				eduMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubEducationMasterModel eduModel = objEducationMasterService.funGetWebClubEducationMaster(objBean.getStrEducationCode(), clientCode);
				if (null == eduModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tbleducationmaster", "EducationMaster", "intGId", clientCode);
					String eduCode = "ED" + String.format("%06d", lastNo);
					eduMasterModel = new clsWebClubEducationMasterModel(new clsWebClubEducationMasterModel_ID(eduCode, clientCode));
					eduMasterModel.setIntGId(lastNo);
					eduMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					eduMasterModel.setStrUserCreated(userCode);

				} else {
					eduMasterModel = new clsWebClubEducationMasterModel(new clsWebClubEducationMasterModel_ID(objBean.getStrEducationCode(), clientCode));
				}
			}

			eduMasterModel.setStrEducationDesc(objBean.getStrEducationDesc());

			return eduMasterModel;
		}

		case "Marital": {
			clsWebClubMaritalStatusModel maritalStatusMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrMaritalCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblmaritalstatusmaster", "MartialMaster", "intGId", clientCode);
				String msCode = "MS" + String.format("%06d", lastNo);
				maritalStatusMasterModel = new clsWebClubMaritalStatusModel(new clsWebClubMaritalStatusModel_ID(msCode, clientCode));
				maritalStatusMasterModel.setIntGId(lastNo);
				maritalStatusMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				maritalStatusMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubMaritalStatusModel msMasterModel = objMaritalStatusService.funGetWebClubMaritalStatus(objBean.getStrMaritalCode(), clientCode);
				if (null == msMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblmaritalstatusmaster", "MartialMaster", "intGId", clientCode);
					String msCode = "MS" + String.format("%06d", lastNo);
					maritalStatusMasterModel = new clsWebClubMaritalStatusModel(new clsWebClubMaritalStatusModel_ID(msCode, clientCode));
					maritalStatusMasterModel.setIntGId(lastNo);
					maritalStatusMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					maritalStatusMasterModel.setStrUserCreated(userCode);

				} else {
					maritalStatusMasterModel = new clsWebClubMaritalStatusModel(new clsWebClubMaritalStatusModel_ID(objBean.getStrMaritalCode(), clientCode));
				}
			}

			maritalStatusMasterModel.setStrMaritalName(objBean.getStrMaritalName());

			return maritalStatusMasterModel;
		}

		case "Profession": {
			clsWebClubProfessionMasterModel professionMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrProfessionCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblprofessionmaster", "ProfessionMaster", "intGId", clientCode);
				String professionCode = "PRO" + String.format("%06d", lastNo);
				professionMasterModel = new clsWebClubProfessionMasterModel(new clsWebClubProfessionMasterModel_ID(professionCode, clientCode));
				professionMasterModel.setIntGId(lastNo);
				professionMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				professionMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubProfessionMasterModel proMasterModel = objProfessionMasterSerice.funGetWebClubProfessionMaster(objBean.getStrProfessionCode(), clientCode);
				if (null == proMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblprofessionmaster", "ProfessionMaster", "intGId", clientCode);
					String professionCode = "PRO" + String.format("%06d", lastNo);
					professionMasterModel = new clsWebClubProfessionMasterModel(new clsWebClubProfessionMasterModel_ID(professionCode, clientCode));
					professionMasterModel.setIntGId(lastNo);
					professionMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					professionMasterModel.setStrUserCreated(userCode);

				} else {
					professionMasterModel = new clsWebClubProfessionMasterModel(new clsWebClubProfessionMasterModel_ID(objBean.getStrProfessionCode(), clientCode));
				}
			}

			professionMasterModel.setStrProfessionName(objBean.getStrProfessionName());

			return professionMasterModel;
		}

		case "Reason": {
			clsWebClubReasonMasterModel reasonMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrReasonCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblreasonmaster", "ReasonMaster", "intGId", clientCode);
				String reasonCode = "REA" + String.format("%06d", lastNo);
				reasonMasterModel = new clsWebClubReasonMasterModel(new clsWebClubReasonMasterModel_ID(reasonCode, clientCode));
				reasonMasterModel.setIntGId(lastNo);
				reasonMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				reasonMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubReasonMasterModel reasMasterModel = objReasonMasterService.funGetWebClubReasonMaster(objBean.getStrReasonCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblreasonmaster", "ReasonMaster", "intGId", clientCode);
					String reasonCode = "REA" + String.format("%06d", lastNo);
					reasonMasterModel = new clsWebClubReasonMasterModel(new clsWebClubReasonMasterModel_ID(reasonCode, clientCode));
					reasonMasterModel.setIntGId(lastNo);
					reasonMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					reasonMasterModel.setStrUserCreated(userCode);

				} else {
					reasonMasterModel = new clsWebClubReasonMasterModel(new clsWebClubReasonMasterModel_ID(objBean.getStrReasonCode(), clientCode));
				}
			}

			reasonMasterModel.setStrReasonDesc(objBean.getStrReasonDesc());
			reasonMasterModel.setStrExcludeInInvoicePrinting("N");

			return reasonMasterModel;
		}

		case "CommitteeMemberRole": {
			clsWebClubCommitteeMemberRoleMasterModel commRoleMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrRoleCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcommitteememberrolemaster", "CommitteeMemberRoleMaster", "intId", clientCode);
				String roleCode = String.format("%08d", lastNo);
				commRoleMasterModel = new clsWebClubCommitteeMemberRoleMasterModel(new clsWebClubCommitteeMemberRoleMasterModel_ID(roleCode, clientCode));
				commRoleMasterModel.setIntId(lastNo);
				commRoleMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				commRoleMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubCommitteeMemberRoleMasterModel objMasterModel = objCommitteeMemberRoleService.funGetWebClubCommitteeMemberRoleMaster(objBean.getStrRoleCode(), clientCode);
				if (null == objMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblcommitteememberrolemaster", "CommitteeMemberRoleMaster", "intId", clientCode);
					String roleCode = String.format("%08d", lastNo);
					commRoleMasterModel = new clsWebClubCommitteeMemberRoleMasterModel(new clsWebClubCommitteeMemberRoleMasterModel_ID(roleCode, clientCode));
					commRoleMasterModel.setIntId(lastNo);
					commRoleMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					commRoleMasterModel.setStrUserCreated(userCode);

				} else {
					commRoleMasterModel = new clsWebClubCommitteeMemberRoleMasterModel(new clsWebClubCommitteeMemberRoleMasterModel_ID(objBean.getStrRoleCode(), clientCode));
				}
			}

			commRoleMasterModel.setStrRoleDesc(objBean.getStrRoleDesc());
			commRoleMasterModel.setIntRoleRank(objBean.getIntRoleRank());

			return commRoleMasterModel;
		}

		case "Relation": {
			clsWebClubRelationMasterModel relationMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrRelationCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblrelationmaster", "RelationMaster", "intId", clientCode);
				String reasonCode = "REL" + String.format("%06d", lastNo);
				relationMasterModel = new clsWebClubRelationMasterModel(new clsWebClubRelationMasterModel_ID(reasonCode, clientCode));
				relationMasterModel.setIntId(lastNo);
				relationMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				relationMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubRelationMasterModel reasMasterModel = objRelationMasterService.funGetWebClubRelationMaster(objBean.getStrRelationCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblrelationmaster", "RelationMaster", "intId", clientCode);
					String relationCode = "REL" + String.format("%06d", lastNo);
					relationMasterModel = new clsWebClubRelationMasterModel(new clsWebClubRelationMasterModel_ID(relationCode, clientCode));
					relationMasterModel.setIntId(lastNo);
					relationMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					relationMasterModel.setStrUserCreated(userCode);

				} else {
					relationMasterModel = new clsWebClubRelationMasterModel(new clsWebClubRelationMasterModel_ID(objBean.getStrRelationCode(), clientCode));
				}
			}

			relationMasterModel.setStrRelation(objBean.getStrRelationDesc());
			relationMasterModel.setStrAgeLimit(objBean.getStrAgeLimit());

			return relationMasterModel;
		}

		case "Staff": {
			clsWebClubStaffMasterModel staffMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrStaffCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblstaffmaster", "StaffMaster", "intId", clientCode);
				String staffCode = "STA" + String.format("%06d", lastNo);
				staffMasterModel = new clsWebClubStaffMasterModel(new clsWebClubStaffMasterModel_ID(staffCode, clientCode));
				staffMasterModel.setIntId(lastNo);
				staffMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				staffMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubStaffMasterModel reasMasterModel = objStaffMasterService.funGetWebClubStaffMaster(objBean.getStrStaffCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblstaffmaster", "StaffMaster", "intId", clientCode);
					String staffCode = "STA" + String.format("%06d", lastNo);
					staffMasterModel = new clsWebClubStaffMasterModel(new clsWebClubStaffMasterModel_ID(staffCode, clientCode));
					staffMasterModel.setIntId(lastNo);
					staffMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					staffMasterModel.setStrUserCreated(userCode);

				} else {
					staffMasterModel = new clsWebClubStaffMasterModel(new clsWebClubStaffMasterModel_ID(objBean.getStrStaffCode(), clientCode));
				}
			}

			staffMasterModel.setStrStaffName(objBean.getStrStaffName());

			return staffMasterModel;
		}
		case "CurrencyDetails": {
			clsWebClubCurrencyDetailsMasterModel currencyMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrCurrCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblcurrencydetails", "CurrencyDetailsMaster", "intId", clientCode);
				String currCode = "CURR" + String.format("%06d", lastNo);
				currencyMasterModel = new clsWebClubCurrencyDetailsMasterModel(new clsWebClubCurrencyDetailsMasterModel_ID(currCode, clientCode));
				currencyMasterModel.setIntId(lastNo);
				currencyMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				currencyMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubCurrencyDetailsMasterModel reasMasterModel = objCurrencyDetailsMasterService.funGetWebClubCurrencyDetailsMaster(objBean.getStrCurrCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblcurrencydetails", "CurrencyDetailsMaster", "intId", clientCode);
					String currCode = "CURR" + String.format("%05d", lastNo);
					currencyMasterModel = new clsWebClubCurrencyDetailsMasterModel(new clsWebClubCurrencyDetailsMasterModel_ID(currCode, clientCode));
					currencyMasterModel.setIntId(lastNo);
					currencyMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					currencyMasterModel.setStrUserCreated(userCode);

				} else {
					currencyMasterModel = new clsWebClubCurrencyDetailsMasterModel(new clsWebClubCurrencyDetailsMasterModel_ID(objBean.getStrCurrCode(), clientCode));
				}
			}

			currencyMasterModel.setStrDesc(objBean.getStrDesc());
			currencyMasterModel.setStrCurrUnit(objBean.getStrCurrUnit());
			currencyMasterModel.setStrExchangeRate(objBean.getStrExchangeRate());
			currencyMasterModel.setStrLongDeciDesc(objBean.getStrLongDeciDesc());
			currencyMasterModel.setStrShortDeciDesc(objBean.getStrShortDeciDesc());
			currencyMasterModel.setStrShortDesc(objBean.getStrShortDesc());
			currencyMasterModel.setStrTraChkRate(objBean.getStrTraChkRate());
			currencyMasterModel.setIntDec(objBean.getIntDec());

			return currencyMasterModel;
		}

		case "InvitedBy": {
			clsWebClubInvitedByMasterModel invitedByMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrInvCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblinvitedby", "InvitedByMaster", "intId", clientCode);
				String invCode = "IN" + String.format("%07d", lastNo);
				invitedByMasterModel = new clsWebClubInvitedByMasterModel(new clsWebClubInvitedByMasterModel_ID(invCode, clientCode));
				invitedByMasterModel.setIntId(lastNo);
				invitedByMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				invitedByMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubInvitedByMasterModel reasMasterModel = objInvitedByMasterService.funGetWebClubInvitedByMaster(objBean.getStrInvCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblinvitedby", "InvitedByMaster", "intId", clientCode);
					String invCode = "IN" + String.format("%07d", lastNo);
					invitedByMasterModel = new clsWebClubInvitedByMasterModel(new clsWebClubInvitedByMasterModel_ID(invCode, clientCode));
					invitedByMasterModel.setIntId(lastNo);
					invitedByMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					invitedByMasterModel.setStrUserCreated(userCode);

				} else {
					invitedByMasterModel = new clsWebClubInvitedByMasterModel(new clsWebClubInvitedByMasterModel_ID(objBean.getStrInvCode(), clientCode));
				}
			}

			invitedByMasterModel.setStrInvName(objBean.getStrInvName());
			invitedByMasterModel.setStrMecompCode(objBean.getStrMecompCode());

			return invitedByMasterModel;
		}

		case "ItemCategory": {
			clsWebClubItemCategoryMasterModel itemCategoryMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrItemCategoryCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblitemcategory", "ItemCategoryMaster", "intId", clientCode);
				String icCode = "IC" + String.format("%07d", lastNo);
				itemCategoryMasterModel = new clsWebClubItemCategoryMasterModel(new clsWebClubItemCategoryMasterModel_ID(icCode, clientCode));
				itemCategoryMasterModel.setIntId(lastNo);
				itemCategoryMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				itemCategoryMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubItemCategoryMasterModel reasMasterModel = objItemCategoryMasterService.funGetWebClubItemCategoryMaster(objBean.getStrItemCategoryCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblitemcategory", "ItemCategoryMaster", "intId", clientCode);
					String icCode = "IC" + String.format("%07d", lastNo);
					itemCategoryMasterModel = new clsWebClubItemCategoryMasterModel(new clsWebClubItemCategoryMasterModel_ID(icCode, clientCode));
					itemCategoryMasterModel.setIntId(lastNo);
					itemCategoryMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					itemCategoryMasterModel.setStrUserCreated(userCode);

				} else {
					itemCategoryMasterModel = new clsWebClubItemCategoryMasterModel(new clsWebClubItemCategoryMasterModel_ID(objBean.getStrItemCategoryCode(), clientCode));
				}
			}

			itemCategoryMasterModel.setStrItemCategoryName(objBean.getStrItemCategoryName());
			itemCategoryMasterModel.setStrAccountIn(objBean.getStrAccountIn());
			itemCategoryMasterModel.setIntRowId(lastNo);
			itemCategoryMasterModel.setStrAddUserId(objBean.getStrAddUserId());
			itemCategoryMasterModel.setStrCatItemType(objBean.getStrCatItemType());
			itemCategoryMasterModel.setStrDisAccIn(objBean.getStrDisAccIn());
			itemCategoryMasterModel.setStrFreeze(objBean.getStrFreeze());
			itemCategoryMasterModel.setStrGLCode(objBean.getStrGLCode());
			itemCategoryMasterModel.setStrItemTypeCode(objBean.getStrItemTypeCode());
			itemCategoryMasterModel.setStrSideledgerCode(objBean.getStrSideledgerCode());
			itemCategoryMasterModel.setStrTaxCode(objBean.getStrTaxCode());
			itemCategoryMasterModel.setStrTaxName(objBean.getStrTaxName());
			itemCategoryMasterModel.setStrTaxType(objBean.getStrTaxType());

			return itemCategoryMasterModel;
		}

		case "Profile": {
			clsWebClubProfileMasterModel profileMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrProfileCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblprofilesource", "ProfileMaster", "intId", clientCode);
				String profileCode = "PRO" + String.format("%06d", lastNo);
				profileMasterModel = new clsWebClubProfileMasterModel(new clsWebClubProfileMasterModel_ID(profileCode, clientCode));
				profileMasterModel.setIntId(lastNo);
				profileMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				profileMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubProfileMasterModel reasMasterModel = objProfileMasterService.funGetWebClubProfileMaster(objBean.getStrProfileCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblprofilesource", "ProfileMaster", "intId", clientCode);
					String profileCode = "PRO" + String.format("%06d", lastNo);
					profileMasterModel = new clsWebClubProfileMasterModel(new clsWebClubProfileMasterModel_ID(profileCode, clientCode));
					profileMasterModel.setIntId(lastNo);
					profileMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					profileMasterModel.setStrUserCreated(userCode);

				} else {
					profileMasterModel = new clsWebClubProfileMasterModel(new clsWebClubProfileMasterModel_ID(objBean.getStrProfileCode(), clientCode));
				}
			}

			profileMasterModel.setStrProfileDesc(objBean.getStrProfileDesc());

			return profileMasterModel;
		}

		case "Salutation": {
			clsWebClubSalutationMasterModel salutationMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrSalutationCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblsalutationmaster", "SalutationMaster", "intId", clientCode);
				String salCode = "SAL" + String.format("%06d", lastNo);
				salutationMasterModel = new clsWebClubSalutationMasterModel(new clsWebClubSalutationMasterModel_ID(salCode, clientCode));
				salutationMasterModel.setIntId(lastNo);
				salutationMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				salutationMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubSalutationMasterModel reasMasterModel = objSalutationMasterService.funGetWebClubSalutationMaster(objBean.getStrSalutationCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblsalutationmaster", "SalutationMaster", "intId", clientCode);
					String salCode = "SAL" + String.format("%06d", lastNo);
					salutationMasterModel = new clsWebClubSalutationMasterModel(new clsWebClubSalutationMasterModel_ID(salCode, clientCode));
					salutationMasterModel.setIntId(lastNo);
					salutationMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					salutationMasterModel.setStrUserCreated(userCode);

				} else {
					salutationMasterModel = new clsWebClubSalutationMasterModel(new clsWebClubSalutationMasterModel_ID(objBean.getStrSalutationCode(), clientCode));
				}
			}

			salutationMasterModel.setStrSalutationDesc(objBean.getStrSalutationDesc());

			return salutationMasterModel;
		}

		case "Title": {
			clsWebClubTitleMasterModel titleMasterModel;

			objGlobal = new clsGlobalFunctions();
			long lastNo = 0;
			if (objBean.getStrTitleCode().trim().length() == 0) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbltitlemaster", "TitleMaster", "intId", clientCode);
				String ttlCode = "TTL" + String.format("%06d", lastNo);
				titleMasterModel = new clsWebClubTitleMasterModel(new clsWebClubTitleMasterModel_ID(ttlCode, clientCode));
				titleMasterModel.setIntId(lastNo);
				titleMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				titleMasterModel.setStrUserCreated(userCode);
			} else {
				clsWebClubTitleMasterModel reasMasterModel = objTitleMasterService.funGetWebClubTitleMaster(objBean.getStrTitleCode(), clientCode);
				if (null == reasMasterModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tbltitlemaster", "TitleMaster", "intId", clientCode);
					String ttlCode = "TTL" + String.format("%06d", lastNo);
					titleMasterModel = new clsWebClubTitleMasterModel(new clsWebClubTitleMasterModel_ID(ttlCode, clientCode));
					titleMasterModel.setIntId(lastNo);
					titleMasterModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					titleMasterModel.setStrUserCreated(userCode);

				} else {
					titleMasterModel = new clsWebClubTitleMasterModel(new clsWebClubTitleMasterModel_ID(objBean.getStrTitleCode(), clientCode));
				}
			}

			titleMasterModel.setStrTitleDesc(objBean.getStrTitleDesc());

			return titleMasterModel;
		}

		}

		return null;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadAreaCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubAreaMasterModel funLoadAreaMasterData(@RequestParam("docCode") String areaCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubAreaMasterModel objArea = objAreaMasterService.funGetWebClubAreaMaster(areaCode, clientCode);
		if (null == objArea) {
			objArea = new clsWebClubAreaMasterModel();
			objArea.setStrAreaCode("Invalid Code");
		}

		return objArea;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadAllDataOfPaticulorMaster", method = RequestMethod.GET)
	public @ResponseBody List funLoadAllAreaData(@RequestParam("docCode") String tableName, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List tableElementList = objGeneralMasterService.funGetWebClubAllPaticulorMasterData(tableName, clientCode);
		if (null == tableElementList) {
			tableElementList = null;

		}

		return tableElementList;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadRegionCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubRegionMasterModel funLoadRegionMasterData(@RequestParam("docCode") String regionCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubRegionMasterModel objRegion = objRegionMasterService.funGetWebClubRegionMaster(regionCode, clientCode);
		if (null == objRegion) {
			objRegion = new clsWebClubRegionMasterModel();
			objRegion.setStrRegionCode("Invalid Code");
		}

		return objRegion;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadStateCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubStateMasterModel funLoadStateMasterData(@RequestParam("docCode") String stateCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubStateMasterModel objRegion = objStateMasterService.funGetWebClubStateMaster(stateCode, clientCode);
		if (null == objRegion) {
			objRegion = new clsWebClubStateMasterModel();
			objRegion.setStrRegionCode("Invalid Code");
		}

		return objRegion;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadCityCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubCityMasterModel funLoadCityMasterData(@RequestParam("docCode") String cityCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubCityMasterModel objCity = objCityMasterService.funGetWebClubCityMaster(cityCode, clientCode);
		if (null == objCity) {
			objCity = new clsWebClubCityMasterModel();
			objCity.setStrCityCode("Invalid Code");
		}

		return objCity;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadCountryCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubCountryMasterModel funLoadCountryMasterData(@RequestParam("docCode") String countryCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubCountryMasterModel objCountry = objCountryMasterService.funGetWebClubCountryMaster(countryCode, clientCode);
		if (null == objCountry) {
			objCountry = new clsWebClubCountryMasterModel();
			objCountry.setStrCountryCode("Invalid Code");
		}

		return objCountry;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadEducation", method = RequestMethod.GET)
	public @ResponseBody clsWebClubEducationMasterModel funLoadEducationMasterData(@RequestParam("docCode") String eduCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubEducationMasterModel objedu = objEducationMasterService.funGetWebClubEducationMaster(eduCode, clientCode);
		if (null == objedu) {
			objedu = new clsWebClubEducationMasterModel();
			objedu.setStrEducationCode("Invalid Code");
		}

		return objedu;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadMarital", method = RequestMethod.GET)
	public @ResponseBody clsWebClubMaritalStatusModel funLoadMaritalStatusModelData(@RequestParam("docCode") String msCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubMaritalStatusModel objMarital = objMaritalStatusService.funGetWebClubMaritalStatus(msCode, clientCode);
		if (null == objMarital) {
			objMarital = new clsWebClubMaritalStatusModel();
			objMarital.setStrMaritalCode("Invalid Code");
		}

		return objMarital;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadProfession", method = RequestMethod.GET)
	public @ResponseBody clsWebClubProfessionMasterModel funLoadProfessionModelData(@RequestParam("docCode") String proCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubProfessionMasterModel objPro = objProfessionMasterSerice.funGetWebClubProfessionMaster(proCode, clientCode);
		if (null == objPro) {
			objPro = new clsWebClubProfessionMasterModel();
			objPro.setStrProfessionCode("Invalid Code");
		}

		return objPro;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadDesignation", method = RequestMethod.GET)
	public @ResponseBody clsWebClubDesignationMasterModel funLoadDesignationModelData(@RequestParam("docCode") String desCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubDesignationMasterModel objDesignation = objDesignationMasterService.funGetWebClubDesignationMaster(desCode, clientCode);
		if (null == objDesignation) {
			objDesignation = new clsWebClubDesignationMasterModel();
			objDesignation.setStrDesignationCode("Invalid Code");
		}

		return objDesignation;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadReason", method = RequestMethod.GET)
	public @ResponseBody clsWebClubReasonMasterModel funLoadReasonModelData(@RequestParam("docCode") String reasonCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubReasonMasterModel objReason = objReasonMasterService.funGetWebClubReasonMaster(reasonCode, clientCode);
		if (null == objReason) {
			objReason = new clsWebClubReasonMasterModel();
			objReason.setStrReasonCode("Invalid Code");
		}

		return objReason;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadCommitteeMemberRole", method = RequestMethod.GET)
	public @ResponseBody clsWebClubCommitteeMemberRoleMasterModel funLoadCommitteeMemberRoleModelData(@RequestParam("docCode") String reasonCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubCommitteeMemberRoleMasterModel objMemberRole = objCommitteeMemberRoleService.funGetWebClubCommitteeMemberRoleMaster(reasonCode, clientCode);
		if (null == objMemberRole) {
			objMemberRole = new clsWebClubCommitteeMemberRoleMasterModel();
			objMemberRole.setStrRoleCode("Invalid Code");
		}

		return objMemberRole;
	}

	@RequestMapping(value = "/loadRelationCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubRelationMasterModel funLoadRelationModelData(@RequestParam("docCode") String reasonCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubRelationMasterModel objRelation = objRelationMasterService.funGetWebClubRelationMaster(reasonCode, clientCode);
		if (null == objRelation) {
			objRelation = new clsWebClubRelationMasterModel();
			objRelation.setStrRelationCode("Invalid Code");
		}

		return objRelation;
	}

	@RequestMapping(value = "/loadStaffCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubStaffMasterModel funLoadStaffModelData(@RequestParam("docCode") String reasonCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubStaffMasterModel objStaff = objStaffMasterService.funGetWebClubStaffMaster(reasonCode, clientCode);
		if (null == objStaff) {
			objStaff = new clsWebClubStaffMasterModel();
			objStaff.setStrStaffCode("Invalid Code");
		}

		return objStaff;
	}

	@RequestMapping(value = "/loadCurrencyDetailsCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubCurrencyDetailsMasterModel funLoadCurrencyDetailsModelData(@RequestParam("docCode") String currCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubCurrencyDetailsMasterModel objCurrency = objCurrencyDetailsMasterService.funGetWebClubCurrencyDetailsMaster(currCode, clientCode);
		if (null == objCurrency) {
			objCurrency = new clsWebClubCurrencyDetailsMasterModel();
			objCurrency.setStrCurrCode("Invalid Code");
		}

		return objCurrency;
	}

	@RequestMapping(value = "/loadInvitedByCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubInvitedByMasterModel funLoadInvitedByModelData(@RequestParam("docCode") String invCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubInvitedByMasterModel objInv = objInvitedByMasterService.funGetWebClubInvitedByMaster(invCode, clientCode);
		if (null == objInv) {
			objInv = new clsWebClubInvitedByMasterModel();
			objInv.setStrInvCode("Invalid Code");
		}

		return objInv;
	}

	@RequestMapping(value = "/loadItemCategoryCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubItemCategoryMasterModel funLoadItemCategoryModelData(@RequestParam("docCode") String itemCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubItemCategoryMasterModel objItem = objItemCategoryMasterService.funGetWebClubItemCategoryMaster(itemCode, clientCode);
		if (null == objItem) {
			objItem = new clsWebClubItemCategoryMasterModel();
			objItem.setStrItemCategoryCode("Invalid Code");
		}

		return objItem;
	}

	@RequestMapping(value = "/loadProfileCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubProfileMasterModel funLoadProfileModelData(@RequestParam("docCode") String proCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubProfileMasterModel objProfile = objProfileMasterService.funGetWebClubProfileMaster(proCode, clientCode);
		if (null == objProfile) {
			objProfile = new clsWebClubProfileMasterModel();
			objProfile.setStrProfileCode("Invalid Code");
		}

		return objProfile;
	}

	@RequestMapping(value = "/loadSalutationCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubSalutationMasterModel funLoadSalutationModelData(@RequestParam("docCode") String currCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubSalutationMasterModel objSalutation = objSalutationMasterService.funGetWebClubSalutationMaster(currCode, clientCode);
		if (null == objSalutation) {
			objSalutation = new clsWebClubSalutationMasterModel();
			objSalutation.setStrSalutationCode("Invalid Code");
		}

		return objSalutation;
	}

	@RequestMapping(value = "/loadTitleCode", method = RequestMethod.GET)
	public @ResponseBody clsWebClubTitleMasterModel funLoadTitleModelData(@RequestParam("docCode") String currCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubTitleMasterModel objTitle = objTitleMasterService.funGetWebClubTitleMaster(currCode, clientCode);
		if (null == objTitle) {
			objTitle = new clsWebClubTitleMasterModel();
			objTitle.setStrTitleCode("Invalid Code");
		}

		return objTitle;
	}

}
