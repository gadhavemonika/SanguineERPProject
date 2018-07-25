package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsRateMasterModel;

public interface clsBrandMasterService {

	public boolean funAddUpdateBrandMaster(clsBrandMasterModel objMaster);

	public List<clsBrandMasterModel> funGetBrandMaster(String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String brandCode, String clientCode);

	public boolean funAddUpdateRateMaster(clsRateMasterModel objMaster);

	public clsRateMasterModel funGetRateObject(String brandCode, String clientCode);

}
