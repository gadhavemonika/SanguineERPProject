package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel;
import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel_ID;

@Repository("clsWebClubCompanyTypeMasterDao")
public class clsWebClubCompanyTypeMasterDaoImpl implements clsWebClubCompanyTypeMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubCompanyTypeMaster(clsWebClubCompanyTypeMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubCompanyTypeMasterModel funGetWebClubCompanyTypeMaster(String docCode, String clientCode) {
		return (clsWebClubCompanyTypeMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCompanyTypeMasterModel.class, new clsWebClubCompanyTypeMasterModel_ID(docCode, clientCode));
	}

}
