package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsPermitMasterModel;

@Repository("clsPermitMasterImportDao")
public class clsPermitMasterImportDaoImpl implements clsPermitMasterImportDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public void funAddUpdatePermitMaster(clsPermitMasterModel objMaster) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public clsPermitMasterModel funGetPermitMaster(String intId, String clientCode) {
		clsPermitMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsPermitMasterModel) currentSession.get(clsPermitMasterModel.class, intId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

}
