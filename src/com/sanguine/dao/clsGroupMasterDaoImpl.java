package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;

@Repository("clsGroupMasterDao")
public class clsGroupMasterDaoImpl implements clsGroupMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsGroupMasterModel> funListGroups(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGroupMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsGroupMasterModel> list = query.list();
		return list;
	}

	public clsGroupMasterModel funGetGroup(String groupCode, String clientCode) {
		return (clsGroupMasterModel) sessionFactory.getCurrentSession().get(clsGroupMasterModel.class, new clsGroupMasterModel_ID(groupCode, clientCode));
	}

	public void funAddGroup(clsGroupMasterModel group) {
		sessionFactory.getCurrentSession().saveOrUpdate(group);
	}

	@SuppressWarnings("finally")
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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetGroups(String clientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGroupMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsGroupMasterModel> groups = query.list();
		// List<clsGroupMasterModel>
		// groups=sessionFactory.getCurrentSession().createQuery("from clsGroupMasterModel where strClientCode=:clientCode").list();
		for (clsGroupMasterModel group : groups) {
			map.put(group.getStrGCode(), group.getStrGName());
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String groupCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsGroupMasterModel ");
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String funCheckGroupName(String GroupName, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from tblgroupmaster where strGName='" + GroupName + "' and strClientCode='" + clientCode + "'");
		List list = query.list();
		return list.get(0).toString();
	}

}
