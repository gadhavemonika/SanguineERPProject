package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubEducationMasterDao;
import com.sanguine.webclub.model.clsWebClubEducationMasterModel;

@Service("clsWebClubEducationMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubEducationMasterServiceImpl implements clsWebClubEducationMasterService {
	@Autowired
	private clsWebClubEducationMasterDao objWebClubEducationMasterDao;

	@Override
	public void funAddUpdateWebClubEducationMaster(clsWebClubEducationMasterModel objMaster) {
		objWebClubEducationMasterDao.funAddUpdateWebClubEducationMaster(objMaster);
	}

	@Override
	public clsWebClubEducationMasterModel funGetWebClubEducationMaster(String docCode, String clientCode) {
		return objWebClubEducationMasterDao.funGetWebClubEducationMaster(docCode, clientCode);
	}

}
