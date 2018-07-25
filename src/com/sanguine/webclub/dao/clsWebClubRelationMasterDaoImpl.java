package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubRelationMasterModel;
import com.sanguine.webclub.model.clsWebClubRelationMasterModel_ID;

@Repository("clsWebClubRelationMasterDao")
public class clsWebClubRelationMasterDaoImpl implements clsWebClubRelationMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubRelationMaster(clsWebClubRelationMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubRelationMasterModel funGetWebClubRelationMaster(String docCode, String clientCode) {
		return (clsWebClubRelationMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubRelationMasterModel.class, new clsWebClubRelationMasterModel_ID(docCode, clientCode));
	}
}
