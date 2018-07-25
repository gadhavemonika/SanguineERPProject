package com.sanguine.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsPurchaseOrderDao;
import com.sanguine.model.clsPOTaxDtlModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.model.clsPurchaseOrderHdModel;

@Service("objPurchaseOrderService")
public class clsPurchaseOrderServiceImpl implements clsPurchaseOrderService {

	@Autowired
	clsPurchaseOrderDao objclsPurchaseOrderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePurchaseOrderHd(clsPurchaseOrderHdModel PurchaseOrderHdModel) {
		objclsPurchaseOrderDao.funAddUpdatePurchaseOrderHd(PurchaseOrderHdModel);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePurchaseOrderDtl(clsPurchaseOrderDtlModel PurchaseOrderDtlModel) {
		objclsPurchaseOrderDao.funAddUpdatePurchaseOrderDtl(PurchaseOrderDtlModel);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeletePODtl(String POCode, String clientCode) {
		objclsPurchaseOrderDao.funDeletePODtl(POCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsPurchaseOrderHdModel funGetObject(String POCode, String clientCode) {

		return objclsPurchaseOrderDao.funGetObject(POCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String POCode, String clientCode) {
		return objclsPurchaseOrderDao.funGetDtlList(POCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetPIData(String sql, String PICode, String clientCode) {
		return objclsPurchaseOrderDao.funGetPIData(sql, PICode, clientCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetHelpdataPIforPo(String clientCode, String strPropCode) {
		return objclsPurchaseOrderDao.funGetHelpdataPIforPo(clientCode, strPropCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeletePOTaxDtl(String POCode, String clientCode) {
		return objclsPurchaseOrderDao.funDeletePOTaxDtl(POCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePOTaxDtl(clsPOTaxDtlModel objModel) {
		objclsPurchaseOrderDao.funAddUpdatePOTaxDtl(objModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPurchaseOrderDtlModel> funGetPODtlList(String strPOCode, String clientCode) {
		return objclsPurchaseOrderDao.funGetPODtlList(strPOCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetHdList(String fDate, String tDate, String clientCode) {
		return objclsPurchaseOrderDao.funGetHdList(fDate, tDate, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPurchaseOrderDtlModel> funGetPODtlModelList(String POCode, String clientCode) {

		return objclsPurchaseOrderDao.funGetPODtlModelList(POCode, clientCode);
	}

}
