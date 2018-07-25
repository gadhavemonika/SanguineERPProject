package com.sanguine.crm.dao;

import java.util.List;

import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.crm.model.clsPartyMasterModel;

public interface clsPartyMasterDao {

	public void funAddUpdate(clsPartyMasterModel objModel);

	public List<clsPartyMasterModel> funGetList();

	public clsPartyMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	@SuppressWarnings("rawtypes")
	public List funGetDtlList(String pCode, String clientCode);

	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator);

	public void funDeletePartyTaxDtl(String partyCode, String clientCode);

	public clsPartyMasterModel funGetPartyDtl(String pCode, String clientCode);

	public List<clsPartyMasterModel> funGetListCustomer(String clientCode);

	public List<clsPartyMasterModel> funGetLinkLocCustomer(String locCode, String clientCode);

}
