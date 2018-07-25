package com.sanguine.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel_ID;

@Repository("clsCurrencyMasterDao")
public class clsCurrencyMasterDaoImpl implements clsCurrencyMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdateCurrencyMaster(clsCurrencyMasterModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsCurrencyMasterModel funGetCurrencyMaster(String docCode, String clientCode) {
		return (clsCurrencyMasterModel) sessionFactory.getCurrentSession().get(clsCurrencyMasterModel.class, new clsCurrencyMasterModel_ID(docCode, clientCode));
	}

	@Override
	public Map<String, String> funGetAllCurrency(String clientCode) {
		Map<String, String> map = new HashMap<String, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from clsCurrencyMasterModel WHERE strClientCode='" + clientCode + "'");
		List<clsCurrencyMasterModel> list = query.list();
		// List<clsCurrencyMasterModel> mapLocation= query.list();
		for (clsCurrencyMasterModel obj : list) {
			map.put(obj.getStrCurrencyCode(), obj.getStrCurrencyName());
		}
		return map;

	}

	@Override
	public List<clsCurrencyMasterModel> funGetAllCurrencyDataModel(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsCurrencyMasterModel WHERE strClientCode='" + clientCode + "'");
		List<clsCurrencyMasterModel> list = query.list();
		return list;
	}

	@Override
	public Map<String, String> funCurrencyListToDisplay(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsCurrencyMasterModel WHERE strClientCode='" + clientCode + "'");
		List<clsCurrencyMasterModel> list = query.list();

		Map<String, String> hmCurrency = new HashMap<String, String>();
		for (clsCurrencyMasterModel objCurrencyModel : list) {
			hmCurrency.put(objCurrencyModel.getStrCurrencyCode(), objCurrencyModel.getStrCurrencyName());
		}

		return hmCurrency;
	}

}
