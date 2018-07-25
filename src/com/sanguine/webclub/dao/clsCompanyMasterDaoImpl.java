package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel_ID;

@Repository("clsCompanyMasterDao")
public class clsCompanyMasterDaoImpl implements clsCompanyMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateCompanyMaster(clsWebClubCompanyMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubCompanyMasterModel funGetCompanyMaster(String docCode, String clientCode) {
		return (clsWebClubCompanyMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCompanyMasterModel.class, new clsWebClubCompanyMasterModel_ID(docCode, clientCode));
	}

}
