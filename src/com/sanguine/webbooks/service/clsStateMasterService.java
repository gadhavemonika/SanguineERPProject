package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsStateMasterModel;

public interface clsStateMasterService {

	public void funAddUpdateStateMaster(clsStateMasterModel objMaster);

	public clsStateMasterModel funGetStateMaster(String docCode, String clientCode);

}
