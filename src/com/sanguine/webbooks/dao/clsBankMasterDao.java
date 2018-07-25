package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsBankMasterModel;

public interface clsBankMasterDao {

	public void funAddUpdateBankMaster(clsBankMasterModel objMaster);

	public clsBankMasterModel funGetBankMaster(String docCode, String clientCode);

}
