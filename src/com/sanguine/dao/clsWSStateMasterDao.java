package com.sanguine.dao;

import com.sanguine.model.clsWSStateMasterModel;

public interface clsWSStateMasterDao {

	public void funAddUpdateWSStateMaster(clsWSStateMasterModel objMaster);

	public clsWSStateMasterModel funGetWSStateMaster(String docCode, String clientCode);

}
