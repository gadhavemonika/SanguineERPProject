package com.sanguine.webbooks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsChargeMasterModel;
import com.sanguine.webbooks.model.clsChargeMasterModel_ID;

@Repository("clsChargeMasterDao")
public class clsChargeMasterDaoImpl implements clsChargeMasterDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	public void funAddUpdateChargeMaster(clsChargeMasterModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public List funGetChargeMaster(String chargeCode, String clientCode) {
		List list = null;

		Query query = webBooksSessionFactory.getCurrentSession().createQuery("from clsChargeMasterModel a,clsWebBooksAccountMasterModel b where a.strClientCode=b.strClientCode and " + "a.strAcctCode=b.strAccountCode and a.strChargeCode=:chargeCode and a.strClientCode=:clientCode");
		query.setParameter("chargeCode", chargeCode);
		query.setParameter("clientCode", clientCode);

		list = query.list();

		return list;
	}

	@Override
	public List funGetDebtoMemberList(String sqlQuery) {
		return webBooksSessionFactory.getCurrentSession().createSQLQuery(sqlQuery).list();
	}

	@Override
	public List<clsChargeMasterModel> funGetAllChargesData(String clientCode, String propertyCode) {
		List<clsChargeMasterModel> list = null;

		Query query = webBooksSessionFactory.getCurrentSession().createQuery("from clsChargeMasterModel  where  strClientCode=:clientCode and strPropertyCode=:propertyCode");
		query.setParameter("clientCode", clientCode);
		query.setParameter("propertyCode", propertyCode);

		list = query.list();

		return list;
	}

}
