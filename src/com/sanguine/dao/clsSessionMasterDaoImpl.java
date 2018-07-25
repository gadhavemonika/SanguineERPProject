package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsSessionMasterModel;
import com.sanguine.model.clsSessionMasterModel_ID;

@Repository("clsSessionMasterDao")
public class clsSessionMasterDaoImpl implements clsSessionMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void funAddSession(clsSessionMasterModel session) {
		sessionFactory.getCurrentSession().saveOrUpdate(session);
	}

	public clsSessionMasterModel funGetSession(String SessionCode, String clientCode) {
		return (clsSessionMasterModel) sessionFactory.getCurrentSession().get(clsSessionMasterModel.class, new clsSessionMasterModel_ID(SessionCode, clientCode));
	}

	@SuppressWarnings("unchecked")
	public List<clsSessionMasterModel> funListSession(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsSessionMasterModel where strClientCode=:clientCode");
		query.setParameter("clientCode", clientCode);
		List<clsSessionMasterModel> list = query.list();
		return list;
	}
}
