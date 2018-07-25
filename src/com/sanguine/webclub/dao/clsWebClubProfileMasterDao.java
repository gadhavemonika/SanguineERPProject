package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubProfileMasterModel;

public interface clsWebClubProfileMasterDao {
	public void funAddUpdateWebClubProfileMaster(clsWebClubProfileMasterModel objMaster);

	public clsWebClubProfileMasterModel funGetWebClubProfileMaster(String docCode, String clientCode);

}
