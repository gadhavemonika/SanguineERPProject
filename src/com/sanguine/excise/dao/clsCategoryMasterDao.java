package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsCategoryMasterModel;

public interface clsCategoryMasterDao {

	public boolean funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster);

	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode);

	public clsCategoryMasterModel funGetObject(String code, String clientCode);

}
