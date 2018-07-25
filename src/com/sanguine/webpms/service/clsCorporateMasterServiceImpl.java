package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsCorporateMasterBean;
import com.sanguine.webpms.dao.clsCorporateMasterDao;
import com.sanguine.webpms.model.clsCorporateMasterHdModel;

@Service("clsCorporateMasterService")
public class clsCorporateMasterServiceImpl implements clsCorporateMasterService {
	@Autowired
	private clsCorporateMasterDao objCorporateMasterDao;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	public void funAddUpdateCorporateMaster(clsCorporateMasterHdModel objMaster) {
		objCorporateMasterDao.funAddUpdateCorporateMaster(objMaster);
	}

	@Override
	public clsCorporateMasterHdModel funGetCorporateMaster(String docCode, String clientCode) {
		return objCorporateMasterDao.funGetCorporateMaster(docCode, clientCode);
	}

	// Convert bean to model function
	public clsCorporateMasterHdModel funPrepareModel(clsCorporateMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsCorporateMasterHdModel objCorporateMasterHdModel = new clsCorporateMasterHdModel();
		if (objBean.getStrCorporateCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblcorporatemaster", "CorporateCode", "strCorporateCode", clientCode);
			String corporateCode = "CO" + String.format("%06d", lastNo);
			objCorporateMasterHdModel.setStrCorporateCode(corporateCode);
			objCorporateMasterHdModel.setStrUserCreated(userCode);
			objCorporateMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objCorporateMasterHdModel.setStrCorporateCode(objBean.getStrCorporateCode());
			objCorporateMasterHdModel.setStrUserCreated(userCode);
			objCorporateMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}
		objCorporateMasterHdModel.setStrCorporateDesc(objBean.getStrCorporateDesc());
		objCorporateMasterHdModel.setStrPersonIncharge(objBean.getStrPersonIncharge());
		objCorporateMasterHdModel.setStrAddress(objBean.getStrAddress());
		objCorporateMasterHdModel.setStrCity(objBean.getStrCity());
		objCorporateMasterHdModel.setStrState(objBean.getStrState());
		objCorporateMasterHdModel.setStrCountry(objBean.getStrCountry());
		objCorporateMasterHdModel.setLngMobileNo(objBean.getLngMobileNo());
		if (null == objBean.getLngTelephoneNo()) {
			objCorporateMasterHdModel.setLngTelephoneNo(new Long(0));
		} else {
			objCorporateMasterHdModel.setLngTelephoneNo(objBean.getLngTelephoneNo());
		}

		if (null == objBean.getLngFax()) {
			objCorporateMasterHdModel.setLngFax(new Long(0));
		} else {
			objCorporateMasterHdModel.setLngFax(objBean.getLngFax());
		}

		objCorporateMasterHdModel.setStrArea(objBean.getStrArea());
		objCorporateMasterHdModel.setIntPinCode(objBean.getIntPinCode());
		objCorporateMasterHdModel.setStrSegmentCode(objBean.getStrSegmentCode());
		objCorporateMasterHdModel.setStrPlanCode(objBean.getStrPlanCode());
		objCorporateMasterHdModel.setStrRemarks(objBean.getStrRemarks());
		objCorporateMasterHdModel.setStrAgentType(objBean.getStrAgentType());
		objCorporateMasterHdModel.setStrCreditAllowed(objGlobal.funIfNull(objBean.getStrCreditAllowed(), "N", "Y"));
		objCorporateMasterHdModel.setDblCreditLimit(objBean.getDblCreditLimit());
		objCorporateMasterHdModel.setStrBlackList(objGlobal.funIfNull(objBean.getStrBlackList(), "N", "Y"));
		objCorporateMasterHdModel.setStrUserEdited(userCode);
		objCorporateMasterHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objCorporateMasterHdModel.setStrClientCode(clientCode);
		objCorporateMasterHdModel.setDblDiscountPer(objBean.getDblDiscountPer());
		objCorporateMasterHdModel.setStrCorporateDesc(objBean.getStrCorporateDesc());
		return objCorporateMasterHdModel;

	}

}
