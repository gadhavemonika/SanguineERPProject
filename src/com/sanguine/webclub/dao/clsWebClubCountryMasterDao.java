package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubCountryMasterModel;

public interface clsWebClubCountryMasterDao {

	public void funAddUpdateWebClubCountryMaster(clsWebClubCountryMasterModel objMaster);

	public clsWebClubCountryMasterModel funGetWebClubCountryMaster(String docCode, String clientCode);

}
