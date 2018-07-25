package com.sanguine.excise.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.model.clsSizeMasterModel_ID;

@Repository("clsSizeMasterDao")
public class clsSizeMasterDaoImpl implements clsSizeMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateSizeMaster(clsSizeMasterModel objMaster) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetSizeMasterList(String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsSizeMasterModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	@Transactional(value = "OtherTransactionManager")
	public clsSizeMasterModel funGetObject(String code, String clientCode) {
		clsSizeMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsSizeMasterModel) currentSession.get(clsSizeMasterModel.class, new clsSizeMasterModel_ID(code, clientCode));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;

	}

}
