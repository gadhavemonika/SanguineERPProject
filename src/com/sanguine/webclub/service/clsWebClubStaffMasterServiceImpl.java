package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubStaffMasterDao;
import com.sanguine.webclub.model.clsWebClubStaffMasterModel;

@Service("clsWebClubStaffMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubStaffMasterServiceImpl implements clsWebClubStaffMasterService {

	@Autowired
	private clsWebClubStaffMasterDao objWebClubStaffMasterDao;

	@Override
	public void funAddUpdateWebClubStaffMaster(clsWebClubStaffMasterModel objMaster) {
		objWebClubStaffMasterDao.funAddUpdateWebClubStaffMaster(objMaster);
	}

	@Override
	public clsWebClubStaffMasterModel funGetWebClubStaffMaster(String docCode, String clientCode) {
		return objWebClubStaffMasterDao.funGetWebClubStaffMaster(docCode, clientCode);
	}
}
