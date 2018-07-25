package com.sanguine.webclub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel;
import com.sanguine.webclub.model.clsWebClubCommitteeMemberRoleMasterModel_ID;

@Repository("clsWebClubCommitteeMemberRoleMasterDao")
public class clsWebClubCommitteeMemberRoleMasterDaoImpl implements clsWebClubCommitteeMemberRoleMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public void funAddUpdateWebClubCommitteeMemberRoleMaster(clsWebClubCommitteeMemberRoleMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleMaster(String docCode, String clientCode) {
		return (clsWebClubCommitteeMemberRoleMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCommitteeMemberRoleMasterModel.class, new clsWebClubCommitteeMemberRoleMasterModel_ID(docCode, clientCode));
	}

	@Override
	@Transactional(value = "WebClubTransactionManager")
	public clsWebClubCommitteeMemberRoleMasterModel funGetWebClubCommitteeMemberRoleName(String roleName, String clientCode) {
		clsWebClubCommitteeMemberRoleMasterModel objModel = null;

		Query query = WebClubSessionFactory.getCurrentSession().createQuery(" from clsWebClubCommitteeMemberRoleMasterModel a " + "	where a.strRoleDesc=:roleName and a.strClientCode=:clientCode ");
		query.setParameter("roleName", roleName);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objModel = (clsWebClubCommitteeMemberRoleMasterModel) list.get(0);
		}

		return objModel;
	}

}
