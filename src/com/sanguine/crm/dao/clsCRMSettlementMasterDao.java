package com.sanguine.crm.dao;

import java.util.Map;

import com.sanguine.model.clsSettlementMasterModel;

public interface clsCRMSettlementMasterDao {

	public void funAddUpdate(clsSettlementMasterModel objModel);

	public clsSettlementMasterModel funGetObject(String strCode, String clientCode);

	public clsSettlementMasterModel funGeSettlementObject(String strCode, String dteInvDate, String clientCode);

	public Map<String, String> funGetSettlementComboBox(String clientCode);
}
