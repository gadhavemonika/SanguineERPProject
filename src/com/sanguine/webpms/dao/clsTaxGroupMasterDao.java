package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsTaxGroupMasterModel;

public interface clsTaxGroupMasterDao {

	public void funAddUpdateTaxGroupMaster(clsTaxGroupMasterModel objMaster);

	public clsTaxGroupMasterModel funGetTaxGroupMaster(String taxGroupCode, String clientCode);

}
