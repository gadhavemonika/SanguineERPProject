package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubTitleMasterModel;

public interface clsWebClubTitleMasterService {
	public void funAddUpdateWebClubTitleMaster(clsWebClubTitleMasterModel objMaster);

	public clsWebClubTitleMasterModel funGetWebClubTitleMaster(String docCode, String clientCode);

}
