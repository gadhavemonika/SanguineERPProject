package com.sanguine.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsSalesPersonMasterDao;
import com.sanguine.crm.model.clsSalesPersonMasterModel;

@Service("clsSalesPersonMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsSalesPersonMasterServiceImpl implements clsSalesPersonMasterService{

	@Autowired
	private clsSalesPersonMasterDao objSalesPersonMasterDao;
	
	@Override
	public void funAddUpdateclsSalesPersonMaster(clsSalesPersonMasterModel objMaster) {
		// TODO Auto-generated method stub
		objSalesPersonMasterDao.funAddUpdateclsSalesPersonMaster(objMaster);
	}

	@Override
	public List funGetclsSalesPersonMaster(String docCode, String clientCode) {
		// TODO Auto-generated method stub
		return objSalesPersonMasterDao.funGetclsSalesPersonMaster(docCode, clientCode);
	}

	@Override
	public Map<String, String> funCurrencyListToDisplay(String clientCode) {
		// TODO Auto-generated method stub
		return objSalesPersonMasterDao.funCurrencyListToDisplay(clientCode);
	}

}
