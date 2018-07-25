package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubLockerMasterModel;
import com.sanguine.webclub.model.clsWebClubLockerMasterModel_ID;

@Repository("clsWebClubLockerMasterDao")
public class clsWebClubLockerMasterDaoImpl implements clsWebClubLockerMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubLockerMaster(clsWebClubLockerMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubLockerMasterModel funGetWebClubLockerMaster(String docCode, String clientCode) {
		return (clsWebClubLockerMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubLockerMasterModel.class, new clsWebClubLockerMasterModel_ID(docCode, clientCode));
	}

}
