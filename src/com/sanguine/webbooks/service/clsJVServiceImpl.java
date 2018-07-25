package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsJVDao;
import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;

@Service("clsJVService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsJVServiceImpl implements clsJVService {
	@Autowired
	private clsJVDao objJVDao;

	@Override
	public void funAddUpdateJVHd(clsJVHdModel objHdModel) {
		objJVDao.funAddUpdateJVHd(objHdModel);
	}

	@Override
	public void funAddUpdateJVDtl(clsJVDtlModel objDtlModel) {
		objJVDao.funAddUpdateJVDtl(objDtlModel);
	}

	@Override
	public clsJVHdModel funGetJVList(String vouchNo, String clientCode, String propertyCode) {
		return objJVDao.funGetJVList(vouchNo, clientCode, propertyCode);
	}

	@Override
	public void funDeleteJV(clsJVHdModel objJVHdModel) {
		objJVDao.funDeleteJV(objJVHdModel);
	}

	@Override
	public void funInsertJV(String sqltempDtlDr) {
		objJVDao.funInsertJV(sqltempDtlDr);

	}
}
