package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsSalesReturnDao;
import com.sanguine.crm.model.clsSalesRetrunTaxModel;
import com.sanguine.crm.model.clsSalesReturnDtlModel;
import com.sanguine.crm.model.clsSalesReturnHdModel;

@Service("clsSalesReturnService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsSalesReturnServiceImpl implements clsSalesReturnService {

	@Autowired
	private clsSalesReturnDao objSalesReturnDao;

	@Override
	public boolean funAddUpdateSalesReturnHd(clsSalesReturnHdModel objHdModel) {
		return objSalesReturnDao.funAddUpdateSalesReturnHd(objHdModel);
	}

	public void funAddUpdateSalesReturnDtl(clsSalesReturnDtlModel objDtlModel) {
		objSalesReturnDao.funAddUpdateSalesReturnDtl(objDtlModel);
	}

	public clsSalesReturnHdModel funGetSalesReturnHd(String srCode, String clientCode) {
		return objSalesReturnDao.funGetSalesReturnHd(srCode, clientCode);
	}

	public void funDeleteDtl(String srCode, String clientCode) {

		objSalesReturnDao.funDeleteDtl(srCode, clientCode);
	}

	public List<Object> funGetSalesReturn(String srCode, String clientCode) {
		return objSalesReturnDao.funGetSalesReturn(srCode, clientCode);
	}

	public List<Object> funGetSalesReturnDtl(String srCode, String clientCode) {
		return objSalesReturnDao.funGetSalesReturnDtl(srCode, clientCode);

	}

	public void funDeleteTax(String srCode, String clientCode) {

		objSalesReturnDao.funDeleteTax(srCode, clientCode);
	}

	public void funAddTaxDtl(clsSalesRetrunTaxModel objTaxDtl) {
		objSalesReturnDao.funAddTaxDtl(objTaxDtl);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List funGetInvDtlList(String invCode, String clientCode) {
		return objSalesReturnDao.funGetInvDtlList(invCode, clientCode);
	}

}
