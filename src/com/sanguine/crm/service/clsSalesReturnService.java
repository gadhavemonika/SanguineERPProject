package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsSalesRetrunTaxModel;
import com.sanguine.crm.model.clsSalesReturnDtlModel;
import com.sanguine.crm.model.clsSalesReturnHdModel;

public interface clsSalesReturnService {

	public boolean funAddUpdateSalesReturnHd(clsSalesReturnHdModel objHdModel);

	public void funAddUpdateSalesReturnDtl(clsSalesReturnDtlModel objDtlModel);

	public clsSalesReturnHdModel funGetSalesReturnHd(String srCode, String clientCode);

	public void funDeleteDtl(String srCode, String clientCode);

	public List<Object> funGetSalesReturn(String srCode, String clientCode);

	public List<Object> funGetSalesReturnDtl(String srCode, String clientCode);

	public void funDeleteTax(String srCode, String clientCode);

	public void funAddTaxDtl(clsSalesRetrunTaxModel objInvoiceTaxDtl);
	
	public List funGetInvDtlList(String invCode, String clientCode);
}
