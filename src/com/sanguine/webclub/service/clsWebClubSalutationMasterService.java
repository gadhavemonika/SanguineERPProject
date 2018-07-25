package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubSalutationMasterModel;

public interface clsWebClubSalutationMasterService {
	public void funAddUpdateWebClubSalutationMaster(clsWebClubSalutationMasterModel objMaster);

	public clsWebClubSalutationMasterModel funGetWebClubSalutationMaster(String docCode, String clientCode);

}
