package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsJOAllocationDao;
import com.sanguine.crm.model.clsJOAllocationDtlModel;
import com.sanguine.crm.model.clsJOAllocationHdModel;

@Service("clsJOAllocationService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsJOAllocationServiceImpl implements clsJOAllocationService {
	@Autowired
	private clsJOAllocationDao objJADao;

	@Override
	public boolean funAddUpdateJAHd(clsJOAllocationHdModel objMaster) {
		return objJADao.funAddUpdateJAHd(objMaster);
	}

	@Override
	public clsJOAllocationHdModel funGetJAHdObject(String strJAcode, String clientCode) {
		return objJADao.funGetJAHdObject(strJAcode, clientCode);
	}

	@Override
	public void funDeleteDtl(String strJAcode, String clientCode) {
		objJADao.funDeleteDtl(strJAcode, clientCode);
	}

	@Override
	public boolean funAddUpdateJADtl(clsJOAllocationDtlModel objMaster) {
		return objJADao.funAddUpdateJADtl(objMaster);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetJAHdData(String strJAcode, String clientCode) {
		return objJADao.funGetJAHdData(strJAcode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetJADtlData(String strJAcode, String clientCode) {
		return objJADao.funGetJADtlData(strJAcode, clientCode);
	}

}
