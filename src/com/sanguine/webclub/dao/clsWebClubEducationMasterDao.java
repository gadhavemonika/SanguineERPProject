package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubEducationMasterModel;

public interface clsWebClubEducationMasterDao {

	public void funAddUpdateWebClubEducationMaster(clsWebClubEducationMasterModel objMaster);

	public clsWebClubEducationMasterModel funGetWebClubEducationMaster(String docCode, String clientCode);

}
