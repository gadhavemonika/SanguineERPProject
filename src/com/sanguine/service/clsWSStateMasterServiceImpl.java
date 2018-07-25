package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.dao.clsWSStateMasterDao;
import com.sanguine.model.clsWSStateMasterModel;

@Service("clsWSStateMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsWSStateMasterServiceImpl implements clsWSStateMasterService {
	@Autowired
	private clsWSStateMasterDao objWSStateMasterDao;

	@Override
	public void funAddUpdateWSStateMaster(clsWSStateMasterModel objMaster) {
		objWSStateMasterDao.funAddUpdateWSStateMaster(objMaster);
	}

	@Override
	public clsWSStateMasterModel funGetWSStateMaster(String docCode, String clientCode) {
		return objWSStateMasterDao.funGetWSStateMaster(docCode, clientCode);
	}

}
