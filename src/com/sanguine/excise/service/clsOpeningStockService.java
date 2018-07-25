package com.sanguine.excise.service;

import java.util.List;

import com.sanguine.excise.model.clsOpeningStockModel;

public interface clsOpeningStockService {

	public Boolean funAddUpdateExBrandOpMaster(clsOpeningStockModel objMaster);

	public clsOpeningStockModel funGetExBrandOpMaster(Long intId, String ClientCode);

	@SuppressWarnings("rawtypes")
	public List funGetMasterObject(Long intId, String clientCode);

	public Boolean funDeleteDtl(Long intId, String clientCode);

	public clsOpeningStockModel funGetOpenedEXBrandMaster(String brandCode, String licenceCode, String clientCode);

}
