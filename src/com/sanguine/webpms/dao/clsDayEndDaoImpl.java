package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsDayEndHdModel;

@Repository("clsDayEndDao")
public class clsDayEndDaoImpl implements clsDayEndDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateDayEndHd(clsDayEndHdModel objHdModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

}
