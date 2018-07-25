package com.sanguine.dao;

import com.sanguine.model.clsWSCityMasterModel;

public interface clsWSCityMasterDao {

	public void funAddUpdateWSCityMaster(clsWSCityMasterModel objMaster);

	public clsWSCityMasterModel funGetWSCityMaster(String docCode, String clientCode);

}
