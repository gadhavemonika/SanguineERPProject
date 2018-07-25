package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsAttributeValueMasterDao;
import com.sanguine.model.clsAttributeValueMasterModel;

@Service("objAttributeValueMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsAttributeValueMasterServiceImpl implements clsAttributeValueMasterService {
	@Autowired
	private clsAttributeValueMasterDao objAttributeValueMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsAttributeValueMasterModel> funGetList() {
		return objAttributeValueMasterDao.funGetList();
	}

	public clsAttributeValueMasterModel funGetObject(String code, String clientCode) {
		return objAttributeValueMasterDao.funGetObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsAttributeValueMasterModel objModel) {

		objAttributeValueMasterDao.funAddUpdate(objModel);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objAttributeValueMasterDao.funGetLastNo(tableName, masterName, columnName);
	}
}
