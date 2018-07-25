package com.sanguine.excise.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.model.clsExciseSupplierMasterModel;

@Repository("clsExciseSupplierMasterDao")
public class clsExciseSupplierMasterDaoImpl implements clsExciseSupplierMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateExciseSupplierMaster(clsExciseSupplierMasterModel objMaster) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<clsExciseSupplierMasterModel> funGetExciseSupplierMaster(String clientCode) {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsExciseSupplierMasterModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseSupplierMasterModel a,clsCityMasterModel b " + "where a.strSupplierCode= :supplierCode and a.strCityCode=b.strCityCode and a.strClientCode= :clientCode");
			query.setParameter("supplierCode", code);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

}
