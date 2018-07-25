package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsAttributeValueMasterModel;
import com.sanguine.model.clsAttributeValueMasterModel_ID;

@Repository("clsAttributeValueMasterDao")
public class clsAttributeValueMasterDaoImpl implements clsAttributeValueMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsAttributeValueMasterModel> funGetList() {
		return (List<clsAttributeValueMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsAttributeValueMasterModel.class).list();
	}

	public clsAttributeValueMasterModel funGetObject(String code, String clientCode) {
		return (clsAttributeValueMasterModel) sessionFactory.getCurrentSession().get(clsAttributeValueMasterModel.class, new clsAttributeValueMasterModel_ID(code, clientCode));
	}

	public void funAddUpdate(clsAttributeValueMasterModel objModel) {
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
