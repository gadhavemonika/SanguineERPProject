package com.sanguine.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.model.clsJOAllocationDtlModel;
import com.sanguine.crm.model.clsJOAllocationHdModel;

@Repository("clsJOAllocationDao")
public class clsJOAllocationDaoImpl implements clsJOAllocationDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean funAddUpdateJAHd(clsJOAllocationHdModel objMaster) {
		boolean success = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objMaster);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public clsJOAllocationHdModel funGetJAHdObject(String strJACode, String clientCode) {
		clsJOAllocationHdModel obj = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsJOAllocationHdModel a  where a.strJACode = '" + strJACode + "' and  a.strClientCode ='" + clientCode + "'");
			List list = query.list();
			if (list.size() > 0) {
				obj = (clsJOAllocationHdModel) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;

	}

	@Override
	public void funDeleteDtl(String strJAcode, String clientCode) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("delete clsJOAllocationDtlModel a  where a.strJACode = '" + strJAcode + "' and  a.strClientCode ='" + clientCode + "'");
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean funAddUpdateJADtl(clsJOAllocationDtlModel objMaster) {
		boolean success = false;
		try {
			sessionFactory.getCurrentSession().save(objMaster);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetJAHdData(String strJAcode, String clientCode) {
		List ls = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsJOAllocationHdModel a ,clsPartyMasterModel b" + " where a.strJACode = '" + strJAcode + "' and  a.strSCCode=b.strPCode " + " and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ");
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetJADtlData(String strJAcode, String clientCode) {
		List ls = new ArrayList();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from clsJOAllocationDtlModel a ,clsJobOrderModel b,clsProductMasterModel c " + " where a.strJACode = '" + strJAcode + "' and a.strJOCode=b.strJOCode " + " and b.strProdCode=c.strProdCode and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode");
			ls = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

}
