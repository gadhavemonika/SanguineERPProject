package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;

public interface clsSupplierMasterService {
	public void funAddUpdate(clsSupplierMasterModel object);

	public List<clsSupplierMasterModel> funGetList(String clientCode);

	public clsSupplierMasterModel funGetObject(String code, String clientCode);

	public List funGetDtlList(String pCode, String clientCode);

	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator);

	public void funDeletePartyTaxDtl(String partyCode, String clientCode);

	public void funExciseUpdate(String partyCode, String clientCode, String exSuppCode);

}
