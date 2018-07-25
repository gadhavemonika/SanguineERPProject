package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsAuthorizeUserModel;

@Repository("clsAuthorizeDao")
public class clsAuthorizeDaoImpl implements clsAuthorizeDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void funAddUpdate(clsAuthorizeUserModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void funUpdateTransLevel(String sql) {
		sessionFactory.getCurrentSession().createQuery(sql).executeUpdate();
	}
}
