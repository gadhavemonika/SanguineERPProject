package com.sanguine.service;

import com.sanguine.model.clsWSCityMasterModel;

public interface clsWSCityMasterService {

	public void funAddUpdateWSCityMaster(clsWSCityMasterModel objMaster);

	public clsWSCityMasterModel funGetWSCityMaster(String docCode, String clientCode);

}
