package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsCountryMasterModel;

public interface clsCountryMasterDao {

	public void funAddUpdateCountryMaster(clsCountryMasterModel objMaster);

	public clsCountryMasterModel funGetCountryMaster(String docCode, String clientCode);

}
