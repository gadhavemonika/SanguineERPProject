package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubMaritalStatusModel;
import com.sanguine.webclub.model.clsWebClubMaritalStatusModel_ID;

@Repository("clsWebClubMaritalStatusDao")
public class clsWebClubMaritalStatusDaoImpl implements clsWebClubMaritalStatusDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubMaritalStatus(clsWebClubMaritalStatusModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubMaritalStatusModel funGetWebClubMaritalStatus(String docCode, String clientCode) {
		return (clsWebClubMaritalStatusModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubMaritalStatusModel.class, new clsWebClubMaritalStatusModel_ID(docCode, clientCode));
	}

}
