package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubMaritalStatusDao;
import com.sanguine.webclub.model.clsWebClubMaritalStatusModel;

@Service("clsWebClubMaritalStatusService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubMaritalStatusServiceImpl implements clsWebClubMaritalStatusService {
	@Autowired
	private clsWebClubMaritalStatusDao objWebClubMaritalStatusDao;

	@Override
	public void funAddUpdateWebClubMaritalStatus(clsWebClubMaritalStatusModel objMaster) {
		objWebClubMaritalStatusDao.funAddUpdateWebClubMaritalStatus(objMaster);
	}

	@Override
	public clsWebClubMaritalStatusModel funGetWebClubMaritalStatus(String docCode, String clientCode) {
		return objWebClubMaritalStatusDao.funGetWebClubMaritalStatus(docCode, clientCode);
	}

}
