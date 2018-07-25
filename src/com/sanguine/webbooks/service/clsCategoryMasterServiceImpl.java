package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsCategoryMasterDao;
import com.sanguine.webbooks.model.clsCategoryMasterModel;

@Service("webBooksclsCategoryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsCategoryMasterServiceImpl implements clsCategoryMasterService {
	@Autowired
	@Qualifier("webBooksclsCategoryMasterDao")
	private clsCategoryMasterDao objCategoryMasterDao;

	@Override
	public void funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster) {
		objCategoryMasterDao.funAddUpdateCategoryMaster(objMaster);
	}

	@Override
	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode) {
		return objCategoryMasterDao.funGetCategoryMaster(docCode, clientCode);
	}

}
