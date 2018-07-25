package com.sanguine.excise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsTransportPassModel;
import com.sanguine.excise.model.clsTransportPassDtlModel;

@SuppressWarnings("rawtypes")
@Repository("clsTransportPassDao")
public class clsTransportPassDaoImpl implements clsTransportPassDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateTPMaster(clsTransportPassModel objMaster) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public boolean funAddUpdateTPDtl(clsTransportPassDtlModel objTPDtl) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.save(objTPDtl);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsTransportPassModel> funGetList(String clientCode) {
		List<clsTransportPassModel> ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = (List<clsTransportPassModel>) currentSession.createCriteria(clsTransportPassModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List funGetObject(String tpCode, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsTransportPassModel a,clsExciseSupplierMasterModel b, clsExciseLicenceMasterModel c " + "where a.strTPCode= :strTPCode and a.strSupplierCode=b.strSupplierCode and a.strLicenceCode=c.strLicenceCode and a.strClientCode= :clientCode");
			query.setParameter("strTPCode", tpCode);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List funGetTPDtlList(String tpCode, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsTransportPassDtlModel a,clsBrandMasterModel b, clsSizeMasterModel c  " + "where strTPCode= :Code and a.strBrandCode= b.strBrandCode and b.strSizeCode=c.strSizeCode and a.strClientCode= :clientCode");
			query.setParameter("Code", tpCode);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public boolean funDeleteHd(String tpCode, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsTransportPassModel where strTPCode = :tpCode " + "and strClientCode=:clientCode");
			query.setParameter("tpCode", tpCode);
			query.setParameter("clientCode", clientCode);
			query.executeUpdate();
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public boolean funDeleteDtl(String tpCode, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsTransportPassDtlModel where strTPCode = :tpCode " + "and strClientCode=:clientCode");
			query.setParameter("tpCode", tpCode);
			query.setParameter("clientCode", clientCode);
			query.executeUpdate();
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public List funGetTpNOObject(String tpNo, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsTransportPassModel a " + "where a.strTPNo= :strTPNo and a.strClientCode= :clientCode ");
			query.setParameter("strTPNo", tpNo);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
}
