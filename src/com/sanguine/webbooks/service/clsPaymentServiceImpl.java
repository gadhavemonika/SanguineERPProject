package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webbooks.dao.clsPaymentDao;
import com.sanguine.webbooks.model.clsPaymentHdModel;

@Service("clsPaymentService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsPaymentServiceImpl implements clsPaymentService {

	@Autowired
	private clsPaymentDao objPaymentDao;

	@Override
	public void funAddUpdatePaymentHd(clsPaymentHdModel objHdModel) {
		objPaymentDao.funAddUpdatePaymentHd(objHdModel);
	}

	@Override
	public clsPaymentHdModel funGetPaymentList(String vouchNo, String clientCode, String propertyCode) {
		return objPaymentDao.funGetPaymentList(vouchNo, clientCode, propertyCode);
	}

	@Override
	public void funDeletePayment(clsPaymentHdModel objPaymentHdModel) {
		objPaymentDao.funDeletePayment(objPaymentHdModel);
	}

	public void funInsertPayment(String query) {
		objPaymentDao.funInsertPayment(query);
	}

}
