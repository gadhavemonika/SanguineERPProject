package com.sanguine.webbooks.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsJVHdModel;
import com.sanguine.webbooks.model.clsPaymentHdModel;
import com.sanguine.webbooks.model.clsReceiptDtlModel;
import com.sanguine.webbooks.model.clsReceiptHdModel;

@Repository("clsReceiptDao")
public class clsReceiptDaoImpl implements clsReceiptDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public void funAddUpdateReceiptHd(clsReceiptHdModel objHdModel) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	@Override
	public clsReceiptHdModel funGetReceiptList(String vouchNo, String clientCode, String propertyCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsReceiptHdModel.class);
		cr.add(Restrictions.eq("strVouchNo", vouchNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strPropertyCode", propertyCode));
		List list = cr.list();

		clsReceiptHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsReceiptHdModel) list.get(0);
			objModel.getListReceiptDebtorDtlModel().size();
			objModel.getListReceiptDtlModel().size();
		}

		return objModel;
	}

	@Override
	public void funDeleteReceipt(clsReceiptHdModel objReceiptHdModel) {
		webBooksSessionFactory.getCurrentSession().delete(objReceiptHdModel);
	}

	public void funInsertRecipt(String sqltempDtlDr) {

		try {
			Query query = webBooksSessionFactory.getCurrentSession().createSQLQuery(sqltempDtlDr);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
