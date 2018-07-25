package com.sanguine.webbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsSundryDebtorMasterDao;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;

@Service("clsSundryDebtorMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsSundryDebtorMasterServiceImpl implements clsSundryDebtorMasterService {
	@Autowired
	private clsSundryDebtorMasterDao objSundryDebtorMasterDao;

	@Override
	public void funAddUpdateSundryDebtorMaster(clsSundryDebtorMasterModel objMaster) {
		objSundryDebtorMasterDao.funAddUpdateSundryDebtorMaster(objMaster);
	}

	@Override
	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode) {
		return objSundryDebtorMasterDao.funGetSundryCreditorMaster(docCode, clientCode);
	}

	@Override
	public clsSundryDebtorMasterModel funGetSundryDebtorMaster(String docCode, String clientCode) {
		return objSundryDebtorMasterDao.funGetSundryDebtorMaster(docCode, clientCode);
	}

}
