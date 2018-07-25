package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsTallyLinkUpModel;

@Repository("clsTallyLinkUpDao")
public class clsTallyLinkUpDaoImpl implements clsTallyLinkUpDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int funExecute(String sql) {
		return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdate(clsTallyLinkUpModel objModel) {
		boolean flg = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objModel);
			flg = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return flg = true;
		}
	}

}
