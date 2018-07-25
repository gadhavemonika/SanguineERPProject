package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel;

public interface clsWebClubItemCategoryMasterService {
	public void funAddUpdateWebClubItemCategoryMaster(clsWebClubItemCategoryMasterModel objMaster);

	public clsWebClubItemCategoryMasterModel funGetWebClubItemCategoryMaster(String docCode, String clientCode);

}
