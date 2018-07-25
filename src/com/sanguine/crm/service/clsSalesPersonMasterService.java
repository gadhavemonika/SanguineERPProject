package com.sanguine.crm.service;

import java.util.List;
import java.util.Map;

import com.sanguine.crm.model.clsSalesPersonMasterModel;

public interface clsSalesPersonMasterService {

	public void funAddUpdateclsSalesPersonMaster(clsSalesPersonMasterModel objMaster);

	//public clsSalesPersonMasterModel funGetclsSalesPersonMaster(String docCode,String clientCode);

	public List funGetclsSalesPersonMaster(String docCode,String clientCode);
	
	public Map<String, String> funCurrencyListToDisplay(String clientCode);
}
