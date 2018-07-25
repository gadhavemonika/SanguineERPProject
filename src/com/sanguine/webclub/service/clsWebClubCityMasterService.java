package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubCityMasterModel;

public interface clsWebClubCityMasterService {

	public void funAddUpdateWebClubCityMaster(clsWebClubCityMasterModel objMaster);

	public clsWebClubCityMasterModel funGetWebClubCityMaster(String docCode, String clientCode);

}
