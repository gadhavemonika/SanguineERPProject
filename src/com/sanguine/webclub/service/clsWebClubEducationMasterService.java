package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubEducationMasterModel;

public interface clsWebClubEducationMasterService {

	public void funAddUpdateWebClubEducationMaster(clsWebClubEducationMasterModel objMaster);

	public clsWebClubEducationMasterModel funGetWebClubEducationMaster(String docCode, String clientCode);

}
