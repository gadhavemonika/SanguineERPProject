package com.sanguine.webbooks.apgl.dao;

import com.sanguine.webbooks.apgl.model.clsSundaryCrBillModel;

public interface clsSuppGRNBillDao {

	public void funAddUpdateSuppGRNBill(clsSundaryCrBillModel objMaster);

	public clsSundaryCrBillModel funGetSuppGRNBill(String docCode, String clientCode);

	public clsSundaryCrBillModel funGetSundryCriditorDtl(String docCode, String clientCode);

}
