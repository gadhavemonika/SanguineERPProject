package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsDebtorMaster;

public interface clsDebtorLedgerService {

	public clsDebtorMaster funGetDebtorDetails(String debtorCode, String clientCode, String propertyCode);

}
