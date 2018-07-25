package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsAgentMasterHdModel;
import com.sanguine.webpms.model.clsAgentMasterModel_ID;

@Repository("clsAgentMasterDao")
public class clsAgentMasterDaoImpl implements clsAgentMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateAgentMaster(clsAgentMasterHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsAgentMasterHdModel funGetAgentMaster(String docCode, String clientCode) {
		return (clsAgentMasterHdModel) webPMSSessionFactory.getCurrentSession().get(clsAgentMasterHdModel.class, new clsAgentMasterModel_ID(docCode, clientCode));
	}

}
