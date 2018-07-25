package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsLinkUpHdModel;

@Repository("clsARLinkUpDao")
public class clsLinkUpDaoImpl implements clsLinkUpDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public void funAddUpdateARLinkUp(clsLinkUpHdModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "hibernateTransactionManager")
	public clsLinkUpHdModel funGetARLinkUp(String docCode, String clientCode, String propCode, String operationType, String moduleType) {
		// return (clsLinkUpHdModel)
		// sessionFactory.getCurrentSession().get(clsLinkUpHdModel.class,new
		// clsLinkUpModel_ID(docCode,clientCode, propCode,strSGType));

		clsLinkUpHdModel objLinkUpModel = null;
		String sql = "from clsLinkUpHdModel where strMasterCode=:masterCode and strClientCode=:clientCode and strPropertyCode=:propertyCode " + " and strOperationType=:operationType and strModuleType=:moduleType";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("masterCode", docCode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("propertyCode", propCode);
		query.setParameter("operationType", operationType);
		query.setParameter("moduleType", moduleType);
		List list = query.list();
		if (list.size() > 0)
			objLinkUpModel = (clsLinkUpHdModel) list.get(0);

		return objLinkUpModel;
	}

	@Override
	public int funExecute(String sql) {
		return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

}
