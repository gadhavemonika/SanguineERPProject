package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsPermitMasterModel;

public interface clsPermitMasterImportDao {

	public void funAddUpdatePermitMaster(clsPermitMasterModel objMaster);

	public clsPermitMasterModel funGetPermitMaster(String docCode, String clientCode);

}
