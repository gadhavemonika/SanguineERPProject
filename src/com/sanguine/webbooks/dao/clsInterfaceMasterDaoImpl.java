package com.sanguine.webbooks.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsInterfaceMasterModel;
import com.sanguine.webbooks.model.clsInterfaceMasterModel_ID;

@Repository("clsInterfaceMasterDao")
public class clsInterfaceMasterDaoImpl implements clsInterfaceMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateInterfaceMaster(clsInterfaceMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsInterfaceMasterModel funGetInterfaceMaster(String docCode, String clientCode) {
		return (clsInterfaceMasterModel) webBooksSessionFactory.getCurrentSession().get(clsInterfaceMasterModel.class, new clsInterfaceMasterModel_ID(docCode, clientCode));
	}

}
