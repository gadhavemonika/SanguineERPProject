package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsReasonMasterDao;
import com.sanguine.model.clsReasonMaster;

@Service("clsReasonMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsReasonMasterServiceImpl implements clsReasonMasterService {
	@Autowired
	private clsReasonMasterDao objReasonMasterDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateReason(clsReasonMaster reason) {

		objReasonMasterDao.funAddUpdateReason(reason);
	}

	@Override
	public List<clsReasonMaster> funListReasons() {
		// TODO Auto-generated method stub
		return objReasonMasterDao.funListReasons();
	}

	@Override
	public clsReasonMaster funGetReason(String reasonCode) {
		// TODO Auto-generated method stub
		return objReasonMasterDao.funGetReason(reasonCode);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objReasonMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsReasonMaster funGetObject(String code, String clientCode) {

		return objReasonMasterDao.funGetObject(code, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetResonList(String clientCode) {

		return objReasonMasterDao.funGetResonList(clientCode);
	}

}
