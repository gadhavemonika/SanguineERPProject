package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsSessionMasterDao;
import com.sanguine.model.clsSessionMasterModel;
import com.sanguine.model.clsSessionMasterModel_ID;

@Service("clsSessionMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsSessionMasterServiceImpl implements clsSessionMasterService {
	@Autowired
	private clsSessionMasterDao objMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddSession(clsSessionMasterModel session) {
		objMasterDao.funAddSession(session);
	}

	public clsSessionMasterModel funGetSession(String SessionCode, String clientCode) {
		return objMasterDao.funGetSession(SessionCode, clientCode);
	}

	public List<clsSessionMasterModel> funListSession(String clientCode) {
		return objMasterDao.funListSession(clientCode);
	}

}
