package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsIncomeHeadMasterBean;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.model.clsIncomeHeadMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;

@Service("objIncomeHeadMasterService")
public class clsIncomeHeadMasterServiceImpl implements clsIncomeHeadMasterService {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsIncomeHeadMasterModel objIncomeHeadMasterModel;

	@Override
	public clsIncomeHeadMasterModel funPrepareIncomeHeadModel(clsIncomeHeadMasterBean objIncomeMasterBean, String clientCode, String userCode) {
		objIncomeHeadMasterModel = new clsIncomeHeadMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objIncomeMasterBean.getStrIncomeHeadCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblincomehead", "IncomeHeadMaster", "strIncomeHeadCode", clientCode);
			String incomeCode = "IH" + String.format("%06d", lastNo);

			objIncomeHeadMasterModel.setStrIncomeHeadCode(incomeCode);
			objIncomeHeadMasterModel.setStrUserCreated(userCode);
			objIncomeHeadMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objIncomeHeadMasterModel.setStrIncomeHeadCode(objIncomeMasterBean.getStrIncomeHeadCode());

		}
		objIncomeHeadMasterModel.setStrIncomeHeadDesc(objIncomeMasterBean.getStrIncomeHeadDesc());
		objIncomeHeadMasterModel.setStrDeptCode(objIncomeMasterBean.getStrDeptCode());
		objIncomeHeadMasterModel.setStrAccountCode(objIncomeMasterBean.getStrAccountCode());

		objIncomeHeadMasterModel.setStrUserEdited(userCode);
		objIncomeHeadMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objIncomeHeadMasterModel.setStrClientCode(clientCode);

		return objIncomeHeadMasterModel;
	}

}