package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsBillPassingTaxDtlModel;
import com.sanguine.model.clsPOTaxDtlModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.model.clsPurchaseOrderHdModel;

@Repository("clsPurchaseOrderDao")
public class clsPurchaseOrderDaoImpl implements clsPurchaseOrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdatePurchaseOrderHd(clsPurchaseOrderHdModel PurchaseOrderHdModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(PurchaseOrderHdModel);

	}

	@Override
	public void funAddUpdatePurchaseOrderDtl(clsPurchaseOrderDtlModel PurchaseOrderDtlModel) {
		sessionFactory.getCurrentSession().save(PurchaseOrderDtlModel);

	}

	@Override
	public void funDeletePODtl(String POCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE clsPurchaseOrderDtlModel WHERE strPOCode= :POCode and strClientCode=:clientCode");
		query.setParameter("POCode", POCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	@Override
	public clsPurchaseOrderHdModel funGetObject(String POCode, String clientCode) {
		clsPurchaseOrderHdModel ob = null;
		String sql = "from clsPurchaseOrderHdModel where strPOCode=:poCode and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("poCode", POCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		if (list.size() == 0) {
			return ob;
		} else {
			return (clsPurchaseOrderHdModel) list.get(0);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetDtlList(String POCode, String clientCode) {
		String sql = "select a.strProdCode,a.dblOrdQty,a.dblPrice,a.dblWeight,a.strProdChar,a.strRemarks,a.strPICode," + " a.dblDiscount,a.strAmntNO,a.strUpdate,a.dtDelDate,c.strProdName,c.strUOM,c.dblCostRM,d.strPCode,d.strPName,a.dblAmount " + " from clsPurchaseOrderDtlModel a,clsPurchaseOrderHdModel b,clsProductMasterModel c,clsSupplierMasterModel d "
				+ " where a.strPOCode=:POCode and a.strPOCode=b.strPOCode and b.strSuppCode=d.strPCode " + " and a.strProdCode=c.strProdCode and a.strClientCode=:clientCode and b.strClientCode=:clientCode" + " and c.strClientCode=:clientCode and d.strClientCode=:clientCode";
		System.out.println(sql);
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("POCode", POCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List funGetPIData(String sql, String PICode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetHelpdataPIforPo(String clientCode, String strPropCode) {

		String sql = "select a.strPICode, date(a.dtPIDate),b.strLocName,a.strNarration " + "from tblpurchaseindendhd a,tbllocationmaster b where strPICode IN " + "(select a.strPICode from tblpurchaseindenddtl a left outer join " + "(select b.strPICode, b.strProdCode,sum(dblOrdQty) POQty " + " from tblpurchaseorderhd a,  tblpurchaseorderdtl b "
				+ "where a.strPOCode = b.strPOCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + "group by b.strPICode, b.strProdCode) as b on a.strPIcode = b.strPICode  and a.strProdCode = b.strProdCode " + "where a.strClientCode='" + clientCode + "' and a.dblQty > ifnull(b.POQty,0)) " + " and a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode
				+ "' " + "and b.strClientCode='" + clientCode + "' and b.strPropertyCode ='" + strPropCode + "'";

		// System.out.println(sql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}

	@Override
	public int funDeletePOTaxDtl(String poCode, String clientCode) {
		String sql = "DELETE clsPOTaxDtlModel WHERE strPOCode= :poCode and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("poCode", poCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}

	@Override
	public void funAddUpdatePOTaxDtl(clsPOTaxDtlModel objTaxDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTaxDtlModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsPurchaseOrderDtlModel> funGetPODtlList(String strPOCode, String clientCode) {
		String sql = "from clsPurchaseOrderDtlModel where strPOCode=:poCode and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("poCode", strPOCode);
		query.setParameter("clientCode", clientCode);
		List<clsPurchaseOrderDtlModel> list = query.list();
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List funGetHdList(String fDate, String tDate, String clientCode) {
		String hql = " from  clsPurchaseOrderHdModel   where dtPODate between :fDate and :tDate " + " and  strClientCode=:clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("fDate", fDate);
		query.setParameter("tDate", tDate);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		return list;

	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<clsPurchaseOrderDtlModel> funGetPODtlModelList(String POCode, String clientCode) {
		String hql = "from clsPurchaseOrderDtlModel a where a.strPOCode=:POCode and a.strClientCode=:clientCode";
		System.out.println(hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("POCode", POCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		return list;
	}

}
