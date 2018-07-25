package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubSalutationMasterDao;
import com.sanguine.webclub.model.clsWebClubSalutationMasterModel;

@Service("clsWebClubSalutationMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubSalutationMasterServiceImpl implements clsWebClubSalutationMasterService {

	@Autowired
	private clsWebClubSalutationMasterDao objWebClubSalutationMasterDao;

	@Override
	public void funAddUpdateWebClubSalutationMaster(clsWebClubSalutationMasterModel objMaster) {
		objWebClubSalutationMasterDao.funAddUpdateWebClubSalutationMaster(objMaster);
	}

	@Override
	public clsWebClubSalutationMasterModel funGetWebClubSalutationMaster(String docCode, String clientCode) {
		return objWebClubSalutationMasterDao.funGetWebClubSalutationMaster(docCode, clientCode);
	}

}
