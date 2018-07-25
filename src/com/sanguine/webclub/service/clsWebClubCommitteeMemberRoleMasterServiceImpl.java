package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubCommitteeMemberRoleMasterDao;
import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel;

@Service("clsWebClubCommitteeMemberRoleMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubCommitteeMemberRoleMasterServiceImpl implements clsWebClubCommitteeMemberRoleMasterService {
	@Autowired
	private clsWebClubCommitteeMemberRoleMasterDao objWebClubCommitteeMemberRoleMasterDao;

	@Override
	public void funAddUpdateWebClubCommitteeMemberRoleMaster(clsWebClubCommitteeMemberRoleMasterModel objMaster) {
		objWebClubCommitteeMemberRoleMasterDao.funAddUpdateWebClubCommitteeMemberRoleMaster(objMaster);
	}

	@Override
	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleMaster(String docCode, String clientCode) {
		return objWebClubCommitteeMemberRoleMasterDao.funGetWebClubCommitteeMemberRoleMaster(docCode, clientCode);
	}

	@Override
	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleName(String roleName, String clientCode) {
		return objWebClubCommitteeMemberRoleMasterDao.funGetWebClubCommitteeMemberRoleName(roleName, clientCode);
	}

}
