package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsCityMasterDao;
import com.sanguine.webbooks.model.clsCityMasterModel;

@Service("WebBooksclsCityMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsCityMasterServiceImpl implements clsCityMasterService {
	@Autowired
	private clsCityMasterDao objCityMasterDao;

	@Override
	public void funAddUpdateCityMaster(clsCityMasterModel objMaster) {
		objCityMasterDao.funAddUpdateCityMaster(objMaster);
	}

	@Override
	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode) {
		return objCityMasterDao.funGetCityMaster(docCode, clientCode);
	}

}
