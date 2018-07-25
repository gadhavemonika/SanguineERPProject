package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubCountryMasterModel;
import com.sanguine.webclub.model.clsWebClubCountryMasterModel_ID;

@Repository("clsWebClubCountryMasterDao")
public class clsWebClubCountryMasterDaoImpl implements clsWebClubCountryMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubCountryMaster(clsWebClubCountryMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubCountryMasterModel funGetWebClubCountryMaster(String docCode, String clientCode) {
		return (clsWebClubCountryMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCountryMasterModel.class, new clsWebClubCountryMasterModel_ID(docCode, clientCode));
	}

}
