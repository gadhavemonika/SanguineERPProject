package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsBusinessSourceMasterModel;
import com.sanguine.webpms.model.clsMarketSourceMasterModel;

public interface clsMarketSourceMasterDao {
	
	public void funAddUpdateMarketMaster(clsMarketSourceMasterModel objMarketMasterModel);

	public List funGetMarketMaster(String MarketCode, String clientCode);

}
