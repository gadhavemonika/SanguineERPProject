package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsCorporateMasterHdModel;
import com.sanguine.webpms.model.clsCorporateMasterModel_ID;

@Repository("clsCorporateMasterDao")
public class clsCorporateMasterDaoImpl implements clsCorporateMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateCorporateMaster(clsCorporateMasterHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsCorporateMasterHdModel funGetCorporateMaster(String docCode, String clientCode) {
		return (clsCorporateMasterHdModel) webPMSSessionFactory.getCurrentSession().get(clsCorporateMasterHdModel.class, new clsCorporateMasterModel_ID(docCode, clientCode));
	}

}
