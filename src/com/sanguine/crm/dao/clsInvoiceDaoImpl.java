package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsInvoiceGSTModel;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceHdModel_ID;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.crm.model.clsInvSettlementdtlModel;
import com.sanguine.crm.model.clsInvSettlementdtlModel_ID;
import com.sanguine.model.clsPurchaseOrderHdModel;

@Repository("clsInvoiceDao")
public class clsInvoiceDaoImpl implements clsInvoiceDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateInvoiceHd(clsInvoiceHdModel objHdModel) {
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

	public void funAddUpdateInvoiceDtl(clsInvoiceModelDtl objDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
	}

	public clsInvoiceHdModel funGetInvoiceHd(String invCode, String clientCode) {
		clsInvoiceHdModel objInvoiceHdModel = (clsInvoiceHdModel) sessionFactory.getCurrentSession().get(clsInvoiceHdModel.class, new clsInvoiceHdModel_ID(invCode, clientCode));
		objInvoiceHdModel.getListInvDtlModel();
		objInvoiceHdModel.getListInvTaxDtlModel();
		return objInvoiceHdModel;
	}

	@Override
	public void funDeleteDtl(String invCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from tblinvoicedtl " + " where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "' ");
		query.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> funGetInvoice(String invCode, String clientCode) {
		List<Object> objDCList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from  clsInvoiceHdModel a " + "	,clsLocationMasterModel d ,clsPartyMasterModel e " + "	where a.strInvCode=:invCode and " + " a.strCustCode=e.strPCode and " + " a.strLocCode = d.strLocCode and " + " a.strClientCode=:clientCode ");
		query.setParameter("invCode", invCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objDCList = list;

		}
		return objDCList;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public clsInvoiceHdModel funGetInvoiceDtl(String invCode, String clientCode) {

		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsInvoiceHdModel.class);
		cr.add(Restrictions.eq("strInvCode", invCode));
		cr.add(Restrictions.eq("strClientCode", clientCode));

		List list = cr.list();

		clsInvoiceHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsInvoiceHdModel) list.get(0);
			objModel.getListInvDtlModel().size();
			objModel.getListInvProdTaxDtlModel().size();
			objModel.getListInvTaxDtlModel().size();
			objModel.getListVoidedProdInvModel().size();
		}

		return objModel;

	}

	public void funDeleteTax(String invCode, String clientCode) {

		Query query = sessionFactory.getCurrentSession().createQuery("delete from clsInvoiceTaxDtlModel where strInvCode=:invCode and strClientCode=:clientCode ");
		query.setParameter("invCode", invCode);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	public void funAddTaxDtl(clsInvoiceTaxDtlModel objInvoiceTaxDtl) {
		sessionFactory.getCurrentSession().saveOrUpdate(objInvoiceTaxDtl);
	}

	@Override
	public List funListSOforInvoice(String strlocCode, String dtFullFilled, String clientCode, String custCode) {

		String sql = "  select a.strSOCode,a.dteSODate,b.strLocName,c.strPName,a.strStatus " 
			+ " from tblsalesorderhd a,tbllocationmaster b,tblpartymaster c " 
			+ " where a.strLocCode=b.strLocCode and a.strCustCode=c.strPCode and c.strPType='cust' " 
			+ " and a.strSOCode NOT In(select strSOCode from tblinvsalesorderdtl) and a.strClientCode='" + clientCode + "' ";
		if (!custCode.equals("")) {
			sql += " and a.strCustCode='" + custCode + "' ";
		}

		if (!strlocCode.equals("")) {
			sql = sql + " and a.strLocCode='" + strlocCode + "' ";
		}
		if (!dtFullFilled.equals("")) {
			sql = sql + " and a.dteFulmtDate='" + dtFullFilled + "' ";
		}

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;

	}

	public void funDeleteHDDtl(String invCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from tblinvoicehd " + " where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "' ");
		query.executeUpdate();
	}

	@Override
	public List funGetHdList(String fromDate, String toDate, String clientCode) {
		String sql = "from clsInvoiceHdModel where dteInvDate  >= :fromDate and dteInvDate < :toDate and strClientCode=:clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("clientCode", clientCode);
		List<clsPurchaseOrderHdModel> list = query.list();
		return list;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateInvoiceGST(clsInvoiceGSTModel objHdModel) {
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

	@Override
	public void funAddUpdateInvSettlementdtl(clsInvSettlementdtlModel objMaster) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
	}

	@Override
	public clsInvSettlementdtlModel funGetInvSettlementdtl(String strInvNo, String strSettlementCode, String strClientCode, String dteInvDate) {
		return (clsInvSettlementdtlModel) sessionFactory.getCurrentSession().get(clsInvSettlementdtlModel.class, new clsInvSettlementdtlModel_ID(strInvNo, strSettlementCode, strClientCode, dteInvDate));
	}

	@Override
	public List<clsInvSettlementdtlModel> funGetListInvSettlementdtl(String strInvCode, String strClientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from  clsInvSettlementdtlModel where strInvCode='" + strInvCode + "' and strClientCode='" + strClientCode + "' ");
		List list = query.list();
		return list;
	}

	@Override
	public List<clsInvSettlementdtlModel> funGetListInvSettlementdtl(String strInvCode, String dteInvDate, String strClientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from  clsInvSettlementdtlModel where strInvCode='" + strInvCode + "' and dteInvDate='" + dteInvDate + "' and strClientCode='" + strClientCode + "' ");
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String SACode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsInvoiceHdModel a " + "where a.strInvCode= :saCode and a.strClientCode= :clientCode ");
		query.setParameter("saCode", SACode);
		query.setParameter("clientCode", clientCode);
		return query.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetDtlList(String SACode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsInvoiceModelDtl a " + "where a.strInvCode = :saCode ");
		query.setParameter("saCode", SACode);

		List list = query.list();
		return list;
	}

}
