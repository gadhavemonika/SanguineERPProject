package com.sanguine.excise.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExciseSaleModel;

@Repository("clsExciseSaleDao")
public class clsExciseSaleDaoImpl implements clsExciseSaleDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public void funAddUpdate(clsExciseSaleModel objMaster) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean funAddBulkly(ArrayList<clsExciseSaleModel> objList) {
		Session currentSession = exciseSessionFactory.openSession();
		Transaction tx = null;
		Boolean result = false;
		try {
			tx = currentSession.beginTransaction();
			for (int i = 0; i < objList.size(); i++) {
				clsExciseSaleModel obj = objList.get(i);
				currentSession.saveOrUpdate(obj);
				if (i % 50 == 0) {
					currentSession.flush();
					currentSession.clear();
				}
			}
			tx.commit();
			result = true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			result = false;
		} finally {
			currentSession.close();
		}
		return result;
	}

}
