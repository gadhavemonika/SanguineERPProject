package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsProcessMasterModel;
import com.sanguine.model.clsProcessMasterModel_ID;

@Repository("clsProcessMasterDao")
public class clsProcessMasterDaoImpl implements clsProcessMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public void funAddUpdateProcessMaster(clsProcessMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public clsProcessMasterModel funGetProcessMaster(String docCode, String clientCode) {
		return (clsProcessMasterModel) sessionFactory.getCurrentSession().get(clsProcessMasterModel.class, new clsProcessMasterModel_ID(docCode, clientCode));
	}

}
