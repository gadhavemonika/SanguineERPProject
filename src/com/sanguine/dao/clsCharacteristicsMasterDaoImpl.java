package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsCharacteristicsMasterModel;
import com.sanguine.model.clsCharacteristicsMasterModel_ID;

@Repository("clsCharacteristicsMasterDao")
public class clsCharacteristicsMasterDaoImpl implements clsCharacteristicsMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddCharacteristics(clsCharacteristicsMasterModel characteristics) {
		sessionFactory.getCurrentSession().saveOrUpdate(characteristics);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsCharacteristicsMasterModel> funListCharacteristics() {

		return (List<clsCharacteristicsMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsCharacteristicsMasterModel.class).list();
	}

	@Override
	public clsCharacteristicsMasterModel funGetCharacteristics(String characteristicsCode, String clientCode) {

		return (clsCharacteristicsMasterModel) sessionFactory.getCurrentSession().get(clsCharacteristicsMasterModel.class, new clsCharacteristicsMasterModel_ID(characteristicsCode, clientCode));
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

}
