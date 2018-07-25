package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;

@Repository("clsBankMasterDao")
public class clsBankMasterDaoImpl implements clsBankMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateBankMaster(clsBankMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsBankMasterModel funGetBankMaster(String docCode, String clientCode) {
		return (clsBankMasterModel) webBooksSessionFactory.getCurrentSession().get(clsBankMasterModel.class, new clsBankMasterModel_ID(docCode, clientCode));
	}

}
