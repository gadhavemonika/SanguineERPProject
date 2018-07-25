package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsPaymentHdModel;

public interface clsPaymentDao {

	public void funAddUpdatePaymentHd(clsPaymentHdModel objHdModel);

	public clsPaymentHdModel funGetPaymentList(String vouchNo, String clientCode, String propertyCode);

	public void funDeletePayment(clsPaymentHdModel objPaymentHdModel);

	public void funInsertPayment(String query);
}
