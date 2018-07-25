package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubCityMasterModel;

public interface clsWebClubCityMasterDao {

	public void funAddUpdateWebClubCityMaster(clsWebClubCityMasterModel objMaster);

	public clsWebClubCityMasterModel funGetWebClubCityMaster(String docCode, String clientCode);

}
