package com.sanguine.crm.service;

import java.util.Map;

import com.sanguine.model.clsSettlementMasterModel;

public interface clsCRMSettlementMasterService {

	public void funAddUpdate(clsSettlementMasterModel objModel);

	public clsSettlementMasterModel funGetObject(String strCode, String clientCode);

	public clsSettlementMasterModel funGeSettlementObject(String strCode, String steInvDate, String clientCode);

	public Map<String, String> funGetSettlementComboBox(String clientCode);
}
