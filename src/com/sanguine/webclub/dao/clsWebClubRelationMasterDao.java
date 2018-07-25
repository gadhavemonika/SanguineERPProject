package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubRelationMasterModel;

public interface clsWebClubRelationMasterDao {
	public void funAddUpdateWebClubRelationMaster(clsWebClubRelationMasterModel objMaster);

	public clsWebClubRelationMasterModel funGetWebClubRelationMaster(String docCode, String clientCode);

}
