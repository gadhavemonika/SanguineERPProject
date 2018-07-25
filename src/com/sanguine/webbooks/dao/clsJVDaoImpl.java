package com.sanguine.webbooks.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;

@Repository("clsJVDao")
public class clsJVDaoImpl implements clsJVDao {

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Override
	@Transactional(value = "WebBooksTransactionManager")
	public void funAddUpdateJVHd(clsJVHdModel objHdModel) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
	}

	@Override
	public void funAddUpdateJVDtl(clsJVDtlModel objDtlModel) {
		webBooksSessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	@Override
	public clsJVHdModel funGetJVList(String vouchNo, String clientCode, String propertyCode) {
		Criteria cr = webBooksSessionFactory.getCurrentSession().createCriteria(clsJVHdModel.class);
		cr.add(Restrictions.eq("strVouchNo", vouchNo));
		cr.add(Restrictions.eq("strClientCode", clientCode));
		cr.add(Restrictions.eq("strPropertyCode", propertyCode));
		List list = cr.list();

		clsJVHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsJVHdModel) list.get(0);
			objModel.getListJVDebtorDtlModel().size();
			objModel.getListJVDtlModel().size();
		}

		return objModel;
	}

	@Override
	public void funDeleteJV(clsJVHdModel objJVHdModel) {
		webBooksSessionFactory.getCurrentSession().delete(objJVHdModel);
	}

	public void funInsertJV(String sqltempDtlDr) {

		try {
			Query query = webBooksSessionFactory.getCurrentSession().createSQLQuery(sqltempDtlDr);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
