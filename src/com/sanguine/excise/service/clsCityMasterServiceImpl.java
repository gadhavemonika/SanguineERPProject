package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsCityMasterDao;
import com.sanguine.excise.model.clsCityMasterModel;

@Service("clsCityMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsCityMasterServiceImpl implements clsCityMasterService {
	@Autowired
	private clsCityMasterDao objCityMasterDao;

	@Override
	public boolean funAddUpdateCityMaster(clsCityMasterModel objMaster) {
		return objCityMasterDao.funAddUpdateCityMaster(objMaster);
	}

	@Override
	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode) {
		return objCityMasterDao.funGetCityMaster(docCode, clientCode);
	}

	@Override
	public clsCityMasterModel funGetObject(String code, String clientCode) {
		return objCityMasterDao.funGetObject(code, clientCode);
	}

}
