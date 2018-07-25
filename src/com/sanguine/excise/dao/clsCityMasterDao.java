package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsCityMasterModel;

public interface clsCityMasterDao {

	public boolean funAddUpdateCityMaster(clsCityMasterModel objMaster);

	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode);

	public clsCityMasterModel funGetObject(String code, String clientCode);

}
