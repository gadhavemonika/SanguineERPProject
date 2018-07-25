package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsCategoryMasterModel;
import com.sanguine.webbooks.model.clsCategoryMasterModel_ID;

@Repository("webBooksclsCategoryMasterDao")
public class clsCategoryMasterDaoImpl implements clsCategoryMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode) {
		return (clsCategoryMasterModel) webBooksSessionFactory.getCurrentSession().get(clsCategoryMasterModel.class, new clsCategoryMasterModel_ID(docCode, clientCode));
	}
}
