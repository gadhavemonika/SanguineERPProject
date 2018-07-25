package com.sanguine.excise.dao;

import java.util.List;

import com.sanguine.excise.model.clsOpeningStockModel;

public interface clsOpeningStockDao {

	public boolean funAddUpdateExBrandOpMaster(clsOpeningStockModel objMaster);

	public clsOpeningStockModel funGetExBrandOpMaster(Long intId, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetMasterObject(Long intId, String clientCode);

	public Boolean funDeleteBrandOpening(Long intId, String clientCode);

	public clsOpeningStockModel funGetOpenedEXBrandMaster(String brandCode, String licenceCode, String clientCode);
}
