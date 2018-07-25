package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsMRPIDtl;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPurchaseIndentDtlModel;
import com.sanguine.model.clsPurchaseIndentHdModel;
import com.sanguine.model.clsPurchaseIndentHdModel_ID;

@Repository("clsPurchaseIndentHdDao")
public class clsPurchaseIndentHdDaoImpl implements clsPurchaseIndentHdDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdatePurchaseIndent(clsPurchaseIndentHdModel PurchaseIndentHdModel) {

		sessionFactory.getCurrentSession().saveOrUpdate(PurchaseIndentHdModel);
	}

	@Override
	public void funAddUpdatePurchaseIndentDtl(clsPurchaseIndentDtlModel PurchaseIndentDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(PurchaseIndentDtlModel);

	}

	@Override
	public List<clsPurchaseIndentHdModel> funListPurchaseIndentHdModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public clsPurchaseIndentHdModel funGetPurchaseIndent(String PurchaseIndentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void funDeleteDtl(String PICode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete  clsPurchaseIndentDtlModel where strPIcode = :strPIcode");
		query.setParameter("strPIcode", PICode);
		int result = query.executeUpdate();
		System.out.println("Result=" + result);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetDtlList(String PICode, String clientCode, String strLocCode) {

		String sql = "from clsPurchaseIndentDtlModel a,clsProductMasterModel b,clsProductReOrderLevelModel c " + "where a.strPICode = :PICode and a.strProdCode=b.strProdCode and b.strProdCode=c.strProdCode " + "and c.strProdCode=a.strProdCode and c.strLocationCode=:strLocCode and c.strClientCode= :clientCode " + "and a.strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("PICode", PICode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("strLocCode", strLocCode);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("finally")
	@Override
	public clsPurchaseIndentHdModel funGetObject(String PICode, String clientCode) {
		clsPurchaseIndentHdModel objModel = null;
		try {
			objModel = (clsPurchaseIndentHdModel) sessionFactory.getCurrentSession().get(clsPurchaseIndentHdModel.class, new clsPurchaseIndentHdModel_ID(PICode, clientCode));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			return objModel;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public clsProductReOrderLevelModel funGetReOrderLevel(String strProdCode, String strLocCode, String clientCode) {
		String sql = "from clsProductReOrderLevelModel where strProdCode=:strProdCode and strLocationCode=:strLocCode and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("strProdCode", strProdCode);
		query.setParameter("clientCode", clientCode);
		query.setParameter("strLocCode", strLocCode);
		List list = query.list();
		if (!list.isEmpty()) {
			return (clsProductReOrderLevelModel) list.get(0);
		} else {
			return new clsProductReOrderLevelModel();
		}

	}

}
