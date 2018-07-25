package com.sanguine.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsExciseMasterModel;
import com.sanguine.model.clsExciseMasterModel_ID;

@Repository("clsExciseMasterDao")
public class clsExciseMasterDaoImpl implements clsExciseMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsExciseMasterModel> funListExcise(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsExciseMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsExciseMasterModel> list = query.list();
		return list;
	}

	public clsExciseMasterModel funGetExcise(String exciseCode, String clientCode) {
		return (clsExciseMasterModel) sessionFactory.getCurrentSession().get(clsExciseMasterModel.class, new clsExciseMasterModel_ID(exciseCode, clientCode));
	}

	public void funAddExcise(clsExciseMasterModel excise) {
		sessionFactory.getCurrentSession().saveOrUpdate(excise);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String exciseCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsExciseMasterModel ");
		List list = query.list();
		return list;
	}
}
