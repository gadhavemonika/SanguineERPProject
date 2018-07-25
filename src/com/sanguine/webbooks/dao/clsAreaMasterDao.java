package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsAreaMasterModel;

public interface clsAreaMasterDao {

	public void funAddUpdateAreaMaster(clsAreaMasterModel objMaster);

	public clsAreaMasterModel funGetAreaMaster(String docCode, String clientCode);

}
