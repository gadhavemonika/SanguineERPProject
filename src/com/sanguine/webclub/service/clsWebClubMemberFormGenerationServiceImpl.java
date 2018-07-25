package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubMemberFormGenerationDao;
import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel;

@Service("clsWebClubMemberFormGenerationService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubMemberFormGenerationServiceImpl implements clsWebClubMemberFormGenerationService {
	@Autowired
	private clsWebClubMemberFormGenerationDao objWebClubMemberFormGenerationDao;

	@Override
	public void funAddUpdateWebClubMemberFormGeneration(clsWebClubMemberFormGenerationModel objMaster) {
		objWebClubMemberFormGenerationDao.funAddUpdateWebClubMemberFormGeneration(objMaster);
	}

	@Override
	public clsWebClubMemberFormGenerationModel funGetWebClubMemberFormGeneration(String docCode, String clientCode) {
		return objWebClubMemberFormGenerationDao.funGetWebClubMemberFormGeneration(docCode, clientCode);
	}

}
