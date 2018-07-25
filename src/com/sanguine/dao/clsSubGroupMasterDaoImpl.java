package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.model.clsSubGroupMasterModel_ID;

@Repository("clsSubGroupMasterDao")
public class clsSubGroupMasterDaoImpl implements clsSubGroupMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsSubGroupMasterModel> funGetList() {
		return (List<clsSubGroupMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsSubGroupMasterModel.class).list();
	}

	public clsSubGroupMasterModel funGetObject(String code, String clientCode) {
		return (clsSubGroupMasterModel) sessionFactory.getCurrentSession().get(clsSubGroupMasterModel.class, new clsSubGroupMasterModel_ID(code, clientCode));
	}

	public void funAddUpdate(clsSubGroupMasterModel objModel) {

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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetSubgroups(String GroupCode, String clientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from clsSubGroupMasterModel where strGCode=:GroupCode and strClientCode=:clientCode");
		query.setParameter("GroupCode", GroupCode);
		query.setParameter("clientCode", clientCode);
		List<clsSubGroupMasterModel> subGroups = query.list();
		System.out.println(subGroups);
		for (clsSubGroupMasterModel subGroup : subGroups) {
			map.put(subGroup.getStrSGCode(), subGroup.getStrSGName());
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetSubgroupsCombobox(String clientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from clsSubGroupMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsSubGroupMasterModel> subGroups = query.list();
		System.out.println(subGroups);
		for (clsSubGroupMasterModel subGroup : subGroups) {
			map.put(subGroup.getStrSGCode(), subGroup.getStrSGName());
		}
		return map;

	}

}
