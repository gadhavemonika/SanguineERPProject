package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubProfessionMasterModel;

public interface clsWebClubProfessionMasterService {

	public void funAddUpdateWebClubProfessionMaster(clsWebClubProfessionMasterModel objMaster);

	public clsWebClubProfessionMasterModel funGetWebClubProfessionMaster(String docCode, String clientCode);

}
