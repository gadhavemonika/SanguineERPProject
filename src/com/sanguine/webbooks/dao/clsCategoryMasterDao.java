package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsCategoryMasterModel;

public interface clsCategoryMasterDao {

	public void funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster);

	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode);

}
