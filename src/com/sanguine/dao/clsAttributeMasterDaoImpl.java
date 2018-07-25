package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsAttributeMasterModel;
import com.sanguine.model.clsAttributeMasterModel_ID;

@Repository("clsAttributeMasterDao")
public class clsAttributeMasterDaoImpl implements clsAttributeMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List funGetList(String attributeCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsAttributeMasterModel ");
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

	public clsAttributeMasterModel funGetObject(String code, String clientCode) {
		return (clsAttributeMasterModel) sessionFactory.getCurrentSession().get(clsAttributeMasterModel.class, new clsAttributeMasterModel_ID(code, clientCode));
	}

	public void funAddUpdate(clsAttributeMasterModel objModel) {

		sessionFactory.getCurrentSession().saveOrUpdate(objModel);

	}

	@SuppressWarnings("finally")
	public long funGetLastNo(String tableName, String masterName, String columnName)

	{
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
}
