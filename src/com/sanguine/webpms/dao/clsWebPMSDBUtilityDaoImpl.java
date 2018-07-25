package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("clsWebPMSDBUtilityDao")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsWebPMSDBUtilityDaoImpl implements clsWebPMSDBUtilityDao {
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Override
	public int funExecuteUpdate(String query, String queryType) {
		// TODO Auto-generated method stub
		int ret = 0;
		if (queryType.equals("hql")) {
			ret = webPMSSessionFactory.getCurrentSession().createQuery(query).executeUpdate();
		} else {
			ret = webPMSSessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
		}

		return ret;
	}

	@Override
	public List funExecuteQuery(String query, String queryType) {
		// TODO Auto-generated method stub

		List list = null;
		if (queryType.equals("hql")) {
			list = webPMSSessionFactory.getCurrentSession().createQuery(query).list();
		} else {
			list = webPMSSessionFactory.getCurrentSession().createSQLQuery(query).list();
		}
		return list;
	}

}
