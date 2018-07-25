package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;

@Service("objPlanMasterService")
public class clsPlanMasterServiceImpl implements clsPlanMasterService {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsPlanMasterModel objPlanMasterModel;

	@Override
	public clsPlanMasterModel funPreparePlanModel(clsPlanMasterBean objPlanMasterBean, String clientCode, String userCode) {
		objPlanMasterModel = new clsPlanMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objPlanMasterBean.getStrPlanCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblplanmaster", "PlanMaster", "strPlanCode", clientCode);
			String planCode = "PL" + String.format("%06d", lastNo);

			objPlanMasterModel.setStrPlanCode(planCode);
			objPlanMasterModel.setStrUserCreated(userCode);
			objPlanMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objPlanMasterModel.setStrPlanCode(objPlanMasterBean.getStrPlanCode());

		}
		objPlanMasterModel.setStrPlanDesc(objPlanMasterBean.getStrPlanDesc());

		objPlanMasterModel.setStrUserEdited(userCode);
		objPlanMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objPlanMasterModel.setStrClientCode(clientCode);

		return objPlanMasterModel;
	}

}
