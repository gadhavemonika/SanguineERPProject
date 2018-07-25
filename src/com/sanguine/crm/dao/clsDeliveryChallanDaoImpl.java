package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanHdModel_ID;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;
import com.sanguine.crm.model.clsSalesOrderHdModel;
import com.sanguine.crm.model.clsSalesOrderHdModel_ID;

@Repository("clsDeliveryChallanDao")
public class clsDeliveryChallanDaoImpl implements clsDeliveryChallanDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateDeliveryChallanHd(clsDeliveryChallanHdModel objHdModel) {
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

	public void funAddUpdateDeliveryChallanDtl(clsDeliveryChallanModelDtl objDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	public clsDeliveryChallanHdModel funGetDeliveryChallanHd(String dcCode, String clientCode) {
		return (clsDeliveryChallanHdModel) sessionFactory.getCurrentSession().get(clsDeliveryChallanHdModel.class, new clsDeliveryChallanHdModel_ID(dcCode, clientCode));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void funDeleteDtl(String dcCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsDeliveryChallanModelDtl " + " where strDCCode=:dcCode and strClientCode=:clientCode " + "and strClientCode=:clientCode");
		query.setParameter("dcCode", dcCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	public List<Object> funGetDeliveryChallan(String dcCode, String clientCode) {
		List<Object> objDCList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsDeliveryChallanHdModel a " + "	,clsLocationMasterModel d ,clsPartyMasterModel e " + "	where a.strDCCode=:dcCode and " + " a.strCustCode=e.strPCode and " + " a.strLocCode = d.strLocCode and " + " a.strClientCode=:clientCode ");
		query.setParameter("dcCode", dcCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objDCList = list;

		}
		return objDCList;

	}

	public List<Object> funGetDeliveryChallanDtl(String dcCode, String clientCode) {
		List<Object> objDCDtlList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from clsDeliveryChallanModelDtl a,clsProductMasterModel b ,clsDeliveryChallanHdModel c " + "	where a.strDCCode=:dcCode and a.strProdCode= b.strProdCode and a.strClientCode=:clientCode  and a.strDCCode=c.strDCCode " + " and b.strClientCode=:clientCode ");
		query.setParameter("dcCode", dcCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objDCDtlList = list;

		}

		return objDCDtlList;
	}

}
