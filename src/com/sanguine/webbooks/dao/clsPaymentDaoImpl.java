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
import com.sanguine.webbooks.model.clsReceiptHdModel;

@Repository("clsPaymentDao")
public class clsPaymentDaoImpl implements clsPaymentDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public void funAddUpdatePaymentHd(clsPaymentHdModel objHdModel) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public clsPaymentHdModel funGetPaymentList(String vouchNo, String clientCode, String propertyCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsPaymentHdModel.class);
		cr.add(Restrictions.eq("strVouchNo", vouchNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strPropertyCode", propertyCode));
		List list = cr.list();

		clsPaymentHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsPaymentHdModel) list.get(0);
			objModel.getListPaymentDebtorDtlModel().size();
			objModel.getListPaymentDtlModel().size();
		}

		return objModel;
	}

	@Override
	public void funDeletePayment(clsPaymentHdModel objPaymentHdModel) {
		webBooksSessionFactory.getCurrentSession().delete(objPaymentHdModel);
	}

	public void funInsertPayment(String sql) {

		try {
			Query query = webBooksSessionFactory.getCurrentSession().createSQLQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
