package com.sanguine.crm.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.crm.model.clsSalesPersonMasterModel;

public interface clsSalesPersonMasterDao {
	public void funAddUpdateclsSalesPersonMaster(clsSalesPersonMasterModel objMaster);

	public List funGetclsSalesPersonMaster(String docCode,String clientCode);

	Map<String, String> funCurrencyListToDisplay(String clientCode);

}
