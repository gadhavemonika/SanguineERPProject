package com.sanguine.excise.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsSubCategoryMasterModel;

@Repository("clsSubCategoryMasterDao")
public class clsSubCategoryMasterDaoImpl implements clsSubCategoryMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateSubCategoryMaster(clsSubCategoryMasterModel objMaster) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsSubCategoryMasterModel> funGetSubCategoryMaster(String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsSubCategoryMasterModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsSubCategoryMasterModel a,clsCategoryMasterModel b " + "where a.strSubCategoryCode= :subCategoryCode and a.strCategoryCode=b.strCategoryCode and a.strClientCode= :clientCode");
			query.setParameter("subCategoryCode", code);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

}
