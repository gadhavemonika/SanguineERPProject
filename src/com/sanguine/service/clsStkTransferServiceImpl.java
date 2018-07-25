package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsStockTransferDao;
import com.sanguine.model.clsStkTransferDtlModel;
import com.sanguine.model.clsStkTransferHdModel;

@Repository("clsStkTransferService")
public class clsStkTransferServiceImpl implements clsStkTransferService {
	@Autowired
	private clsStockTransferDao objStkTransDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsStkTransferHdModel object) {
		objStkTransDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsStkTransferDtlModel object) {
		objStkTransDao.funAddUpdateDtl(object);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetList(String clientCode) {
		return objStkTransDao.funGetList(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetObject(String SACode, String clientCode) {
		return objStkTransDao.funGetObject(SACode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String SACode, String clientCode) {
		return objStkTransDao.funGetDtlList(SACode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String SACode, String clientCode) {
		objStkTransDao.funDeleteDtl(SACode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetProdAgainstActualProduction(String strOPCode, String clientCode) {
		return objStkTransDao.funGetProdAgainstActualProduction(strOPCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsStkTransferHdModel funGetModel(String strPDCode, String clientCode) {
		return objStkTransDao.funGetModel(strPDCode, clientCode);
	}

	@Override
	public List funStkforSRDetails(String strLocFrom, String strLocTo, String strClientCode) {

		return objStkTransDao.funStkforSRDetails(strLocFrom, strLocTo, strClientCode);
	}

	
}
