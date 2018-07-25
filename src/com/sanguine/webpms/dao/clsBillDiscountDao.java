package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsBillDiscountHdModel;

public interface clsBillDiscountDao {

	public void funAddUpdateBillDiscount(clsBillDiscountHdModel objMaster);

	public clsBillDiscountHdModel funGetBillDiscount(String docCode, String clientCode);

	public boolean funDeleteBillDiscount(String docCode, String clientCode);

}
