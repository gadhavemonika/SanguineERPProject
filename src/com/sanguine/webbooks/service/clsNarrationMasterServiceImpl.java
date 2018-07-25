package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsNarrationMasterDao;
import com.sanguine.webbooks.model.clsNarrationMasterModel;

@Service("clsNarrationMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsNarrationMasterServiceImpl implements clsNarrationMasterService {
	@Autowired
	private clsNarrationMasterDao objNarrationMasterDao;

	@Override
	public void funAddUpdateNarrationMaster(clsNarrationMasterModel objMaster) {
		objNarrationMasterDao.funAddUpdateNarrationMaster(objMaster);
	}

	@Override
	public clsNarrationMasterModel funGetNarrationMaster(String docCode, String clientCode) {
		return objNarrationMasterDao.funGetNarrationMaster(docCode, clientCode);
	}

}
