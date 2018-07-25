package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsCountryMasterDao;
import com.sanguine.webbooks.model.clsCountryMasterModel;

@Service("clsCountryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsCountryMasterServiceImpl implements clsCountryMasterService {
	@Autowired
	private clsCountryMasterDao objCountryMasterDao;

	@Override
	public void funAddUpdateCountryMaster(clsCountryMasterModel objMaster) {
		objCountryMasterDao.funAddUpdateCountryMaster(objMaster);
	}

	@Override
	public clsCountryMasterModel funGetCountryMaster(String docCode, String clientCode) {
		return objCountryMasterDao.funGetCountryMaster(docCode, clientCode);
	}

}
