package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubItemCategoryMasterModel_ID;

@Repository("clsWebClubItemCategoryMasterDao")
public class clsWebClubItemCategoryMasterDaoImpl implements clsWebClubItemCategoryMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubItemCategoryMaster(clsWebClubItemCategoryMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubItemCategoryMasterModel funGetWebClubItemCategoryMaster(String docCode, String clientCode) {
		return (clsWebClubItemCategoryMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubItemCategoryMasterModel.class, new clsWebClubItemCategoryMasterModel_ID(docCode, clientCode));
	}
}
