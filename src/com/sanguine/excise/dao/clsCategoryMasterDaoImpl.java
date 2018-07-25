package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsCategoryMasterModel;
import com.sanguine.excise.model.clsCategoryMasterModel_ID;

@Repository("clsCategoryMasterDao")
public class clsCategoryMasterDaoImpl implements clsCategoryMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateCategoryMaster(clsCategoryMasterModel objMaster) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
			success = true;

		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@Override
	public clsCategoryMasterModel funGetCategoryMaster(String docCode, String clientCode) {
		clsCategoryMasterModel objModel = null;
		;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsCategoryMasterModel) currentSession.get(clsCategoryMasterModel.class, new clsCategoryMasterModel_ID(docCode, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	@Override
	public clsCategoryMasterModel funGetObject(String code, String clientCode) {
		clsCategoryMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsCategoryMasterModel) currentSession.get(clsCategoryMasterModel.class, new clsCategoryMasterModel_ID(code, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

}
