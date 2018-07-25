package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubInvitedByMasterDao;

import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel;

@Service("clsWebClubInvitedByMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubInvitedByMasterServiceImpl implements clsWebClubInvitedByMasterService {
	@Autowired
	private clsWebClubInvitedByMasterDao objWebClubInvitedByMasterDao;

	@Override
	public void funAddUpdateWebClubInvitedByMaster(clsWebClubInvitedByMasterModel objMaster) {
		objWebClubInvitedByMasterDao.funAddUpdateWebClubInvitedByMaster(objMaster);
	}

	@Override
	public clsWebClubInvitedByMasterModel funGetWebClubInvitedByMaster(String docCode, String clientCode) {
		return objWebClubInvitedByMasterDao.funGetWebClubInvitedByMaster(docCode, clientCode);
	}

}
