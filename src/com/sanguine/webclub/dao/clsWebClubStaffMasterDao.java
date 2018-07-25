package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubStaffMasterModel;

public interface clsWebClubStaffMasterDao {
	public void funAddUpdateWebClubStaffMaster(clsWebClubStaffMasterModel objMaster);

	public clsWebClubStaffMasterModel funGetWebClubStaffMaster(String docCode, String clientCode);

}
