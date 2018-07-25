package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsStateMasterDao;
import com.sanguine.webbooks.model.clsStateMasterModel;

@Service("clsStateMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsStateMasterServiceImpl implements clsStateMasterService {
	@Autowired
	private clsStateMasterDao objStateMasterDao;

	@Override
	public void funAddUpdateStateMaster(clsStateMasterModel objMaster) {
		objStateMasterDao.funAddUpdateStateMaster(objMaster);
	}

	@Override
	public clsStateMasterModel funGetStateMaster(String docCode, String clientCode) {
		return objStateMasterDao.funGetStateMaster(docCode, clientCode);
	}

}
