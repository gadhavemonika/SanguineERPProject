package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsRateContractDtlModel;
import com.sanguine.model.clsRateContractHdModel;

public interface clsRateContractService {
	public void funAddUpdate(clsRateContractHdModel object);

	public void funAddUpdateDtl(clsRateContractDtlModel object);

	public List<clsRateContractHdModel> funGetList(String clientCode);

	public List<clsRateContractDtlModel> funGetDtlList(String rateContNo, String clientCode);

	public List funGetObject(String rateContractNo, String clientCode);

	public List funGetSupplierList(String sql);

	public void funDeleteDtl(String rateContNo, String clientCode);
}
