package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsAreaMasterDao;
import com.sanguine.webbooks.model.clsAreaMasterModel;

@Service("clsAreaMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsAreaMasterServiceImpl implements clsAreaMasterService {
	@Autowired
	private clsAreaMasterDao objAreaMasterDao;

	@Override
	public void funAddUpdateAreaMaster(clsAreaMasterModel objMaster) {
		objAreaMasterDao.funAddUpdateAreaMaster(objMaster);
	}

	@Override
	public clsAreaMasterModel funGetAreaMaster(String docCode, String clientCode) {
		return objAreaMasterDao.funGetAreaMaster(docCode, clientCode);
	}

}
