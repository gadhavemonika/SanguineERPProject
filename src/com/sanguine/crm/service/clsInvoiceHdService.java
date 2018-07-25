package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsInvSettlementdtlModel;
import com.sanguine.crm.model.clsInvoiceGSTModel;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.model.clsProdSuppMasterModel;

public interface clsInvoiceHdService {

	public boolean funAddUpdateInvoiceHd(clsInvoiceHdModel objHdModel);

	public void funAddUpdateInvoiceDtl(clsInvoiceModelDtl objDtlModel);

	public clsInvoiceHdModel funGetInvoiceHd(String invCode, String clientCode);

	public void funDeleteDtl(String invCode, String clientCode);

	public List<Object> funGetInvoice(String invCode, String clientCode);

	public clsInvoiceHdModel funGetInvoiceDtl(String invCode, String clientCode);

	public void funDeleteTax(String invCode, String clientCode);

	public void funAddTaxDtl(clsInvoiceTaxDtlModel objInvoiceTaxDtl);

	public List funListSOforInvoice(String strlocCode, String dtFullFilled, String clientCode, String custCode);

	public void funDeleteHDDtl(String invCode, String clientCode);

	public List funGetHdList(String fromDate, String toDate, String clientCode);

	public boolean funAddUpdateInvoiceGST(clsInvoiceGSTModel objHdModel);

	public void funAddUpdateInvSettlementdtl(clsInvSettlementdtlModel objMaster);

	public clsInvSettlementdtlModel funGetInvSettlementdtl(String strInvNo, String strSettlementCode, String strClientCode, String dteInvDate);

	public List<clsInvSettlementdtlModel> funGetListInvSettlementdtl(String strInvCode, String strClientCode);

	public List<clsInvSettlementdtlModel> funGetListInvSettlementdtl(String strInvCode, String dteInvDate, String strClientCode);

	public List funGetObject(String SACode, String clientCode);

	public List<clsInvoiceModelDtl> funGetDtlList(String SACode, String clientCode);
	
}
