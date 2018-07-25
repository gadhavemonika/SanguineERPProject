package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;

public interface clsSupplierMasterDao {
	public void funAddUpdate(clsSupplierMasterModel objModel);

	public List<clsSupplierMasterModel> funGetList(String clientCode);

	public clsSupplierMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public List funGetDtlList(String pCode, String clientCode);

	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator);

	public void funDeletePartyTaxDtl(String partyCode, String clientCode);

	public void funExciseUpdate(String partyCode, String clientCode, String exSuppCode);

}
