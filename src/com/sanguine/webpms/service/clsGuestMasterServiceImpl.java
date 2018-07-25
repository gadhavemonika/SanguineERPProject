package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsGuestMasterBean;
import com.sanguine.webpms.dao.clsGuestMasterDao;
import com.sanguine.webpms.model.clsGuestMasterHdModel;

@Service("objGuestMasterService")
public class clsGuestMasterServiceImpl implements clsGuestMasterService {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGuestMasterDao objGuestMasterDao;

	@Override
	public clsGuestMasterHdModel funPrepareGuestModel(clsGuestMasterBean objGuestMasterBean, String clientCode, String userCode) {
		clsGuestMasterHdModel objGuestMasterModel = new clsGuestMasterHdModel();
		long lastNo = 0;
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		// String formName="";
		String sql = "select strGuestCode from tblguestmaster where lngMobileNo='" + objGuestMasterBean.getIntMobileNo() + "' ";
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		if (objGuestMasterBean.getStrGuestCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblguestmaster", "GuestMaster", "strGuestCode", clientCode);
			String guestCode = "GT" + String.format("%06d", lastNo);
			objGuestMasterModel.setStrGuestCode(guestCode);
			objGuestMasterModel.setStrUserCreated(userCode);
			objGuestMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objGuestMasterModel.setStrGuestCode(objGuestMasterBean.getStrGuestCode());
			objGuestMasterModel.setStrUserCreated(userCode);
			objGuestMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		objGuestMasterModel.setStrGuestPrefix(objGuestMasterBean.getStrGuestPrefix());
		objGuestMasterModel.setStrFirstName(objGuestMasterBean.getStrFirstName());
		objGuestMasterModel.setStrMiddleName(objGuestMasterBean.getStrMiddleName());
		objGuestMasterModel.setStrLastName(objGuestMasterBean.getStrLastName());
		objGuestMasterModel.setStrGender(objGlobal.funIfNull(objGuestMasterBean.getStrGender(), "M", objGuestMasterBean.getStrGender()));
		objGuestMasterModel.setStrDesignation(objGlobal.funIfNull(objGuestMasterBean.getStrDesignation(), "NA", objGuestMasterBean.getStrDesignation()));
		if (null == objGuestMasterBean.getDteDOB()) {
			objGuestMasterModel.setDteDOB("1900-01-01");
		} else {
			objGuestMasterModel.setDteDOB(objGlobal.funGetDate("yyyy-MM-dd", objGuestMasterBean.getDteDOB()));
		}

		// objGuestMasterModel.setDteDOB(objGlobal.funIfNull(objGuestMasterBean.getDteDOB(),"1900-01-01",objGuestMasterBean.getDteDOB()));
		objGuestMasterModel.setStrAddress(objGlobal.funIfNull(objGuestMasterBean.getStrAddress(), "NA", objGuestMasterBean.getStrAddress()));
		objGuestMasterModel.setStrCity(objGlobal.funIfNull(objGuestMasterBean.getStrCity(), "NA", objGuestMasterBean.getStrCity()));
		objGuestMasterModel.setStrState(objGlobal.funIfNull(objGuestMasterBean.getStrState(), "NA", objGuestMasterBean.getStrState()));
		objGuestMasterModel.setStrCountry(objGlobal.funIfNull(objGuestMasterBean.getStrCountry(), "NA", objGuestMasterBean.getStrCountry()));
		objGuestMasterModel.setStrNationality(objGlobal.funIfNull(objGuestMasterBean.getStrNationality(), "NA", objGuestMasterBean.getStrNationality()));
		objGuestMasterModel.setIntPinCode(objGuestMasterBean.getIntPinCode());
		objGuestMasterModel.setLngMobileNo(objGuestMasterBean.getIntMobileNo());
		objGuestMasterModel.setLngFaxNo(objGuestMasterBean.getIntFaxNo());
		objGuestMasterModel.setStrEmailId(objGlobal.funIfNull(objGuestMasterBean.getStrEmailId(), "NA", objGuestMasterBean.getStrEmailId()));
		objGuestMasterModel.setStrPANNo(objGlobal.funIfNull(objGuestMasterBean.getStrPANNo(), "NA", objGuestMasterBean.getStrPANNo()));
		objGuestMasterModel.setStrArrivalFrom(objGlobal.funIfNull(objGuestMasterBean.getStrArrivalFrom(), "NA", objGuestMasterBean.getStrArrivalFrom()));
		objGuestMasterModel.setStrProceedingTo(objGlobal.funIfNull(objGuestMasterBean.getStrProceedingTo(), "NA", objGuestMasterBean.getStrProceedingTo()));
		objGuestMasterModel.setStrStatus(objGlobal.funIfNull(objGuestMasterBean.getStrStatus(), "NA", objGuestMasterBean.getStrStatus()));
		objGuestMasterModel.setStrVisitingType(objGlobal.funIfNull(objGuestMasterBean.getStrVisitingType(), "NA", objGuestMasterBean.getStrVisitingType()));
		objGuestMasterModel.setStrCorporate(objGlobal.funIfNull(objGuestMasterBean.getStrCorporate(), "N", objGuestMasterBean.getStrCorporate()));
		objGuestMasterModel.setStrPassportNo(objGlobal.funIfNull(objGuestMasterBean.getStrPassportNo(), "NA", objGuestMasterBean.getStrPassportNo()));
		objGuestMasterModel.setStrUserEdited(userCode);
		objGuestMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objGuestMasterModel.setStrProceedingTo(objGlobal.funIfNull(objGuestMasterBean.getStrProceedingTo(), "NA", objGuestMasterBean.getStrProceedingTo()));

		if (null == objGuestMasterBean.getDtePassportExpiryDate()) {
			objGuestMasterModel.setDtePassportExpiryDate("1900-01-01");
		} else {
			objGuestMasterModel.setDtePassportExpiryDate(objGlobal.funGetDate("yyyy-MM-dd", objGuestMasterBean.getDtePassportExpiryDate()));
		}

		if (null == objGuestMasterBean.getDtePassportIssueDate()) {
			objGuestMasterModel.setDtePassportIssueDate("1900-01-01");
		} else {
			objGuestMasterModel.setDtePassportIssueDate(objGlobal.funGetDate("yyyy-MM-dd", objGuestMasterBean.getDtePassportIssueDate()));
		}

		objGuestMasterModel.setStrGSTNo(objGuestMasterBean.getStrGSTNo());
		objGuestMasterModel.setStrUIDNo(objGuestMasterBean.getStrUIDNo());
		
		if (null == objGuestMasterBean.getDteAnniversaryDate()) {
			objGuestMasterModel.setDteAnniversaryDate("1900-01-01");
		} else {
			objGuestMasterModel.setDteAnniversaryDate(objGlobal.funGetDate("yyyy-MM-dd", objGuestMasterBean.getDteAnniversaryDate()));
		}
		
		objGuestMasterModel.setStrDefaultAddr(objGuestMasterBean.getStrDefaultAddr());
		
		objGuestMasterModel.setStrAddressLocal(objGuestMasterBean.getStrAddressLocal());
		objGuestMasterModel.setStrCityLocal(objGuestMasterBean.getStrCityLocal());
		objGuestMasterModel.setStrStateLocal(objGuestMasterBean.getStrStateLocal());
		objGuestMasterModel.setStrCountryLocal(objGuestMasterBean.getStrCountryLocal());
		objGuestMasterModel.setIntPinCodeLocal(objGuestMasterBean.getIntPinCodeLocal());
		
		objGuestMasterModel.setStrAddrPermanent(objGuestMasterBean.getStrAddrPermanent());
		objGuestMasterModel.setStrCityPermanent(objGuestMasterBean.getStrCityPermanent());
		objGuestMasterModel.setStrStatePermanent(objGuestMasterBean.getStrStatePermanent());
		objGuestMasterModel.setStrCountryPermanent(objGuestMasterBean.getStrCountryPermanent());
		objGuestMasterModel.setIntPinCodePermanent(objGuestMasterBean.getIntPinCodePermanent());
		
		objGuestMasterModel.setStrAddressOfc(objGuestMasterBean.getStrAddressOfc());
		objGuestMasterModel.setStrCityOfc(objGuestMasterBean.getStrCityOfc());
		objGuestMasterModel.setStrStateOfc(objGuestMasterBean.getStrStateOfc());
		objGuestMasterModel.setStrCountryOfc(objGuestMasterBean.getStrCountryOfc());
		objGuestMasterModel.setIntPinCodeOfc(objGuestMasterBean.getIntPinCodeOfc());

		// objGuestMasterModel.setDtePassportExpiryDate(objGlobal.funIfNull(objGuestMasterBean.getDtePassportExpiryDate(),"1900-01-01",objGuestMasterBean.getDtePassportExpiryDate()));
		// objGuestMasterModel.setDtePassportIssueDate(objGlobal.funIfNull(objGuestMasterBean.getDtePassportIssueDate(),"1900-01-01",objGuestMasterBean.getDtePassportIssueDate()));
		objGuestMasterModel.setStrClientCode(clientCode);

		return objGuestMasterModel;
	}

	public List funGetGuestMaster(String guestCode, String clientCode) {
		return objGuestMasterDao.funGetGuestMaster(guestCode, clientCode);
	}

}
