package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsCountryMasterModel;
import com.sanguine.webbooks.model.clsCountryMasterModel_ID;

@Repository("clsCountryMasterDao")
public class clsCountryMasterDaoImpl implements clsCountryMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateCountryMaster(clsCountryMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsCountryMasterModel funGetCountryMaster(String docCode, String clientCode) {
		return (clsCountryMasterModel) webBooksSessionFactory.getCurrentSession().get(clsCountryMasterModel.class, new clsCountryMasterModel_ID(docCode, clientCode));
	}

}
