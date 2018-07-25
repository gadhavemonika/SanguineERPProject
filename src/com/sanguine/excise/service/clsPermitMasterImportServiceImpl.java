package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsPermitMasterImportDao;
import com.sanguine.excise.model.clsPermitMasterModel;

@Service("clsPermitMasterImportService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsPermitMasterImportServiceImpl implements clsPermitMasterImportService {
	@Autowired
	private clsPermitMasterImportDao objMasterDataImportDao;

	@Override
	public void funAddUpdatePermitMaster(clsPermitMasterModel objMaster) {
		objMasterDataImportDao.funAddUpdatePermitMaster(objMaster);
	}

	@Override
	public clsPermitMasterModel funGetPermitMaster(String docCode, String clientCode) {
		return objMasterDataImportDao.funGetPermitMaster(docCode, clientCode);
	}

}
