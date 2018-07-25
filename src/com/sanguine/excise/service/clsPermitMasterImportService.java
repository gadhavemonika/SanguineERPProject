package com.sanguine.excise.service;

import com.sanguine.excise.model.clsPermitMasterModel;

public interface clsPermitMasterImportService {

	public void funAddUpdatePermitMaster(clsPermitMasterModel objMaster);

	public clsPermitMasterModel funGetPermitMaster(String docCode, String clientCode);

}
