package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsTransactionTimeDao;
import com.sanguine.model.clsTransactionTimeModel;

@Repository("clsTransactionTimeService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsTransactionTimeServiceImpl implements clsTransactionTimeService {

	@Autowired
	private clsTransactionTimeDao objTransactionTimeDao;

	public void funAddUpdate(clsTransactionTimeModel clsTransactionTimeModel) {
		objTransactionTimeDao.funAddUpdate(clsTransactionTimeModel);

	}

	public List<clsTransactionTimeModel> funLoadTransactionTime(String strPropertyCode, String strClientCode, String strTransactionName) {
		return objTransactionTimeDao.funLoadTransactionTime(strPropertyCode, strClientCode, strTransactionName);
	}
	
	public List<clsTransactionTimeModel> funLoadTransactionTimeLocationWise(String strPropertyCode, String strClientCode, String LocCode) {
		return objTransactionTimeDao.funLoadTransactionTime(strPropertyCode, strClientCode, LocCode);
	}
	

}
