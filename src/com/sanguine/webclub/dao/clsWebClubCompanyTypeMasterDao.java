package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel;

public interface clsWebClubCompanyTypeMasterDao {

	public void funAddUpdateWebClubCompanyTypeMaster(clsWebClubCompanyTypeMasterModel objMaster);

	public clsWebClubCompanyTypeMasterModel funGetWebClubCompanyTypeMaster(String docCode, String clientCode);

}
