package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsLicenceMasterDao;
import com.sanguine.excise.model.clsExciseLicenceMasterModel;

@Service("clsLicenceMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsLicenceMasterServiceImpl implements clsLicenceMasterService {
	@Autowired
	private clsLicenceMasterDao objLicenceMasterDao;

	@Override
	public boolean funAddUpdateLicenceMaster(clsExciseLicenceMasterModel objMaster) {
		return objLicenceMasterDao.funAddUpdateLicenceMaster(objMaster);
	}

	@Override
	public clsExciseLicenceMasterModel funGetLicenceMaster(String docCode, String propertyCode, String clientCode) {
		// return objLicenceMasterDao.funGetLicenceMaster(docCode,clientCode);
		return objLicenceMasterDao.funGetLicenceMaster(docCode, propertyCode, clientCode);
	}

	@Override
	public clsExciseLicenceMasterModel funGetObject(String code, String propertyCode, String clientCode) {
		return objLicenceMasterDao.funGetObject(code, propertyCode, clientCode);
	}
}
