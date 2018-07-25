package com.sanguine.service;

import com.sanguine.model.clsWSCountryMasterModel;

public interface clsWSCountryMasterService {

	public void funAddUpdateCountryMaster(clsWSCountryMasterModel objMaster);

	public clsWSCountryMasterModel funGetCountryMaster(String docCode, String clientCode);

}
