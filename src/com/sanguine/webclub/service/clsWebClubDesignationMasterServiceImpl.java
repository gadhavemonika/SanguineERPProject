package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubDesignationMasterDao;
import com.sanguine.webclub.model.clsWebClubDesignationMasterModel;

@Service("clsWebClubDesignationMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubDesignationMasterServiceImpl implements clsWebClubDesignationMasterService {
	@Autowired
	private clsWebClubDesignationMasterDao objWebClubDesignationMasterDao;

	@Override
	public void funAddUpdateWebClubDesignationMaster(clsWebClubDesignationMasterModel objMaster) {
		objWebClubDesignationMasterDao.funAddUpdateWebClubDesignationMaster(objMaster);
	}

	@Override
	public clsWebClubDesignationMasterModel funGetWebClubDesignationMaster(String docCode, String clientCode) {
		return objWebClubDesignationMasterDao.funGetWebClubDesignationMaster(docCode, clientCode);
	}

}
