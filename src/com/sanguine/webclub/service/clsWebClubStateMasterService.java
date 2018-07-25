package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubStateMasterModel;

public interface clsWebClubStateMasterService {

	public void funAddUpdateWebClubStateMaster(clsWebClubStateMasterModel objMaster);

	public clsWebClubStateMasterModel funGetWebClubStateMaster(String docCode, String clientCode);

}
