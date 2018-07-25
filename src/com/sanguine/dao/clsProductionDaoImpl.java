package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsProductionDtlModel;
import com.sanguine.model.clsProductionHdModel;
import com.sanguine.model.clsProductionHdModel_ID;

@Repository("clsProductionDao")
public class clsProductionDaoImpl implements clsProductionDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		// TODO Auto-generated method stub
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

	@SuppressWarnings("finally")
	@Override
	public boolean funAddPDHd(clsProductionHdModel PDHd) {
		boolean flgSave = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(PDHd);
			flgSave = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			flgSave = false;
		} finally {
			return flgSave;
		}

	}

	@Override
	public void funAddUpdatePDDtl(clsProductionDtlModel PDDtl) {

		sessionFactory.getCurrentSession().saveOrUpdate(PDDtl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsProductionHdModel> funGetList() {

		return (List<clsProductionHdModel>) sessionFactory.getCurrentSession().createCriteria(clsProductionHdModel.class).list();
	}

	@Override
	public clsProductionHdModel funGetObject(String code, String clientCode) {

		return (clsProductionHdModel) sessionFactory.getCurrentSession().get(clsProductionHdModel.class, new clsProductionHdModel_ID(code, clientCode));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetDtlList(String PDCode, String clientCode) {
		// select
		// pd.strProdCode,p.strProdName,pd.strProcessCode,pd.dblQtyProd,pd.dblQtyRej,pd.dblWeight,pd.dblPrice,pd.dblActTime
		// Query query =
		// sessionFactory.getCurrentSession().createQuery("from clsProductionDtlModel where a.strSACode = :saCode and a.strProdCode=b.strProdCode and a.strClientCode= :clientCode");
		Query query = sessionFactory.getCurrentSession().createQuery("from clsProductionDtlModel pd ,clsProductMasterModel p,clsProdProcessModel prodProcess,clsProcessMasterModel process where pd.strPDCode = :pdCode and pd.strProdCode = p.strProdCode and pd.strClientCode= :clientCode and prodProcess.strProcessCode=process.strProcessCode and prodProcess.strProdProcessCode=pd.strProdCode ");
		query.setParameter("pdCode", PDCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return list;
		// return (List<clsProductionDtlModel>) list;
	}

	@Override
	public void funDeleteDtl(String PDCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("delete clsProductionDtlModel where strPDCode = :pdCode and strClientCode= :clientCode");
		query.setParameter("pdCode", PDCode);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
		System.out.println("Result=" + result);
	}

	@SuppressWarnings({ "rawtypes", "null" })
	@Override
	public List funGetWOHdData(String workOrderCode, String clientCode) {
		// Query query =
		// sessionFactory.getCurrentSession().createQuery("from clsWorkOrderHdModel wo,clsWorkOrderDtlModel woDtl ,clsProcessMasterModel Pr"
		// + " ,clsProductMasterModel p "
		// +
		// " where wo.strWOCode=woDtl.strWOCode and   wo.strProdCode = p.strProdCode and woDtl.strProcessCode=Pr.strProcessCode "
		// + " and wo.strWOCode=:WoCode and wo.strClientCode=:clientCode");
		List list = null;
		String sql = " select wo.strProdCode,p.strProdName," + " woDtl.strProcessCode,pr.strProcessName,p.dblWeight,wo.dblQty,p.strUOM,p.dblCostRM " + " from tblworkorderhd wo,tblworkorderdtl woDtl,tblprocessmaster pr, tblproductmaster p " + " where wo.strWOCode ='" + workOrderCode + "' and wo.strWOCode=woDtl.strWOCode "
				+ " and  wo.strProdCode = p.strProdCode  and woDtl.strProcessCode=pr.strProcessCode " + " and wo.strClientCode='" + clientCode + "'";
		list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		if (null != list || list.isEmpty()) {
			return list;
		} else {
			return list;
		}

	}

	@Override
	public int funUpdateWorkOrderStatus(String strWOCode, String strStatus, String strUser, String dteModifieddate, String strClientCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("update tblworkorderhd set strStatus='" + strStatus + "', strUserModified='" + strUser + "',dtLastModified='" + dteModifieddate + "' where strWOCode='" + strWOCode + "' and strClientCode='" + strClientCode + "'");
		int result = query.executeUpdate();
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Double funGetPdProdQty(String workOrderCode, String ProdCode, String clientCode) {
		String sql = "select ifnull(sum(a.dblQtyProd),0) from tblproductiondtl a, tblproductionhd b " + " where a.strPDCode=b.strPDCode and b.strWOCode='" + workOrderCode + "'  and a.strProdCode='" + ProdCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
		List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		if (!list.isEmpty()) {
			return Double.parseDouble(list.get(0).toString());
		} else {
			return 0.0;
		}
	}

	@SuppressWarnings("finally")
	public List funGetWorkOrdersComplete(String[] woCodes, String clientCode) {
		String codes = "";
		for (int i = 0; i < woCodes.length; i++) {
			if (codes.length() > 0) {
				codes = codes + " or a.strSOCode='" + woCodes[i] + "' ";
			} else {
				codes = "a.strSOCode='" + woCodes[i] + "' ";
			}

		}

		List list = null;
		String sql = " select a.strPDCode from tblproductionhd a " + " where " + codes + " " + " and a.strClientCode='" + clientCode + "'  ";
		try {
			list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		} catch (Exception ex) {

			ex.printStackTrace();
		} finally {
			return list;
		}
	}

}
