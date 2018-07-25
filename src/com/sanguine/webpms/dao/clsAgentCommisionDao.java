package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsAgentCommisionHdModel;

public interface clsAgentCommisionDao {

	public void funAddUpdateAgentCommision(clsAgentCommisionHdModel objMaster);

	public clsAgentCommisionHdModel funGetAgentCommision(String docCode, String clientCode);

}
