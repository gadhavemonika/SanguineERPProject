package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsProductionOrderDtlModel;
import com.sanguine.model.clsProductionOrderHdModel;
import com.sanguine.model.clsProductionOrderHdModel_ID;

@Repository("clsProductionOrderDao")
public class clsProductionOrderDaoImpl implements clsProductionOrderDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdateProductionHd(clsProductionOrderHdModel ProductionOrderHdModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(ProductionOrderHdModel);

	}

	@Override
	public void funAddUpdateProductionDtl(clsProductionOrderDtlModel ProductionOrderDtlModel) {
		sessionFactory.getCurrentSession().save(ProductionOrderDtlModel);
	}

	@Override
	public void funDeleteProductionOrderDtl(String OPCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE clsProductionOrderDtlModel WHERE strOPCode= :OPCode");
		query.setParameter("OPCode", OPCode);
		query.executeUpdate();

	}

	@Override
	public clsProductionOrderHdModel funGetObject(String OPCode, String clientCode) {

		return (clsProductionOrderHdModel) sessionFactory.getCurrentSession().get(clsProductionOrderHdModel.class, new clsProductionOrderHdModel_ID(OPCode, clientCode));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsProductionOrderDtlModel> funGetDtlList(String OPCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("FROM clsProductionOrderDtlModel a, clsProductMasterModel p WHERE a.strOPCode= :OPCode AND a.strClientCode= :clientCode and p.strProdCode=a.strProdCode");
		query.setParameter("OPCode", OPCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsProductionOrderDtlModel>) list;
	}

	@Override
	public List funListSOforProductionOrder(String strlocCode, String dtFullFilled, String clientCode, String orderType) {

		String sql = "  select a.strSOCode,a.dteSODate,b.strLocName,c.strPName,a.strStatus " + " from tblsalesorderhd a,tbllocationmaster b,tblpartymaster c " + " where a.strLocCode=b.strLocCode and a.strCustCode=c.strPCode and c.strPType='cust' " + " and a.strSOCode NOT IN( select strSOCode from tblinvsalesorderdtl) and a.strClientCode='" + clientCode + "' ";
		if (!strlocCode.equals("")) {
			sql = sql + " and a.strLocCode='" + strlocCode + "' ";
		}
		if (!dtFullFilled.equals("")) {
			sql = sql + " and a.dteFulmtDate='" + dtFullFilled + "' ";
		}

		if (!orderType.equals("All")) {
			sql = sql + " and a.strStatus='" + orderType + "'  ";
		}

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;

	}

	@SuppressWarnings("finally")
	public boolean funUpdateProductionOrderAginstMaterialProcution(String strOPCode, String clientCode) {
		boolean flgUpdate = false;

		String sql = " Update tblproductionorderhd set strStatus ='Y' where  strOPCode='" + strOPCode + "' and  strClientCode ='" + clientCode + "' ";

		try {
			int num = sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
			flgUpdate = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return flgUpdate;
		}

	}

}
