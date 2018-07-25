package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsReceiptHdModel;

public interface clsReceiptService {

	public void funAddUpdateReceiptHd(clsReceiptHdModel objHdModel);

	public clsReceiptHdModel funGetReceiptList(String vouchNo, String clientCode, String propertyCode);

	public void funDeleteReceipt(clsReceiptHdModel objReceiptHdModel);

	public void funInsertRecipt(String sqltempDtlDr);
}
