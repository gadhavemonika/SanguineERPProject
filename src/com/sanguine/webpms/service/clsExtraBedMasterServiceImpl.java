package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsExtraBedMasterBean;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;
import com.sanguine.webpms.model.clsExtraBedMasterModel;

@Service("objExtraBedMasterService")
public class clsExtraBedMasterServiceImpl implements clsExtraBedMasterService {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsExtraBedMasterModel objExtraBedMasterModel;

	@Override
	public clsExtraBedMasterModel funPrepareExtraBedModel(clsExtraBedMasterBean objExtraBedMasterBean, String clientCode, String userCode) {

		objExtraBedMasterModel = new clsExtraBedMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objExtraBedMasterBean.getStrExtraBedTypeCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblextrabed", "ExtraBedMaster", "strExtraBedTypeCode", clientCode);
			// lastNo=1;
			String extraBedCode = "EB" + String.format("%06d", lastNo);
			objExtraBedMasterModel.setStrExtraBedTypeCode(extraBedCode);
			objExtraBedMasterModel.setStrUserCreated(userCode);
			objExtraBedMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objExtraBedMasterModel.setStrExtraBedTypeCode(objExtraBedMasterBean.getStrExtraBedTypeCode());

		}
		objExtraBedMasterModel.setStrExtraBedTypeDesc(objExtraBedMasterBean.getStrExtraBedTypeDesc());
		objExtraBedMasterModel.setIntNoBeds(objExtraBedMasterBean.getIntNoBeds());
		objExtraBedMasterModel.setDblChargePerBed(objExtraBedMasterBean.getDblChargePerBed());
		objExtraBedMasterModel.setStrUserEdited(userCode);
		objExtraBedMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objExtraBedMasterModel.setStrClientCode(clientCode);

		objExtraBedMasterModel.setStrAccountCode(objExtraBedMasterBean.getStrAccountCode());

		return objExtraBedMasterModel;

	}

}
