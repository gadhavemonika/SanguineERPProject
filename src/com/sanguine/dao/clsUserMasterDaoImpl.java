package com.sanguine.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertyMaster;
import com.sanguine.model.clsUserMasterModel;
import com.sanguine.model.clsUserMasterModel_ID;

@Repository("clsUserMasterDao")
public class clsUserMasterDaoImpl implements clsUserMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdateUser(clsUserMasterModel userMaster) {

		sessionFactory.getCurrentSession().saveOrUpdate(userMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsUserMasterModel> funListUserMaster() {

		return (List<clsUserMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsUserMasterModel.class).list();
	}

	@Override
	public clsUserMasterModel funGetUser(String userCode, String clientCode) throws SQLGrammarException {
		/*
		 * clsUserMasterModel obj=null; try { obj =(clsUserMasterModel)
		 * sessionFactory.getCurrentSession().get(clsUserMasterModel.class,new
		 * clsUserMasterModel_ID(userCode,clientCode));
		 * 
		 * }catch(Exception ex) { ex.printStackTrace(); } return obj;
		 */

		return (clsUserMasterModel) sessionFactory.getCurrentSession().get(clsUserMasterModel.class, new clsUserMasterModel_ID(userCode, clientCode));

	}

	@Override
	public clsUserMasterModel funGetObject(String userCode, String clientCode) {

		// return (clsUserMasterModel)
		// sessionFactory.getCurrentSession().get(clsUserMasterModel.class, new
		// clsUserMasterModel_ID(userCode,clientCode));

		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsUserMasterModel.class);
		cr.add(Restrictions.eq("strUserCode", userCode));
		cr.add(Restrictions.eq("strClientCode", clientCode));

		List list = cr.list();

		clsUserMasterModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsUserMasterModel) list.get(0);
			objModel.getListUserLocDtlModel().size();
		}

		return objModel;
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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetProperties(String strClientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("-select-", "-select-");
		List<clsPropertyMaster> properties = sessionFactory.getCurrentSession().createQuery("from clsPropertyMaster where strClientCode='" + strClientCode + "'").list();
		for (clsPropertyMaster property : properties) {
			map.put(property.getPropertyCode(), property.getPropertyName());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetUserProperties(String strClientCode) {
		Map<String, String> map = new TreeMap<String, String>();
		// map.put("ALL","ALL");
		List<clsPropertyMaster> properties = sessionFactory.getCurrentSession().createQuery("from clsPropertyMaster where strClientCode='" + strClientCode + "'").list();
		for (clsPropertyMaster property : properties) {
			map.put(property.getPropertyCode(), property.getPropertyName());
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetUsers() {
		Map<String, String> map = new HashMap<String, String>();
		List<clsUserMasterModel> users = sessionFactory.getCurrentSession().createQuery("from clsUserMasterModel").list();
		for (clsUserMasterModel user : users) {
			map.put(user.getStrUserCode1(), user.getStrUserName1());
		}
		return map;
	}

	@Override
	public List funGetDtlList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsUserHdModel ");
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}

	@Override
	public Map<String, String> funGetLocMapPropertyNUserWise(String propertyCode, String clientCode, String usercode, HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		if (usercode.equalsIgnoreCase("Sanguine")) {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsLocationMasterModel where strClientCode=:clientCode and strPropertyCode=:propertyCode");
			query.setParameter("clientCode", clientCode);
			query.setParameter("propertyCode", propertyCode);
			@SuppressWarnings("unchecked")
			List<clsLocationMasterModel> mapLocation = query.list();
			for (clsLocationMasterModel location : mapLocation) {
				map.put(location.getStrLocCode(), location.getStrLocName());
			}
		} else {
			String selectedMoule = req.getSession().getAttribute("selectedModuleName").toString();
			selectedMoule = selectedMoule.split("-")[1];
			if (selectedMoule.equalsIgnoreCase("WebBookAR") || selectedMoule.equalsIgnoreCase("WebBookAPGL")) {
				selectedMoule = "WebBooks";
			}
			String sql = " select a.strLocCode,b.strLocName ,a.strModule from tbluserlocdtl a ,tbllocationmaster b " + " where a.strLocCode=b.strLocCode  " + " and a.strUserCode=:usercode  " + " and a.strPropertyCode=:propertyCode " + " and a.strPropertyCode=b.strPropertyCode and " + " a.strClientCode=:clientCode and " + " b.strClientCode=:clientCode ";
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameter("clientCode", clientCode);
			query.setParameter("propertyCode", propertyCode);
			query.setParameter("usercode", usercode);
			List listLoc = query.list();
			for (Object ob : listLoc) {
				Object[] arrob = (Object[]) ob;
				if (selectedMoule.contains(arrob[2].toString())) {
					map.put(arrob[0].toString(), arrob[1].toString());
				}
			}
			if (map.isEmpty()) {
				map.put("", "");
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetUserWiseProperties(List<String> propCodes, String strClientCode) {
		List<clsPropertyMaster> properties = null;
		Map<String, String> map = new TreeMap<String, String>();
		map.put("-select-", "-select-");
		for (String propCode : propCodes) {
			properties = sessionFactory.getCurrentSession().createQuery("from clsPropertyMaster where strClientCode='" + strClientCode + "' and strPropertyCode='" + propCode + "' ").list();
			for (clsPropertyMaster property : properties) {
				map.put(property.getPropertyCode(), property.getPropertyName());
			}
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> funGetUserBasedProperty(String strUserCode, String strClientCode) {

		Map<String, String> map = new TreeMap<String, String>();

		Query query = sessionFactory.getCurrentSession().createSQLQuery("select a.strPropertyCode,b.strPropertyName from tbluserlocdtl a,tblpropertymaster b  " + " where a.strPropertyCode=b.strPropertyCode and a.strUserCode=:strUserCode and a.strClientCode=:strClientCode group by a.strPropertyCode ");
		query.setParameter("strClientCode", strClientCode);
		query.setParameter("strUserCode", strUserCode);
		List listProp = query.list();
		for (Object ob : listProp) {
			Object[] arrob = (Object[]) ob;
			map.put(arrob[0].toString(), arrob[1].toString());
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> funGetUserBasedPropertyLocation(String strPropertyCode, String strUserCode, String strClientCode) {

		HashMap<String, String> map = new HashMap<String, String>();

		Query query = sessionFactory.getCurrentSession().createSQLQuery("select a.strLocCode,c.strLocName " + " from tbluserlocdtl a,tblpropertymaster b,tbllocationmaster c " + " where a.strPropertyCode=b.strPropertyCode " + " and a.strLocCode=c.strLocCode and a.strPropertyCode=:strPropertyCode  and a.strUserCode=:strUserCode " + " and a.strClientCode=:strClientCode group by a.strLocCode  ");
		query.setParameter("strClientCode", strClientCode);
		query.setParameter("strUserCode", strUserCode);
		query.setParameter("strPropertyCode", strPropertyCode);
		List listProp = query.list();
		for (Object ob : listProp) {
			Object[] arrob = (Object[]) ob;
			map.put(arrob[0].toString(), arrob[1].toString());
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> funGetUserWiseModules(String userCode, String clientCode) {
		String sql = "select strModule from tbluserlocdtl where strUserCode='" + userCode + "' and strClientCode='" + clientCode + "' group by strModule";
		List<String> listModules = new ArrayList<String>();
		List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		for (Object obj : list) {
			listModules.add(obj.toString());
		}
		return listModules;
	}

}
