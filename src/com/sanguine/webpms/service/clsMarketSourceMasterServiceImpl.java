package com.sanguine.webpms.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsMarketSourceMasterBean;
import com.sanguine.webpms.model.clsMarketSourceMasterModel;

@Service("objMarketSourceMasterService")
public class clsMarketSourceMasterServiceImpl implements clsMarketSourceMasterService{
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsMarketSourceMasterModel objMarketMasterModel;

	@Override
	public clsMarketSourceMasterModel funPrepareMarketModel(clsMarketSourceMasterBean objMarketMasterBean, String clientCode, String userCode) {
		objMarketMasterModel = new clsMarketSourceMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objMarketMasterBean.getStrMarketSourceCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblmarketsource", "MarketMaster", "strMarketSourceCode", clientCode);
			// lastNo=1;
			String MarketSourceCode = "MS" + String.format("%06d", lastNo);
			objMarketMasterModel.setStrMarketSourceCode(MarketSourceCode);
			objMarketMasterModel.setStrUserCreated(userCode);
			objMarketMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objMarketMasterModel.setStrMarketSourceCode(objMarketMasterBean.getStrMarketSourceCode());

		}
		objMarketMasterModel.setStrDescription(objMarketMasterBean.getStrDescription());
		objMarketMasterModel.setStrReqSlipReqd(objMarketMasterBean.getStrReqSlipReqd());
		objMarketMasterModel.setStrUserEdited(userCode);
		objMarketMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objMarketMasterModel.setStrClientCode(clientCode);

		return objMarketMasterModel;

	}
	
	

}
