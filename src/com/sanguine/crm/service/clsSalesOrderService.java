package com.sanguine.crm.service;

import java.util.List;

import org.hibernate.Query;

import com.sanguine.crm.model.clsSalesCharModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.model.clsSalesOrderTaxDtlModel;

public interface clsSalesOrderService {

	public boolean funAddUpdate(clsSalesOrderHdModel object);

	public void funAddUpdateDtl(clsSalesOrderDtl object);

	public clsSalesOrderHdModel funGetSalesOrderHd(String soCode, String clientCode);

	public List<Object> funGetSalesOrder(String soCode, String clientCode);

	public List<Object> funGetSalesOrderDtl(String soCode, String clientCode);

	public void funDeleteDtl(String soCode, String clientCode);

	public void funUpdateSOforPO(String soCode, String clientCode);

	public List funGetMultipleSODtl(String[] soCodes, String clientCode);

	public boolean funAddUpdateSaleChar(clsSalesCharModel object);

	public void funDeleteSalesChar(String soCode, String prodCode);

	public List funGetSalesChar(String soCode, String prodCode);

	public List funGetMultipleSODtlForInvoice(String[] soCodes, String clientCode);

	public List funGetHdList(String fDate, String tDate, String clientCode);
	
	
	public int funDeleteSalesOrderTaxDtl(String strSOCode, String clientCode) ;
	
	public void funAddUpdateSoTaxDtl(clsSalesOrderTaxDtlModel objTaxDtlModel);
}
