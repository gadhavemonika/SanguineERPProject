package com.sanguine.crm.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsPartyMasterModel_ID;
import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;

@Repository("clsPartyMasterDao")
public class clsPartyMasterDaoImpl implements clsPartyMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsPartyMasterModel> funGetList() {
		return (List<clsPartyMasterModel>) sessionFactory.getCurrentSession().createCriteria(clsPartyMasterModel.class).list();
	}

	public clsPartyMasterModel funGetObject(String code, String clientCode) {
		return (clsPartyMasterModel) sessionFactory.getCurrentSession().get(clsPartyMasterModel.class, new clsPartyMasterModel_ID(code, clientCode));
	}

	public void funAddUpdate(clsPartyMasterModel objModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objModel);
	}

	@SuppressWarnings("finally")
	public long funGetLastNo(String tableName, String masterName, String columnName)

	{
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

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String pCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsPartyMasterModel");
		List list = query.list();
		return list;
	}

	@Override
	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator) {
		sessionFactory.getCurrentSession().save(objPartyTaxIndicator);

	}

	@Override
	public void funDeletePartyTaxDtl(String partyCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsPartyTaxIndicatorDtlModel where strPCode = '" + partyCode + "' and strClientCode='" + clientCode + "'");

		int result = query.executeUpdate();

	}

	@SuppressWarnings("finally")
	@Override
	public clsPartyMasterModel funGetPartyDtl(String pCode, String clientCode) {
		clsPartyMasterModel objPartyMaster = new clsPartyMasterModel();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsPartyMasterModel");
			List list = query.list();
			objPartyMaster = (clsPartyMasterModel) list.get(0);
		} catch (Exception ex) {
			objPartyMaster = null;
			ex.printStackTrace();

		} finally {
			return objPartyMaster;
		}

	}

	@SuppressWarnings("unchecked")
	public List<clsPartyMasterModel> funGetListCustomer(String clientCode) {
		String sql = "from clsPartyMasterModel where strClientCode=:clientcode and strPType='cust' ";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("clientcode", clientCode);
		return (List<clsPartyMasterModel>) query.list();
	}

	public List<clsPartyMasterModel> funGetLinkLocCustomer(String locCode, String clientCode) {
		String sql = "from clsPartyMasterModel where strClientCode=:clientcode and strPType='cust' and strLocCode=:locCode  ";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("locCode", locCode);

		query.setParameter("clientcode", clientCode);

		return (List<clsPartyMasterModel>) query.list();

	}

}
