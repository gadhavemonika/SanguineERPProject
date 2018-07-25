package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubReasonMasterModel;
import com.sanguine.webclub.model.clsWebClubReasonMasterModel_ID;

@Repository("clsWebClubReasonMasterDao")
public class clsWebClubReasonMasterDaoImpl implements clsWebClubReasonMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubReasonMaster(clsWebClubReasonMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubReasonMasterModel funGetWebClubReasonMaster(String docCode, String clientCode) {
		return (clsWebClubReasonMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubReasonMasterModel.class, new clsWebClubReasonMasterModel_ID(docCode, clientCode));
	}

}
