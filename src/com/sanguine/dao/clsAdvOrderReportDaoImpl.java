package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.bean.clsProductionComPilationBean;

@Repository("clsAdvOrderReportDao")
public class clsAdvOrderReportDaoImpl implements clsAdvOrderReportDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List funGetImageAdvOrder(String strCode, String strActualFileName, String clientCode) {
		List listResult = null;
		SessionFactory sessionFactory1 = null;
		clsProductionComPilationBean obj = new clsProductionComPilationBean();
		String sqlImage = "select binContent from clsAttachDocModel where strCode=:strCode and strActualFileName=:strActualFileName ";

		try {
			sessionFactory1 = sessionFactory;
			Query query = sessionFactory1.getCurrentSession().createQuery(sqlImage);
			query.setParameter("strCode", strCode);
			query.setParameter("strActualFileName", strActualFileName);
			listResult = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listResult;

	}

}
