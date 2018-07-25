package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsDebtorMaster;

public interface clsDebtorLedgerDao {

	public clsDebtorMaster funGetDebtorDetails(String debtorCode, String clientCode, String propertyCode);
}
