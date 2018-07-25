package com.sanguine.webbooks.apgl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.apgl.dao.clsSuppGRNBillDao;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillModel;

@Service("clsSuppGRNBillService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsSuppGRNBillServiceImpl implements clsSuppGRNBillService {
	@Autowired
	private clsSuppGRNBillDao objSuppGRNBillDao;

	@Override
	public void funAddUpdateSuppGRNBill(clsSundaryCrBillModel objMaster) {
		objSuppGRNBillDao.funAddUpdateSuppGRNBill(objMaster);
	}

	@Override
	public clsSundaryCrBillModel funGetSuppGRNBill(String docCode, String clientCode) {
		return objSuppGRNBillDao.funGetSuppGRNBill(docCode, clientCode);
	}

	@Override
	public clsSundaryCrBillModel funGetSundryCriditorDtl(String docCode, String clientCode) {
		return objSuppGRNBillDao.funGetSundryCriditorDtl(docCode, clientCode);
	}

}
