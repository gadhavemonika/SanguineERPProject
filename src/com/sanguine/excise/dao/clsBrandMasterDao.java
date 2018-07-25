package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsRateMasterModel;

public interface clsBrandMasterDao {

	public boolean funAddUpdateBrandMaster(clsBrandMasterModel objMaster);

	public List<clsBrandMasterModel> funGetBrandMaster(String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String code, String clientCode);

	public boolean funAddUpdateRateMaster(clsRateMasterModel objMaster);

	public clsRateMasterModel funGetRateObject(String brandCode, String clientCode);

}
