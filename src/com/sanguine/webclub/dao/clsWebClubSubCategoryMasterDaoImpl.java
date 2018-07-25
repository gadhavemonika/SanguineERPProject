package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubSubCategoryMasterModel_ID;

@Repository("clsWebClubSubCategoryMasterDao")
public class clsWebClubSubCategoryMasterDaoImpl implements clsWebClubSubCategoryMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateSubCategoryMaster(clsWebClubSubCategoryMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubSubCategoryMasterModel funGetSubCategoryMaster(String docCode, String clientCode) {
		return (clsWebClubSubCategoryMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubSubCategoryMasterModel.class, new clsWebClubSubCategoryMasterModel_ID(docCode, clientCode));
	}

}
