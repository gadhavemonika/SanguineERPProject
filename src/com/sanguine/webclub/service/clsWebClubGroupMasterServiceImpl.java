package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.webclub.dao.clsWebClubGroupMasterDao;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel;

@Service("clsWebClubGroupMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubGroupMasterServiceImpl implements clsWebClubGroupMasterService {

	@Autowired
	private clsWebClubGroupMasterDao objGrpMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddGroup(clsWebClubGroupMasterModel group) {
		objGrpMasterDao.funAddGroup(group);
	}

	public clsWebClubGroupMasterModel funGetGroup(String groupCode, String clientCode) {
		return objGrpMasterDao.funGetGroup(groupCode, clientCode);
	}

}
