package com.sanguine.excise.service;

import com.sanguine.excise.model.clsExciseLicenceMasterModel;

public interface clsLicenceMasterService {

	public boolean funAddUpdateLicenceMaster(clsExciseLicenceMasterModel objMaster);

	public clsExciseLicenceMasterModel funGetLicenceMaster(String docCode, String propertyCode, String clientCode);

	public clsExciseLicenceMasterModel funGetObject(String code, String propertyCode, String clientCode);

}
