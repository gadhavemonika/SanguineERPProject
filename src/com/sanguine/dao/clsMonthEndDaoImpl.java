package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsMonthEndModel;

@Repository("clsMonthEndDao")
public class clsMonthEndDaoImpl implements clsMonthEndDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddMonthEnd(clsMonthEndModel MonthEndModel) {

		sessionFactory.getCurrentSession().saveOrUpdate(MonthEndModel);
	}

}
