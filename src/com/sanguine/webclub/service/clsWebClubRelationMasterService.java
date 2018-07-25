package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubRelationMasterModel;

public interface clsWebClubRelationMasterService {

	public void funAddUpdateWebClubRelationMaster(clsWebClubRelationMasterModel objMaster);

	public clsWebClubRelationMasterModel funGetWebClubRelationMaster(String docCode, String clientCode);

}
