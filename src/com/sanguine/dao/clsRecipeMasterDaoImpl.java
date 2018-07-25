package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsBomHdModel;

@Repository("clsRecipeMasterDao")
public class clsRecipeMasterDaoImpl implements clsRecipeMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsBomHdModel object) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Override
	public void funAddUpdateDtl(clsBomDtlModel object) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsBomHdModel> funGetList(String bomCode, String clientCode) {
		// TODO Auto-generated method stub
		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(
		 * "from clsBomHdModel where strBOMCode = :bomCode and strClientCode= :clientCode"
		 * ); query.setParameter("bomCode", bomCode);
		 * query.setParameter("clientCode", clientCode); List list =
		 * query.list(); return (List<clsBomHdModel>) list;
		 */
		return (List<clsBomHdModel>) sessionFactory.getCurrentSession().createCriteria(clsBomHdModel.class).list();
	}

	@Override
	public clsBomHdModel funGetObject(String bomCode, String clientCode) {
		// TODO Auto-generated method stub
		/*
		 * Query query = sessionFactory.getCurrentSession().createQuery(
		 * "from clsBomHdModel where strBOMCode = :bomCode and strClientCode= :clientCode"
		 * ); query.setParameter("bomCode", bomCode);
		 * query.setParameter("clientCode", clientCode); return
		 * (clsBomHdModel)query.list();
		 */
		return (clsBomHdModel) sessionFactory.getCurrentSession().get(clsBomHdModel.class, bomCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String bomCode, String clientCode) {
		// TODO Auto-generated method stub
		String hql = "from clsBomDtlModel a, clsProductMasterModel b " + "where a.strBOMCode = :bomCode and a.strChildCode=b.strProdCode and a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("bomCode", bomCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List funGetProductList(String sql) {
		return sessionFactory.getCurrentSession().createSQLQuery(sql).list();
	}

	@SuppressWarnings("finally")
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		long lastNo = 0;
		try {
			@SuppressWarnings("rawtypes")
			List listLastNo = sessionFactory.getCurrentSession().createSQLQuery("select max(" + columnName + ") from " + tableName).list();
			if (listLastNo.size() > 1) {
				lastNo = ((BigInteger) listLastNo.get(0)).longValue();
			}
			lastNo++;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return lastNo;
		}
	}

	@SuppressWarnings("unused")
	public void funDeleteDtl(String bomCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsBomDtlModel where strBOMCode = :bomCode and strClientCode= :clientCode");
		query.setParameter("bomCode", bomCode);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
	}

	public List funGetBOMCode(String strParentCode, String strClientCode) {
		// String
		// sql="select a.strBOMCode from tblbommasterhd a where a.strParentCode='"+strParentCode+"' and a.strClientCode='"+strClientCode+"'";

		String hql = "from clsBomHdModel a " + "where a.strParentCode= :parentCode and a.strClientCode= :clientCode";

		/*
		 * String hql="select b.strBOMCode,b.strChildCode,b.dblQty,b.dblWeight "
		 * + "from clsBomHdModel a,clsBomDtlModel b " +
		 * "where a.strParentCode= :parentCode and a.strClientCode= :clientCode "
		 * + "and and a.strBOMCode= :bomCode";
		 */

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("parentCode", strParentCode);
		query.setParameter("clientCode", strClientCode);
		List list = query.list();
		return list;
	}

	public List funGetBOMDtl(String strClientCode, String BOMCode) {
		// String
		// sql="select a.strBOMCode from tblbommasterhd a where a.strParentCode='"+strParentCode+"' and a.strClientCode='"+strClientCode+"'";

		String hql = "from clsBomDtlModel a " + "where a.strClientCode= :clientCode and strBOMCOde= :bomCode";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("clientCode", strClientCode);
		query.setParameter("bomCode", BOMCode);
		List list = query.list();
		return list;
	}

}
