package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsCorporateMasterHdModel;

public interface clsCorporateMasterDao {

	public void funAddUpdateCorporateMaster(clsCorporateMasterHdModel objMaster);

	public clsCorporateMasterHdModel funGetCorporateMaster(String docCode, String clientCode);

}
