package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsParameterSetupDao;
import com.sanguine.webbooks.model.clsParameterSetupModel;

@Service("clsParameterSetupService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsParameterSetupServiceImpl implements clsParameterSetupService {
	@Autowired
	private clsParameterSetupDao objParameterSetupDao;

	@Override
	public void funAddUpdateParameterSetup(clsParameterSetupModel objMaster) {
		objParameterSetupDao.funAddUpdateParameterSetup(objMaster);
	}

	@Override
	public clsParameterSetupModel funGetParameterSetup(String docCode, String clientCode) {
		return objParameterSetupDao.funGetParameterSetup(docCode, clientCode);
	}

}
