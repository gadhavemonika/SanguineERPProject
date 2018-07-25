package com.sanguine.service;

import com.sanguine.model.clsWSStateMasterModel;

public interface clsWSStateMasterService {

	public void funAddUpdateWSStateMaster(clsWSStateMasterModel objMaster);

	public clsWSStateMasterModel funGetWSStateMaster(String docCode, String clientCode);

}
