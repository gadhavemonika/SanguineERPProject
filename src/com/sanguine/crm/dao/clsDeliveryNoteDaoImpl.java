package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.crm.model.clsDeliveryNoteDtlModel;
import com.sanguine.crm.model.clsDeliveryNoteHdModel;

@Repository("clsDeliveryNoteDao")
public class clsDeliveryNoteDaoImpl implements clsDeliveryNoteDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Boolean funAddUpdateDeliveryNoteHd(clsDeliveryNoteHdModel objHdModel) {
		Boolean success = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objHdModel);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public Boolean funAddUpdateDeliveryNoteDtl(clsDeliveryNoteDtlModel objDtlModel) {
		Boolean success = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objDtlModel);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List funGetDelNoteHdObject(String DNCode, String clientCode) {

		List<Object> list = new ArrayList<Object>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsDeliveryNoteHdModel a,clsPartyMasterModel b " + "where a.strDNCode = '" + DNCode + "' and a.strSCCode=b.strPCode and " + " a.strClientCode ='" + clientCode + "' and b.strClientCode ='" + clientCode + "'");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List funGetDelNoteDtlList(String DNCode, String clientCode) {
		List<Object> list = new ArrayList<Object>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"from clsDeliveryNoteDtlModel a,clsProductMasterModel b,clsProcessMasterModel c " + "where a.strDNCode = '" + DNCode + "' and a.strProdCode=b.strProdCode and  a.strProcessCode= c.strProcessCode and " + " a.strClientCode ='" + clientCode + "' and b.strClientCode ='" + clientCode + "' and c.strClientCode ='" + clientCode + "' ");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean funDeleteDtl(String DNCode, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsDeliveryNoteDtlModel where strDNCode = :strDNCode " + "and strClientCode=:clientCode");
			query.setParameter("strDNCode", DNCode);
			query.setParameter("clientCode", clientCode);
			query.executeUpdate();
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

}
