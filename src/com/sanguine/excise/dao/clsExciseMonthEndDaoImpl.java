package com.sanguine.excise.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExciseMonthEndModel;

@Repository("clsExciseMonthEndDao")
public class clsExciseMonthEndDaoImpl implements clsExciseMonthEndDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public void funAddMonthEnd(clsExciseMonthEndModel MonthEndModel) {

		exciseSessionFactory.getCurrentSession().saveOrUpdate(MonthEndModel);
	}

}
