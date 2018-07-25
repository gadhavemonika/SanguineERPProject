package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsSanctionAutherityMasterDao;
import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel;

@Service("clsSanctionAutherityMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsSanctionAutherityMasterServiceImpl implements clsSanctionAutherityMasterService {
	@Autowired
	private clsSanctionAutherityMasterDao objSanctionAutherityMasterDao;

	@Override
	public void funAddUpdateSanctionAutherityMaster(clsSanctionAutherityMasterModel objMaster) {
		objSanctionAutherityMasterDao.funAddUpdateSanctionAutherityMaster(objMaster);
	}

	@Override
	public clsSanctionAutherityMasterModel funGetSanctionAutherityMaster(String docCode, String clientCode) {
		return objSanctionAutherityMasterDao.funGetSanctionAutherityMaster(docCode, clientCode);
	}

}
