package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsProdSuppMasterModel;

@Repository("clsProdSuppMasterDao")
public class clsProdSuppMasterDaoImpl implements clsProdSuppMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsProdSuppMasterModel> funGetList() {
		return (List<clsProdSuppMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsProdSuppMasterModel.class).list();
	}

	public clsProdSuppMasterModel funGetObject(String code) {
		return (clsProdSuppMasterModel) sessionFactory.getCurrentSession().get(clsProdSuppMasterModel.class, code);
	}

	public void funAddUpdate(clsProdSuppMasterModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}
}
