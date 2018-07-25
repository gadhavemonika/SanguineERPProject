package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubProfileMasterModel;
import com.sanguine.webclub.model.clsWebClubProfileMasterModel_ID;

@Repository("clsWebClubProfileMasterDao")
public class clsWebClubProfileMasterDaoImpl implements clsWebClubProfileMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubProfileMaster(clsWebClubProfileMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubProfileMasterModel funGetWebClubProfileMaster(String docCode, String clientCode) {
		return (clsWebClubProfileMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubProfileMasterModel.class, new clsWebClubProfileMasterModel_ID(docCode, clientCode));
	}

}
