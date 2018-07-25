package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubProfessionMasterModel;
import com.sanguine.webclub.model.clsWebClubProfessionMasterModel_ID;

@Repository("clsWebClubProfessionMasterDao")
public class clsWebClubProfessionMasterDaoImpl implements clsWebClubProfessionMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubProfessionMaster(clsWebClubProfessionMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubProfessionMasterModel funGetWebClubProfessionMaster(String docCode, String clientCode) {
		return (clsWebClubProfessionMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubProfessionMasterModel.class, new clsWebClubProfessionMasterModel_ID(docCode, clientCode));
	}

}
