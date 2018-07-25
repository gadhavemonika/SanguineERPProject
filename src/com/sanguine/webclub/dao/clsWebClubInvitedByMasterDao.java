package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel;

public interface clsWebClubInvitedByMasterDao {
	public void funAddUpdateWebClubInvitedByMaster(clsWebClubInvitedByMasterModel objMaster);

	public clsWebClubInvitedByMasterModel funGetWebClubInvitedByMaster(String docCode, String clientCode);

}
