package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubLockerMasterModel;

public interface clsWebClubLockerMasterService {

	public void funAddUpdateWebClubLockerMaster(clsWebClubLockerMasterModel objMaster);

	public clsWebClubLockerMasterModel funGetWebClubLockerMaster(String docCode, String clientCode);

}
