package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBusinessSourceMasterBean;
import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;

@Service("objBusinessSourceMasterService")
public class clsBusinessSourceMasterServiceImpl implements clsBusinessSourceMasterService {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsBusinessSourceMasterModel objBusinessMasterModel;

	@Override
	public clsBusinessSourceMasterModel funPrepareBusinessModel(clsBusinessSourceMasterBean objBusinessMasterBean, String clientCode, String userCode) {
		objBusinessMasterModel = new clsBusinessSourceMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objBusinessMasterBean.getStrBusinessSourceCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblbusinesssource", "BusinessMaster", "strBusinessSourceCode", clientCode);
			// lastNo=1;
			String businessSourceCode = "BS" + String.format("%06d", lastNo);
			objBusinessMasterModel.setStrBusinessSourceCode(businessSourceCode);
			objBusinessMasterModel.setStrUserCreated(userCode);
			objBusinessMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objBusinessMasterModel.setStrBusinessSourceCode(objBusinessMasterBean.getStrBusinessSourceCode());

		}
		objBusinessMasterModel.setStrDescription(objBusinessMasterBean.getStrDescription());
		objBusinessMasterModel.setStrInstAccepted(objBusinessMasterBean.getStrInstAccepted());
		objBusinessMasterModel.setStrInvolvesAmt(objBusinessMasterBean.getStrInvolvesAmt());
		objBusinessMasterModel.setStrReqSlipReqd(objBusinessMasterBean.getStrReqSlipReqd());
		objBusinessMasterModel.setStrUserEdited(userCode);
		objBusinessMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBusinessMasterModel.setStrClientCode(clientCode);

		return objBusinessMasterModel;

	}

}
