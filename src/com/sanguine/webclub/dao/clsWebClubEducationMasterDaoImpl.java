package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubEducationMasterModel;
import com.sanguine.webclub.model.clsWebClubEducationMasterModel_ID;

@Repository("clsWebClubEducationMasterDao")
public class clsWebClubEducationMasterDaoImpl implements clsWebClubEducationMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubEducationMaster(clsWebClubEducationMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubEducationMasterModel funGetWebClubEducationMaster(String docCode, String clientCode) {
		return (clsWebClubEducationMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubEducationMasterModel.class, new clsWebClubEducationMasterModel_ID(docCode, clientCode));
	}

}
