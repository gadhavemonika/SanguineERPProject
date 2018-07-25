package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubReasonMasterModel;

public interface clsWebClubReasonMasterDao {

	public void funAddUpdateWebClubReasonMaster(clsWebClubReasonMasterModel objMaster);

	public clsWebClubReasonMasterModel funGetWebClubReasonMaster(String docCode, String clientCode);

}
