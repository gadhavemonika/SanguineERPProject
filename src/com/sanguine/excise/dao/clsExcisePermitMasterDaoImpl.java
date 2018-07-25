package com.sanguine.excise.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.model.clsExcisePermitMasterModel;
import com.sanguine.excise.model.clsExcisePermitMasterModel_ID;

@Repository("clsPermitMasterDao")
public class clsExcisePermitMasterDaoImpl implements clsExcisePermitMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	@Transactional(value = "OtherTransactionManager")
	public boolean funAddUpdatePermitMaster(clsExcisePermitMasterModel objMaster) {

		boolean success = false;
		try {
			exciseSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	@Transactional(value = "OtherTransactionManager")
	public clsExcisePermitMasterModel funGetPermitMaster(String docCode, String clientCode) {
		clsExcisePermitMasterModel objModel = null;
		try {
			objModel = (clsExcisePermitMasterModel) exciseSessionFactory.getCurrentSession().get(clsExcisePermitMasterModel.class, new clsExcisePermitMasterModel_ID(docCode, clientCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

}
