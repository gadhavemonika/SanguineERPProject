package com.sanguine.webclub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubCommitteeMasterDtl;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterModel;
import com.sanguine.webclub.model.clsWebClubCommitteeMasterModel_ID;

@Repository("clsWebClubCommitteeMasterDao")
public class clsWebClubCommitteeMasterDaoImpl implements clsWebClubCommitteeMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public void funAddUpdateWebClubCommitteeMaster(clsWebClubCommitteeMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public clsWebClubCommitteeMasterModel funGetWebClubCommitteeMaster(String docCode, String clientCode) {
		return (clsWebClubCommitteeMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCommitteeMasterModel.class, new clsWebClubCommitteeMasterModel_ID(docCode, clientCode));
	}

	public void funAddUpdateCommittteeMasterDtl(clsWebClubCommitteeMasterDtl objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	public List<Object> funGetWebClubCommittteeMasterDtl(String docCode, String clientCode) {
		List<Object> objCommitteeDtlList = null;
		Query query = WebClubSessionFactory.getCurrentSession().createQuery(" from clsWebClubCommitteeMasterDtl a " + "	where a.strCommitteeCode=:CommitteeCode and a.strClientCode=:clientCode " + "and b.strClientCode=:clientCode ");
		query.setParameter("CommitteeCode", docCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objCommitteeDtlList = list;
		}

		return objCommitteeDtlList;
	}

}
