package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubDesignationMasterModel;

public interface clsWebClubDesignationMasterDao {

	public void funAddUpdateWebClubDesignationMaster(clsWebClubDesignationMasterModel objMaster);

	public clsWebClubDesignationMasterModel funGetWebClubDesignationMaster(String docCode, String clientCode);

}
