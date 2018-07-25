package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsCategoryMasterModel;

public interface clsCategoryMasterService {

	public void funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster);

	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode);

}
