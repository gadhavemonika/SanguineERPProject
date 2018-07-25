package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel;
import com.sanguine.webclub.model.clsWebClubMemberFormGenerationModel_ID;

@Repository("clsWebClubMemberFormGenerationDao")
public class clsWebClubMemberFormGenerationDaoImpl implements clsWebClubMemberFormGenerationDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public void funAddUpdateWebClubMemberFormGeneration(clsWebClubMemberFormGenerationModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public clsWebClubMemberFormGenerationModel funGetWebClubMemberFormGeneration(String docCode, String clientCode) {
		return (clsWebClubMemberFormGenerationModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubMemberFormGenerationModel.class, new clsWebClubMemberFormGenerationModel_ID(docCode, clientCode));
	}

}
