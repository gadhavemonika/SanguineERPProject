package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsTaxGroupMasterModel;
import com.sanguine.webpms.model.clsTaxGroupMasterModel_ID;

@Repository("clsTaxGroupMasterDao")
public class clsTaxGroupMasterDaoImpl implements clsTaxGroupMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateTaxGroupMaster(clsTaxGroupMasterModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsTaxGroupMasterModel funGetTaxGroupMaster(String taxGroupCode, String clientCode) {
		return (clsTaxGroupMasterModel) webPMSSessionFactory.getCurrentSession().get(clsTaxGroupMasterModel.class, new clsTaxGroupMasterModel_ID(taxGroupCode, clientCode));
	}

}
