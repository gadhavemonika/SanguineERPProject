package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel;

public interface clsWebClubInvitedByMasterService {
	public void funAddUpdateWebClubInvitedByMaster(clsWebClubInvitedByMasterModel objMaster);

	public clsWebClubInvitedByMasterModel funGetWebClubInvitedByMaster(String docCode, String clientCode);

}
