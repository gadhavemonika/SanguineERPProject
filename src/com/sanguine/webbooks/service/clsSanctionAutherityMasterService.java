package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel;

public interface clsSanctionAutherityMasterService {

	public void funAddUpdateSanctionAutherityMaster(clsSanctionAutherityMasterModel objMaster);

	public clsSanctionAutherityMasterModel funGetSanctionAutherityMaster(String docCode, String clientCode);

}
