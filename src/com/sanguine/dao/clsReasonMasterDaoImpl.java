package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsReasonMaster;
import com.sanguine.model.clsReasonMaster_ID;

@Repository("clsReasonMasterDao")
public class clsReasonMasterDaoImpl implements clsReasonMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdateReason(clsReasonMaster reason) {
		sessionFactory.getCurrentSession().saveOrUpdate(reason);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsReasonMaster> funListReasons() {

		return (List<clsReasonMaster>) sessionFactory.getCurrentSession().createCriteria(clsReasonMaster.class).list();
	}

	@Override
	public clsReasonMaster funGetReason(String reasonCode) {

		return (clsReasonMaster) sessionFactory.getCurrentSession().get(clsReasonMaster.class, reasonCode);
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
	public clsReasonMaster funGetObject(String reasonCode, String clientCode) {

		return (clsReasonMaster) sessionFactory.getCurrentSession().get(clsReasonMaster.class, new clsReasonMaster_ID(reasonCode, clientCode));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetResonList(String clientCode) {
		Map<String, String> map = new TreeMap<String, String>();

		String hql = "select strReasonCode,strReasonName from clsReasonMaster where  strClientCode=:clientCode ORDER BY strReasonName";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("clientCode", clientCode);
		List mapLocation = query.list();
		// for(clsReasonMaster reason: mapLocation)
		// {
		for (int i = 0; i < mapLocation.size(); i++) {
			Object[] obj = (Object[]) mapLocation.get(i);
			map.put(obj[0].toString(), obj[1].toString());
		}
		return map;
	}

}
