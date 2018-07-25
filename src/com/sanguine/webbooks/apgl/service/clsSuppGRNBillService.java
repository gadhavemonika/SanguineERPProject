package com.sanguine.webbooks.apgl.service;

import com.sanguine.webbooks.apgl.model.clsSundaryCrBillModel;

public interface clsSuppGRNBillService {

	public void funAddUpdateSuppGRNBill(clsSundaryCrBillModel objMaster);

	public clsSundaryCrBillModel funGetSuppGRNBill(String docCode, String clientCode);

	public clsSundaryCrBillModel funGetSundryCriditorDtl(String docCode, String clientCode);

}
