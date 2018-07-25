package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubCountryMasterDao;
import com.sanguine.webclub.model.clsWebClubCountryMasterModel;

@Service("clsWebClubCountryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubCountryMasterServiceImpl implements clsWebClubCountryMasterService {
	@Autowired
	private clsWebClubCountryMasterDao objWebClubCountryMasterDao;

	@Override
	public void funAddUpdateWebClubCountryMaster(clsWebClubCountryMasterModel objMaster) {
		objWebClubCountryMasterDao.funAddUpdateWebClubCountryMaster(objMaster);
	}

	@Override
	public clsWebClubCountryMasterModel funGetWebClubCountryMaster(String docCode, String clientCode) {
		return objWebClubCountryMasterDao.funGetWebClubCountryMaster(docCode, clientCode);
	}

}
