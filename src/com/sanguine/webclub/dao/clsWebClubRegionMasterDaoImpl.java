package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubRegionMasterModel;
import com.sanguine.webclub.model.clsWebClubRegionMasterModel_ID;

@Repository("clsWebClubRegionMasterDao")
public class clsWebClubRegionMasterDaoImpl implements clsWebClubRegionMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubRegionMaster(clsWebClubRegionMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubRegionMasterModel funGetWebClubRegionMaster(String docCode, String clientCode) {
		return (clsWebClubRegionMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubRegionMasterModel.class, new clsWebClubRegionMasterModel_ID(docCode, clientCode));
	}

}
