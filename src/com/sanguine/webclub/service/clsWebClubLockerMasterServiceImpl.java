package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubLockerMasterDao;
import com.sanguine.webclub.model.clsWebClubLockerMasterModel;

@Service("clsWebClubLockerMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubLockerMasterServiceImpl implements clsWebClubLockerMasterService {
	@Autowired
	private clsWebClubLockerMasterDao objWebClubLockerMasterDao;

	@Override
	public void funAddUpdateWebClubLockerMaster(clsWebClubLockerMasterModel objMaster) {
		objWebClubLockerMasterDao.funAddUpdateWebClubLockerMaster(objMaster);
	}

	@Override
	public clsWebClubLockerMasterModel funGetWebClubLockerMaster(String docCode, String clientCode) {
		return objWebClubLockerMasterDao.funGetWebClubLockerMaster(docCode, clientCode);
	}

}
