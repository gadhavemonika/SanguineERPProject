package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsAgentCommisionHdModel;
import com.sanguine.webpms.model.clsAgentCommisionModel_ID;

@Repository("clsAgentCommisionDao")
public class clsAgentCommisionDaoImpl implements clsAgentCommisionDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateAgentCommision(clsAgentCommisionHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsAgentCommisionHdModel funGetAgentCommision(String docCode, String clientCode) {
		return (clsAgentCommisionHdModel) webPMSSessionFactory.getCurrentSession().get(clsAgentCommisionHdModel.class, new clsAgentCommisionModel_ID(docCode, clientCode));
	}

}
