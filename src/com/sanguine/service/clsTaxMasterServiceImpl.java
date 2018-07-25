package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.dao.clsTaxMasterDao;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxSettlementMasterModel;

@Service("objTaxMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsTaxMasterServiceImpl implements clsTaxMasterService {
	@Autowired
	private clsTaxMasterDao objTaxMasterDao;

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objTaxMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsTaxHdModel object) {

		objTaxMasterDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsTaxSettlementMasterModel object) {

		objTaxMasterDao.funAddUpdateDtl(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTaxHdModel> funGetList() {
		return objTaxMasterDao.funGetList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsTaxHdModel funGetObject(String code, String clientCode) {
		// TODO Auto-generated method stub
		return objTaxMasterDao.funGetObject(code, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String taxCode, String clientCode) {
		return objTaxMasterDao.funGetDtlList(taxCode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetTaxes(String taxCode, String clientCode) {
		return objTaxMasterDao.funGetTaxes(taxCode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetSubGroupList(String clientCode) {
		return objTaxMasterDao.funGetSubGroupList(clientCode);
	}

}
