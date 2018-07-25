package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsFloorMasterBean;
import com.sanguine.webpms.model.clsFloorMasterModel;

@Service("objFloorMasterService")
public class clsFloorMasterServiceImpl implements clsFloorMasterService{
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	 
	clsFloorMasterModel objFloorMasterModel;
	
	
	@Override
	public clsFloorMasterModel funPrepareFloorModel(clsFloorMasterBean objFloorBean, String clientCode, String userCode){
		objFloorMasterModel = new clsFloorMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		
		if (objFloorBean.getStrFloorCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblfloormaster", "FloorMaster", "strFloorCode", clientCode);
			
			String FloorCode = "FL" + String.format("%06d", lastNo);
			objFloorMasterModel.setStrFloorCode(FloorCode);
			objFloorMasterModel.setStrUserCreated(userCode);
			objFloorMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			
		} else {
			objFloorMasterModel.setStrFloorCode(objFloorBean.getStrFloorCode());

		}
		
		objFloorMasterModel.setStrFloorName(objFloorBean.getStrFloorName());
		objFloorMasterModel.setDblFloorAmt(objFloorBean.getDblFloorAmt());
		objFloorMasterModel.setStrUserEdited(userCode);
		objFloorMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objFloorMasterModel.setStrClientCode(clientCode);

		return objFloorMasterModel;
		
		
	}

}
