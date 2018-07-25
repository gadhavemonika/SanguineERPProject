package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsRegionMasterModel;
import com.sanguine.webbooks.model.clsRegionMasterModel_ID;

@Repository("clsRegionMasterDao")
public class clsRegionMasterDaoImpl implements clsRegionMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateRegionMaster(clsRegionMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsRegionMasterModel funGetRegionMaster(String docCode, String clientCode) {
		return (clsRegionMasterModel) webBooksSessionFactory.getCurrentSession().get(clsRegionMasterModel.class, new clsRegionMasterModel_ID(docCode, clientCode));
	}

}
