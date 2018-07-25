package com.sanguine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sanguine.model.clsCurrencyMasterModel;

public interface clsCurrencyMasterService {

	public void funAddUpdateCurrencyMaster(clsCurrencyMasterModel objMaster);

	public clsCurrencyMasterModel funGetCurrencyMaster(String docCode, String clientCode);

	public Map<String, String> funGetAllCurrency(String clientCode);

	public List<clsCurrencyMasterModel> funGetAllCurrencyDataModel(String clientCode);

	public Map<String, String> funCurrencyListToDisplay(String clientCode);

}
