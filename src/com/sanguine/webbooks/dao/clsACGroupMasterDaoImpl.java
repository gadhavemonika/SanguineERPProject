package com.sanguine.webbooks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsACGroupMasterModel;
import com.sanguine.webbooks.model.clsACGroupMasterModel_ID;

@Repository("clsACGroupMasterDao")
public class clsACGroupMasterDaoImpl implements clsACGroupMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateACGroupMaster(clsACGroupMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsACGroupMasterModel funGetACGroupMaster(String docCode, String clientCode) {
		return (clsACGroupMasterModel) webBooksSessionFactory.getCurrentSession().get(clsACGroupMasterModel.class, new clsACGroupMasterModel_ID(docCode, clientCode));
	}

	@Override
	public List<String> funGetGroupCategory(String clientCode) {
		SQLQuery query = webBooksSessionFactory.getCurrentSession().createSQLQuery("select strCatName from vcategorymaster where strClientCode='" + clientCode + "' order by strCatCode ");

		return query.list();
	}

}
