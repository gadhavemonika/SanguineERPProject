package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.crm.model.clsPartyMasterModel;

public interface clsPartyMasterService {

	public void funAddUpdate(clsPartyMasterModel object);

	public List<clsPartyMasterModel> funGetList();

	public clsPartyMasterModel funGetObject(String code, String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetDtlList(String pCode, String clientCode);

	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator);

	public void funDeletePartyTaxDtl(String partyCode, String clientCode);

	public clsPartyMasterModel funGetPartyDtl(String pCode, String clientCode);

	public List<clsPartyMasterModel> funGetListCustomer(String clientCode);

	public List<clsPartyMasterModel> funGetLinkLocCustomer(String locCode, String clientCode);

}
