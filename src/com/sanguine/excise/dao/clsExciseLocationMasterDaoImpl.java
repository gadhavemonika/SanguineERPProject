package com.sanguine.excise.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExciseLocationMasterModel;
import com.sanguine.excise.model.clsExciseLocationMasterModel_ID;

@Repository("clsExciseLocationMasterDao")
public class clsExciseLocationMasterDaoImpl implements clsExciseLocationMasterDao {
	@Autowired
	private SessionFactory exciseSessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<clsExciseLocationMasterModel> funGetList() {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = (List<clsExciseLocationMasterModel>) currentSession.createCriteria(clsExciseLocationMasterModel.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	public clsExciseLocationMasterModel funGetObject(String code, String clientCode) {
		clsExciseLocationMasterModel objModel = null;
		;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsExciseLocationMasterModel) currentSession.get(clsExciseLocationMasterModel.class, new clsExciseLocationMasterModel_ID(code, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	public boolean funAddUpdate(clsExciseLocationMasterModel objModel) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objModel);
			success = true;

		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	public long funGetLastNo(String tableName, String masterName, String columnName)

	{
		long lastNo = 0;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			List listLastNo = currentSession.createSQLQuery("select max(" + columnName + ") from " + tableName).list();
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsExciseLocationMasterModel> funGetdtlList(String locCode, String clientCode) {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseLocationMasterModel where strClientCode=:clientCode ");
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public Map<String, String> funGetLocMapPropertyWise(String propertyCode, String clientCode) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseLocationMasterModel where strClientCode=:clientCode and strPropertyCode=:propertyCode");
			query.setParameter("clientCode", clientCode);
			query.setParameter("propertyCode", propertyCode);
			@SuppressWarnings("unchecked")
			List<clsExciseLocationMasterModel> mapLocation = query.list();
			for (clsExciseLocationMasterModel location : mapLocation) {
				map.put(location.getStrLocCode(), location.getStrLocName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
