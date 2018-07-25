package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubStateMasterModel;
import com.sanguine.webclub.model.clsWebClubStateMasterModel_ID;

@Repository("clsWebClubStateMasterDao")
public class clsWebClubStateMasterDaoImpl implements clsWebClubStateMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubStateMaster(clsWebClubStateMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubStateMasterModel funGetWebClubStateMaster(String docCode, String clientCode) {
		return (clsWebClubStateMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubStateMasterModel.class, new clsWebClubStateMasterModel_ID(docCode, clientCode));
	}

}
