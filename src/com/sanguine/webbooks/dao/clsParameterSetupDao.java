package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsParameterSetupModel;

public interface clsParameterSetupDao {

	public void funAddUpdateParameterSetup(clsParameterSetupModel objMaster);

	public clsParameterSetupModel funGetParameterSetup(String docCode, String clientCode);

}
