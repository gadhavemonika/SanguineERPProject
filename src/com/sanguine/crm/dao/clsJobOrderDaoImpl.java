package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsJobOrderModel;

@Repository("clsJobOrderDao")
public class clsJobOrderDaoImpl implements clsJobOrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean funAddUpdateJobOrder(clsJobOrderModel objMaster) {
		boolean success = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> funGetJobOrderUsingSOCode(String strSOcode, String clientCode) {
		List<Object> list = new ArrayList<Object>();
		try {
			String hql = "select ifnull(b.strJOCode,''),a.strSOCode,a.strChildCode,c.strProdName,a.dblQty,ifnull(b.strStatus,'') " + " FROM tblsalesorderbom a " + " left outer join tblproductmaster c on  a.strChildCode=c.strProdCode " + " left outer join tbljoborderhd b on a.strSOCode=b.strSOCode and a.strChildCode=b.strProdCode " + " and b.strClientCode ='" + clientCode + "' "
					+ " where a.strSOCode='" + strSOcode + "' and c.strProdType='Sub-Contracted' " + " and a.strClientCode ='" + clientCode + "' and c.strClientCode ='" + clientCode + "' ";
			// Query query =
			// sessionFactory.getCurrentSession().createQuery(hql);
			/*
			 * Query query = sessionFactory.getCurrentSession().createQuery(
			 * "from clsJobOrderModel a,clsProductMasterModel b " +
			 * "where a.strSOCode = '"
			 * +strSOcode+"' and a.strProdCode=b.strProdCode and " +
			 * " a.strClientCode ='"
			 * +clientCode+"' and b.strClientCode ='"+clientCode+"'");
			 */

			Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);

			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> funGetJobOrder(String strJOCode, String clientCode) {
		List<Object> list = new ArrayList<Object>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsJobOrderModel a,clsProductMasterModel b " + " where a.strJOCode = '" + strJOCode + "' and a.strProdCode=b.strProdCode and " + " a.strClientCode ='" + clientCode + "' and b.strClientCode ='" + clientCode + "'");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

}
