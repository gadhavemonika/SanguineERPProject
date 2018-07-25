package com.sanguine.webpms.service;

import com.sanguine.webpms.model.clsBillDiscountHdModel;

public interface clsBillDiscountService {

	public void funAddUpdateBillDiscount(clsBillDiscountHdModel objMaster);

	public clsBillDiscountHdModel funGetBillDiscount(String docCode, String clientCode);

	public boolean funDeleteBillDiscount(String docCode, String clientCode);

}
