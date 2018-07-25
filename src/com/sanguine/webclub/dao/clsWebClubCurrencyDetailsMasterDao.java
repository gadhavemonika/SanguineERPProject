package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel;

public interface clsWebClubCurrencyDetailsMasterDao {
	public void funAddUpdateWebClubCurrencyDetailsMaster(clsWebClubCurrencyDetailsMasterModel objMaster);

	public clsWebClubCurrencyDetailsMasterModel funGetWebClubCurrencyDetailsMaster(String docCode, String clientCode);

}
