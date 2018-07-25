package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsBankMasterModel;

public interface clsBankMasterService {

	public void funAddUpdateBankMaster(clsBankMasterModel objMaster);

	public clsBankMasterModel funGetBankMaster(String docCode, String clientCode);

}
