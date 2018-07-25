package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsSubCategoryMasterModel;

public interface clsSubCategoryMasterService {

	public boolean funAddUpdateSubCategoryMaster(clsSubCategoryMasterModel objMaster);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String docCode, String clientCode);

	public List<clsSubCategoryMasterModel> funGetSubCategoryMaster(String clientCode);

}
