package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBillingInstructionsHdModel;
import com.sanguine.webpms.model.clsBillingInstructionsModel_ID;

@Repository("clsBillingInstructionsDao")
public class clsBillingInstructionsDaoImpl implements clsBillingInstructionsDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateBillingInstructions(clsBillingInstructionsHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsBillingInstructionsHdModel funGetBillingInstructions(String docCode, String clientCode) {
		return (clsBillingInstructionsHdModel) webPMSSessionFactory.getCurrentSession().get(clsBillingInstructionsHdModel.class, new clsBillingInstructionsModel_ID(docCode, clientCode));
	}

}
