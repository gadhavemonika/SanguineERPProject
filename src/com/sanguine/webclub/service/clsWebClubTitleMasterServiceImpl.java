package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubTitleMasterDao;
import com.sanguine.webclub.model.clsWebClubTitleMasterModel;

@Service("clsWebClubTitleMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubTitleMasterServiceImpl implements clsWebClubTitleMasterService {

	@Autowired
	private clsWebClubTitleMasterDao objWebClubTitleMasterDao;

	@Override
	public void funAddUpdateWebClubTitleMaster(clsWebClubTitleMasterModel objMaster) {
		objWebClubTitleMasterDao.funAddUpdateWebClubTitleMaster(objMaster);

	}

	@Override
	public clsWebClubTitleMasterModel funGetWebClubTitleMaster(String docCode, String clientCode) {

		return objWebClubTitleMasterDao.funGetWebClubTitleMaster(docCode, clientCode);
	}

}
