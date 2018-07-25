package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsWebBooksReasonMasterDao;
import com.sanguine.webbooks.model.clsWebBooksReasonMasterModel;

@Service("clsWebBooksReasonMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsWebBooksReasonMasterServiceImpl implements clsWebBooksReasonMasterService {
	@Autowired
	private clsWebBooksReasonMasterDao objWebBooksReasonMasterDao;

	@Override
	public void funAddUpdateWebBooksReasonMaster(clsWebBooksReasonMasterModel objMaster) {
		objWebBooksReasonMasterDao.funAddUpdateWebBooksReasonMaster(objMaster);
	}

	@Override
	public clsWebBooksReasonMasterModel funGetWebBooksReasonMaster(String docCode, String clientCode) {
		return objWebBooksReasonMasterDao.funGetWebBooksReasonMaster(docCode, clientCode);
	}

}
