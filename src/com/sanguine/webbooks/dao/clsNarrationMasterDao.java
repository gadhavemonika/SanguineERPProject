package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsNarrationMasterModel;

public interface clsNarrationMasterDao {

	public void funAddUpdateNarrationMaster(clsNarrationMasterModel objMaster);

	public clsNarrationMasterModel funGetNarrationMaster(String docCode, String clientCode);

}
