package com.sanguine.excise.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseLocationMasterDao;
import com.sanguine.excise.model.clsExciseLocationMasterModel;

@Service("objClsExciseLocationMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsExciseLocationMasterServiceImpl implements clsExciseLocationMasterService {
	@Autowired
	private clsExciseLocationMasterDao objLocationMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsExciseLocationMasterModel> funGetList() {
		return objLocationMasterDao.funGetList();
	}

	public clsExciseLocationMasterModel funGetObject(String code, String clientCode) {
		return objLocationMasterDao.funGetObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean funAddUpdate(clsExciseLocationMasterModel objModel) {
		return objLocationMasterDao.funAddUpdate(objModel);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objLocationMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsExciseLocationMasterModel> funGetdtlList(String locCode, String clientCode) {
		return objLocationMasterDao.funGetdtlList(locCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetLocMapPropertyWise(String propertyCode, String clientCode) {
		// TODO Auto-generated method stub
		return objLocationMasterDao.funGetLocMapPropertyWise(propertyCode, clientCode);
	}

}
