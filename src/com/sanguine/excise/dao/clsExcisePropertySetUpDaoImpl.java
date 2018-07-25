package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExcisePropertySetUpModel;

@Repository("clsExcisePropertySetUpDao")
public class clsExcisePropertySetUpDaoImpl implements clsExcisePropertySetUpDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateSetUpMaster(clsExcisePropertySetUpModel objMaster) {
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
	public clsExcisePropertySetUpModel funGetSetUpMaster(String clientCode) {
		clsExcisePropertySetUpModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsExcisePropertySetUpModel) currentSession.get(clsExcisePropertySetUpModel.class, clientCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	@Override
	public clsExcisePropertySetUpModel funGetObject(String clientCode) {
		clsExcisePropertySetUpModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsExcisePropertySetUpModel) currentSession.get(clsExcisePropertySetUpModel.class, clientCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

}
