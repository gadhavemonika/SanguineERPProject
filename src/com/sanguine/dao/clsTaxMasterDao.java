package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxSettlementMasterModel;

public interface clsTaxMasterDao {
	public void funAddUpdate(clsTaxHdModel object);

	public void funAddUpdateDtl(clsTaxSettlementMasterModel object);

	public List<clsTaxHdModel> funGetList();

	public clsTaxHdModel funGetObject(String Code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public List funGetDtlList(String taxCode, String clientCode);

	public List funGetTaxes(String taxCode, String clientCode);

	public List funGetSubGroupList(String clientCode);
}
