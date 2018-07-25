package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExcisePropertySetUpDao;
import com.sanguine.excise.model.clsExcisePropertySetUpModel;

@Service("clsExcisePropertySetUpService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExcisePropertySetUpServiceImpl implements clsExcisePropertySetUpService {
	@Autowired
	private clsExcisePropertySetUpDao objclsExcisePropertySetUpDao;

	@Override
	public boolean funAddUpdateSetUpMaster(clsExcisePropertySetUpModel objMaster) {
		return objclsExcisePropertySetUpDao.funAddUpdateSetUpMaster(objMaster);
	}

	@Override
	public clsExcisePropertySetUpModel funGetSetUpMaster(String clientCode) {
		return objclsExcisePropertySetUpDao.funGetSetUpMaster(clientCode);
	}

	@Override
	public clsExcisePropertySetUpModel funGetObject(String clientCode) {
		return objclsExcisePropertySetUpDao.funGetObject(clientCode);
	}
}
