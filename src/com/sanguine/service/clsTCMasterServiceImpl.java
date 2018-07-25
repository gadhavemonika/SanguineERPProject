package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.dao.clsTCMasterDao;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsTCMasterModel;

@Service("objTCMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsTCMasterServiceImpl implements clsTCMasterService {
	@Autowired
	private clsTCMasterDao objTCMasterDao;

	@Override
	public clsTCMasterModel funGetTCMaster(String tcCode, String clientCode) {
		return objTCMasterDao.funGetTCMaster(tcCode, clientCode);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void funAddUpdate(clsTCMasterModel objTCMaster) {
		objTCMasterDao.funAddUpdate(objTCMaster);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<clsTCMasterModel> funGetTCMasterList(String clientCode) {
		return objTCMasterDao.funGetTCMasterList(clientCode);
	}
}
