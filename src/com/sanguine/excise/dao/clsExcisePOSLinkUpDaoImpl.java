package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExcisePOSLinkUpModel;
import com.sanguine.excise.model.clsExcisePOSLinkUpModel_ID;

@Repository("clsExcisePOSLinkUpDao")
public class clsExcisePOSLinkUpDaoImpl implements clsExcisePOSLinkUpDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public void funAddUpdatePOSLinkUp(clsExcisePOSLinkUpModel objMaster) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public clsExcisePOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode) {
		clsExcisePOSLinkUpModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsExcisePOSLinkUpModel) currentSession.get(clsExcisePOSLinkUpModel.class, new clsExcisePOSLinkUpModel_ID(docCode, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	@Override
	public int funExecute(String sql) {
		int i = 0;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			i = currentSession.createSQLQuery(sql).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

}
