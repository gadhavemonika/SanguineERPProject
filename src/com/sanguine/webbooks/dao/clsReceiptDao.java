package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsReceiptHdModel;

public interface clsReceiptDao {

	public void funAddUpdateReceiptHd(clsReceiptHdModel objHdModel);

	public clsReceiptHdModel funGetReceiptList(String vouchNo, String clientCode, String propertyCode);

	public void funDeleteReceipt(clsReceiptHdModel objReceiptHdModel);

	public void funInsertRecipt(String sqltempDtlDr);
}
