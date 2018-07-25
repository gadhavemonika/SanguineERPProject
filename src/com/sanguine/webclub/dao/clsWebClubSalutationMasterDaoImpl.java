package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubSalutationMasterModel;
import com.sanguine.webclub.model.clsWebClubSalutationMasterModel_ID;

@Repository("clsWebClubSalutationMasterDao")
public class clsWebClubSalutationMasterDaoImpl implements clsWebClubSalutationMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubSalutationMaster(clsWebClubSalutationMasterModel objMaster) {

		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubSalutationMasterModel funGetWebClubSalutationMaster(String docCode, String clientCode) {

		return (clsWebClubSalutationMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubSalutationMasterModel.class, new clsWebClubSalutationMasterModel_ID(docCode, clientCode));
	}

}
