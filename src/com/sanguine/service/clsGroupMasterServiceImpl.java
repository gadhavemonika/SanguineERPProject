package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.model.clsGroupMasterModel;

@Service("objGrpMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsGroupMasterServiceImpl implements clsGroupMasterService {
	@Autowired
	private clsGroupMasterDao objGrpMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsGroupMasterModel> funListGroups(String clientCode) {
		return objGrpMasterDao.funListGroups(clientCode);
	}

	public clsGroupMasterModel funGetGroup(String groupCode, String clientCode) {
		return objGrpMasterDao.funGetGroup(groupCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddGroup(clsGroupMasterModel group) {
		objGrpMasterDao.funAddGroup(group);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objGrpMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetGroups(String clientCode) {

		return objGrpMasterDao.funGetGroups(clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetList(String groupCode, String clientCode) {
		return objGrpMasterDao.funGetList(groupCode, clientCode);
	}

	@Override
	public String funCheckGroupName(String GroupName, String clientCode) {
		return objGrpMasterDao.funCheckGroupName(GroupName, clientCode);
	}
}
