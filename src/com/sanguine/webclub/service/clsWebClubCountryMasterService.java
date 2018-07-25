package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubCountryMasterModel;

public interface clsWebClubCountryMasterService {

	public void funAddUpdateWebClubCountryMaster(clsWebClubCountryMasterModel objMaster);

	public clsWebClubCountryMasterModel funGetWebClubCountryMaster(String docCode, String clientCode);

}
