package com.sanguine.webbooks.service;

import java.util.List;

import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;

public interface clsWebBooksAccountMasterService {

	public void funAddUpdateWebBooksAccountMaster(clsWebBooksAccountMasterModel objMaster);

	public List funGetWebBooksAccountMaster(String accountCode, String clientCode);

	public clsWebBooksAccountMasterModel funGetAccountCodeAndName(String accountCode, String clientCode);

	public String funGetMaxAccountNo(String strGroupCode, String clientCode, String propertyCode);

	public clsWebBooksAccountMasterModel funGetAccountForNonDebtor(String accountCode, String clientCode);

	public List<clsWebBooksAccountMasterModel> funGetAccountForCashBank(String clientCode);

	public List<clsWebBooksAccountMasterModel> funGetDebtorAccountList(String clientCode);

	public List<clsWebBooksAccountMasterModel> funGetAccountForGLCode(String clientCode);
}
