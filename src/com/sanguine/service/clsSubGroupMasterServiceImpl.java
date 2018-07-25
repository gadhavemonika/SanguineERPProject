package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsSubGroupMasterDao;
import com.sanguine.model.clsSubGroupMasterModel;

@Service("objSubGrpMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsSubGroupMasterServiceImpl implements clsSubGroupMasterService {
	@Autowired
	private clsSubGroupMasterDao objSubGrpMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsSubGroupMasterModel> funGetList() {
		return objSubGrpMasterDao.funGetList();
	}

	public clsSubGroupMasterModel funGetObject(String code, String clientCode) {
		return objSubGrpMasterDao.funGetObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsSubGroupMasterModel objModel) {

		objSubGrpMasterDao.funAddUpdate(objModel);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objSubGrpMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetSubgroups(String GroupCode, String clientCode) {

		return objSubGrpMasterDao.funGetSubgroups(GroupCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetSubgroupsCombobox(String clientCode) {
		return objSubGrpMasterDao.funGetSubgroupsCombobox(clientCode);
	}
}
