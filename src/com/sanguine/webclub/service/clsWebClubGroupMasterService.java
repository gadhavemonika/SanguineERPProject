package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubGroupMasterModel;

public interface clsWebClubGroupMasterService {

	public void funAddGroup(clsWebClubGroupMasterModel group);

	public clsWebClubGroupMasterModel funGetGroup(String groupCode, String clientCode);

}
