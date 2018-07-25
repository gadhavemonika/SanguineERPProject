package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.webpms.dao.clsBillDiscountDao;
import com.sanguine.webpms.model.clsBillDiscountHdModel;

@Service("clsBillDiscountService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsBillDiscountServiceImpl implements clsBillDiscountService {
	@Autowired
	private clsBillDiscountDao objBillDiscountDao;

	@Override
	public void funAddUpdateBillDiscount(clsBillDiscountHdModel objMaster) {
		objBillDiscountDao.funAddUpdateBillDiscount(objMaster);
	}

	@Override
	public clsBillDiscountHdModel funGetBillDiscount(String docCode, String clientCode) {
		return objBillDiscountDao.funGetBillDiscount(docCode, clientCode);
	}

	@Override
	public boolean funDeleteBillDiscount(String docCode, String clientCode) {
		return objBillDiscountDao.funDeleteBillDiscount(docCode, clientCode);
	}

}
