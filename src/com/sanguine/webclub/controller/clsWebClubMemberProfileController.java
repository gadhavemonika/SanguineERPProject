package com.sanguine.webclub.controller;

import java.util.ArrayList;
import java.util.LinkedList;
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

import com.sanguine.bean.clsPOSItemMasterImportBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubMemberProfileBean;
import com.sanguine.webclub.model.clsWebClubAreaMasterModel;
import com.sanguine.webclub.model.clsWebClubCityMasterModel;
import com.sanguine.webclub.model.clsWebClubDependentMasterModel;
import com.sanguine.webclub.model.clsWebClubMemberProfileModel;
import com.sanguine.webclub.model.clsWebClubMemberProfileModel_ID;
import com.sanguine.webclub.service.clsWebClubAreaMasterService;
import com.sanguine.webclub.service.clsWebClubCityMasterService;
import com.sanguine.webclub.service.clsWebClubCountryMasterService;
import com.sanguine.webclub.service.clsWebClubDependentMasterService;
import com.sanguine.webclub.service.clsWebClubMemberProfileService;
import com.sanguine.webclub.service.clsWebClubRegionMasterService;
import com.sanguine.webclub.service.clsWebClubStateMasterService;

@Controller
public class clsWebClubMemberProfileController {

	clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsWebClubMemberProfileService objMemberProfileService;

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
	private clsWebClubDependentMasterService objDependentMasterService;

	// Open MemberProfile
	@RequestMapping(value = "/frmMemberProfile", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMemberProfile_1", "command", new clsWebClubMemberProfileBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMemberProfile", "command", new clsWebClubMemberProfileBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savefrmWebClubMemberProfile", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubMemberProfileBean memProfileBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			// for primary member
			clsWebClubMemberProfileModel objMemProfileModel = funPrepareModel(memProfileBean, req);
			objMemberProfileService.funAddUpdateMemberProfile(objMemProfileModel);

			// for Spouse member
			clsWebClubMemberProfileModel objMemberProfileSpouseModel = funPrepardSpouseModel(memProfileBean, objMemProfileModel, req);
			objMemberProfileService.funAddUpdateMemberProfile(objMemberProfileSpouseModel);

			// for Dependent member
			funPrepardDependentModel(memProfileBean, objMemProfileModel, req);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Member Code : ".concat(objMemProfileModel.getStrMemberCode()));
			return new ModelAndView("redirect:/frmMemberProfile.html?saddr=" + urlHits);
		}
		return new ModelAndView("redirect:/frmMemberProfile.html?saddr=" + urlHits);

	}

	private clsWebClubMemberProfileModel funPrepareModel(clsWebClubMemberProfileBean memProfileBean, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubMemberProfileModel mpModel;
		if (memProfileBean.getStrCustomerCode().trim().length() == 0) {

			lastNo = objGlobalFunctionsService.funGetLastNo("tblmembermaster", "MemberProfile", "intGId", clientCode);
			String customerCode = "C" + String.format("%06d", lastNo);
			mpModel = new clsWebClubMemberProfileModel(new clsWebClubMemberProfileModel_ID(customerCode, clientCode));
			mpModel.setIntGId(lastNo);
			mpModel.setStrCustomerID("01");
			mpModel.setStrUserCreated(userCode);
			mpModel.setStrUserModified(userCode);
			mpModel.setStrPropertyCode(propCode);
			mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			mpModel.setStrPrimaryCode(customerCode);

		} else {

			clsWebClubMemberProfileModel objMemberProfile = objMemberProfileService.funGetCustomer(memProfileBean.getStrCustomerCode(), clientCode);
			if (null == objMemberProfile) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblmembermaster", "MemberProfile", "intGId", clientCode);
				String customerCode = "C" + String.format("%06d", lastNo);
				mpModel = new clsWebClubMemberProfileModel(new clsWebClubMemberProfileModel_ID(customerCode, clientCode));
				mpModel.setIntGId(lastNo);
				mpModel.setStrCustomerID("01");
				mpModel.setStrUserCreated(userCode);
				mpModel.setStrUserModified(userCode);
				mpModel.setStrPropertyCode(propCode);
				mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setStrPrimaryCode(customerCode);
			} else {
				mpModel = new clsWebClubMemberProfileModel(new clsWebClubMemberProfileModel_ID(memProfileBean.getStrCustomerCode(), clientCode));
				mpModel.setStrUserModified(userCode);
				mpModel.setStrPropertyCode(propCode);
				mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setStrPrimaryCode(memProfileBean.getStrCustomerCode());
			}
		}
		// String memberCode ="";
		// String custID =
		// objMemberProfileService.funGetCustomerID(mpModel.getStrCustomerCode(),
		// clientCode);
		// if(mpModel.getStrCustomerID()=="01")
		// {
		//
		// memberCode = memProfileBean.getStrMemberCode() +
		// " "+mpModel.getStrCustomerID();
		// mpModel.setStrMemberCode(memberCode);
		//
		// }else
		// {
		//
		// if(!(memProfileBean.getStrMemberCode().contains(" ")))
		// {
		// int intcustId = Integer.parseInt(custID);
		// intcustId = intcustId+1;
		// mpModel.setStrCustomerID("0"+intcustId);
		// memberCode = memProfileBean.getStrMemberCode() +
		// mpModel.getStrCustomerID();
		// mpModel.setStrMemberCode(memberCode);
		// }else
		// {
		// mpModel.setStrMemberCode(memProfileBean.getStrMemberCode());
		// }
		//
		// }

		mpModel.setStrMemberCode(memProfileBean.getStrMemberCode() + " 01");

		mpModel.setStrUserCreated(userCode);
		mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		mpModel.setStrPrefixCode(memProfileBean.getStrPrefixCode());
		mpModel.setStrFirstName(memProfileBean.getStrFirstName());
		mpModel.setStrMiddleName(memProfileBean.getStrMiddleName());
		mpModel.setStrLastName(memProfileBean.getStrLastName());
		mpModel.setStrFullName(memProfileBean.getStrFullName());
		mpModel.setStrNameOnCard(memProfileBean.getStrNameOnCard());

		// Residence Address
		mpModel.setStrResidentAddressLine1(memProfileBean.getStrResidentAddressLine1());
		mpModel.setStrResidentAddressLine2(memProfileBean.getStrResidentAddressLine2());
		mpModel.setStrResidentAddressLine3(memProfileBean.getStrResidentAddressLine3());
		mpModel.setStrResidentAreaCode(memProfileBean.getStrResidentAreaCode());
		mpModel.setStrResidentCountryCode(memProfileBean.getStrResidentCountryCode());
		mpModel.setStrResidentCtCode(memProfileBean.getStrResidentCtCode());
		mpModel.setStrResidentEmailID(memProfileBean.getStrResidentEmailID());
		mpModel.setStrResidentFax1(memProfileBean.getStrResidentFax1());
		mpModel.setStrResidentFax2(memProfileBean.getStrResidentFax2());
		mpModel.setStrResidentLandMark(memProfileBean.getStrResidentLandMark());
		mpModel.setStrResidentMobileNo(memProfileBean.getStrResidentMobileNo());
		mpModel.setStrResidentPinCode(memProfileBean.getStrResidentPinCode());
		mpModel.setStrResidentRegionCode(memProfileBean.getStrResidentRegionCode());
		mpModel.setStrResidentStateCode(memProfileBean.getStrResidentStateCode());
		mpModel.setStrResidentTelephone1(memProfileBean.getStrResidentTelephone1());
		mpModel.setStrResidentTelephone2(memProfileBean.getStrResidentTelephone2());

		// Company Address
		mpModel.setStrCompanyAddressLine1(memProfileBean.getStrCompanyAddressLine1());
		mpModel.setStrCompanyAddressLine2(memProfileBean.getStrCompanyAddressLine2());
		mpModel.setStrCompanyAddressLine3(memProfileBean.getStrCompanyAddressLine3());
		mpModel.setStrCompanyAreaCode(memProfileBean.getStrCompanyAreaCode());
		mpModel.setStrCompanyCode(memProfileBean.getStrCompanyCode());
		mpModel.setStrCompanyCountryCode(memProfileBean.getStrCompanyCountryCode());
		mpModel.setStrCompanyCtCode(memProfileBean.getStrCompanyCtCode());
		mpModel.setStrCompanyEmailID(memProfileBean.getStrCompanyEmailID());
		mpModel.setStrCompanyFax1(memProfileBean.getStrCompanyFax1());
		mpModel.setStrCompanyFax2(memProfileBean.getStrCompanyFax2());
		mpModel.setStrCompanyLandMark(memProfileBean.getStrCompanyLandMark());
		mpModel.setStrCompanyMobileNo(memProfileBean.getStrCompanyMobileNo());
		mpModel.setStrCompanyName(memProfileBean.getStrCompanyName());
		mpModel.setStrCompanyPinCode(memProfileBean.getStrCompanyPinCode());
		mpModel.setStrCompanyRegionCode(memProfileBean.getStrCompanyRegionCode());
		mpModel.setStrCompanyStateCode(memProfileBean.getStrCompanyStateCode());
		mpModel.setStrCompanyTelePhone1(memProfileBean.getStrCompanyTelePhone1());
		mpModel.setStrCompanyTelePhone2(memProfileBean.getStrCompanyTelePhone2());
		mpModel.setStrHoldingCode(memProfileBean.getStrHoldingCode());
		mpModel.setStrJobProfileCode(memProfileBean.getStrJobProfileCode());

		// Bill Address
		mpModel.setStrBillingAddressLine1(memProfileBean.getStrBillingAddressLine1());
		mpModel.setStrBillingAddressLine2(memProfileBean.getStrBillingAddressLine2());
		mpModel.setStrBillingAddressLine3(memProfileBean.getStrBillingAddressLine3());
		mpModel.setStrBillingAreaCode(memProfileBean.getStrBillingAreaCode());
		mpModel.setStrBillingCountryCode(memProfileBean.getStrBillingCountryCode());
		mpModel.setStrBillingCtCode(memProfileBean.getStrBillingCtCode());
		mpModel.setStrBillingEmailID(memProfileBean.getStrBillingEmailID());
		mpModel.setStrBillingFax1(memProfileBean.getStrBillingFax1());
		mpModel.setStrBillingFax2(memProfileBean.getStrBillingFax2());
		mpModel.setStrBillingFlag(memProfileBean.getStrBillingFlag());
		mpModel.setStrBillingLandMark(memProfileBean.getStrBillingLandMark());
		mpModel.setStrBillingMobileNo(memProfileBean.getStrBillingMobileNo());
		mpModel.setStrBillingPinCode(memProfileBean.getStrBillingPinCode());
		mpModel.setStrBillingRegionCode(memProfileBean.getStrBillingRegionCode());
		mpModel.setStrBillingStateCode(memProfileBean.getStrBillingStateCode());
		mpModel.setStrBillingTelePhone1(memProfileBean.getStrBillingTelePhone1());
		mpModel.setStrBillingTelePhone2(memProfileBean.getStrBillingTelePhone2());

		// Personal Information
		mpModel.setStrGender(memProfileBean.getStrGender());
		mpModel.setDteDateofBirth(memProfileBean.getDteDateofBirth());
		mpModel.setStrMaritalStatus(memProfileBean.getStrMaritalStatus());
		mpModel.setStrProfessionCode(memProfileBean.getStrProfessionCode());
		mpModel.setDteMembershipStartDate(memProfileBean.getDteMembershipStartDate());
		mpModel.setDteMembershipEndDate(memProfileBean.getDteMembershipEndDate());
		mpModel.setDteAnniversary(memProfileBean.getDteAnniversary());
		// mpModel.setStrpName

		// Member Information
		// mpModel.setStr txtMSCategoryCode
		// mpModel.setStr txtMSCategoryNamae
		mpModel.setStrCategoryCode(memProfileBean.getStrCategoryCode());
		mpModel.setStrProposerCode(memProfileBean.getStrProposerCode());
		// mpModel.setStrProposerName
		mpModel.setStrSeconderCode(memProfileBean.getStrSeconderCode());
		// mpModel.setStrseconderName
		mpModel.setStrFatherMemberCode(memProfileBean.getStrFatherMemberCode());
		mpModel.setStrInstation(memProfileBean.getStrInstation());

		// mpModel.set txtdtMembershipStartDate
		// mpModel.setStr txtdtMembershipEndDate
		// mpModel.setStrBlocked(memProfileBean.getStrBlocked());
		mpModel.setStrAlternateMemberCode("");
		mpModel.setStrAttachment("");

		// Card Authontication Check Box
		// mpModel.setStrReasonCode(memProfileBean.getStrBlockedreasonCode());
		mpModel.setStrQualification(memProfileBean.getStrQualification());
		mpModel.setStrDesignationCode(memProfileBean.getStrDesignationCode());
		mpModel.setDblEntranceFee(memProfileBean.getDblEntranceFee());
		mpModel.setDblSubscriptionFee(memProfileBean.getDblSubscriptionFee());
		mpModel.setStrPanNumber(memProfileBean.getStrPanNumber());
		// mpModel.setStr Bill Detail
		mpModel.setStrLocker(memProfileBean.getStrLocker());
		mpModel.setStrLibrary(memProfileBean.getStrLibrary());
		mpModel.setStrSeniorCitizen(memProfileBean.getStrSeniorCitizen());
		mpModel.setStrStopCredit(memProfileBean.getStrStopCredit());
		// mpModel.setStr Rescident Yes/No
		mpModel.setStrInstation(memProfileBean.getStrInstation());
		mpModel.setStrGolfMemberShip(memProfileBean.getStrGolfMemberShip());
		mpModel.setStrBlocked(memProfileBean.getStrBlocked());
		mpModel.setStrBlockedreasonCode(memProfileBean.getStrBlockedreasonCode());
		mpModel.setStrDepedentRelation("");

		mpModel.setDtePermitExpDate("1990-01-01 00:00:00");
		mpModel.setStrLiquorPermitNo("");
		mpModel.setIntFormNo(0);
		mpModel.setStrGuestEntry("Y");
		mpModel.setStrVirtualAccountCode("");
		mpModel.setChkmail(0);
		mpModel.setStrSSuffixCode("0");
		mpModel.setStrNSuffixCode("0");

		mpModel.setChrCircularemail("0");
		mpModel.setStrAuthorisedMember("");
		mpModel.setStrMemberStatusCode("");
		mpModel.setStrLikes("");
		mpModel.setStrDisLikes("");
		mpModel.setStrSendInvThrough("");
		mpModel.setStrSendCircularNoticeThrough("");
		mpModel.setDteInterviewDate("1990-01-01 00:00:00");
		mpModel.setDblCMSBalance(0.00);
		mpModel.setStrPhoto("");
		mpModel.setStrRemark("");
		mpModel.setStrDependentYesNo("N");
		mpModel.setStrSalesStaffCode("");
		mpModel.setDteProfileCreationDate("1990-01-01 00:00:00");
		mpModel.setStrResNonRes("Y");
		mpModel.setDteDependentDateofBirth("1990-01-01 00:00:00");
		mpModel.setDteMemberBlockDate("1990-01-01 00:00:00");
		mpModel.setDteMembershipExpiryDate(memProfileBean.getDteMembershipEndDate());
		mpModel.setStrDebtorCode("");
		mpModel.setStrDependentFullName("");
		mpModel.setStrDependentMemberCode("");
		mpModel.setStrDependentReasonCode("");

		mpModel.setStrCustomerID("");
		mpModel.setStrBillingFlag("N");
		mpModel.setStrMemberYesNo("");

		// mpModel.set SEND INNVOICE Through

		// mpModel.setStr Circle Notice

		// Facility Information

		// mpModel.setstr Facility Information
		// mpModel.setStrPayment;

		// mpModel.set From Date
		// mpModel.set To Date

		// mpModel.setst Block Facilty

		return mpModel;
	}

	private void funPrepardDependentModel(clsWebClubMemberProfileBean memProfileBean, clsWebClubMemberProfileModel objMemberProfile, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		objGlobal = new clsGlobalFunctions();
		String memberCode = objMemberProfile.getStrMemberCode();

		if (memberCode.length() > 0) {
			memberCode.split(" ");

		}

		// clsWebClubDependentMasterModel objDependentMasterModel = new
		// clsWebClubDependentMasterModel();

		List<clsWebClubDependentMasterModel> listDependentMaster = memProfileBean.getListDependentMember();
		if (null != listDependentMaster && listDependentMaster.size() > 0) {
			for (clsWebClubDependentMasterModel obDM : listDependentMaster) {
				long lastNo = 1;
				clsWebClubMemberProfileModel mpModel = new clsWebClubMemberProfileModel();
				String dependentMember = obDM.getStrMemberCode();
				String[] arrCustID = dependentMember.split(" ");

				if (obDM.getStrCustomerCode() == null) {

					lastNo = objGlobalFunctionsService.funGetLastNo("tblmembermaster", "MemberProfile", "intGId", clientCode);
					String customerCode = "C" + String.format("%06d", lastNo);
					obDM.setStrCustomerCode(customerCode);
					obDM.setIntGId(lastNo);

					obDM.setStrUserCreated(userCode);
					obDM.setStrUserModified(userCode);
					obDM.setStrPropertyCode(propCode);
					obDM.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					obDM.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

					mpModel.setStrCustomerID(arrCustID[1]);
					mpModel.setStrCustomerCode(obDM.getStrCustomerCode());
					mpModel.setStrPrimaryCode(objMemberProfile.getStrCustomerCode());

				} else {
					clsWebClubMemberProfileModel objMemProfile = objMemberProfileService.funGetCustomer(obDM.getStrCustomerCode(), clientCode);
					if (null == objMemProfile) {
						lastNo = objGlobalFunctionsService.funGetLastNo("tblmembermaster", "MemberProfile", "intGId", clientCode);
						String customerCode = "C" + String.format("%06d", lastNo);

						obDM.setStrCustomerCode(customerCode);
						obDM.setStrUserCreated(userCode);
						obDM.setStrUserModified(userCode);
						obDM.setStrPropertyCode(propCode);
						obDM.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						obDM.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
						mpModel.setStrPrimaryCode(objMemberProfile.getStrCustomerCode());

						mpModel.setStrCustomerID(arrCustID[1]);
						mpModel.setStrCustomerCode(obDM.getStrCustomerCode());
						mpModel.setIntGId(lastNo);
					} else {

						mpModel.setIntGId(objMemProfile.getIntGId());
						mpModel.setStrCustomerID(arrCustID[1]);
						mpModel.setStrCustomerCode(obDM.getStrCustomerCode());
						mpModel.setStrPrimaryCode(objMemberProfile.getStrCustomerCode());
					}
				}

				mpModel.setStrMemberCode(obDM.getStrMemberCode());

				mpModel.setStrUserCreated(userCode);
				mpModel.setStrUserModified(userCode);
				mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setStrPropertyCode(propCode);
				obDM.setDteMemberBlockDate("1990-01-01 00:00:00");
				mpModel.setStrClientCode(clientCode);
				mpModel.setStrDepedentRelation(obDM.getStrDepedentRelation());
				mpModel.setStrPrefixCode("");
				mpModel.setStrFirstName("");
				mpModel.setStrMiddleName("");
				mpModel.setStrLastName("");
				mpModel.setStrFullName(obDM.getStrDependentFullName());
				mpModel.setStrNameOnCard(obDM.getStrDependentFullName());

				// Residence Address
				mpModel.setStrResidentAddressLine1(memProfileBean.getStrResidentAddressLine1());
				mpModel.setStrResidentAddressLine2(memProfileBean.getStrResidentAddressLine2());
				mpModel.setStrResidentAddressLine3(memProfileBean.getStrResidentAddressLine3());
				mpModel.setStrResidentAreaCode(memProfileBean.getStrResidentAreaCode());
				mpModel.setStrResidentCountryCode(memProfileBean.getStrResidentCountryCode());
				mpModel.setStrResidentCtCode(memProfileBean.getStrResidentCtCode());
				mpModel.setStrResidentEmailID(memProfileBean.getStrResidentEmailID());
				mpModel.setStrResidentFax1(memProfileBean.getStrResidentFax1());
				mpModel.setStrResidentFax2(memProfileBean.getStrResidentFax2());
				mpModel.setStrResidentLandMark(memProfileBean.getStrResidentLandMark());
				mpModel.setStrResidentMobileNo(memProfileBean.getStrResidentMobileNo());
				mpModel.setStrResidentPinCode(memProfileBean.getStrResidentPinCode());
				mpModel.setStrResidentRegionCode(memProfileBean.getStrResidentRegionCode());
				mpModel.setStrResidentStateCode(memProfileBean.getStrResidentStateCode());
				mpModel.setStrResidentTelephone1(memProfileBean.getStrResidentTelephone1());
				mpModel.setStrResidentTelephone2(memProfileBean.getStrResidentTelephone2());
				// Company Address
				mpModel.setStrCompanyAddressLine1("");
				mpModel.setStrCompanyAddressLine2("");
				mpModel.setStrCompanyAddressLine3("");
				mpModel.setStrCompanyAreaCode("");
				mpModel.setStrCompanyCode("");
				mpModel.setStrCompanyCountryCode("");
				mpModel.setStrCompanyCtCode("");
				mpModel.setStrCompanyEmailID("");
				mpModel.setStrCompanyFax1("");
				mpModel.setStrCompanyFax2("");
				mpModel.setStrCompanyLandMark("");
				mpModel.setStrCompanyMobileNo("");
				mpModel.setStrCompanyName("");
				mpModel.setStrCompanyPinCode("");
				mpModel.setStrCompanyRegionCode("");
				mpModel.setStrCompanyStateCode("");
				mpModel.setStrCompanyTelePhone1("");
				mpModel.setStrCompanyTelePhone2("");
				mpModel.setStrHoldingCode("");
				mpModel.setStrJobProfileCode("");
				// Bill Address
				mpModel.setStrBillingAddressLine1("");
				mpModel.setStrBillingAddressLine2("");
				mpModel.setStrBillingAddressLine3("");
				mpModel.setStrBillingAreaCode("");
				mpModel.setStrBillingCountryCode("");
				mpModel.setStrBillingCtCode("");
				mpModel.setStrBillingEmailID("");
				mpModel.setStrBillingFax1("");
				mpModel.setStrBillingFax2("");
				mpModel.setStrBillingFlag("");
				mpModel.setStrBillingLandMark("");
				mpModel.setStrBillingMobileNo("");
				mpModel.setStrBillingPinCode("");
				mpModel.setStrBillingRegionCode("");
				mpModel.setStrBillingStateCode("");
				mpModel.setStrBillingTelePhone1("");
				mpModel.setStrBillingTelePhone2("");
				// Personal Information
				mpModel.setStrGender(obDM.getStrGender());

				mpModel.setDteDateofBirth(obDM.getDteDependentDateofBirth());
				mpModel.setStrMaritalStatus(obDM.getStrMaritalStatus());
				mpModel.setStrProfessionCode(obDM.getStrProfessionCode());
				mpModel.setDteAnniversary("1990-01-01 00:00:00");
				// mpModel.setStrpName

				// Member Information
				mpModel.setStrCategoryCode("");
				mpModel.setStrProposerCode("");
				mpModel.setStrSeconderCode("");
				mpModel.setStrFatherMemberCode("");
				mpModel.setDteMembershipStartDate(memProfileBean.getDteMembershipStartDate());
				mpModel.setDteMembershipEndDate(obDM.getDteMembershipExpiryDate());

				// Card Authontication Check Box
				mpModel.setStrQualification("");
				mpModel.setStrDesignationCode("");
				mpModel.setDblEntranceFee(new Double(0));
				mpModel.setDblSubscriptionFee(new Double(0));
				mpModel.setStrPanNumber("");
				mpModel.setStrLocker("No");
				mpModel.setStrLibrary("No");
				mpModel.setStrSeniorCitizen("No");
				mpModel.setStrStopCredit("No");
				mpModel.setStrInstation("No");
				mpModel.setStrGolfMemberShip("No");
				mpModel.setStrBlocked(obDM.getStrBlocked());
				mpModel.setStrBlockedreasonCode(obDM.getStrDependentReasonCode());
				mpModel.setStrAlternateMemberCode("");
				mpModel.setStrAttachment("");
				mpModel.setDtePermitExpDate("1990-01-01 00:00:00");
				mpModel.setStrLiquorPermitNo("");
				mpModel.setIntFormNo(0);
				mpModel.setStrGuestEntry("Y");
				mpModel.setStrVirtualAccountCode("");
				mpModel.setChkmail(0);
				mpModel.setStrSSuffixCode("0");
				mpModel.setStrNSuffixCode("0");

				mpModel.setChrCircularemail("0");
				mpModel.setStrAuthorisedMember("");
				mpModel.setStrMemberStatusCode("");
				mpModel.setStrLikes("");
				mpModel.setStrDisLikes("");
				mpModel.setStrSendInvThrough("");
				mpModel.setStrSendCircularNoticeThrough("");
				mpModel.setDteInterviewDate("1990-01-01 00:00:00");
				mpModel.setDblCMSBalance(0.00);
				mpModel.setStrPhoto("");
				mpModel.setStrRemark("");
				mpModel.setStrDependentYesNo("N");
				mpModel.setStrSalesStaffCode("");
				mpModel.setDteProfileCreationDate("1990-01-01 00:00:00");
				mpModel.setStrResNonRes("Y");
				mpModel.setDteDependentDateofBirth("1990-01-01 00:00:00");
				mpModel.setDteMemberBlockDate("1990-01-01 00:00:00");
				mpModel.setDteMembershipExpiryDate(memProfileBean.getDteMembershipEndDate());
				mpModel.setStrDebtorCode("");
				mpModel.setStrDependentFullName("");
				mpModel.setStrDependentMemberCode("");
				mpModel.setStrDependentReasonCode("");

				mpModel.setStrCustomerID("");
				mpModel.setStrBillingFlag("N");
				mpModel.setStrMemberYesNo("");

				objMemberProfileService.funAddUpdateMemberProfile(mpModel);
			}

		}

	}

	private clsWebClubMemberProfileModel funPrepardSpouseModel(clsWebClubMemberProfileBean memProfileBean, clsWebClubMemberProfileModel objMemberProfile, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubMemberProfileModel mpModel;

		if (memProfileBean.getStrSpouseCustomerCode() == null) {

			lastNo = objGlobalFunctionsService.funGetLastNo("tblmembermaster", "MemberProfile", "intGId", clientCode);
			String customerCode = "C" + String.format("%06d", lastNo);
			mpModel = new clsWebClubMemberProfileModel(new clsWebClubMemberProfileModel_ID(customerCode, clientCode));
			mpModel.setIntGId(lastNo);
			mpModel.setStrCustomerID("02");
			mpModel.setStrUserCreated(userCode);
			mpModel.setStrUserModified(userCode);
			mpModel.setStrPropertyCode(propCode);
			mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			mpModel.setStrPrimaryCode(objMemberProfile.getStrCustomerCode());

		} else {

			clsWebClubMemberProfileModel objMemProfile = objMemberProfileService.funGetCustomer(memProfileBean.getStrSpouseCustomerCode(), clientCode);
			if (null == objMemProfile) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblmembermaster", "MemberProfile", "intGId", clientCode);
				String customerCode = "C" + String.format("%06d", lastNo);
				mpModel = new clsWebClubMemberProfileModel(new clsWebClubMemberProfileModel_ID(customerCode, clientCode));
				mpModel.setIntGId(lastNo);
				mpModel.setStrCustomerID("02");
				mpModel.setStrUserCreated(userCode);
				mpModel.setStrUserModified(userCode);
				mpModel.setStrPropertyCode(propCode);
				mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setStrPrimaryCode(objMemberProfile.getStrCustomerCode());
			} else {
				mpModel = new clsWebClubMemberProfileModel(new clsWebClubMemberProfileModel_ID(objMemProfile.getStrCustomerCode(), clientCode));
				mpModel.setStrUserModified(userCode);
				mpModel.setStrPropertyCode(propCode);
				mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				mpModel.setStrCustomerID("02");
				mpModel.setStrPrimaryCode(objMemberProfile.getStrCustomerCode());
			}
		}

		mpModel.setStrMemberCode(memProfileBean.getStrSpouseCode());

		mpModel.setStrUserCreated(userCode);
		mpModel.setStrUserModified(userCode);
		mpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		mpModel.setDteModifiedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		mpModel.setStrPropertyCode(propCode);
		mpModel.setDteMemberBlockDate("1990-01-01 00:00:00");
		mpModel.setStrClientCode(clientCode);
		mpModel.setStrDepedentRelation("");
		mpModel.setStrPrefixCode("");
		mpModel.setStrFirstName(memProfileBean.getStrSpouseFirstName());
		mpModel.setStrMiddleName(memProfileBean.getStrSpouseMiddleName());
		mpModel.setStrLastName(memProfileBean.getStrSpouseLastName());
		mpModel.setStrFullName(memProfileBean.getStrSpouseFirstName() + " " + memProfileBean.getStrSpouseMiddleName() + " " + memProfileBean.getStrSpouseLastName());
		mpModel.setStrNameOnCard(memProfileBean.getStrSpouseFirstName() + " " + memProfileBean.getStrSpouseMiddleName() + " " + memProfileBean.getStrSpouseLastName());
		// Residence Address
		mpModel.setStrResidentAddressLine1(memProfileBean.getStrResidentAddressLine1());
		mpModel.setStrResidentAddressLine2(memProfileBean.getStrResidentAddressLine2());
		mpModel.setStrResidentAddressLine3(memProfileBean.getStrResidentAddressLine3());
		mpModel.setStrResidentAreaCode(memProfileBean.getStrResidentAreaCode());
		mpModel.setStrResidentCountryCode(memProfileBean.getStrResidentCountryCode());
		mpModel.setStrResidentCtCode(memProfileBean.getStrResidentCtCode());
		mpModel.setStrResidentEmailID(memProfileBean.getStrSpouseResidentMobileNo());
		mpModel.setStrResidentFax1(memProfileBean.getStrResidentFax1());
		mpModel.setStrResidentFax2(memProfileBean.getStrResidentFax2());
		mpModel.setStrResidentLandMark(memProfileBean.getStrResidentLandMark());
		mpModel.setStrResidentMobileNo(memProfileBean.getStrSpouseResidentMobileNo());
		mpModel.setStrResidentPinCode(memProfileBean.getStrResidentPinCode());
		mpModel.setStrResidentRegionCode(memProfileBean.getStrResidentRegionCode());
		mpModel.setStrResidentStateCode(memProfileBean.getStrResidentStateCode());
		mpModel.setStrResidentTelephone1(memProfileBean.getStrResidentTelephone1());
		mpModel.setStrResidentTelephone2(memProfileBean.getStrResidentTelephone2());
		// Company Address
		mpModel.setStrCompanyAddressLine1("");
		mpModel.setStrCompanyAddressLine2("");
		mpModel.setStrCompanyAddressLine3("");
		mpModel.setStrCompanyAreaCode("");
		mpModel.setStrCompanyCode(memProfileBean.getStrSpouseCompanyCode());
		mpModel.setStrCompanyCountryCode("");
		mpModel.setStrCompanyCtCode("");
		mpModel.setStrCompanyEmailID("");
		mpModel.setStrCompanyFax1("");
		mpModel.setStrCompanyFax2("");
		mpModel.setStrCompanyLandMark("");
		mpModel.setStrCompanyMobileNo("");
		mpModel.setStrCompanyName("");
		mpModel.setStrCompanyPinCode("");
		mpModel.setStrCompanyRegionCode("");
		mpModel.setStrCompanyStateCode("");
		mpModel.setStrCompanyTelePhone1("");
		mpModel.setStrCompanyTelePhone2("");
		mpModel.setStrHoldingCode("");
		mpModel.setStrJobProfileCode(memProfileBean.getStrSpouseJobProfileCode());
		// Bill Address
		mpModel.setStrBillingAddressLine1("");
		mpModel.setStrBillingAddressLine2("");
		mpModel.setStrBillingAddressLine3("");
		mpModel.setStrBillingAreaCode("");
		mpModel.setStrBillingCountryCode("");
		mpModel.setStrBillingCtCode("");
		mpModel.setStrBillingEmailID("");
		mpModel.setStrBillingFax1("");
		mpModel.setStrBillingFax2("");
		mpModel.setStrBillingFlag("");
		mpModel.setStrBillingLandMark("");
		mpModel.setStrBillingMobileNo("");
		mpModel.setStrBillingPinCode("");
		mpModel.setStrBillingRegionCode("");
		mpModel.setStrBillingStateCode("");
		mpModel.setStrBillingTelePhone1("");
		mpModel.setStrBillingTelePhone2("");
		// Personal Information
		mpModel.setStrGender("F");
		mpModel.setDteDateofBirth(memProfileBean.getDteSpouseDateofBirth());
		mpModel.setStrMaritalStatus("married");
		mpModel.setStrProfessionCode(memProfileBean.getStrSpouseProfessionCode());
		mpModel.setDteAnniversary(memProfileBean.getDteAnniversary());
		// mpModel.setStrpName

		// Member Information
		mpModel.setStrCategoryCode("");
		mpModel.setStrProposerCode("");
		mpModel.setStrSeconderCode("");
		mpModel.setStrFatherMemberCode("");
		mpModel.setDteMembershipStartDate(memProfileBean.getDteMembershipStartDate());
		// mpModel.setDteMembershipEndDate(memProfileBean.getDteMembershipExpiryDate());
		mpModel.setDteMembershipEndDate(memProfileBean.getDteMembershipEndDate());
		mpModel.setStrInstation("");

		// Card Authontication Check Box
		mpModel.setStrQualification("");
		mpModel.setStrDesignationCode("");
		mpModel.setDblEntranceFee(new Double(0));
		mpModel.setDblSubscriptionFee(new Double(0));
		mpModel.setStrPanNumber("");

		mpModel.setStrStopCredit(memProfileBean.getStrSpouseStopCredit());
		mpModel.setStrLocker("No");
		mpModel.setStrLibrary("No");
		mpModel.setStrSeniorCitizen("No");

		mpModel.setStrInstation("No");
		mpModel.setStrGolfMemberShip("No");
		mpModel.setStrBlocked(memProfileBean.getStrSpouseBlocked());
		mpModel.setStrBlockedreasonCode("");

		mpModel.setDtePermitExpDate("1990-01-01 00:00:00");
		mpModel.setStrLiquorPermitNo("");
		mpModel.setIntFormNo(0);
		mpModel.setStrGuestEntry("Y");
		mpModel.setStrVirtualAccountCode("");
		mpModel.setChkmail(0);
		mpModel.setStrSSuffixCode("0");
		mpModel.setStrNSuffixCode("0");

		mpModel.setChrCircularemail("0");
		mpModel.setStrAuthorisedMember("");
		mpModel.setStrMemberStatusCode("");
		mpModel.setStrLikes("");
		mpModel.setStrDisLikes("");
		mpModel.setStrSendInvThrough("");
		mpModel.setStrSendCircularNoticeThrough("");
		mpModel.setDteInterviewDate("1990-01-01 00:00:00");
		mpModel.setDblCMSBalance(0.00);
		mpModel.setStrPhoto("");
		mpModel.setStrRemark("");
		mpModel.setStrDependentYesNo("N");
		mpModel.setStrSalesStaffCode("");
		mpModel.setDteProfileCreationDate("1990-01-01 00:00:00");
		mpModel.setStrResNonRes("Y");
		mpModel.setDteDependentDateofBirth("1990-01-01 00:00:00");
		mpModel.setDteMemberBlockDate("1990-01-01 00:00:00");
		mpModel.setDteMembershipExpiryDate(memProfileBean.getDteMembershipEndDate());
		mpModel.setStrDebtorCode("");
		mpModel.setStrDependentFullName("");
		mpModel.setStrDependentMemberCode("");
		mpModel.setStrDependentReasonCode("");
		mpModel.setStrAlternateMemberCode("");
		mpModel.setStrAttachment("");
		mpModel.setStrCustomerID("");
		mpModel.setStrBillingFlag("N");
		mpModel.setStrMemberYesNo("");
		return mpModel;
	}

	@RequestMapping(value = "/loadWebClubMemberProfileData", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields(@RequestParam("primaryCode") String primaryCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsWebClubMemberProfileModel> objMemberModelList = objMemberProfileService.funGetAllMember(primaryCode, clientCode);

		if (null == objMemberModelList) {
			List<clsWebClubMemberProfileModel> objMemModelList = new ArrayList<clsWebClubMemberProfileModel>();
			objMemModelList.get(0).setStrMemberCode("Invalid Code");
		}
		return objMemberModelList;
	}

	@RequestMapping(value = "/loadWebClubMemberProfileCustomerData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubMemberProfileModel funAssignFieldsUsingCustomer(@RequestParam("customerCode") String customerCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubMemberProfileModel objMemberModel = objMemberProfileService.funGetCustomer(customerCode, clientCode);
		if (null == objMemberModel) {
			objMemberModel = new clsWebClubMemberProfileModel();
			objMemberModel.setStrMemberCode("Invalid Code");
		}
		return objMemberModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadDependentMasterData", method = RequestMethod.GET)
	public @ResponseBody List funLoadDependentMasterData(@RequestParam("docCode") String docCode, HttpServletRequest req) {
		List<clsWebClubDependentMasterModel> listDMData = new LinkedList<clsWebClubDependentMasterModel>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		listDMData = objDependentMasterService.funGetWebClubDependentMasterList(docCode, clientCode);

		return listDMData;
	}

	// //Open MemberProfile
	// @RequestMapping(value = "/saveMemberPreProfile", method =
	// RequestMethod.GET)
	// public ModelAndView funOpenFormMenberProfile(Map<String,Object> model
	// ,HttpServletRequest request){
	// String urlHits="1";
	// try{
	// urlHits=request.getParameter("saddr").toString();
	// }catch(NullPointerException e){
	// urlHits="1";
	// }
	// model.put("urlHits",urlHits);
	//
	// if("2".equalsIgnoreCase(urlHits)){
	// return new ModelAndView("frmMemberProfile_1","command", new
	// clsWebClubMemberProfileBean());
	// }else if("1".equalsIgnoreCase(urlHits)){
	// return new ModelAndView("frmMemberProfile","command", new
	// clsWebClubMemberProfileBean());
	// }else {
	// return null;
	// }
	//
	// }
	//

}
