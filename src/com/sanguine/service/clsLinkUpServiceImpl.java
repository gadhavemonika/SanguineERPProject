package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.dao.clsLinkUpDao;
import com.sanguine.model.clsLinkUpHdModel;

@Service("clsARLinkUpService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsLinkUpServiceImpl implements clsLinkUpService {
	@Autowired
	private clsLinkUpDao objARLinkUpDao;

	@Override
	public void funAddUpdateARLinkUp(clsLinkUpHdModel objMaster) {
		objARLinkUpDao.funAddUpdateARLinkUp(objMaster);
	}

	@Override
	public clsLinkUpHdModel funGetARLinkUp(String docCode, String clientCode, String propCode, String operationType, String moduleType) {
		return objARLinkUpDao.funGetARLinkUp(docCode, clientCode, propCode, operationType, moduleType);
	}

	@Override
	public int funExecute(String sql) {
		return objARLinkUpDao.funExecute(sql);
	}

}
