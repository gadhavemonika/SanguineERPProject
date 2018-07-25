package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubProfileMasterDao;

import com.sanguine.webclub.model.clsWebClubProfileMasterModel;

@Service("clsWebClubProfileMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubProfileMasterServiceImpl implements clsWebClubProfileMasterService {

	@Autowired
	private clsWebClubProfileMasterDao objWebClubProfileMasterDao;

	@Override
	public void funAddUpdateWebClubProfileMaster(clsWebClubProfileMasterModel objMaster) {

		objWebClubProfileMasterDao.funAddUpdateWebClubProfileMaster(objMaster);
	}

	@Override
	public clsWebClubProfileMasterModel funGetWebClubProfileMaster(String docCode, String clientCode) {

		return objWebClubProfileMasterDao.funGetWebClubProfileMaster(docCode, clientCode);
	}

}
