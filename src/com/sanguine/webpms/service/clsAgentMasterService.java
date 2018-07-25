package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsAgentMasterBean;
import com.sanguine.webpms.model.clsAgentMasterHdModel;

public interface clsAgentMasterService {

	public void funAddUpdateAgentMaster(clsAgentMasterHdModel objMaster);

	public clsAgentMasterHdModel funGetAgentMaster(String docCode, String clientCode);

	public clsAgentMasterHdModel funPrepareModel(clsAgentMasterBean objBean, String userCode, String clientCode);

}
