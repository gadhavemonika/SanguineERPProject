package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsAreaMasterModel;

public interface clsAreaMasterService {

	public void funAddUpdateAreaMaster(clsAreaMasterModel objMaster);

	public clsAreaMasterModel funGetAreaMaster(String docCode, String clientCode);

}
