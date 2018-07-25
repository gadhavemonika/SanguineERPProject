package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsInvoiceDao;
import com.sanguine.crm.model.clsInvoiceGSTModel;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.crm.model.clsInvSettlementdtlModel;
import com.sanguine.model.clsProdSuppMasterModel;

@Service("clsInvoiceService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsInvoiceHdServiceImpl implements clsInvoiceHdService {

	@Autowired
	private clsInvoiceDao objInvoiceDao;

	@Override
	public boolean funAddUpdateInvoiceHd(clsInvoiceHdModel objHdModel) {
		return objInvoiceDao.funAddUpdateInvoiceHd(objHdModel);
	}

	public void funAddUpdateInvoiceDtl(clsInvoiceModelDtl objDtlModel) {
		objInvoiceDao.funAddUpdateInvoiceDtl(objDtlModel);
	}

	public clsInvoiceHdModel funGetInvoiceHd(String dcCode, String clientCode) {
		return objInvoiceDao.funGetInvoiceHd(dcCode, clientCode);
	}

	public void funDeleteDtl(String soCode, String clientCode) {

		objInvoiceDao.funDeleteDtl(soCode, clientCode);
	}

	public List<Object> funGetInvoice(String dcCode, String clientCode) {
		return objInvoiceDao.funGetInvoice(dcCode, clientCode);
	}

	public clsInvoiceHdModel funGetInvoiceDtl(String invCode, String clientCode) {
		return objInvoiceDao.funGetInvoiceDtl(invCode, clientCode);

	}

	public void funDeleteTax(String invCode, String clientCode) {

		objInvoiceDao.funDeleteTax(invCode, clientCode);
	}

	public void funAddTaxDtl(clsInvoiceTaxDtlModel objInvoiceTaxDtl) {
		objInvoiceDao.funAddTaxDtl(objInvoiceTaxDtl);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funListSOforInvoice(String strlocCode, String dtFullFilled, String clientCode, String custCode) {
		return objInvoiceDao.funListSOforInvoice(strlocCode, dtFullFilled, clientCode, custCode);
	}

	public void funDeleteHDDtl(String invCode, String clientCode) {
		objInvoiceDao.funDeleteHDDtl(invCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetHdList(String fromDate, String toDate, String clientCode) {
		return objInvoiceDao.funGetHdList(fromDate, toDate, clientCode);
	}

	@Override
	public boolean funAddUpdateInvoiceGST(clsInvoiceGSTModel objHdModel) {
		return objInvoiceDao.funAddUpdateInvoiceGST(objHdModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateInvSettlementdtl(clsInvSettlementdtlModel objMaster) {
		objInvoiceDao.funAddUpdateInvSettlementdtl(objMaster);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsInvSettlementdtlModel funGetInvSettlementdtl(String strInvNo, String strSettlementCode, String strClientCode, String dteInvDate) {
		return objInvoiceDao.funGetInvSettlementdtl(strInvNo, strSettlementCode, strClientCode, dteInvDate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsInvSettlementdtlModel> funGetListInvSettlementdtl(String strInvCode, String strClientCode) {
		return objInvoiceDao.funGetListInvSettlementdtl(strInvCode, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsInvSettlementdtlModel> funGetListInvSettlementdtl(String strInvCode, String dteInvDate, String strClientCode) {
		return objInvoiceDao.funGetListInvSettlementdtl(strInvCode, dteInvDate, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetObject(String SACode, String clientCode) {
		return objInvoiceDao.funGetObject(SACode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String SACode, String clientCode) {
		return objInvoiceDao.funGetDtlList(SACode, clientCode);
	}

}
