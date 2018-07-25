package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsAgentCommisionBean;
import com.sanguine.webpms.model.clsAgentCommisionHdModel;

public interface clsAgentCommisionService {

	public void funAddUpdateAgentCommision(clsAgentCommisionHdModel objMaster);

	public clsAgentCommisionHdModel funGetAgentCommision(String docCode, String clientCode);

	public clsAgentCommisionHdModel funPrepareModel(clsAgentCommisionBean objBean, String userCode, String clientCode);
}
