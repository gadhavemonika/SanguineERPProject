package com.sanguine.crm.service;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("clsDataImportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsDataImportService {

	@Autowired
	private SessionFactory sessionFactory;

	public void funSaveGroupSubGroup(String sql) {
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@SuppressWarnings("finally")
	public int funExecuteQuery(String sql) {
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			return 0;
		}
	}
}
