package com.sanguine.dao;

import com.sanguine.model.clsProcessMasterModel;

public interface clsProcessMasterDao {

	public void funAddUpdateProcessMaster(clsProcessMasterModel objMaster);

	public clsProcessMasterModel funGetProcessMaster(String ProcessCode, String clientCode);

}
