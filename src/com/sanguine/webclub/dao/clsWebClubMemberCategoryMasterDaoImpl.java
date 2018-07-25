package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel;
import com.sanguine.webclub.model.clsWebClubMemberCategoryMasterModel_ID;

@Repository("clsWebClubMemberCategoryMasterDao")
public class clsWebClubMemberCategoryMasterDaoImpl implements clsWebClubMemberCategoryMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubMemberCategoryMaster(clsWebClubMemberCategoryMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubMemberCategoryMasterModel funGetWebClubMemberCategoryMaster(String docCode, String clientCode) {
		return (clsWebClubMemberCategoryMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubMemberCategoryMasterModel.class, new clsWebClubMemberCategoryMasterModel_ID(docCode, clientCode));
	}

}
