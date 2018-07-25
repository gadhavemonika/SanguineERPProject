package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsWSCountryMasterModel_ID;
import com.sanguine.model.clsWSCountryMasterModel;

@Repository("clsWSCountryMasterDao")
public class clsWSCountryMasterDaoImpl implements clsWSCountryMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public void funAddUpdateCountryMaster(clsWSCountryMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public clsWSCountryMasterModel funGetCountryMaster(String docCode, String clientCode) {
		return (clsWSCountryMasterModel) sessionFactory.getCurrentSession().get(clsWSCountryMasterModel.class, new clsWSCountryMasterModel_ID(docCode, clientCode));
	}

}
