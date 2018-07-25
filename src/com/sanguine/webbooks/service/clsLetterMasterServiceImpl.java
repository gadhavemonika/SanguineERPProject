package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsLetterMasterDao;
import com.sanguine.webbooks.model.clsLetterMasterModel;

@Service("clsLetterMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsLetterMasterServiceImpl implements clsLetterMasterService {
	@Autowired
	private clsLetterMasterDao objLetterMasterDao;

	@Override
	public void funAddUpdateLetterMaster(clsLetterMasterModel objMaster) {
		objLetterMasterDao.funAddUpdateLetterMaster(objMaster);
	}

	@Override
	public clsLetterMasterModel funGetLetterMaster(String docCode, String clientCode) {
		return objLetterMasterDao.funGetLetterMaster(docCode, clientCode);
	}

}
