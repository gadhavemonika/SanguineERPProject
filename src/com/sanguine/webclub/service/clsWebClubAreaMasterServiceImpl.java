package com.sanguine.webclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.model.clsExciseLocationMasterModel;
import com.sanguine.webclub.dao.clsWebClubAreaMasterDao;
import com.sanguine.webclub.model.clsWebClubAreaMasterModel;

@Service("clsWebClubAreaMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubAreaMasterServiceImpl implements clsWebClubAreaMasterService {
	@Autowired
	private clsWebClubAreaMasterDao objWebClubAreaMasterDao;

	@Override
	public void funAddUpdateWebClubAreaMaster(clsWebClubAreaMasterModel objMaster) {
		objWebClubAreaMasterDao.funAddUpdateWebClubAreaMaster(objMaster);
	}

	@Override
	public clsWebClubAreaMasterModel funGetWebClubAreaMaster(String docCode, String clientCode) {
		return objWebClubAreaMasterDao.funGetWebClubAreaMaster(docCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetWebClubAllAreaData(String areaCode, String clientCode) {
		return objWebClubAreaMasterDao.funGetWebClubAllAreaData(areaCode, clientCode);
	}

}
