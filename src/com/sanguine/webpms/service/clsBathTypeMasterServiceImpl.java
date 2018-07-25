package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.bean.clsBathTypeMasterBean;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;

@Service("objBathTypeMasterService")
public class clsBathTypeMasterServiceImpl implements clsBathTypeMasterService {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsBathTypeMasterModel objBathTypeMasterModel;

	@Override
	public clsBathTypeMasterModel funPrepareBathTypeModel(clsBathTypeMasterBean objBathTypeMasterBean, String clientCode, String userCode)

	{
		objBathTypeMasterModel = new clsBathTypeMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objBathTypeMasterBean.getStrBathTypeCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblbathtypemaster", "BathTypeMaster", "strBathTypeCode", clientCode);
			// lastNo=1;
			String bathTypeCode = "BT" + String.format("%06d", lastNo);
			objBathTypeMasterModel.setStrBathTypeCode(bathTypeCode);
			objBathTypeMasterModel.setStrUserCreated(userCode);
			objBathTypeMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objBathTypeMasterModel.setStrBathTypeCode(objBathTypeMasterBean.getStrBathTypeCode());

		}
		objBathTypeMasterModel.setStrBathTypeDesc(objBathTypeMasterBean.getStrBathTypeDesc());
		objBathTypeMasterModel.setStrUserEdited(userCode);
		objBathTypeMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBathTypeMasterModel.setStrClientCode(clientCode);

		return objBathTypeMasterModel;
	}

}
