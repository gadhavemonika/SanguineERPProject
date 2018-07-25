package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsRegionMasterModel;

public interface clsRegionMasterDao {

	public void funAddUpdateRegionMaster(clsRegionMasterModel objMaster);

	public clsRegionMasterModel funGetRegionMaster(String docCode, String clientCode);

}
