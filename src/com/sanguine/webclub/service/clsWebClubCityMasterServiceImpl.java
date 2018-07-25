package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubCityMasterDao;
import com.sanguine.webclub.model.clsWebClubCityMasterModel;

@Service("clsWebClubCityMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubCityMasterServiceImpl implements clsWebClubCityMasterService {
	@Autowired
	private clsWebClubCityMasterDao objWebClubCityMasterDao;

	@Override
	public void funAddUpdateWebClubCityMaster(clsWebClubCityMasterModel objMaster) {
		objWebClubCityMasterDao.funAddUpdateWebClubCityMaster(objMaster);
	}

	@Override
	public clsWebClubCityMasterModel funGetWebClubCityMaster(String docCode, String clientCode) {
		return objWebClubCityMasterDao.funGetWebClubCityMaster(docCode, clientCode);
	}

}
