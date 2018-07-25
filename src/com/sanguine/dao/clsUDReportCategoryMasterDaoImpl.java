package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsUDReportCategoryMasterModel;
import com.sanguine.model.clsUDReportCategoryMasterModel_ID;

@Repository("clsUDReportCategoryMasterDao")
public class clsUDReportCategoryMasterDaoImpl implements clsUDReportCategoryMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	// @Transactional(value = "OtherTransactionManager")
	public void funAddUpdateUDReportCategoryMaster(clsUDReportCategoryMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	// @Transactional(value = "OtherTransactionManager")
	public clsUDReportCategoryMasterModel funGetUDReportCategoryMaster(String docCode, String clientCode) {
		return (clsUDReportCategoryMasterModel) sessionFactory.getCurrentSession().get(clsUDReportCategoryMasterModel.class, new clsUDReportCategoryMasterModel_ID(docCode, clientCode));
	}

}
