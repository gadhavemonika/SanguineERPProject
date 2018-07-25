package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.dao.clsWSCountryMasterDao;
import com.sanguine.model.clsWSCountryMasterModel;

@Service("clsWSCountryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsWSCountryMasterServiceImpl implements clsWSCountryMasterService {
	@Autowired
	private clsWSCountryMasterDao objCountryMasterDao;

	@Override
	public void funAddUpdateCountryMaster(clsWSCountryMasterModel objMaster) {
		objCountryMasterDao.funAddUpdateCountryMaster(objMaster);
	}

	@Override
	public clsWSCountryMasterModel funGetCountryMaster(String docCode, String clientCode) {
		return objCountryMasterDao.funGetCountryMaster(docCode, clientCode);
	}

}
