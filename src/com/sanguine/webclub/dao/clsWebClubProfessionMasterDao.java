package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubProfessionMasterModel;

public interface clsWebClubProfessionMasterDao {

	public void funAddUpdateWebClubProfessionMaster(clsWebClubProfessionMasterModel objMaster);

	public clsWebClubProfessionMasterModel funGetWebClubProfessionMaster(String docCode, String clientCode);

}
