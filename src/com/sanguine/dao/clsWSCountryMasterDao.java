package com.sanguine.dao;

import com.sanguine.model.clsWSCountryMasterModel;

public interface clsWSCountryMasterDao {

	public void funAddUpdateCountryMaster(clsWSCountryMasterModel objMaster);

	public clsWSCountryMasterModel funGetCountryMaster(String docCode, String clientCode);

}
