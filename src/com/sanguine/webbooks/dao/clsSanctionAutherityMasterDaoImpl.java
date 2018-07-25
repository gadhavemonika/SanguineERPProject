package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel;
import com.sanguine.webbooks.model.clsSanctionAutherityMasterModel_ID;

@Repository("clsSanctionAutherityMasterDao")
public class clsSanctionAutherityMasterDaoImpl implements clsSanctionAutherityMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateSanctionAutherityMaster(clsSanctionAutherityMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsSanctionAutherityMasterModel funGetSanctionAutherityMaster(String docCode, String clientCode) {
		return (clsSanctionAutherityMasterModel) webBooksSessionFactory.getCurrentSession().get(clsSanctionAutherityMasterModel.class, new clsSanctionAutherityMasterModel_ID(docCode, clientCode));
	}

}
