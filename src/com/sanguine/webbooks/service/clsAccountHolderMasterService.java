package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsAccountHolderMasterModel;

public interface clsAccountHolderMasterService {

	public void funAddUpdateAccountHolderMaster(clsAccountHolderMasterModel objMaster);

	public clsAccountHolderMasterModel funGetAccountHolderMaster(String docCode, String clientCode);

}
