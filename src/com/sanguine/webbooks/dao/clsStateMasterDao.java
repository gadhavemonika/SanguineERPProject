package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsStateMasterModel;

public interface clsStateMasterDao {

	public void funAddUpdateStateMaster(clsStateMasterModel objMaster);

	public clsStateMasterModel funGetStateMaster(String docCode, String clientCode);

}
