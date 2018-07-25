package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubItemCategoryMasterDao;

import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel;

@Service("clsWebClubItemCategoryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubItemCategoryMasterServiceImpl implements clsWebClubItemCategoryMasterService {

	@Autowired
	private clsWebClubItemCategoryMasterDao objWebClubItemCategoryMasterDao;

	@Override
	public void funAddUpdateWebClubItemCategoryMaster(clsWebClubItemCategoryMasterModel objMaster) {
		objWebClubItemCategoryMasterDao.funAddUpdateWebClubItemCategoryMaster(objMaster);

	}

	@Override
	public clsWebClubItemCategoryMasterModel funGetWebClubItemCategoryMaster(String docCode, String clientCode) {
		return objWebClubItemCategoryMasterDao.funGetWebClubItemCategoryMaster(docCode, clientCode);
	}

}
