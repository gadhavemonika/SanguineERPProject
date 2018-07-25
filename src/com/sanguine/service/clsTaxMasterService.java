package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxSettlementMasterModel;

public interface clsTaxMasterService {
	public void funAddUpdate(clsTaxHdModel object);

	public void funAddUpdateDtl(clsTaxSettlementMasterModel object);

	public List<clsTaxHdModel> funGetList();

	public clsTaxHdModel funGetObject(String code, String clientCode);

	public List funGetDtlList(String taxCode, String clientCode);

	public List funGetTaxes(String taxCode, String clientCode);

	public List funGetSubGroupList(String clientCode);
}
