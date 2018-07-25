package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sanguine.model.clsTransactionTimeModel;

@Repository("clsTransactionTimeDao")
public class clsTransactionTimeDaoImpl implements clsTransactionTimeDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsTransactionTimeModel objTransactionTimeModel) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objTransactionTimeModel);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<clsTransactionTimeModel> funLoadTransactionTime(String strPropertyCode, String strClientCode, String strTransactionName) {

		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsTransactionTimeModel.class);
		cr.add(Restrictions.eq("strPropertyCode", strPropertyCode));
		cr.add(Restrictions.eq("strClientCode", strClientCode));
		cr.add(Restrictions.eq("strTransactionName", strTransactionName));

		List list = cr.list();

		List<clsTransactionTimeModel> objListModel = new ArrayList<clsTransactionTimeModel>();
		if (list.size() > 0) {
			clsTransactionTimeModel obj = null;

			for (int i = 0; i < list.size(); i++) {

				obj = (clsTransactionTimeModel) list.get(i);

				objListModel.add(obj);
			}
		}
		return objListModel;
	}

	@Override
	public List<clsTransactionTimeModel> funLoadTransactionTimeLocationWise(String strPropertyCode, String strClientCode, String LocCode) {

		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsTransactionTimeModel.class);
		cr.add(Restrictions.eq("strPropertyCode", strPropertyCode));
		cr.add(Restrictions.eq("strClientCode", strClientCode));
		cr.add(Restrictions.eq("strLocCode", LocCode));

		List list = cr.list();

		List<clsTransactionTimeModel> objListModel = new ArrayList<clsTransactionTimeModel>();
		if (list.size() > 0) {
			clsTransactionTimeModel obj = null;

			for (int i = 0; i < list.size(); i++) {

				obj = (clsTransactionTimeModel) list.get(i);

				objListModel.add(obj);
			}
		}
		return objListModel;
	}
	
}
