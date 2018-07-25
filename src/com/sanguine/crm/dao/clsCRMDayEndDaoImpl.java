package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsCRMDayEndModel;

@Repository("objCRMDayEndDaoImpl")
public class clsCRMDayEndDaoImpl implements clsCRMDayEndDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdadte(clsCRMDayEndModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	@Override
	public String funGetCRMDayEndLocationDate(String strLocCode, String strClientCode) {
		String retDate = "";
		try {
			// Query
			// query=sessionFactory.getCurrentSession().createQuery("select max(strDayEnd) from clsCRMDayEndModel where strClientCode=:clientCode and strLocCode=:strLocCode ");
			Query query = sessionFactory.getCurrentSession().createSQLQuery("select max(strDayEnd) from tbldayend where strClientCode=:clientCode and strLocCode=:strLocCode ");
			query.setParameter("clientCode", strClientCode);
			query.setParameter("strLocCode", strLocCode);
			List list = query.list();
			if (list.get(0) != null) {
				retDate = list.get(0).toString();

			}
			return retDate;
		} catch (Exception ex) {
			ex.printStackTrace();
			return retDate;
		}

	}

}
