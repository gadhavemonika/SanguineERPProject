package com.sanguine.webclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubGeneralMasterDao;
import com.sanguine.webclub.dao.clsWebClubGroupMasterDao;

@Service("clsWebClubGeneralMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubGeneralMasterServiceImpl implements clsWebClubGeneralMasterService {

	@Autowired
	private clsWebClubGeneralMasterDao objGenralMasterDao;

	public List funGetWebClubAllPaticulorMasterData(String tableName, String clientCode) {
		return objGenralMasterDao.funGetWebClubAllPaticulorMasterData(tableName, clientCode);
	}

}
