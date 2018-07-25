package com.sanguine.service;

import com.sanguine.model.clsProcessMasterModel;

public interface clsProcessMasterService {

	public void funAddUpdateProcessMaster(clsProcessMasterModel objMaster);

	public clsProcessMasterModel funGetProcessMaster(String processCode, String clientCode);

}
