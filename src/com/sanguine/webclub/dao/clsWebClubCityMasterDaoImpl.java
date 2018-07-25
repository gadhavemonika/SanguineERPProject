package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubCityMasterModel;
import com.sanguine.webclub.model.clsWebClubCityMasterModel_ID;

@Repository("clsWebClubCityMasterDao")
public class clsWebClubCityMasterDaoImpl implements clsWebClubCityMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubCityMaster(clsWebClubCityMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubCityMasterModel funGetWebClubCityMaster(String docCode, String clientCode) {
		return (clsWebClubCityMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCityMasterModel.class, new clsWebClubCityMasterModel_ID(docCode, clientCode));
	}

}
