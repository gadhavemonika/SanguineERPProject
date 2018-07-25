package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsCountryMasterModel;

public interface clsCountryMasterService {

	public void funAddUpdateCountryMaster(clsCountryMasterModel objMaster);

	public clsCountryMasterModel funGetCountryMaster(String docCode, String clientCode);

}
