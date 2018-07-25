package com.sanguine.webpms.service;

import com.sanguine.webpms.model.clsTaxGroupMasterModel;

public interface clsTaxGroupMasterService {

	public void funAddUpdateTaxGroupMaster(clsTaxGroupMasterModel objMaster);

	public clsTaxGroupMasterModel funGetTaxGroupMaster(String taxGroupCode, String clientCode);

}
