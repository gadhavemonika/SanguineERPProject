package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsNarrationMasterModel;
import com.sanguine.webbooks.model.clsNarrationMasterModel_ID;

@Repository("clsNarrationMasterDao")
public class clsNarrationMasterDaoImpl implements clsNarrationMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateNarrationMaster(clsNarrationMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsNarrationMasterModel funGetNarrationMaster(String docCode, String clientCode) {
		return (clsNarrationMasterModel) webBooksSessionFactory.getCurrentSession().get(clsNarrationMasterModel.class, new clsNarrationMasterModel_ID(docCode, clientCode));
	}

}
