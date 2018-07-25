package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsExciseLicenceMasterModel;

public interface clsLicenceMasterDao {

	public boolean funAddUpdateLicenceMaster(clsExciseLicenceMasterModel objMaster);

	public clsExciseLicenceMasterModel funGetLicenceMaster(String docCode, String propertyCode, String clientCode);

	public clsExciseLicenceMasterModel funGetObject(String code, String propertyCode, String clientCode);

}
