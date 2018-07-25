package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsSalesRetrunTaxModel;
import com.sanguine.crm.model.clsSalesReturnDtlModel;
import com.sanguine.crm.model.clsSalesReturnHdModel;
import com.sanguine.crm.model.clsSalesReturnHdModel_ID;

@Repository("clsSalesReturnDao")
public class clsSalesReturnDaoImpl implements clsSalesReturnDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateSalesReturnHd(clsSalesReturnHdModel objHdModel) {
		boolean flgSaveHd = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
			flgSaveHd = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return flgSaveHd;
		}

	}

	public void funAddUpdateSalesReturnDtl(clsSalesReturnDtlModel objDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	public clsSalesReturnHdModel funGetSalesReturnHd(String srCode, String clientCode) {
		return (clsSalesReturnHdModel) sessionFactory.getCurrentSession().get(clsSalesReturnHdModel.class, new clsSalesReturnHdModel_ID(srCode, clientCode));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void funDeleteDtl(String srCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsSalesReturnDtlModel " + " where strSRCode=:srCode and strClientCode=:clientCode " + "and strClientCode=:clientCode");
		query.setParameter("srCode", srCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	public List<Object> funGetSalesReturn(String srCode, String clientCode) {
		List<Object> objDCList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsSalesReturnHdModel a " + "	,clsLocationMasterModel d ,clsPartyMasterModel e " + "	where a.strSRCode=:srCode and " + " a.strCustCode=e.strPCode and " + " a.strLocCode = d.strLocCode and " + " a.strClientCode=:clientCode ");
		query.setParameter("srCode", srCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objDCList = list;

		}
		return objDCList;

	}

	public List<Object> funGetSalesReturnDtl(String srCode, String clientCode) {
		List<Object> objInvDtlList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsSalesReturnDtlModel a,clsProductMasterModel b " + "	where a.strSRCode=:srCode and a.strProdCode= b.strProdCode and a.strClientCode=:clientCode " + "and b.strClientCode=:clientCode ");
		query.setParameter("srCode", srCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objInvDtlList = list;

		}

		return objInvDtlList;

	}

	public void funDeleteTax(String srCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("delete from clsSalesRetrunTaxModel where strSRCode=:srCode and strClientCode=:clientCode ");
		query.setParameter("srCode", srCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	public void funAddTaxDtl(clsSalesRetrunTaxModel objTaxDtl) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTaxDtl);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List funGetInvDtlList(String invCode, String clientCode) {
		
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT c.strProdCode,a.strInvCode,a.dblQty, IFNULL(b.SRQty,0), a.dblQty - IFNULL(b.SRQty,0), a.dblUnitPrice, a.dblWeight,c.strUOM,c.strProdName,"
				+ " IF(LENGTH(c.strExpDate)=0,'N',c.strExpDate),a.dblProdDiscAmount, IFNULL(c.strNonStockableItem,''),"
				+ " IFNULL(p.dblCurrencyConv,1), IFNULL(p.strCurrencyCode,''),c.strProdType,a.strRemarks,a.strPktNo "
				+" FROM tblinvoicehd p, tblinvoicedtl a "
				+" LEFT OUTER JOIN ( "
				+" SELECT a.strDCCode AS INVCode, b.strProdCode, SUM(b.dblQty) AS SRQty "
				+" FROM tblsalesreturnhd a "
				+" INNER JOIN tblsalesreturndtl b ON a.strSRCode = b.strSRCode AND b.strClientCode='"+clientCode+"' "
				+" WHERE (a.strAgainst = 'Invoice') AND a.strClientCode='"+clientCode+"' "
				+" GROUP BY INVCode, b.strProdCode) b ON a.strInvCode = b.INVCode AND a.strProdCode = b.strProdCode "
				+" LEFT OUTER JOIN tblproductmaster c ON a.strProdCode=c.strProdCode AND c.strClientCode='"+clientCode+"' "
				+" WHERE a.dblQty > IFNULL(b.SRQty,0) "
 				+" AND a.strInvCode='"+invCode+"' AND a.strClientCode='"+clientCode+"' AND p.strInvCode=a.strInvCode;");

		System.out.println(sbsql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sbsql.toString());

		List list = query.list();
		return list;
		
	}

}
