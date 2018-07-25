package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsInterfaceMasterModel;

public interface clsInterfaceMasterService {

	public void funAddUpdateInterfaceMaster(clsInterfaceMasterModel objMaster);

	public clsInterfaceMasterModel funGetInterfaceMaster(String docCode, String clientCode);

}
