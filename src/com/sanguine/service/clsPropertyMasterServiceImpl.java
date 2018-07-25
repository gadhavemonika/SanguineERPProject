package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsPropertyMasterDao;
import com.sanguine.model.clsPropertyMaster;

@Service("objPropertyMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsPropertyMasterServiceImpl implements clsPropertyMasterService {

	@Autowired
	private clsPropertyMasterDao objPropertyMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddProperty(clsPropertyMaster property) {
		objPropertyMasterDao.funAddProperty(property);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPropertyMaster> funListProperty(String clientCode) {

		return objPropertyMasterDao.funListProperty(clientCode);
	}

	@Override
	public clsPropertyMaster funGetProperty(String popertyCode, String clientCode) {

		return objPropertyMasterDao.getProperty(popertyCode, clientCode);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objPropertyMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

}
