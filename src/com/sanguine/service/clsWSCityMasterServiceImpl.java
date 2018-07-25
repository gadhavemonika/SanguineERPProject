package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.dao.clsWSCityMasterDao;
import com.sanguine.model.clsWSCityMasterModel;

@Service("clsWSCityMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsWSCityMasterServiceImpl implements clsWSCityMasterService {
	@Autowired
	private clsWSCityMasterDao objWSCityMasterDao;

	@Override
	public void funAddUpdateWSCityMaster(clsWSCityMasterModel objMaster) {
		objWSCityMasterDao.funAddUpdateWSCityMaster(objMaster);
	}

	@Override
	public clsWSCityMasterModel funGetWSCityMaster(String docCode, String clientCode) {
		return objWSCityMasterDao.funGetWSCityMaster(docCode, clientCode);
	}

}
