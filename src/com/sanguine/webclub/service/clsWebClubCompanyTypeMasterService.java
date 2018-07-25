package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel;

public interface clsWebClubCompanyTypeMasterService {

	public void funAddUpdateWebClubCompanyTypeMaster(clsWebClubCompanyTypeMasterModel objMaster);

	public clsWebClubCompanyTypeMasterModel funGetWebClubCompanyTypeMaster(String docCode, String clientCode);

}
