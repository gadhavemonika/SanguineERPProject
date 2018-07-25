package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsBillPassDtlModel;
import com.sanguine.model.clsBillPassHdModel;
import com.sanguine.model.clsBillPassingTaxDtlModel;

public interface clsBillPassingService {
	public void funAddUpdateBillPassingHD(clsBillPassHdModel BillPassHdModel);

	public void funAddUpdateBillPassingDtl(clsBillPassDtlModel BillPassDtlModel);

	public void funAddUpdateBillPassingTaxDtl(clsBillPassingTaxDtlModel objTaxDtlModel);

	public void funDeleteBillPassingDtlData(String billNo, String clientCode);

	public clsBillPassHdModel funGetObject(String billNo, String clientCode);

	public List<clsBillPassDtlModel> funGetDtlList(String billNo, String clientCode);

	public void funDeleteBillPassTaxDtl(String billPassNo, String clientCode);
}
