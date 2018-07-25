package com.sanguine.webclub.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel;
import com.sanguine.webclub.model.clsWebClubCurrencyDetailsMasterModel_ID;

@Repository("clsWebClubCurrencyDetailsMasterDao")
public class clsWebClubCurrencyDetailsMasterDaoImpl implements clsWebClubCurrencyDetailsMasterDao {

	@Autowired
	private SessionFactory WebClubSessionFactory;

	@Override
	public void funAddUpdateWebClubCurrencyDetailsMaster(clsWebClubCurrencyDetailsMasterModel objMaster) {
		WebClubSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsWebClubCurrencyDetailsMasterModel funGetWebClubCurrencyDetailsMaster(String docCode, String clientCode) {
		return (clsWebClubCurrencyDetailsMasterModel) WebClubSessionFactory.getCurrentSession().get(clsWebClubCurrencyDetailsMasterModel.class, new clsWebClubCurrencyDetailsMasterModel_ID(docCode, clientCode));
	}
}
