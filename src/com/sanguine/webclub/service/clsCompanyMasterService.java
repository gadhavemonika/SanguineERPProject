package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;

public interface clsCompanyMasterService {

	public void funAddUpdateCompanyMaster(clsWebClubCompanyMasterModel objMaster);

	public clsWebClubCompanyMasterModel funGetCompanyMaster(String docCode, String clientCode);

}
