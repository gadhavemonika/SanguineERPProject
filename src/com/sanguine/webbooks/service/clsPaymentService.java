package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsPaymentHdModel;

public interface clsPaymentService {

	public void funAddUpdatePaymentHd(clsPaymentHdModel objHdModel);

	public clsPaymentHdModel funGetPaymentList(String vouchNo, String clientCode, String propertyCode);

	public void funDeletePayment(clsPaymentHdModel objPaymentHdModel);

	public void funInsertPayment(String query);
}
