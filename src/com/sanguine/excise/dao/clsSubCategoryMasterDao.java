package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsSubCategoryMasterModel;

public interface clsSubCategoryMasterDao {

	public boolean funAddUpdateSubCategoryMaster(clsSubCategoryMasterModel objMaster);

	public List<clsSubCategoryMasterModel> funGetSubCategoryMaster(String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String code, String clientCode);
}
