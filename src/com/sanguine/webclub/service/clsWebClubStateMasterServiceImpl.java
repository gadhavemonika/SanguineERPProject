package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubStateMasterDao;
import com.sanguine.webclub.model.clsWebClubStateMasterModel;

@Service("clsWebClubStateMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubStateMasterServiceImpl implements clsWebClubStateMasterService {
	@Autowired
	private clsWebClubStateMasterDao objWebClubStateMasterDao;

	@Override
	public void funAddUpdateWebClubStateMaster(clsWebClubStateMasterModel objMaster) {
		objWebClubStateMasterDao.funAddUpdateWebClubStateMaster(objMaster);
	}

	@Override
	public clsWebClubStateMasterModel funGetWebClubStateMaster(String docCode, String clientCode) {
		return objWebClubStateMasterDao.funGetWebClubStateMaster(docCode, clientCode);
	}

}
