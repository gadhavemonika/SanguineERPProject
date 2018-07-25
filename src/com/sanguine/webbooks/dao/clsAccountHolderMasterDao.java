package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsAccountHolderMasterModel;

public interface clsAccountHolderMasterDao {

	public void funAddUpdateAccountHolderMaster(clsAccountHolderMasterModel objMaster);

	public clsAccountHolderMasterModel funGetAccountHolderMaster(String docCode, String clientCode);

}
