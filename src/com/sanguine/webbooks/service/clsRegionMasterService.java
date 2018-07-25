package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsRegionMasterModel;

public interface clsRegionMasterService {

	public void funAddUpdateRegionMaster(clsRegionMasterModel objMaster);

	public clsRegionMasterModel funGetRegionMaster(String docCode, String clientCode);

}
