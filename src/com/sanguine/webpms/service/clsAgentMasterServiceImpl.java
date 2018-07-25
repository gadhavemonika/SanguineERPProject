package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsAgentMasterBean;
import com.sanguine.webpms.dao.clsAgentMasterDao;
import com.sanguine.webpms.model.clsAgentMasterHdModel;

@Service("clsAgentMasterService")
public class clsAgentMasterServiceImpl implements clsAgentMasterService {
	@Autowired
	private clsAgentMasterDao objAgentMasterDao;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	public void funAddUpdateAgentMaster(clsAgentMasterHdModel objMaster) {
		objAgentMasterDao.funAddUpdateAgentMaster(objMaster);
	}

	@Override
	public clsAgentMasterHdModel funGetAgentMaster(String docCode, String clientCode) {
		return objAgentMasterDao.funGetAgentMaster(docCode, clientCode);
	}

	// Convert bean to model function
	@Override
	public clsAgentMasterHdModel funPrepareModel(clsAgentMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsAgentMasterHdModel objAgentMasterHdModel = new clsAgentMasterHdModel();

		if (objBean.getStrAgentCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblagentmaster", "AgentMaster", "strAgentCode", clientCode);
			String agentCode = "AG" + String.format("%06d", lastNo);
			objAgentMasterHdModel.setStrAgentCode(agentCode);
			objAgentMasterHdModel.setStrUserCreated(userCode);
			objAgentMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objAgentMasterHdModel.setStrAgentCode(objBean.getStrAgentCode());
			objAgentMasterHdModel.setStrUserCreated(userCode);
			objAgentMasterHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}
		objAgentMasterHdModel.setDteFromDate(objBean.getDteFromDate());
		objAgentMasterHdModel.setDteToDate(objBean.getDteToDate());
		objAgentMasterHdModel.setStrDescription(objBean.getStrDescription());
		objAgentMasterHdModel.setStrAgentCommCode(objBean.getStrAgentCommCode());
		objAgentMasterHdModel.setStrCorporateCode(objBean.getStrCorporateCode());
		objAgentMasterHdModel.setDblAdvToReceive(objBean.getDblAdvToReceive());
		objAgentMasterHdModel.setStrAddress(objBean.getStrAddress());
		objAgentMasterHdModel.setStrCity(objBean.getStrCity());
		objAgentMasterHdModel.setStrState(objBean.getStrState());
		objAgentMasterHdModel.setStrCountry(objBean.getStrCountry());
		objAgentMasterHdModel.setStrEmailId(objBean.getStrEmailId());
		objAgentMasterHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objAgentMasterHdModel.setLngFaxNo(objBean.getLngFaxNo());
		objAgentMasterHdModel.setLngMobileNo(objBean.getLngMobileNo());
		objAgentMasterHdModel.setLngTelphoneNo(objBean.getLngTelphoneNo());
		objAgentMasterHdModel.setStrUserEdited(userCode);
		objAgentMasterHdModel.setStrClientCode(clientCode);

		return objAgentMasterHdModel;

	}

}
