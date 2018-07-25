package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubDesignationMasterModel;

public interface clsWebClubDesignationMasterService {

	public void funAddUpdateWebClubDesignationMaster(clsWebClubDesignationMasterModel objMaster);

	public clsWebClubDesignationMasterModel funGetWebClubDesignationMaster(String docCode, String clientCode);

}
