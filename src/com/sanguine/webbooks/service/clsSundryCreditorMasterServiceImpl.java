package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsSundaryCreditorMasterDao;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;

@Service("clsSundryCreditorMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsSundryCreditorMasterServiceImpl implements clsSundryCreditorMasterService {
	@Autowired
	private clsSundaryCreditorMasterDao objSundryCreditorMasterDao;

	@Override
	public void funAddUpdateSundryCreditorMaster(clsSundaryCreditorMasterModel objMaster) {
		objSundryCreditorMasterDao.funAddUpdateSundryCreditorMaster(objMaster);
	}

	@Override
	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode) {
		return objSundryCreditorMasterDao.funGetSundryCreditorMaster(docCode, clientCode);
	}
}
