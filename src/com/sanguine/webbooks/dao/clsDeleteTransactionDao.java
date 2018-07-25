package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsDeleteTransactionModel;
import com.sanguine.webbooks.model.clsWebBooksAuditHdModel;

public interface clsDeleteTransactionDao {

	public void funAddUpdateDeleteTransaction(clsDeleteTransactionModel objMaster);

	public clsDeleteTransactionModel funGetDeleteTransaction(String docCode, String clientCode);

	public void funAddUpdateAuditHd(clsWebBooksAuditHdModel objHdModel);

	public int funDeleteTransactionRecord(String hqlDelQuery, String vouchNo, String clientCode, String propertyCode);
}
