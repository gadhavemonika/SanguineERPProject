package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsExciseSupplierMasterModel;

public interface clsExciseSupplierMasterDao {

	public boolean funAddUpdateExciseSupplierMaster(clsExciseSupplierMasterModel objMaster);

	public List<clsExciseSupplierMasterModel> funGetExciseSupplierMaster(String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String code, String clientCode);
}
