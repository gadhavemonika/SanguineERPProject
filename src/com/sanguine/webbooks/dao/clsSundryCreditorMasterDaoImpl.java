package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryCreditorMasterModel_ID;

@Repository("clsSundryCreditorMasterDao")
public class clsSundryCreditorMasterDaoImpl implements clsSundaryCreditorMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateSundryCreditorMaster(clsSundaryCreditorMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode) {
		return (clsSundaryCreditorMasterModel) webBooksSessionFactory.getCurrentSession().get(clsSundaryCreditorMasterModel.class, new clsSundryCreditorMasterModel_ID(docCode, clientCode));
	}
}
