package com.sanguine.excise.service;

import com.sanguine.excise.model.clsCategoryMasterModel;

public interface clsCategoryMasterService {

	public boolean funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster);

	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode);

	public clsCategoryMasterModel funGetObject(String code, String clientCode);

}
