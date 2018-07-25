package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubDesignationMasterModel;
import com.sanguine.webclub.model.clsWebClubDesignationMasterModel_ID;

@Repository("clsWebClubDesignationMasterDao")
public class clsWebClubDesignationMasterDaoImpl implements clsWebClubDesignationMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubDesignationMaster(clsWebClubDesignationMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubDesignationMasterModel funGetWebClubDesignationMaster(String docCode, String clientCode) {
		return (clsWebClubDesignationMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubDesignationMasterModel.class, new clsWebClubDesignationMasterModel_ID(docCode, clientCode));
	}

}
