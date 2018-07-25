package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubPreMemberProfileDao;
import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel;

@Service("clsWebClubPreMemberProfileService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubPreMemberProfileServiceImpl implements clsWebClubPreMemberProfileService {
	@Autowired
	private clsWebClubPreMemberProfileDao objWebClubPreMemberProfileDao;

	@Override
	public void funAddUpdateWebClubPreMemberProfile(clsWebClubPreMemberProfileModel objMaster) {
		objWebClubPreMemberProfileDao.funAddUpdateWebClubPreMemberProfile(objMaster);
	}

	@Override
	public clsWebClubPreMemberProfileModel funGetWebClubPreMemberProfile(String docCode, String clientCode) {
		return objWebClubPreMemberProfileDao.funGetWebClubPreMemberProfile(docCode, clientCode);
	}

}
