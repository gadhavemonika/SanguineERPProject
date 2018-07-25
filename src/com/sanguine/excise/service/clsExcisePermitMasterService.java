package com.sanguine.excise.service;

import com.sanguine.excise.model.clsExcisePermitMasterModel;

public interface clsExcisePermitMasterService {

	public boolean funAddUpdatePermitMaster(clsExcisePermitMasterModel objMaster);

	public clsExcisePermitMasterModel funGetPermitMaster(String docCode, String clientCode);

}
