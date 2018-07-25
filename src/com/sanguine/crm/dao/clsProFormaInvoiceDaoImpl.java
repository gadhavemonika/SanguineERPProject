package com.sanguine.crm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsProFormaInvoiceHdModel;

@Repository("clsProFormaInvoiceDao")
public class clsProFormaInvoiceDaoImpl implements clsProFormaInvoiceDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> funGetInvoice(String invCode, String clientCode) {
		List<Object> objDCList = null;
		Query query = sessionFactory.getCurrentSession().createQuery(" from  clsProFormaInvoiceHdModel a " + "	,clsLocationMasterModel d ,clsPartyMasterModel e " + "	where a.strInvCode=:invCode and " + " a.strCustCode=e.strPCode and " + " a.strLocCode = d.strLocCode and " + " a.strClientCode=:clientCode ");
		query.setParameter("invCode", invCode);
		query.setParameter("clientCode", clientCode);
		List list = query.list();

		if (list.size() > 0) {
			objDCList = list;

		}
		return objDCList;

	}

	@SuppressWarnings("finally")
	@Override
	public boolean funAddUpdateProFormaInvoiceHd(clsProFormaInvoiceHdModel objHdModel) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public clsProFormaInvoiceHdModel funGetProFormaInvoiceDtl(String invCode, String clientCode)
	{
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(clsProFormaInvoiceHdModel.class);
		cr.add(Restrictions.eq("strInvCode", invCode));
		cr.add(Restrictions.eq("strClientCode", clientCode));

		List list = cr.list();

		clsProFormaInvoiceHdModel objModel = null;
		if (list.size() > 0) {
			objModel = (clsProFormaInvoiceHdModel) list.get(0);
			objModel.getListInvDtlModel().size();
			objModel.getListInvProdTaxDtlModel().size();
			objModel.getListInvTaxDtlModel().size();
		}

		return objModel;
	}
}
