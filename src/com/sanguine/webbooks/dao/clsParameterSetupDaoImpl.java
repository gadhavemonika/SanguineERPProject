package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsParameterSetupModel;
import com.sanguine.webbooks.model.clsParameterSetupModel_ID;

@Repository("clsParameterSetupDao")
public class clsParameterSetupDaoImpl implements clsParameterSetupDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateParameterSetup(clsParameterSetupModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsParameterSetupModel funGetParameterSetup(String clientCode, String propertyCode) {
		return (clsParameterSetupModel) webBooksSessionFactory.getCurrentSession().get(clsParameterSetupModel.class, new clsParameterSetupModel_ID(clientCode, propertyCode));
	}

}
