package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsRouteMasterDao;
import com.sanguine.model.clsRouteMasterModel;

import org.springframework.transaction.annotation.Propagation;

@Service("clsRouteMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsRouteMasterServiceImpl implements clsRouteMasterService {
	@Autowired
	private clsRouteMasterDao objRouteMasterDao;

	@Override
	public void funAddUpdateRouteMaster(clsRouteMasterModel objMaster) {
		objRouteMasterDao.funAddUpdateRouteMaster(objMaster);
	}

	@Override
	public clsRouteMasterModel funGetRouteMaster(String docCode, String clientCode) {
		return objRouteMasterDao.funGetRouteMaster(docCode, clientCode);
	}

	@Override
	public clsRouteMasterModel funGetRouteNameMaster(String strRouteName, String clientCode) {
		return objRouteMasterDao.funGetRouteNameMaster(strRouteName, clientCode);
	}

}
