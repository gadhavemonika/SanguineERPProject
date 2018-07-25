package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubStaffMasterModel;

public interface clsWebClubStaffMasterService {
	public void funAddUpdateWebClubStaffMaster(clsWebClubStaffMasterModel objMaster);

	public clsWebClubStaffMasterModel funGetWebClubStaffMaster(String docCode, String clientCode);

}
