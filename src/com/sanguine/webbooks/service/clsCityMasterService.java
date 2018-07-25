package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsCityMasterModel;

public interface clsCityMasterService {

	public void funAddUpdateCityMaster(clsCityMasterModel objMaster);

	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode);

}
