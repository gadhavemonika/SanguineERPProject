package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsCityMasterModel;
import com.sanguine.excise.model.clsCityMasterModel_ID;

@Repository("clsCityMasterDao")
public class clsCityMasterDaoImpl implements clsCityMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateCityMaster(clsCityMasterModel objMaster) {
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
	public clsCityMasterModel funGetCityMaster(String docCode, String clientCode) {
		clsCityMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsCityMasterModel) currentSession.get(clsCityMasterModel.class, new clsCityMasterModel_ID(docCode, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	@Override
	public clsCityMasterModel funGetObject(String code, String clientCode) {
		clsCityMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsCityMasterModel) currentSession.get(clsCityMasterModel.class, new clsCityMasterModel_ID(code, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

}
