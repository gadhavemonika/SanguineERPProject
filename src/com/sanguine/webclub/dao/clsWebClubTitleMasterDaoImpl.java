package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubTitleMasterModel;
import com.sanguine.webclub.model.clsWebClubTitleMasterModel_ID;

@Repository("clsWebClubTitleMasterDao")
public class clsWebClubTitleMasterDaoImpl implements clsWebClubTitleMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubTitleMaster(clsWebClubTitleMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubTitleMasterModel funGetWebClubTitleMaster(String docCode, String clientCode) {
		return (clsWebClubTitleMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubTitleMasterModel.class, new clsWebClubTitleMasterModel_ID(docCode, clientCode));
	}

}
