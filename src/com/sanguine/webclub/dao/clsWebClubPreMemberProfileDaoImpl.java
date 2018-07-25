package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel;
import com.sanguine.webclub.model.clsWebClubPreMemberProfileModel_ID;

@Repository("clsWebClubPreMemberProfileDao")
public class clsWebClubPreMemberProfileDaoImpl implements clsWebClubPreMemberProfileDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public void funAddUpdateWebClubPreMemberProfile(clsWebClubPreMemberProfileModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public clsWebClubPreMemberProfileModel funGetWebClubPreMemberProfile(String docCode, String clientCode) {
		return (clsWebClubPreMemberProfileModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubPreMemberProfileModel.class, new clsWebClubPreMemberProfileModel_ID(docCode, clientCode));
	}

}
