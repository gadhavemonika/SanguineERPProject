package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubProfileMasterModel;

public interface clsWebClubProfileMasterService {
	public void funAddUpdateWebClubProfileMaster(clsWebClubProfileMasterModel objMaster);

	public clsWebClubProfileMasterModel funGetWebClubProfileMaster(String docCode, String clientCode);

}
