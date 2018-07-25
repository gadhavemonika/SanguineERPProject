package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel;

public interface clsWebClubSubCategoryMasterDao {

	public void funAddUpdateSubCategoryMaster(clsWebClubSubCategoryMasterModel objMaster);

	public clsWebClubSubCategoryMasterModel funGetSubCategoryMaster(String docCode, String clientCode);

}
