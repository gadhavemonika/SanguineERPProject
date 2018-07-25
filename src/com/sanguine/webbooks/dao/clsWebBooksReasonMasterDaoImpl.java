package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsWebBooksReasonMasterModel;
import com.sanguine.webbooks.model.clsWebBooksReasonMasterModel_ID;

@Repository("clsWebBooksReasonMasterDao")
public class clsWebBooksReasonMasterDaoImpl implements clsWebBooksReasonMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateWebBooksReasonMaster(clsWebBooksReasonMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebBooksReasonMasterModel funGetWebBooksReasonMaster(String docCode, String clientCode) {
		return (clsWebBooksReasonMasterModel) webBooksSessionFactory.getCurrentSession().get(clsWebBooksReasonMasterModel.class, new clsWebBooksReasonMasterModel_ID(docCode, clientCode));
	}

}
