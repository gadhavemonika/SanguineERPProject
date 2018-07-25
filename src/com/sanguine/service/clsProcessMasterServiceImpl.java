package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsProcessMasterDao;
import com.sanguine.model.clsProcessMasterModel;

@Service("clsProcessMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsProcessMasterServiceImpl implements clsProcessMasterService {
	@Autowired
	private clsProcessMasterDao objProcessMasterDao;

	@Override
	public void funAddUpdateProcessMaster(clsProcessMasterModel objMaster) {
		objProcessMasterDao.funAddUpdateProcessMaster(objMaster);
	}

	@Override
	public clsProcessMasterModel funGetProcessMaster(String processMaster, String clientCode) {
		return objProcessMasterDao.funGetProcessMaster(processMaster, clientCode);
	}

}
