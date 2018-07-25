package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsWSStateMasterModel;
import com.sanguine.model.clsWSStateMasterModel_ID;

@Repository("clsWSStateMasterDao")
public class clsWSStateMasterDaoImpl implements clsWSStateMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public void funAddUpdateWSStateMaster(clsWSStateMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public clsWSStateMasterModel funGetWSStateMaster(String docCode, String clientCode) {
		return (clsWSStateMasterModel) sessionFactory.getCurrentSession().get(clsWSStateMasterModel.class, new clsWSStateMasterModel_ID(docCode, clientCode));
	}

}
