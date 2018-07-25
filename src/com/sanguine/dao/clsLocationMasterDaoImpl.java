package com.sanguine.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsLocationMasterModel_ID;
import com.sanguine.model.clsProductReOrderLevelModel;

@Repository("clsLocationMasterDao")
public class clsLocationMasterDaoImpl implements clsLocationMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsLocationMasterModel> funGetList() {
		return (List<clsLocationMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsLocationMasterModel.class).list();
	}

	public clsLocationMasterModel funGetObject(String code, String clientCode) {
		return (clsLocationMasterModel) sessionFactory.getCurrentSession().get(clsLocationMasterModel.class, new clsLocationMasterModel_ID(code, clientCode));
	}

	public void funAddUpdate(clsLocationMasterModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
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

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetdtlList(String prodCode, String clientCode) {
		String hql = "select b.strLocCode,b.strLocName,a.dblReOrderLevel,a.dblReOrderQty from tbllocationmaster b" + " left outer join tblreorderlevel a on b.strLocCode=a.strLocationCode and b.strClientCode=:clientCode" + " where a.strProdCode=:prodCode and a.strClientCode=:clientCode union all " + " select strLocCode,strLocName,0.00,0.00  from tbllocationmaster "
				+ " where strLocCode not in(select strLocationCode from tblreorderlevel where strProdCode=:prodCode) and strClientCode=:clientCode";

		// String
		// oldQuery="from clsLocationMasterModel where strLocCode not in(select strLocationCode from clsProductReOrderLevelModel where strProdCode=:prodCode) and strClientCode=:clientCode ";

		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		query.setParameter("clientCode", clientCode);
		query.setParameter("prodCode", prodCode);
		List list = query.list();
		return list;
	}

	@Override
	public Map<String, String> funGetLocMapPropertyWise(String propertyCode, String clientCode, String usercode) {
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

			String sql = " select a.strLocCode,b.strLocName ,a.strModule from tbluserlocdtl a ,tbllocationmaster b " + " where a.strLocCode=b.strLocCode  " + " and a.strUserCode=:usercode  " + " and a.strPropertyCode=:propertyCode " + " and a.strPropertyCode=b.strPropertyCode and " + " a.strClientCode=:clientCode and " + " b.strClientCode=:clientCode ";
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameter("clientCode", clientCode);
			query.setParameter("propertyCode", propertyCode);
			query.setParameter("usercode", usercode);
			List listLoc = query.list();
			for (Object ob : listLoc) {
				Object[] arrob = (Object[]) ob;
				map.put(arrob[0].toString(), arrob[1].toString());

			}
			if (map.isEmpty()) {
				map.put("", "");
			}
		}
		return map;
	}

	@Override
	public void funAddUpdateProductReOrderLevel(List<clsProductReOrderLevelModel> ProductReOrderLevelModel, String strLocationCode, String strClientCode) {
		Session session = sessionFactory.getCurrentSession();
		for (int i = 0; i < ProductReOrderLevelModel.size(); i++) {
			clsProductReOrderLevelModel Model = ProductReOrderLevelModel.get(i);
			if (Model.getStrProdCode() != null) {
				Model.setStrLocationCode(strLocationCode);
				Model.setStrClientCode(strClientCode);
				session.saveOrUpdate(Model);
				if (i % 20 == 0)// 20, same as the JDBC batch size
				{
					// flush a batch of inserts and release memory
					session.flush();
					session.clear();
				}
			}
		}
	}

	public List<clsLocationMasterModel> funLoadLocationPropertyWise(String PropertyCode, String ClientCode) {

		// List<clsLocationMasterModel>
		// sessionFactory.getCurrentSession().createCriteria(clsLocationMasterModel.class).list();
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsLocationMasterModel.class);
		cr.add(Restrictions.eq("strClientCode", ClientCode));
		cr.add(Restrictions.eq("strPropertyCode", PropertyCode));
		List list = cr.list();
		List<clsLocationMasterModel> objListModel = new ArrayList<clsLocationMasterModel>();
		if (list.size() > 0) {
			clsLocationMasterModel obj = null;
			for (int i = 0; i < list.size(); i++) {
				obj = (clsLocationMasterModel) list.get(i);
				objListModel.add(obj);
			}
		}
		return objListModel;

	}

}
