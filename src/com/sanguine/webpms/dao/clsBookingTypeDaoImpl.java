package com.sanguine.webpms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBookingTypeHdModel;
import com.sanguine.webpms.model.clsBookingTypeModel_ID;

@Repository("clsBookingTypeDao")
public class clsBookingTypeDaoImpl implements clsBookingTypeDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public void funAddUpdateBookingType(clsBookingTypeHdModel objMaster) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebPMSTransactionManager")
	public clsBookingTypeHdModel funGetBookingType(String docCode, String clientCode) {
		return (clsBookingTypeHdModel) webPMSSessionFactory.getCurrentSession().get(clsBookingTypeHdModel.class, new clsBookingTypeModel_ID(docCode, clientCode));
	}

}
