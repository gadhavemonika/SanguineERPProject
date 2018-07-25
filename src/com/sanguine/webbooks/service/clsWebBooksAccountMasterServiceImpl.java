package com.sanguine.webbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsWebBooksAccountMasterDao;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;

@Service("clsWebBooksAccountMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsWebBooksAccountMasterServiceImpl implements clsWebBooksAccountMasterService {
	@Autowired
	private clsWebBooksAccountMasterDao objWebBooksAccountMasterDao;

	@Override
	public void funAddUpdateWebBooksAccountMaster(clsWebBooksAccountMasterModel objMaster) {
		objWebBooksAccountMasterDao.funAddUpdateWebBooksAccountMaster(objMaster);
	}

	@Override
	public List funGetWebBooksAccountMaster(String docCode, String clientCode) {
		return objWebBooksAccountMasterDao.funGetWebBooksAccountMaster(docCode, clientCode);
	}

	@Override
	public clsWebBooksAccountMasterModel funGetAccountCodeAndName(String accountCode, String clientCode) {
		return objWebBooksAccountMasterDao.funGetAccountCodeAndName(accountCode, clientCode);
	}

	@Override
	public String funGetMaxAccountNo(String strGroupCode, String clientCode, String propertyCode) {
		return objWebBooksAccountMasterDao.funGetMaxAccountNo(strGroupCode, clientCode, propertyCode);
	}

	@Override
	public clsWebBooksAccountMasterModel funGetAccountForNonDebtor(String accountCode, String clientCode) {
		return objWebBooksAccountMasterDao.funGetAccountForNonDebtor(accountCode, clientCode);
	}

	@Override
	public List<clsWebBooksAccountMasterModel> funGetAccountForCashBank(String clientCode) {
		return objWebBooksAccountMasterDao.funGetAccountForCashBank(clientCode);
	}

	@Override
	public List<clsWebBooksAccountMasterModel> funGetDebtorAccountList(String clientCode) {
		return objWebBooksAccountMasterDao.funGetDebtorAccountList(clientCode);
	}

	@Override
	public List<clsWebBooksAccountMasterModel> funGetAccountForGLCode(String clientCode) {
		return objWebBooksAccountMasterDao.funGetAccountForGLCode(clientCode);
	}
}
