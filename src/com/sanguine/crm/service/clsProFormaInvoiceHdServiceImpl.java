package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsProFormaInvoiceDao;
import com.sanguine.crm.model.clsProFormaInvoiceHdModel;

@Service("clsProFormaInvoiceHdService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsProFormaInvoiceHdServiceImpl implements clsProFormaInvoiceHdService{

	@Autowired 
	clsProFormaInvoiceDao objDao;
	@Override
	public List<Object> funGetInvoice(String invCode, String clientCode) {

		return objDao.funGetInvoice(invCode, clientCode);
	}
	
	@Override
	public boolean funAddUpdateProFormaInvoiceHd(clsProFormaInvoiceHdModel objHdModel) {
		// TODO Auto-generated method stub
		return objDao.funAddUpdateProFormaInvoiceHd(objHdModel);
		
	}

	@Override
	public clsProFormaInvoiceHdModel funGetProFormaInvoiceDtl(String invCode, String clientCode) {
		// TODO Auto-generated method stub
		return objDao.funGetProFormaInvoiceDtl(invCode,clientCode);
	}

	
}
