package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBookerMasterBean;
import com.sanguine.webpms.dao.clsBookerMasterDao;
import com.sanguine.webpms.model.clsAgentCommisionHdModel;
import com.sanguine.webpms.model.clsBookerMasterHdModel;

@Service("clsBookerMasterService")
public class clsBookerMasterServiceImpl implements clsBookerMasterService {
	@Autowired
	private clsBookerMasterDao objBookerMasterDao;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	public void funAddUpdateBookerMaster(clsBookerMasterHdModel objMaster) {
		objBookerMasterDao.funAddUpdateBookerMaster(objMaster);
	}

	@Override
	public clsBookerMasterHdModel funGetBookerMaster(String docCode, String clientCode) {
		return objBookerMasterDao.funGetBookerMaster(docCode, clientCode);
	}

	// Convert bean to model function
	public clsBookerMasterHdModel funPrepareModel(clsBookerMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsBookerMasterHdModel objBookerMasterHdModel = new clsBookerMasterHdModel();

		if (objBean.getStrBookerCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblbookermaster", "BookerMaster", "strBookerCode", clientCode);
			String agentCode = "BK" + String.format("%06d", lastNo);
			objBookerMasterHdModel.setStrBookerCode(agentCode);
			objBookerMasterHdModel.setStrUserCreated(userCode);
			objBookerMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objBookerMasterHdModel.setStrBookerCode(objBean.getStrBookerCode());
			objBookerMasterHdModel.setStrUserCreated(userCode);
			objBookerMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}
		objBookerMasterHdModel.setStrBookerName(objBean.getStrBookerName());
		objBookerMasterHdModel.setStrAddress(objBean.getStrAddress());
		objBookerMasterHdModel.setStrCity(objBean.getStrCity());
		objBookerMasterHdModel.setStrState(objBean.getStrState());
		objBookerMasterHdModel.setStrCountry(objBean.getStrCountry());
		objBookerMasterHdModel.setStrEmailId(objBean.getStrEmailId());
		objBookerMasterHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBookerMasterHdModel.setLngMobileNo(objBean.getLngMobileNo());
		objBookerMasterHdModel.setLngTelephoneNo(objBean.getLngTelephoneNo());
		objBookerMasterHdModel.setStrBlackList(objGlobal.funIfNull(objBean.getStrBlackList(), "Y", "N"));
		objBookerMasterHdModel.setStrUserEdited(userCode);
		objBookerMasterHdModel.setStrClientCode(clientCode);

		return objBookerMasterHdModel;

	}

}
