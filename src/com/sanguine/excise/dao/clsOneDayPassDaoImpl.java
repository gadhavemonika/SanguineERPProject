package com.sanguine.excise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsOneDayPassHdModel;

@Repository("clsOneDayPassDao")
public class clsOneDayPassDaoImpl implements clsOneDayPassDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	public boolean funAddOneDayPass(clsOneDayPassHdModel pass) {
		boolean success = false;
		try {
			exciseSessionFactory.getCurrentSession().saveOrUpdate(pass);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public clsOneDayPassHdModel funGetOneDayPassObject(Long id) {
		return (clsOneDayPassHdModel) exciseSessionFactory.getCurrentSession().get(clsOneDayPassHdModel.class, id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsOneDayPassHdModel funGetOneDayPass(Long id, String clientCode) {

		clsOneDayPassHdModel obOneDayPassHdModel = null;
		try {
			Query query = exciseSessionFactory.getCurrentSession().createQuery(" From clsOneDayPassHdModel a where a.intId='" + id + "' " + "and a.strClientCode='" + clientCode + "' ");
			List list = query.list();
			obOneDayPassHdModel = (clsOneDayPassHdModel) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obOneDayPassHdModel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsOneDayPassHdModel funGetOneDayPassByDate(String strDate, String clientCode) {

		clsOneDayPassHdModel obOneDayPassHdModel = null;
		try {
			Query query = exciseSessionFactory.getCurrentSession().createQuery(" From clsOneDayPassHdModel a where a.dteDate='" + strDate + "' " + "and a.strClientCode='" + clientCode + "' ");
			List list = query.list();
			if (list.size() > 0) {
				obOneDayPassHdModel = (clsOneDayPassHdModel) list.get(0);
			} else {
				obOneDayPassHdModel = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obOneDayPassHdModel;
	}
}
