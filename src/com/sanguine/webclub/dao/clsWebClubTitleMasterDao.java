package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubTitleMasterModel;

public interface clsWebClubTitleMasterDao {
	public void funAddUpdateWebClubTitleMaster(clsWebClubTitleMasterModel objMaster);

	public clsWebClubTitleMasterModel funGetWebClubTitleMaster(String docCode, String clientCode);

}
