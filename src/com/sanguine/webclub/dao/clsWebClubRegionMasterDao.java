package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubRegionMasterModel;

public interface clsWebClubRegionMasterDao {

	public void funAddUpdateWebClubRegionMaster(clsWebClubRegionMasterModel objMaster);

	public clsWebClubRegionMasterModel funGetWebClubRegionMaster(String docCode, String clientCode);

}
