package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel;

public interface clsSanctionAutherityMasterDao {

	public void funAddUpdateSanctionAutherityMaster(clsSanctionAutherityMasterModel objMaster);

	public clsSanctionAutherityMasterModel funGetSanctionAutherityMaster(String docCode, String clientCode);

}
