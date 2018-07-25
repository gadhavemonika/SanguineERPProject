package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsInterfaceMasterModel;

public interface clsInterfaceMasterDao {

	public void funAddUpdateInterfaceMaster(clsInterfaceMasterModel objMaster);

	public clsInterfaceMasterModel funGetInterfaceMaster(String docCode, String clientCode);

}
