package com.sanguine.webclub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.model.clsWebClubDependentMasterModel;
import com.sanguine.webclub.model.clsWebClubDependentMasterModel_ID;

@Repository("clsWebClubDependentMasterDao")
public class clsWebClubDependentMasterDaoImpl implements clsWebClubDependentMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubDependentMaster(clsWebClubDependentMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubDependentMasterModel funGetWebClubDependentMaster(String docCode, String clientCode) {
		return (clsWebClubDependentMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubDependentMasterModel.class, new clsWebClubDependentMasterModel_ID(docCode, clientCode));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "finally" })
	public List funGetWebClubDependentMasterList(String docCode, String clientCode) {
		List list = null;
		try {

			Query query = WebClubSessionFactory.getCurrentSession().createQuery("from clsWebClubDependentMasterModel where strMemberCode=:docCode and strClientCode=:clientCode");
			query.setParameter("docCode", docCode);
			query.setParameter("clientCode", clientCode);
			list = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			return list;
		}

	}

}
