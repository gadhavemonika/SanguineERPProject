package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsBankMasterDao;
import com.sanguine.webbooks.model.clsBankMasterModel;

@Service("clsBankMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsBankMasterServiceImpl implements clsBankMasterService {
	@Autowired
	private clsBankMasterDao objBankMasterDao;

	@Override
	public void funAddUpdateBankMaster(clsBankMasterModel objMaster) {
		objBankMasterDao.funAddUpdateBankMaster(objMaster);
	}

	@Override
	public clsBankMasterModel funGetBankMaster(String docCode, String clientCode) {
		return objBankMasterDao.funGetBankMaster(docCode, clientCode);
	}

}
