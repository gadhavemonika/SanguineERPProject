package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsPOSLinkUpModel;
import com.sanguine.model.clsPOSLinkUpModel_ID;

@Repository("clsPOSLinkUpDao")
public class clsPOSLinkUpDaoImpl implements clsPOSLinkUpDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdatePOSLinkUp(clsPOSLinkUpModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsPOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode) {
		return (clsPOSLinkUpModel) sessionFactory.getCurrentSession().get(clsPOSLinkUpModel.class, new clsPOSLinkUpModel_ID(docCode, clientCode));
	}

	@Override
	public int funExecute(String sql) {
		return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

}
