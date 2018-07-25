package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsSalesOrderHdDao;
import com.sanguine.crm.model.clsSalesCharModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.model.clsSalesOrderTaxDtlModel;

@Service("clsSalesOrderService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsSalesOrderServiceImpl implements clsSalesOrderService {

	@Autowired
	clsSalesOrderHdDao objSalesOrder;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean funAddUpdate(clsSalesOrderHdModel object) {
		return objSalesOrder.funAddUpdate(object);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsSalesOrderDtl object) {

		objSalesOrder.funAddUpdateDtl(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsSalesOrderHdModel funGetSalesOrderHd(String soCode, String clientCode) {

		return objSalesOrder.funGetSalesOrderHd(soCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Object> funGetSalesOrder(String soCode, String clientCode) {

		return objSalesOrder.funGetSalesOrder(soCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Object> funGetSalesOrderDtl(String soCode, String clientCode) {
		return objSalesOrder.funGetSalesOrderDtl(soCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String soCode, String clientCode) {
		objSalesOrder.funDeleteDtl(soCode, clientCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funUpdateSOforPO(String soCode, String clientCode) {
		objSalesOrder.funUpdateSOforPO(soCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetMultipleSODtl(String[] soCodes, String clientCode) {
		return objSalesOrder.funGetMultipleSODtl(soCodes, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean funAddUpdateSaleChar(clsSalesCharModel object) {
		return objSalesOrder.funAddUpdateSaleChar(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteSalesChar(String soCode, String prodCode) {
		objSalesOrder.funDeleteSalesChar(soCode, prodCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetSalesChar(String soCode, String prodCode) {
		return objSalesOrder.funGetSalesChar(soCode, prodCode);
	}

	public List funGetMultipleSODtlForInvoice(String[] soCodes, String clientCode) {
		return objSalesOrder.funGetMultipleSODtlForInvoice(soCodes, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetHdList(String fDate, String tDate, String clientCode) {
		return objSalesOrder.funGetHdList(fDate, tDate, clientCode);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteSalesOrderTaxDtl(String strSOCode, String clientCode){
		
		return objSalesOrder.funDeleteSalesOrderTaxDtl(strSOCode, clientCode);
	}
	
	@Override
	public void funAddUpdateSoTaxDtl(clsSalesOrderTaxDtlModel objTaxDtlModel) {
		objSalesOrder.funAddUpdateSoTaxDtl(objTaxDtlModel);
	}

}
