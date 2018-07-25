package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel;

public interface clsWebClubSubCategoryMasterService {

	public void funAddUpdateSubCategoryMaster(clsWebClubSubCategoryMasterModel objMaster);

	public clsWebClubSubCategoryMasterModel funGetSubCategoryMaster(String docCode, String clientCode);

}
