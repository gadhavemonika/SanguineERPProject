package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsCompanyMasterDao;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;

@Service("clsCompanyMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsCompanyMasterServiceImpl implements clsCompanyMasterService {
	@Autowired
	private clsCompanyMasterDao objCompanyMasterDao;

	@Override
	public void funAddUpdateCompanyMaster(clsWebClubCompanyMasterModel objMaster) {
		objCompanyMasterDao.funAddUpdateCompanyMaster(objMaster);
	}

	@Override
	public clsWebClubCompanyMasterModel funGetCompanyMaster(String docCode, String clientCode) {
		return objCompanyMasterDao.funGetCompanyMaster(docCode, clientCode);
	}

}
