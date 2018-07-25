package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsLinkLoctoOtherPropLocModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Repository("clsLinkLoctoOtherPropLocDao")
public class clsLinkLoctoOtherPropLocDaoImpl implements clsLinkLoctoOtherPropLocDao {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private SessionFactory sessionFactory;

	public void funAddUpdate(clsLinkLoctoOtherPropLocModel model) {
		sessionFactory.getCurrentSession().saveOrUpdate(model);
	}

	public void funDeleteData(String propCode, String strClientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("delete clsLinkLoctoOtherPropLocModel where strPropertyCode = :strPropertyCode and strClientCode=:strClientCode ");
		query.setParameter("strPropertyCode", propCode);
		query.setParameter("strClientCode", strClientCode);
		int result = query.executeUpdate();
	}

	public List funLoadData(String propCode, String strClientCode) {

		Query query;
		List finalList = new ArrayList();
		query = sessionFactory.getCurrentSession().createQuery("select a.strPropertyCode ,a. strLocCode, a.strToLoc from clsLinkLoctoOtherPropLocModel a where a.strPropertyCode=:strPropertyCode and strClientCode=:strClientCode  ");
		query.setParameter("strPropertyCode", propCode);
		query.setParameter("strClientCode", strClientCode);
		List listData = query.list();
		if (listData.size() > 0) {
			for (int i = 0; i < listData.size(); i++) {
				Object[] obj = (Object[]) listData.get(i);
				List list = new ArrayList();
				list.add(obj[0]);
				list.add(obj[1]);
				list.add(obj[2]);
				Query query1 = sessionFactory.getCurrentSession().createQuery("select propertyName from clsPropertyMaster where strPropertyCode=:strPropertyCode and strClientCode=:strClientCode ");
				query1.setParameter("strPropertyCode", propCode);
				query1.setParameter("strClientCode", strClientCode);
				if (query1.list().size() > 0) {
					List listData1 = query1.list();
					list.add(listData1.get(0));
				}

				Query query2 = sessionFactory.getCurrentSession().createQuery("select strLocName from clsLocationMasterModel where strLocCode=:byloccode and strClientCode=:strClientCode ");
				query2.setParameter("byloccode", obj[1].toString());
				query2.setParameter("strClientCode", strClientCode);
				if (query2.list().size() > 0) {
					List listData1 = query2.list();
					list.add(listData1.get(0));
				}

				Query query3 = sessionFactory.getCurrentSession().createQuery("select strLocName from clsLocationMasterModel where strLocCode=:toLocCode and strClientCode=:strClientCode");
				query3.setParameter("toLocCode", obj[2].toString());
				query3.setParameter("strClientCode", strClientCode);
				if (query3.list().size() > 0) {
					List listData1 = query3.list();
					list.add(listData1.get(0));
				}
				finalList.add(list);
			}
		}

		return finalList;
	}
}
