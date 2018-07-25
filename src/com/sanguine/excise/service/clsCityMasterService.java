package com.sanguine.excise.service;

import com.sanguine.excise.model.clsCityMasterModel;

public interface clsCityMasterService {

	public boolean funAddUpdateCityMaster(clsCityMasterModel objMaster);

	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode);

	public clsCityMasterModel funGetObject(String code, String clientCode);
}
