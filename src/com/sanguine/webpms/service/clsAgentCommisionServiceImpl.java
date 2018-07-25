package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsAgentCommisionBean;
import com.sanguine.webpms.dao.clsAgentCommisionDao;
import com.sanguine.webpms.model.clsAgentCommisionHdModel;

@Service("clsAgentCommisionService")
public class clsAgentCommisionServiceImpl implements clsAgentCommisionService {
	@Autowired
	private clsAgentCommisionDao objAgentCommisionDao;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	public void funAddUpdateAgentCommision(clsAgentCommisionHdModel objMaster) {
		objAgentCommisionDao.funAddUpdateAgentCommision(objMaster);
	}

	@Override
	public clsAgentCommisionHdModel funGetAgentCommision(String docCode, String clientCode) {
		return objAgentCommisionDao.funGetAgentCommision(docCode, clientCode);
	}

	// Convert bean to model function
	public clsAgentCommisionHdModel funPrepareModel(clsAgentCommisionBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsAgentCommisionHdModel objAgentCommisionHdModel = new clsAgentCommisionHdModel();

		if (objBean.getStrAgentCommCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblagentcommision", "AgentCommisionMaster", "strAgentCommCode", clientCode);
			String agentCode = "AC" + String.format("%06d", lastNo);
			objAgentCommisionHdModel.setStrAgentCommCode(agentCode);
			objAgentCommisionHdModel.setStrUserCreated(userCode);
			objAgentCommisionHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objAgentCommisionHdModel.setStrAgentCommCode(objBean.getStrAgentCommCode());
			objAgentCommisionHdModel.setStrUserCreated(userCode);
			objAgentCommisionHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		}
		objAgentCommisionHdModel.setDteFromDate(objBean.getDteFromDate());
		objAgentCommisionHdModel.setDteToDate(objBean.getDteToDate());
		objAgentCommisionHdModel.setStrCalculatedOn(objBean.getStrCalculatedOn());
		objAgentCommisionHdModel.setStrCommisionPaid(objBean.getStrCommisionPaid());
		objAgentCommisionHdModel.setStrCommisionOn(objBean.getStrCommisionOn());
		objAgentCommisionHdModel.setStrUserEdited(userCode);
		objAgentCommisionHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objAgentCommisionHdModel.setStrClientCode(clientCode);

		return objAgentCommisionHdModel;

	}

}
