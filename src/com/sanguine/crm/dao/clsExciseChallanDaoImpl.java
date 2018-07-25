package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsExciseChallanModel;

@Repository("clsExciseChallanDao")
public class clsExciseChallanDaoImpl implements clsExciseChallanDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean funAddUpdateExciseChallan(clsExciseChallanModel objMaster) {
		boolean success = false;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
			success = true;

		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsExciseChallanModel> funGetExciseChallan(String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsExciseChallanModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseChallanModel a " + "	where a.strECCode = :strECCode and a.strClientCode = :clientCode");
			query.setParameter("strECCode", code);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

}
