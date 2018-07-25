package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubRelationMasterDao;
import com.sanguine.webclub.model.clsWebClubRelationMasterModel;

@Service("clsWebClubRelationMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubRelationMasterServiceImpl implements clsWebClubRelationMasterService {

	@Autowired
	private clsWebClubRelationMasterDao objWebClubRelationMasterDao;

	@Override
	public void funAddUpdateWebClubRelationMaster(clsWebClubRelationMasterModel objMaster) {
		objWebClubRelationMasterDao.funAddUpdateWebClubRelationMaster(objMaster);
	}

	@Override
	public clsWebClubRelationMasterModel funGetWebClubRelationMaster(String docCode, String clientCode) {
		return objWebClubRelationMasterDao.funGetWebClubRelationMaster(docCode, clientCode);
	}

}
