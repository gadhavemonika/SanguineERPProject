package com.sanguine.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsRouteMasterModel;
import com.sanguine.model.clsRouteMasterModel_ID;
import com.sanguine.model.clsVehicleMasterModel;

@Repository("clsRouteMasterDao")
public class clsRouteMasterDaoImpl implements clsRouteMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public void funAddUpdateRouteMaster(clsRouteMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public clsRouteMasterModel funGetRouteMaster(String docCode, String clientCode) {
		return (clsRouteMasterModel) sessionFactory.getCurrentSession().get(clsRouteMasterModel.class, new clsRouteMasterModel_ID(docCode, clientCode));
	}

	public clsRouteMasterModel funGetRouteNameMaster(String strRouteName, String clientCode) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsRouteMasterModel.class);
		cr.add(Restrictions.eq("strRouteName", strRouteName));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		@SuppressWarnings("rawtypes")
		clsRouteMasterModel listModel = (clsRouteMasterModel) cr.list().get(0);
		return listModel;

	}

}
