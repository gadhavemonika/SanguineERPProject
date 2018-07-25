package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsWSCityMasterModel;
import com.sanguine.model.clsWSCityMasterModel_ID;

@Repository("clsWSCityMasterDao")
public class clsWSCityMasterDaoImpl implements clsWSCityMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public void funAddUpdateWSCityMaster(clsWSCityMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public clsWSCityMasterModel funGetWSCityMaster(String docCode, String clientCode) {
		return (clsWSCityMasterModel) sessionFactory.getCurrentSession().get(clsWSCityMasterModel.class, new clsWSCityMasterModel_ID(docCode, clientCode));
	}

}
