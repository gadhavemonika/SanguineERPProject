package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSalesCharModel;
import com.sanguine.crm.model.clsSalesCharModel_ID;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.model.clsSalesOrderHdModel_ID;
import com.sanguine.crm.model.clsSalesOrderTaxDtlModel;
import com.sanguine.model.clsLocationMasterModel;

@Repository("clsSalesOrderHdDao")
public class clsSalesOrderHdDaoImpl implements clsSalesOrderHdDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdate(clsSalesOrderHdModel object) {
		boolean saveFlg = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(object);
			saveFlg = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return saveFlg;
		}
	}

	@Override
	public clsSalesOrderHdModel funGetSales(String docCode, String clientCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void funAddUpdateDtl(clsSalesOrderDtl object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	public clsSalesOrderHdModel funGetSalesOrderHd(String soCode, String clientCode) {

		return (clsSalesOrderHdModel) sessionFactory.getCurrentSession().get(clsSalesOrderHdModel.class, new clsSalesOrderHdModel_ID(soCode, clientCode));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> funGetSalesOrder(String soCode, String clientCode) {

		List<Object> objSalesList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsSalesOrderHdModel a " + "	,clsLocationMasterModel d ,clsPartyMasterModel e " + "	where a.strSOCode=:soCode and " + " a.strCustCode=e.strPCode and " + " a.strLocCode = d.strLocCode and " + " a.strClientCode=:clientCode ");
		query.setParameter("soCode", soCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objSalesList = list;

		}
		return objSalesList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> funGetSalesOrderDtl(String soCode, String clientCode) {
		List<Object> objSalesDtlList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsSalesOrderDtl a,clsProductMasterModel b " + "	where a.strSOCode=:soCode and a.strProdCode= b.strProdCode and a.strClientCode=:clientCode " + "and b.strClientCode=:clientCode ");
		query.setParameter("soCode", soCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objSalesDtlList = list;

		}

		return objSalesDtlList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void funDeleteDtl(String soCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsSalesOrderDtl " + " where strSOCode=:soCode and strClientCode=:clientCode " + "and strClientCode=:clientCode");
		query.setParameter("soCode", soCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void funUpdateSOforPO(String soCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("update clsSalesOrderHdModel set strCloseSO='Y'  " + " where strSOCode=:soCode and strClientCode=:clientCode " + "and strClientCode=:clientCode");
		query.setParameter("soCode", soCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "finally" })
	@Override
	public List funGetMultipleSODtl(String[] soCodes, String clientCode) {
		String soCode = "";
		for (int i = 0; i < soCodes.length; i++) {
			if (soCode.length() > 0) {
				soCode = soCode + " or a.strSOCode='" + soCodes[i] + "' ";
			} else {
				soCode = "a.strSOCode='" + soCodes[i] + "' ";
			}

		}

		List objSalesList = null;
		String sql = " select b.strProdCode,c.strProdName,c.strProdType,Sum(b.dblAcceptQty),c.dblCostRM ," + " c.dblWeight " + " from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c " + " where a.strSOCode=b.strSOCode " + " and b.strProdCode=c.strProdCode and a.strCloseSO='N' " + " and ( " + soCode + " ) " + " and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='"
				+ clientCode + "' " + " and c.strClientCode='" + clientCode + "' " + " group by b.strProdCode ";

		try {
			objSalesList = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			return objSalesList;
		}

	}

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateSaleChar(clsSalesCharModel object) {
		boolean saveFlg = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(object);
			saveFlg = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return saveFlg;
		}
	}

	public void funDeleteSalesChar(String soCode, String prodCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from tblsaleschar " + " where strSOCode='" + soCode + "' and strProdCode='" + prodCode + "' ");
		int i = query.executeUpdate();

	}

	@SuppressWarnings("finally")
	public List funGetSalesChar(String soCode, String prodCode) {
		List list = null;
		try {
			Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsSalesCharModel.class);
			cr.add(Restrictions.eq("strSOCode", soCode));
			cr.add(Restrictions.eq("strProdCode", prodCode));
			list = cr.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return list;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "finally" })
	@Override
	public List funGetMultipleSODtlForInvoice(String[] soCodes, String clientCode) {
		String soCode = "";
		for (int i = 0; i < soCodes.length; i++) {
			if (soCode.length() > 0) {
				soCode = soCode + " or a.strSOCode='" + soCodes[i] + "' ";
			} else {
				soCode = "a.strSOCode='" + soCodes[i] + "' ";
			}
		}

		List objSalesList = null;
		String sql = " select b.strProdCode,c.strProdName,c.strProdType,sum(b.dblAcceptQty),b.dblUnitPrice , b.dblWeight,a.strCustCode" 
			+ " ,d.dblDiscount,a.strSOCode,b.dblDiscount,ifnull(a.strCurrency,''),ifnull(a.dblConversion,1.0)  " 
			+ " from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblpartymaster d " 
			+ " where a.strSOCode=b.strSOCode and b.strProdCode=c.strProdCode and ( " + soCode + " ) " + "  and a.strCustCode=d.strPCode and a.strClientCode='"
			+ clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " 
			+ " group by a.strCustCode,b.strProdCode ";

		/*
		 * String sql =
		 * " select b.strProdCode,c.strProdName,c.strProdType,b.dblAcceptQty,c.dblCostRM ,"
		 * + " c.dblWeight,a.strCustCode,d.dblDiscount,a.strSOCode " +
		 * " from tblsalesorderhd a,tblsalesorderdtl b,tblproductmaster c,tblpartymaster d "
		 * + " where a.strSOCode=b.strSOCode " +
		 * " and b.strProdCode=c.strProdCode " + " and ( "+soCode+" ) and  "
		 * +" and a.strCustCode=d.strPCode " +
		 * " and a.strClientCode='"+clientCode+"' " +
		 * " and b.strClientCode='"+clientCode+"' " +
		 * " and c.strClientCode='"+clientCode+"' ";
		 */

		try {
			objSalesList = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			return objSalesList;
		}
	}

	@Override
	public List funGetHdList(String fDate, String tDate, String clientCode) {
		String hql = " from  clsSalesOrderHdModel   where dteSODate between :fDate and :tDate " + " and  strClientCode=:clientCode ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("fDate", fDate);
		query.setParameter("tDate", tDate);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		return list;

	}
	
	@Override
	public int funDeleteSalesOrderTaxDtl(String strSOCode, String clientCode) {
		String sql = "DELETE clsSalesOrderTaxDtlModel WHERE strSOCode= :strSOCode and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("strSOCode", strSOCode);
		query.setParameter("clientCode", clientCode);
		return query.executeUpdate();
	}
	
	
	public void funAddUpdateSoTaxDtl(clsSalesOrderTaxDtlModel objTaxDtlModel){
		sessionFactory.getCurrentSession().saveOrUpdate(objTaxDtlModel);
}

}
