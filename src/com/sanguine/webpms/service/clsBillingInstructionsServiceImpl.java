package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBillingInstructionsBean;
import com.sanguine.webpms.dao.clsBillingInstructionsDao;
import com.sanguine.webpms.model.clsBillingInstructionsHdModel;
import com.sanguine.webpms.model.clsBookingTypeHdModel;

@Service("clsBillingInstructionsService")
public class clsBillingInstructionsServiceImpl implements clsBillingInstructionsService {
	@Autowired
	private clsBillingInstructionsDao objBillingInstructionsDao;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	public void funAddUpdateBillingInstructions(clsBillingInstructionsHdModel objMaster) {
		objBillingInstructionsDao.funAddUpdateBillingInstructions(objMaster);
	}

	@Override
	public clsBillingInstructionsHdModel funGetBillingInstructions(String docCode, String clientCode) {
		return objBillingInstructionsDao.funGetBillingInstructions(docCode, clientCode);
	}

	// Convert bean to model function
	@Override
	public clsBillingInstructionsHdModel funPrepareModel(clsBillingInstructionsBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsBillingInstructionsHdModel objBookingTypeHdModel = new clsBillingInstructionsHdModel();

		if (objBean.getStrBillingInstCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblbillinginstructions", "BillingInstructions", "strBillingInstCode", clientCode);
			String billingInstCode = "BI" + String.format("%06d", lastNo);
			objBookingTypeHdModel.setStrBillingInstCode(billingInstCode);
			objBookingTypeHdModel.setStrUserCreated(userCode);
			objBookingTypeHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objBookingTypeHdModel.setStrBillingInstCode(objBean.getStrBillingInstCode());
			objBookingTypeHdModel.setStrUserCreated(userCode);
			objBookingTypeHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}
		objBookingTypeHdModel.setStrBillingInstDesc(objBean.getStrBillingInstDesc());
		objBookingTypeHdModel.setStrUserEdited(userCode);
		objBookingTypeHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBookingTypeHdModel.setStrClientCode(clientCode);

		return objBookingTypeHdModel;

	}

}
