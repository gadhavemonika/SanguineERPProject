package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsPropertyMaster_ID;

@Repository("clsPropertyMasterDao")
public class clsPropertyMasterDaoImpl implements clsPropertyMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddProperty(clsPropertyMaster property) {
		sessionFactory.getCurrentSession().saveOrUpdate(property);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsPropertyMaster> funListProperty(String clientCode) {

		return (List<clsPropertyMaster>) sessionFactory.getCurrentSession().createCriteria(clsPropertyMaster.class, clientCode).list();
	}

	@SuppressWarnings("finally")
	@Override
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		long lastNo = 0;
		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory.getCurrentSession().createSQLQuery("select max(" + columnName + ") from " + tableName).list();
			if (listLastNo.size() > 1) {
				lastNo = ((BigInteger) listLastNo.get(0)).longValue();
			}
			lastNo++;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	@Override
	public clsPropertyMaster getProperty(String propertyCode, String clientCode) {

		return (clsPropertyMaster) sessionFactory.getCurrentSession().get(clsPropertyMaster.class, new clsPropertyMaster_ID(propertyCode, clientCode));

	}

}
