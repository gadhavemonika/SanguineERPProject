package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubStateMasterModel;

public interface clsWebClubStateMasterDao {

	public void funAddUpdateWebClubStateMaster(clsWebClubStateMasterModel objMaster);

	public clsWebClubStateMasterModel funGetWebClubStateMaster(String docCode, String clientCode);

}
