package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.dao.clsBillDao;
import com.sanguine.webpms.model.clsBillDtlModel;
import com.sanguine.webpms.model.clsBillHdModel;

@Service("clsBillService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsBillServiceImpl implements clsBillService {
	@Autowired
	private clsBillDao objBillDao;

	@Override
	public void funAddUpdateBillHd(clsBillHdModel objHdModel) {
		objBillDao.funAddUpdateBillHd(objHdModel);
	}

	public void funAddUpdateBillDtl(clsBillDtlModel objDtlModel) {
		objBillDao.funAddUpdateBillDtl(objDtlModel);
	}

	public clsBillHdModel funLoadBill(String docCode, String clientCode) {
		return objBillDao.funLoadBill(docCode, clientCode);
	}

	public List<clsBillDtlModel> funLoadBillDtl(String docCode, String clientCode) {
		return objBillDao.funLoadBillDtl(docCode, clientCode);
	}

	@Override
	public void funDeleteBill(clsBillHdModel objBillHdModel) {
		objBillDao.funDeleteBill(objBillHdModel);
	}

}
