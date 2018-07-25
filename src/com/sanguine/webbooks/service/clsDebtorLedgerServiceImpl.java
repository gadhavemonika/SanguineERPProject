package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webbooks.dao.clsDebtorLedgerDao;
import com.sanguine.webbooks.model.clsDebtorMaster;

@Service("clsDebtorLedgerService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsDebtorLedgerServiceImpl implements clsDebtorLedgerService {

	@Autowired
	private clsDebtorLedgerDao objDebtorLedgerDao;

	@Override
	public clsDebtorMaster funGetDebtorDetails(String debtorCode, String clientCode, String propertyCode) {
		// TODO Auto-generated method stub
		return objDebtorLedgerDao.funGetDebtorDetails(debtorCode, clientCode, propertyCode);
	}
}
