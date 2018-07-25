package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsAccountHolderMasterDao;
import com.sanguine.webbooks.model.clsAccountHolderMasterModel;

@Service("clsAccountHolderMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsAccountHolderMasterServiceImpl implements clsAccountHolderMasterService {
	@Autowired
	private clsAccountHolderMasterDao objAccountHolderMasterDao;

	@Override
	public void funAddUpdateAccountHolderMaster(clsAccountHolderMasterModel objMaster) {
		objAccountHolderMasterDao.funAddUpdateAccountHolderMaster(objMaster);
	}

	@Override
	public clsAccountHolderMasterModel funGetAccountHolderMaster(String docCode, String clientCode) {
		return objAccountHolderMasterDao.funGetAccountHolderMaster(docCode, clientCode);
	}

}
