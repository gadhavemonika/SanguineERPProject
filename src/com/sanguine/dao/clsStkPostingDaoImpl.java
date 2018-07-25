package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsStkPostingHdModel;
import com.sanguine.model.clsStkPostingHdModel_ID;

@Repository("clsStkPostingDao")
public class clsStkPostingDaoImpl implements clsStkPostingDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsStkPostingHdModel object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Override
	public void funAddUpdateDtl(clsStkPostingDtlModel object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsStkPostingHdModel> funGetList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsStkPostingHdModel where strClientCode= :clientCode");
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsStkPostingHdModel>) list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String PSCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsStkPostingHdModel a,clsLocationMasterModel b " + "where a.strPSCode= :psCode and a.strLocCode=b.strLocCode and a.strClientCode= :clientCode and b.strClientCode= :clientCode");
		query.setParameter("psCode", PSCode);
		query.setParameter("clientCode", clientCode);
		return query.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetDtlList(String PSCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsStkPostingDtlModel a,clsProductMasterModel b " + "where a.strPSCode = :psCode and a.strProdCode=b.strProdCode and a.strClientCode= :clientCode and b.strClientCode= :clientCode");
		query.setParameter("psCode", PSCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("unused")
	public void funDeleteDtl(String PSCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsStkPostingDtlModel where strPSCode = :psCode and strClientCode= :clientCode");
		query.setParameter("psCode", PSCode);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
	}

	@Override
	public clsStkPostingHdModel funGetModelObject(String strphyStkpostCode, String clientCode) {
		return (clsStkPostingHdModel) sessionFactory.getCurrentSession().get(clsStkPostingHdModel.class, new clsStkPostingHdModel_ID(strphyStkpostCode, clientCode));
	}
}
