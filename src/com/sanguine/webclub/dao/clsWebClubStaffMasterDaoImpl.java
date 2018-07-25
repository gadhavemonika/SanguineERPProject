package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubStaffMasterModel;
import com.sanguine.webclub.model.clsWebClubStaffMasterModel_ID;

@Repository("clsWebClubStaffMasterDao")
public class clsWebClubStaffMasterDaoImpl implements clsWebClubStaffMasterDao {
	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubStaffMaster(clsWebClubStaffMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubStaffMasterModel funGetWebClubStaffMaster(String docCode, String clientCode) {
		return (clsWebClubStaffMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubStaffMasterModel.class, new clsWebClubStaffMasterModel_ID(docCode, clientCode));
	}
}
