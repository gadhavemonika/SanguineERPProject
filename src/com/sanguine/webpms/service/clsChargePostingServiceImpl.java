package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsChargePostingBean;
import com.sanguine.webpms.dao.clsChargePostingDao;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;
import com.sanguine.webpms.model.clsChargePostingHdModel;
import com.sanguine.webpms.model.clsDepartmentMasterModel;

@Service("clsChargePostingService")
public class clsChargePostingServiceImpl implements clsChargePostingService {
	@Autowired
	private clsChargePostingDao objChargePostingDao;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	clsChargePostingHdModel objChargePostingMasterModel;

	@Override
	public clsChargePostingHdModel funPrepareChargePostingModel(clsChargePostingBean objBean, String userCode, String clientCode) {

		objChargePostingMasterModel = new clsChargePostingHdModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objBean.getStrService().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblchargeposting", "Charge Posting Master", "strService", clientCode);
			// lastNo=1;
			String serviceCode = "SR" + String.format("%06d", lastNo);
			objChargePostingMasterModel.setStrService(serviceCode);
			objChargePostingMasterModel.setStrUserCreated(userCode);
			objChargePostingMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objChargePostingMasterModel.setStrService(objBean.getStrService());

		}
		objChargePostingMasterModel.setStrDeptCode(objBean.getStrDeptCode());
		objChargePostingMasterModel.setStrIncomeHeadCode(objBean.getStrIncomeHeadCode());
		objChargePostingMasterModel.setStrUserEdited(userCode);
		objChargePostingMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objChargePostingMasterModel.setStrClientCode(clientCode);

		return objChargePostingMasterModel;
	}

}
