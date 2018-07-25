package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsBillPassingDao;
import com.sanguine.model.clsBillPassDtlModel;
import com.sanguine.model.clsBillPassHdModel;
import com.sanguine.model.clsBillPassingTaxDtlModel;

@Service("objclsBillPassingService")
public class clsBillPassingServiceImpl implements clsBillPassingService {
	@Autowired
	clsBillPassingDao objBillPassingDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateBillPassingHD(clsBillPassHdModel BillPassHdModel) {

		objBillPassingDao.funAddUpdateBillPassingHD(BillPassHdModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateBillPassingDtl(clsBillPassDtlModel BillPassDtlModel) {
		objBillPassingDao.funAddUpdateBillPassingDtl(BillPassDtlModel);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteBillPassingDtlData(String billNo, String clientCode) {

		objBillPassingDao.funDeleteBillPassingDtlData(billNo, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsBillPassHdModel funGetObject(String billNo, String clientCode) {

		return objBillPassingDao.funGetObject(billNo, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsBillPassDtlModel> funGetDtlList(String billNo, String clientCode) {

		return objBillPassingDao.funGetDtlList(billNo, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteBillPassTaxDtl(String billPassNo, String clientCode) {
		objBillPassingDao.funDeleteBillPassTaxDtl(billPassNo, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateBillPassingTaxDtl(clsBillPassingTaxDtlModel objTaxDtlModel) {
		objBillPassingDao.funAddUpdateBillPassingTaxDtl(objTaxDtlModel);
	}

}
