package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubSalutationMasterModel;

public interface clsWebClubSalutationMasterDao {
	public void funAddUpdateWebClubSalutationMaster(clsWebClubSalutationMasterModel objMaster);

	public clsWebClubSalutationMasterModel funGetWebClubSalutationMaster(String docCode, String clientCode);

}
