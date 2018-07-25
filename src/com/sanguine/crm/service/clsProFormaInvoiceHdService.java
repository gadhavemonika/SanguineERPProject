package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsProFormaInvoiceHdModel;

public interface clsProFormaInvoiceHdService {

	public List<Object> funGetInvoice(String invCode, String clientCode);
	
	public boolean funAddUpdateProFormaInvoiceHd(clsProFormaInvoiceHdModel objHdModel);

	public clsProFormaInvoiceHdModel funGetProFormaInvoiceDtl(String invCode, String clientCode);

}
