package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubSubCategoryMasterDao;
import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel;

@Service("clsWebClubSubCategoryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubSubCategoryMasterServiceImpl implements clsWebClubSubCategoryMasterService {
	@Autowired
	private clsWebClubSubCategoryMasterDao objSubCategoryMasterDao;

	@Override
	public void funAddUpdateSubCategoryMaster(clsWebClubSubCategoryMasterModel objMaster) {
		objSubCategoryMasterDao.funAddUpdateSubCategoryMaster(objMaster);
	}

	@Override
	public clsWebClubSubCategoryMasterModel funGetSubCategoryMaster(String docCode, String clientCode) {
		return objSubCategoryMasterDao.funGetSubCategoryMaster(docCode, clientCode);
	}

}
