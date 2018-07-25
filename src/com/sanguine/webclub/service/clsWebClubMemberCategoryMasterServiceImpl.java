package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubMemberCategoryMasterDao;
import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel;

@Service("clsWebClubMemberCategoryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubMemberCategoryMasterServiceImpl implements clsWebClubMemberCategoryMasterService {
	@Autowired
	private clsWebClubMemberCategoryMasterDao objWebClubMemberCategoryMasterDao;

	@Override
	public void funAddUpdateWebClubMemberCategoryMaster(clsWebClubMemberCategoryMasterModel objMaster) {
		objWebClubMemberCategoryMasterDao.funAddUpdateWebClubMemberCategoryMaster(objMaster);
	}

	@Override
	public clsWebClubMemberCategoryMasterModel funGetWebClubMemberCategoryMaster(String docCode, String clientCode) {
		return objWebClubMemberCategoryMasterDao.funGetWebClubMemberCategoryMaster(docCode, clientCode);
	}

}
