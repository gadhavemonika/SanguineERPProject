package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.model.clsExciseLicenceMasterModel;
import com.sanguine.excise.model.clsExciseLicenceMasterModel_ID;

@Repository("clsLicenceMasterDao")
public class clsLicenceMasterDaoImpl implements clsLicenceMasterDao {

	@Autowired
	@Qualifier("exciseSessionFactory")
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateLicenceMaster(clsExciseLicenceMasterModel objMaster) {
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

	@Override
	public clsExciseLicenceMasterModel funGetLicenceMaster(String docCode, String propertyCode, String clientCode) {
		clsExciseLicenceMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsExciseLicenceMasterModel) currentSession.get(clsExciseLicenceMasterModel.class, new clsExciseLicenceMasterModel_ID(docCode, clientCode, propertyCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	@Override
	@Transactional(value = "OtherTransactionManager")
	public clsExciseLicenceMasterModel funGetObject(String code, String propertyCode, String clientCode) {
		clsExciseLicenceMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsExciseLicenceMasterModel) currentSession.get(clsExciseLicenceMasterModel.class, new clsExciseLicenceMasterModel_ID(code, clientCode, propertyCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}
}
