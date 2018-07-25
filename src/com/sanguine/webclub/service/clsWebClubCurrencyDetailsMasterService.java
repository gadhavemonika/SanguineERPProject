package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel;

public interface clsWebClubCurrencyDetailsMasterService {
	public void funAddUpdateWebClubCurrencyDetailsMaster(clsWebClubCurrencyDetailsMasterModel objMaster);

	public clsWebClubCurrencyDetailsMasterModel funGetWebClubCurrencyDetailsMaster(String docCode, String clientCode);

}
