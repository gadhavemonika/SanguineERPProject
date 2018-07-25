package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsCityMasterModel;
import com.sanguine.webbooks.model.clsCityMasterModel_ID;

@Repository("WebBooksclsCityMasterDao")
public class clsCityMasterDaoImpl implements clsCityMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateCityMaster(clsCityMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode) {
		return (clsCityMasterModel) webBooksSessionFactory.getCurrentSession().get(clsCityMasterModel.class, new clsCityMasterModel_ID(docCode, clientCode));
	}

}
