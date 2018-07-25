package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsExcisePermitMasterModel;

public interface clsExcisePermitMasterDao {

	public boolean funAddUpdatePermitMaster(clsExcisePermitMasterModel objMaster);

	public clsExcisePermitMasterModel funGetPermitMaster(String docCode, String clientCode);

}
