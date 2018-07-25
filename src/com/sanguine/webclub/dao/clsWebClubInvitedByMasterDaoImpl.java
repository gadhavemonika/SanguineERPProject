package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel;
import com.sanguine.webclub.model.clsWebClubInvitedByMasterModel_ID;

@Repository("clsWebClubInvitedByMasterDao")
public class clsWebClubInvitedByMasterDaoImpl implements clsWebClubInvitedByMasterDao {
	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubInvitedByMaster(clsWebClubInvitedByMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubInvitedByMasterModel funGetWebClubInvitedByMaster(String docCode, String clientCode) {
		return (clsWebClubInvitedByMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubInvitedByMasterModel.class, new clsWebClubInvitedByMasterModel_ID(docCode, clientCode));
	}
}
