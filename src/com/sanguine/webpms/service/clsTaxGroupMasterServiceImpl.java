package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.dao.clsTaxGroupMasterDao;
import com.sanguine.webpms.model.clsTaxGroupMasterModel;

@Service("clsTaxGroupMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsTaxGroupMasterServiceImpl implements clsTaxGroupMasterService {
	@Autowired
	private clsTaxGroupMasterDao objTaxGroupMasterDao;

	@Override
	public void funAddUpdateTaxGroupMaster(clsTaxGroupMasterModel objMaster) {
		objTaxGroupMasterDao.funAddUpdateTaxGroupMaster(objMaster);
	}

	@Override
	public clsTaxGroupMasterModel funGetTaxGroupMaster(String taxGroupCode, String clientCode) {
		return objTaxGroupMasterDao.funGetTaxGroupMaster(taxGroupCode, clientCode);
	}
}
