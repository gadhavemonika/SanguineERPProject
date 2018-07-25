package com.sanguine.webclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubDependentMasterDao;
import com.sanguine.webclub.model.clsWebClubDependentMasterModel;

@Service("clsWebClubDependentMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubDependentMasterServiceImpl implements clsWebClubDependentMasterService {
	@Autowired
	private clsWebClubDependentMasterDao objWebClubDependentMasterDao;

	@Override
	public void funAddUpdateWebClubDependentMaster(clsWebClubDependentMasterModel objMaster) {
		objWebClubDependentMasterDao.funAddUpdateWebClubDependentMaster(objMaster);
	}

	@Override
	public clsWebClubDependentMasterModel funGetWebClubDependentMaster(String docCode, String clientCode) {
		return objWebClubDependentMasterDao.funGetWebClubDependentMaster(docCode, clientCode);
	}

	@Override
	public List funGetWebClubDependentMasterList(String docCode, String clientCode) {
		return objWebClubDependentMasterDao.funGetWebClubDependentMasterList(docCode, clientCode);
	}

}
