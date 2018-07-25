package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsAttributeMasterDao;
import com.sanguine.model.clsAttributeMasterModel;

@Service("objAttributeMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsAttributeMasterServiceImpl implements clsAttributeMasterService {
	@Autowired
	private clsAttributeMasterDao objAttributeMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsAttributeMasterModel> funGetList(String attCode, String clientCode) {
		return objAttributeMasterDao.funGetList(attCode, clientCode);
	}

	public clsAttributeMasterModel funGetObject(String code, String clientCode) {
		return objAttributeMasterDao.funGetObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsAttributeMasterModel objModel) {

		objAttributeMasterDao.funAddUpdate(objModel);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objAttributeMasterDao.funGetLastNo(tableName, masterName, columnName);
	}
}
