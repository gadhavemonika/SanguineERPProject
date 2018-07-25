package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubReasonMasterModel;

public interface clsWebClubReasonMasterService {

	public void funAddUpdateWebClubReasonMaster(clsWebClubReasonMasterModel objMaster);

	public clsWebClubReasonMasterModel funGetWebClubReasonMaster(String docCode, String clientCode);

}
