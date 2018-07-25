package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsAreaMasterModel;
import com.sanguine.webbooks.model.clsAreaMasterModel_ID;

@Repository("clsAreaMasterDao")
public class clsAreaMasterDaoImpl implements clsAreaMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateAreaMaster(clsAreaMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsAreaMasterModel funGetAreaMaster(String docCode, String clientCode) {
		return (clsAreaMasterModel) webBooksSessionFactory.getCurrentSession().get(clsAreaMasterModel.class, new clsAreaMasterModel_ID(docCode, clientCode));
	}

}
