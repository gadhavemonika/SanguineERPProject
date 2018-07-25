package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsStateMasterModel;
import com.sanguine.webbooks.model.clsStateMasterModel_ID;

@Repository("clsStateMasterDao")
public class clsStateMasterDaoImpl implements clsStateMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateStateMaster(clsStateMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsStateMasterModel funGetStateMaster(String docCode, String clientCode) {
		return (clsStateMasterModel) webBooksSessionFactory.getCurrentSession().get(clsStateMasterModel.class, new clsStateMasterModel_ID(docCode, clientCode));
	}

}
