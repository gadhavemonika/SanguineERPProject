package com.sanguine.webclub.dao;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel;

public interface clsWebClubGroupMasterDao {

	public void funAddGroup(clsWebClubGroupMasterModel group);

	public clsWebClubGroupMasterModel funGetGroup(String groupCode, String clientCode);

}
