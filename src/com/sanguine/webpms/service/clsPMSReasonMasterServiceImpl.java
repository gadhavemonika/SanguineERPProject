package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPMSReasonMasterBean;
import com.sanguine.webpms.bean.clsRoomTypeMasterBean;
import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

@Service("objPMSReasonMasterService")
public class clsPMSReasonMasterServiceImpl implements clsPMSReasonMasterService {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsPMSReasonMasterModel objReasonMasterModel;

	@Override
	public clsPMSReasonMasterModel funPrepareReasonModel(clsPMSReasonMasterBean objReasonMasterBean, String clientCode, String userCode) {
		objReasonMasterModel = new clsPMSReasonMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objReasonMasterBean.getStrReasonCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblreasonmaster", "ReasonMaster", "strReasonCode", clientCode);
			String reasonCode = "RS" + String.format("%06d", lastNo);
			// String deptCode="D0000001";
			objReasonMasterModel.setStrReasonCode(reasonCode);
			objReasonMasterModel.setStrUserCreated(userCode);
			objReasonMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objReasonMasterModel.setStrReasonCode(objReasonMasterBean.getStrReasonCode());

		}

		objReasonMasterModel.setStrReasonDesc(objReasonMasterBean.getStrReasonDesc());
		objReasonMasterModel.setStrReasonType(objReasonMasterBean.getStrReasonType());

		objReasonMasterModel.setStrUserEdited(userCode);
		objReasonMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objReasonMasterModel.setStrClientCode(clientCode);

		return objReasonMasterModel;

	}

}
