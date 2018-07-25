package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsWebBooksAuditHdModel;
import com.sanguine.webbooks.model.clsDeleteTransactionModel;

public interface clsDeleteTransactionService {

	public void funAddUpdateDeleteTransaction(clsDeleteTransactionModel objMaster);

	public clsDeleteTransactionModel funGetDeleteTransaction(String docCode, String clientCode);

	public void funAddUpdateAuditHd(clsWebBooksAuditHdModel objHdModel);

	public int funDeleteTransactionRecord(String hqlDelQuery, String vouchNo, String clientCode, String propertyCode);
}
