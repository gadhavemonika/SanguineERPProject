package com.sanguine.excise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExcisePOSSaleModel;

@Repository("clsExcisePOSSaleDao")
public class clsExcisePOSSaleDaoImpl implements clsExcisePOSSaleDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public void funAddUpdate(clsExcisePOSSaleModel objMaster) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
