package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsNarrationMasterModel;

public interface clsNarrationMasterService {

	public void funAddUpdateNarrationMaster(clsNarrationMasterModel objMaster);

	public clsNarrationMasterModel funGetNarrationMaster(String docCode, String clientCode);

}
