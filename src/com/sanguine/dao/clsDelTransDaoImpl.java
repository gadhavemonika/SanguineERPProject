package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsDeleteTransModel;

@Repository("clsDelTransDao")
public class clsDelTransDaoImpl implements clsDelTransDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void funInsertRecord(clsDeleteTransModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	public void funDeleteRecord(String sql, String queryType) {
		if (queryType.equals("sql")) {
			sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
		} else {
			sessionFactory.getCurrentSession().createQuery(sql).executeUpdate();
		}
	}

}
