package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsPurchaseReturnDtlModel;
import com.sanguine.model.clsPurchaseReturnHdModel;
import com.sanguine.model.clsPurchaseReturnHdModel_ID;
import com.sanguine.model.clsPurchaseReturnTaxDtlModel;
@Transactional
@Repository("clsPurchaseReturnDao")
public class clsPurchaseReturnDaoImpl implements clsPurchaseReturnDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
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

	@Override
	public void funAddPRHd(clsPurchaseReturnHdModel PRHd) {
		sessionFactory.getCurrentSession().saveOrUpdate(PRHd);

	}

	@Override
	public void funAddUpdatePRDtl(clsPurchaseReturnDtlModel PRDtl) {
		sessionFactory.getCurrentSession().saveOrUpdate(PRDtl);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsPurchaseReturnHdModel> funGetList() {
		return (List<clsPurchaseReturnHdModel>) sessionFactory.getCurrentSession().createCriteria(clsPurchaseReturnHdModel.class).list();
	}

	@Override
	public clsPurchaseReturnHdModel funGetObject(String code, String strClientCode) {
		return (clsPurchaseReturnHdModel) sessionFactory.getCurrentSession().get(clsPurchaseReturnHdModel.class, new clsPurchaseReturnHdModel_ID(code, strClientCode));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String PRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsPurchaseReturnDtlModel a, clsProductMasterModel b where strPRCode = :PRCode and a.strProdCode=b.strProdCode and a.strClientCode= :clientCode and b.strClientCode= :clientCode ");
		query.setParameter("PRCode", PRCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@Override
	public void funDeleteDtl(String PRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsPurchaseReturnDtlModel where strPRCode = :PRCode");
		query.setParameter("PRCode", PRCode);
		int result = query.executeUpdate();
		System.out.println("Result=" + result);
	}

	@Override
	public int funDeletePRTaxDtl(String PRCode, String clientCode) {
		String sql = "DELETE clsPurchaseReturnTaxDtlModel WHERE strPRCode= :PRCode and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("PRCode", PRCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public void funAddUpdatePRTaxDtl(clsPurchaseReturnTaxDtlModel objTaxDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTaxDtlModel);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List funGetGRNDtlList(String GrnCode, String clientCode) {
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT c.strProdCode,a.strGRNCode,a.dblQty, IFNULL(b.PRQty,0), a.dblQty - IFNULL(b.PRQty,0), "
				+" a.dblUnitPrice, a.dblWeight,c.strUOM,c.strProdName, IF(LENGTH(c.strExpDate)=0,'N',c.strExpDate),a.dblDiscount, " 
				+"  IFNULL(c.strNonStockableItem,''),  "
				+" IFNULL(p.dblConversion,1), IFNULL(p.strCurrency,''),c.dblWeight "
				+" FROM tblgrnhd p, tblgrndtl a "
				+" LEFT OUTER JOIN ( "
				+" SELECT a.strGRNCode AS GRNCode, b.strProdCode, SUM(b.dblQty) AS PRQty "
				+" FROM tblpurchasereturnhd a "
				+" INNER JOIN tblpurchasereturndtl b ON a.strPRCode = b.strPRCode AND b.strClientCode='"+clientCode+"' "
				+" WHERE (a.strAgainst = 'GRN') AND a.strClientCode='"+clientCode+"' "
				+" GROUP BY GRNCode, b.strProdCode) b ON a.strGRNCode = b.GRNCode AND a.strProdCode = b.strProdCode "
				+" LEFT OUTER "
				+" JOIN tblproductmaster c ON a.strProdCode=c.strProdCode AND c.strClientCode='"+clientCode+"' "
				+" WHERE a.dblQty > IFNULL(b.PRQty,0) AND a.strGRNCode='"+GrnCode+"' AND a.strClientCode='"+clientCode+"' AND p.strGRNCode=a.strGRNCode; ");

		System.out.println(sbsql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sbsql.toString());

		List list = query.list();
		return list;
	}
}
