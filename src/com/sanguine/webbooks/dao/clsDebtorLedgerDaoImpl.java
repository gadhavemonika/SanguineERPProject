package com.sanguine.webbooks.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsDebtorMaster;
import com.sanguine.webbooks.model.clsDeleteTransactionModel;
import com.sanguine.webbooks.model.clsDeleteTransactionModel_ID;
import com.sanguine.webbooks.model.clsWebBooksAuditHdModel;

@Repository("clsDebtorLedgerDao")
public class clsDebtorLedgerDaoImpl implements clsDebtorLedgerDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public clsDebtorMaster funGetDebtorDetails(String debtorCode, String clientCode, String propertyCode) {
		// TODO Auto-generated method stub

		/*
		 * Criteria
		 * crDebtorDetails=webBooksSessionFactory.getCurrentSession().createCriteria
		 * (clsDebtorMaster.class);
		 * crDebtorDetails.add(Restrictions.eq("strMemberCode", debtorCode));
		 * crDebtorDetails.add(Restrictions.eq("strClientCode", clientCode));
		 * crDebtorDetails.add(Restrictions.eq("strPropertyCode",
		 * propertyCode)); List list=crDebtorDetails.list(); clsDebtorMaster
		 * objDebtorMaster=new clsDebtorMaster(); Object arrObj=list.get(0);
		 */

		return null;
	}
}
