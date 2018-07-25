package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubGroupMasterModel;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel_ID;

@Repository("clsWebClubGroupMasterDao")
public class clsWebClubGroupMasterDaoImpl implements clsWebClubGroupMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddGroup(clsWebClubGroupMasterModel group) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(group);
	}

	public clsWebClubGroupMasterModel funGetGroup(String groupCode, String clientCode) {
		return (clsWebClubGroupMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubGroupMasterModel.class, new clsWebClubGroupMasterModel_ID(groupCode, clientCode));
	}

}
