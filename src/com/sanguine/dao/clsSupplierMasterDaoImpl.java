package com.sanguine.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsSupplierMasterModel_ID;

@Repository("clsSupplierMasterDao")
public class clsSupplierMasterDaoImpl implements clsSupplierMasterDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<clsSupplierMasterModel> funGetList(String clientCode) {
		String sql = "from clsSupplierMasterModel where strClientCode=:clientcode and strPType='supp' ";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("clientcode", clientCode);
		return (List<clsSupplierMasterModel>) query.list();

		// return (List<clsSupplierMasterModel>)
		// sessionFactory.getCurrentSession().createCriteria(clsSupplierMasterModel.class,clientCode).list();
	}

	public clsSupplierMasterModel funGetObject(String code, String clientCode) {
		return (clsSupplierMasterModel) sessionFactory.getCurrentSession().get(clsSupplierMasterModel.class, new clsSupplierMasterModel_ID(code, clientCode));
	}

	public void funAddUpdate(clsSupplierMasterModel objModel) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List funGetDtlList(String pCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsSupplierMasterModel");
		@SuppressWarnings("rawtypes")
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

	@Override
	public void funExciseUpdate(String partyCode, String clientCode, String exSuppCode) {
		String hqlUpdate = "update clsSupplierMasterModel a set a.strExcise = :exSuppCode where a.strPCode = :pCode and a.strClientCode = :clientCode ";
		// or String hqlUpdate =
		// "update Customer set name = :newName where name = :oldName";
		int updatedEntities = sessionFactory.getCurrentSession().createQuery(hqlUpdate).setString("clientCode", clientCode).setString("pCode", partyCode).setString("exSuppCode", exSuppCode).executeUpdate();
	}

}
