package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel;

public interface clsWebClubItemCategoryMasterDao {
	public void funAddUpdateWebClubItemCategoryMaster(clsWebClubItemCategoryMasterModel objMaster);

	public clsWebClubItemCategoryMasterModel funGetWebClubItemCategoryMaster(String docCode, String clientCode);

}
