package com.sanguine.webclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubCommitteeMasterDao;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterDtl;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterModel;

@Service("clsWebClubCommitteeMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubCommitteeMasterServiceImpl implements clsWebClubCommitteeMasterService {
	@Autowired
	private clsWebClubCommitteeMasterDao objWebClubCommitteeMasterDao;

	@Override
	public void funAddUpdateWebClubCommitteeMaster(clsWebClubCommitteeMasterModel objMaster) {
		objWebClubCommitteeMasterDao.funAddUpdateWebClubCommitteeMaster(objMaster);
	}

	@Override
	public clsWebClubCommitteeMasterModel funGetWebClubCommitteeMaster(String docCode, String clientCode) {
		return objWebClubCommitteeMasterDao.funGetWebClubCommitteeMaster(docCode, clientCode);
	}

	@Override
	public void funAddUpdateCommittteeMasterDtl(clsWebClubCommitteeMasterDtl objMaster) {
		objWebClubCommitteeMasterDao.funAddUpdateCommittteeMasterDtl(objMaster);
	}

	@Override
	public List<Object> funGetWebClubCommittteeMasterDtl(String docCode, String clientCode) {
		return objWebClubCommitteeMasterDao.funGetWebClubCommittteeMasterDtl(docCode, clientCode);
	}

}
