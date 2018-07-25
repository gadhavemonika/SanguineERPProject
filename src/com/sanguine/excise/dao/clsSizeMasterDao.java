package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsSizeMasterModel;

public interface clsSizeMasterDao {

	public boolean funAddUpdateSizeMaster(clsSizeMasterModel objMaster);

	@SuppressWarnings("rawtypes")
	public List funGetSizeMasterList(String clientCode);

	public clsSizeMasterModel funGetObject(String code, String clientCode);

}
