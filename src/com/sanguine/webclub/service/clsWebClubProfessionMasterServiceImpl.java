package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubProfessionMasterDao;
import com.sanguine.webclub.model.clsWebClubProfessionMasterModel;

@Service("clsWebClubProfessionMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubProfessionMasterServiceImpl implements clsWebClubProfessionMasterService {
	@Autowired
	private clsWebClubProfessionMasterDao objWebClubProfessionMasterDao;

	@Override
	public void funAddUpdateWebClubProfessionMaster(clsWebClubProfessionMasterModel objMaster) {
		objWebClubProfessionMasterDao.funAddUpdateWebClubProfessionMaster(objMaster);
	}

	@Override
	public clsWebClubProfessionMasterModel funGetWebClubProfessionMaster(String docCode, String clientCode) {
		return objWebClubProfessionMasterDao.funGetWebClubProfessionMaster(docCode, clientCode);
	}

}
