package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webbooks.dao.clsReceiptDao;
import com.sanguine.webbooks.model.clsReceiptHdModel;

@Service("clsReceiptService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsReceiptServiceImpl implements clsReceiptService {
	@Autowired
	private clsReceiptDao objReceiptDao;

	@Override
	public void funAddUpdateReceiptHd(clsReceiptHdModel objHdModel) {
		objReceiptDao.funAddUpdateReceiptHd(objHdModel);
	}

	@Override
	public clsReceiptHdModel funGetReceiptList(String vouchNo, String clientCode, String propertyCode) {
		return objReceiptDao.funGetReceiptList(vouchNo, clientCode, propertyCode);
	}

	@Override
	public void funDeleteReceipt(clsReceiptHdModel objReceiptHdModel) {
		objReceiptDao.funDeleteReceipt(objReceiptHdModel);
	}

	public void funInsertRecipt(String sqltempDtlDr) {

		objReceiptDao.funInsertRecipt(sqltempDtlDr);

	}
}
