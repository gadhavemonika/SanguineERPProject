package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsLetterMasterModel;
import com.sanguine.webbooks.model.clsLetterMasterModel_ID;

@Repository("clsLetterMasterDao")
public class clsLetterMasterDaoImpl implements clsLetterMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateLetterMaster(clsLetterMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsLetterMasterModel funGetLetterMaster(String docCode, String clientCode) {
		return (clsLetterMasterModel) webBooksSessionFactory.getCurrentSession().get(clsLetterMasterModel.class, new clsLetterMasterModel_ID(docCode, clientCode));
	}

}
