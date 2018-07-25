package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;

public interface clsCompanyMasterDao {

	public void funAddUpdateCompanyMaster(clsWebClubCompanyMasterModel objMaster);

	public clsWebClubCompanyMasterModel funGetCompanyMaster(String docCode, String clientCode);

}
