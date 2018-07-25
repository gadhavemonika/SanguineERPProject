package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubCurrencyDetailsMasterDao;
import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel;

@Service("clsWebClubCurrencyDetailsMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubCurrencyDetailsMasterServiceImpl implements clsWebClubCurrencyDetailsMasterService {

	@Autowired
	private clsWebClubCurrencyDetailsMasterDao objWebClubCurrencyDetailsMasterDao;

	@Override
	public void funAddUpdateWebClubCurrencyDetailsMaster(clsWebClubCurrencyDetailsMasterModel objMaster) {
		objWebClubCurrencyDetailsMasterDao.funAddUpdateWebClubCurrencyDetailsMaster(objMaster);
	}

	@Override
	public clsWebClubCurrencyDetailsMasterModel funGetWebClubCurrencyDetailsMaster(String docCode, String clientCode) {
		return objWebClubCurrencyDetailsMasterDao.funGetWebClubCurrencyDetailsMaster(docCode, clientCode);
	}
}
