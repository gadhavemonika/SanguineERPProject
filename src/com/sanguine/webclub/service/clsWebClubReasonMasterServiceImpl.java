package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubReasonMasterDao;
import com.sanguine.webclub.model.clsWebClubReasonMasterModel;

@Service("clsWebClubReasonMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubReasonMasterServiceImpl implements clsWebClubReasonMasterService {
	@Autowired
	private clsWebClubReasonMasterDao objWebClubReasonMasterDao;

	@Override
	public void funAddUpdateWebClubReasonMaster(clsWebClubReasonMasterModel objMaster) {
		objWebClubReasonMasterDao.funAddUpdateWebClubReasonMaster(objMaster);
	}

	@Override
	public clsWebClubReasonMasterModel funGetWebClubReasonMaster(String docCode, String clientCode) {
		return objWebClubReasonMasterDao.funGetWebClubReasonMaster(docCode, clientCode);
	}

}
