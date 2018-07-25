package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsTCTransModel;

@Repository("clsTCTransDao")
public class clsTCTransDaoImpl implements clsTCTransDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void funAddTCTrans(clsTCTransModel objTCTrans) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTCTrans);
	}

	public List funGetTCTransList(String sql, String transCode, String clientCode, String transType) {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("transCode", transCode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("transType", transType);
		return query.list();
	}

	public int funDeleteTCTransList(String sql) {
		return sessionFactory.getCurrentSession().createQuery(sql).executeUpdate();
	}
}
