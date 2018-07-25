package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExcisePermitMasterDao;
import com.sanguine.excise.model.clsExcisePermitMasterModel;

@Service("clsPermitMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExcisePermitMasterServiceImpl implements clsExcisePermitMasterService {
	@Autowired
	private clsExcisePermitMasterDao objPermitMasterDao;

	@Override
	public boolean funAddUpdatePermitMaster(clsExcisePermitMasterModel objMaster) {
		return objPermitMasterDao.funAddUpdatePermitMaster(objMaster);
	}

	@Override
	public clsExcisePermitMasterModel funGetPermitMaster(String docCode, String clientCode) {
		return objPermitMasterDao.funGetPermitMaster(docCode, clientCode);
	}

}
