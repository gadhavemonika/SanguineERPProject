package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsCityMasterModel;

public interface clsCityMasterDao {

	public void funAddUpdateCityMaster(clsCityMasterModel objMaster);

	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode);

}
