package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsPurchaseReturnDao;
import com.sanguine.model.clsPurchaseReturnDtlModel;
import com.sanguine.model.clsPurchaseReturnHdModel;
import com.sanguine.model.clsPurchaseReturnTaxDtlModel;

@Service("objPurchaseReturnService")
public class clsPurchaseReturnServiceImpl implements clsPurchaseReturnService {
	@Autowired
	private clsPurchaseReturnDao objPurchaseReturnDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objPurchaseReturnDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddPRHd(clsPurchaseReturnHdModel PRHd) {
		objPurchaseReturnDao.funAddPRHd(PRHd);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePRDtl(clsPurchaseReturnDtlModel PRDtl) {
		objPurchaseReturnDao.funAddUpdatePRDtl(PRDtl);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPurchaseReturnHdModel> funGetList() {

		return objPurchaseReturnDao.funGetList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsPurchaseReturnHdModel funGetObject(String code, String strClientCode) {

		return objPurchaseReturnDao.funGetObject(code, strClientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String PRCode, String clientCode) {

		return objPurchaseReturnDao.funGetDtlList(PRCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String PRCode, String clientCode) {
		objPurchaseReturnDao.funDeleteDtl(PRCode, clientCode);

	}

	@Override
	public void funAddUpdatePRTaxDtl(clsPurchaseReturnTaxDtlModel objTaxDtlModel) {
		objPurchaseReturnDao.funAddUpdatePRTaxDtl(objTaxDtlModel);
	}

	@Override
	public int funDeletePRTaxDtl(String PRCode, String clientCode) {
		
		return objPurchaseReturnDao.funDeletePRTaxDtl(PRCode,clientCode);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetGRNDtlList(String GrnCode, String clientCode) {

		return objPurchaseReturnDao.funGetGRNDtlList(GrnCode, clientCode);
	}


}
