package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsAccountHolderMasterModel;
import com.sanguine.webbooks.model.clsAccountHolderMasterModel_ID;

@Repository("clsAccountHolderMasterDao")
public class clsAccountHolderMasterDaoImpl implements clsAccountHolderMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateAccountHolderMaster(clsAccountHolderMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsAccountHolderMasterModel funGetAccountHolderMaster(String docCode, String clientCode) {
		return (clsAccountHolderMasterModel) webBooksSessionFactory.getCurrentSession().get(clsAccountHolderMasterModel.class, new clsAccountHolderMasterModel_ID(docCode, clientCode));
	}

}
