package com.sanguine.webpms.service;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsDepartmentMasterModel;

@Service("objBaggageMasterService")
public class clsBaggageMasterServiceImpl implements clsBaggageMasterService {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsBaggageMasterModel objBaggageMasterModel;

	@Override
	public clsBaggageMasterModel funPrepareBaggageModel(clsBaggageMasterBean objBaggageMasterBean, String clientCode, String userCode)

	{
		objBaggageMasterModel = new clsBaggageMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objBaggageMasterBean.getStrBaggageCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblbaggagemaster", "BaggageMaster", "strBaggageCode", clientCode);
			// lastNo=1;
			String baggageCode = "BG" + String.format("%06d", lastNo);
			objBaggageMasterModel.setStrBaggageCode(baggageCode);
			objBaggageMasterModel.setStrUserCreated(userCode);
			objBaggageMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objBaggageMasterModel.setStrBaggageCode(objBaggageMasterBean.getStrBaggageCode());

		}
		objBaggageMasterModel.setStrBaggageDesc(objBaggageMasterBean.getStrBaggageDesc());
		objBaggageMasterModel.setStrUserEdited(userCode);
		objBaggageMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBaggageMasterModel.setStrClientCode(clientCode);

		return objBaggageMasterModel;
	}

}
