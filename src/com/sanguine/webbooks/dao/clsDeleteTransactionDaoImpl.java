package com.sanguine.webbooks.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsDeleteTransactionModel;
import com.sanguine.webbooks.model.clsDeleteTransactionModel_ID;
import com.sanguine.webbooks.model.clsWebBooksAuditHdModel;

@Repository("clsDeleteTransactionDao")
public class clsDeleteTransactionDaoImpl implements clsDeleteTransactionDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public void funAddUpdateDeleteTransaction(clsDeleteTransactionModel objMaster) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public clsDeleteTransactionModel funGetDeleteTransaction(String docCode, String clientCode) {
		return (clsDeleteTransactionModel) webBooksSessionFactory.getCurrentSession().get(clsDeleteTransactionModel.class, new clsDeleteTransactionModel_ID(docCode, clientCode));
	}

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public void funAddUpdateAuditHd(clsWebBooksAuditHdModel objHdModel) {
		// TODO Auto-generated method stub
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	@Override
	public int funDeleteTransactionRecord(String hqlDelQuery, String vouchNo, String clientCode, String propertyCode) {
		Session objSesson = webBooksSessionFactory.getCurrentSession();
		Query query = objSesson.createQuery(hqlDelQuery).setString("VouchNo", vouchNo).setString("ClientCode", clientCode).setString("PropertyCode", propertyCode);
		return query.executeUpdate();
	}

}
