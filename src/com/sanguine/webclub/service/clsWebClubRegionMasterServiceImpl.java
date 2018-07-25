package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubRegionMasterDao;
import com.sanguine.webclub.model.clsWebClubRegionMasterModel;

@Service("clsWebClubRegionMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubRegionMasterServiceImpl implements clsWebClubRegionMasterService {
	@Autowired
	private clsWebClubRegionMasterDao objWebClubRegionMasterDao;

	@Override
	public void funAddUpdateWebClubRegionMaster(clsWebClubRegionMasterModel objMaster) {
		objWebClubRegionMasterDao.funAddUpdateWebClubRegionMaster(objMaster);
	}

	@Override
	public clsWebClubRegionMasterModel funGetWebClubRegionMaster(String docCode, String clientCode) {
		return objWebClubRegionMasterDao.funGetWebClubRegionMaster(docCode, clientCode);
	}

}
