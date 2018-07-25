package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsAgentMasterHdModel;

public interface clsAgentMasterDao {

	public void funAddUpdateAgentMaster(clsAgentMasterHdModel objMaster);

	public clsAgentMasterHdModel funGetAgentMaster(String docCode, String clientCode);

}
