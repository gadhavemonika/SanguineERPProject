package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webbooks.dao.clsDeleteTransactionDao;
import com.sanguine.webbooks.model.clsDeleteTransactionModel;
import com.sanguine.webbooks.model.clsWebBooksAuditHdModel;

@Service("clsDeleteTransactionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsDeleteTransactionServiceImpl implements clsDeleteTransactionService {
	@Autowired
	private clsDeleteTransactionDao objDeleteTransactionDao;

	@Override
	public void funAddUpdateDeleteTransaction(clsDeleteTransactionModel objMaster) {
		objDeleteTransactionDao.funAddUpdateDeleteTransaction(objMaster);
	}

	@Override
	public clsDeleteTransactionModel funGetDeleteTransaction(String docCode, String clientCode) {
		return objDeleteTransactionDao.funGetDeleteTransaction(docCode, clientCode);
	}

	@Override
	public void funAddUpdateAuditHd(clsWebBooksAuditHdModel objHdModel) {
		objDeleteTransactionDao.funAddUpdateAuditHd(objHdModel);
	}

	@Override
	public int funDeleteTransactionRecord(String hqlDelQuery, String vouchNo, String clientCode, String propertyCode) {
		return objDeleteTransactionDao.funDeleteTransactionRecord(hqlDelQuery, vouchNo, clientCode, propertyCode);
	}

}
