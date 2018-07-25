package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsCategoryMasterDao;
import com.sanguine.excise.model.clsCategoryMasterModel;

@Service("clsCategoryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsCategoryMasterServiceImpl implements clsCategoryMasterService {
	@Autowired
	private clsCategoryMasterDao objCategoryMasterDao;

	@Override
	public boolean funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster) {
		return objCategoryMasterDao.funAddUpdateCategoryMaster(objMaster);
	}

	@Override
	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode) {
		// return objCategoryMasterDao.funGetCategoryMaster(docCode,clientCode);
		return objCategoryMasterDao.funGetCategoryMaster(docCode, clientCode);
	}

	@Override
	public clsCategoryMasterModel funGetObject(String code, String clientCode) {
		return objCategoryMasterDao.funGetObject(code, clientCode);
	}
}
