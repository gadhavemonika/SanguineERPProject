package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubRegionMasterModel;

public interface clsWebClubRegionMasterService {

	public void funAddUpdateWebClubRegionMaster(clsWebClubRegionMasterModel objMaster);

	public clsWebClubRegionMasterModel funGetWebClubRegionMaster(String docCode, String clientCode);

}
