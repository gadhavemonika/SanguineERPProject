package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubLockerMasterModel;

public interface clsWebClubLockerMasterDao {

	public void funAddUpdateWebClubLockerMaster(clsWebClubLockerMasterModel objMaster);

	public clsWebClubLockerMasterModel funGetWebClubLockerMaster(String docCode, String clientCode);

}
