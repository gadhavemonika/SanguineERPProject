package com.sanguine.webpms.service;

import java.util.List;

import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsBillHdModel;

public interface clsBillService {

	public void funAddUpdateBillHd(clsBillHdModel objHdModel);

	public void funAddUpdateBillDtl(clsBillDtlModel objDtlModel);

	public clsBillHdModel funLoadBill(String docCode, String clientCode);

	public List<clsBillDtlModel> funLoadBillDtl(String docCode, String clientCode);

	public void funDeleteBill(clsBillHdModel objBillHdModel);

}
