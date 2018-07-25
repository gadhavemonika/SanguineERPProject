package com.sanguine.crm.dao;

import java.util.List;

import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.crm.model.clsSubContractorMasterModel;

public interface clsSubContractorMasterDao {

	public Boolean funAddUpdate(clsSubContractorMasterModel objModel);

	public List<clsSubContractorMasterModel> funGetList(String clientCode);

	public clsSubContractorMasterModel funGetObject(String strPCode, String clientCode);

	public Boolean funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator);

	public void funDeletePartyTaxDtl(String partyCode, String clientCode);
}
