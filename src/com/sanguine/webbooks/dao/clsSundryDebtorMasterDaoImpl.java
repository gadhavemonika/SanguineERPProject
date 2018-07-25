package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryCreditorMasterModel_ID;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel_ID;

@Repository("clsSundryDebtorMasterDao")
public class clsSundryDebtorMasterDaoImpl implements clsSundryDebtorMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateSundryDebtorMaster(clsSundryDebtorMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsSundryDebtorMasterModel funGetSundryDebtorMaster(String docCode, String clientCode) {
		return (clsSundryDebtorMasterModel) webBooksSessionFactory.getCurrentSession().get(clsSundryDebtorMasterModel.class, new clsSundryDebtorMasterModel_ID(docCode, clientCode));
	}

	@Override
	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode) {
		return (clsSundaryCreditorMasterModel) webBooksSessionFactory.getCurrentSession().get(clsSundaryCreditorMasterModel.class, new clsSundryCreditorMasterModel_ID(docCode, clientCode));
	}

}
