package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsSizeMasterModel;

public interface clsSizeMasterService {

	public boolean funAddUpdateSizeMaster(clsSizeMasterModel objMaster);

	@SuppressWarnings("rawtypes")
	public List funGetSizeMasterList(String clientCode);

	public clsSizeMasterModel funGetObject(String code, String clientCode);

}
