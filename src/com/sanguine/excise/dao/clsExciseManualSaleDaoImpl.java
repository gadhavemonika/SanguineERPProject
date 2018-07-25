package com.sanguine.excise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExciseManualSaleDtlModel;
import com.sanguine.excise.model.clsExciseManualSaleHdModel;

@Repository("clsExciseManualSaleDao")
public class clsExciseManualSaleDaoImpl implements clsExciseManualSaleDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateExciseSalesMaster(clsExciseManualSaleHdModel objMaster) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(long intId, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseManualSaleHdModel a, clsExciseLicenceMasterModel b" + " where a.intId= :intId and a.strLicenceCode=b.strLicenceCode and b.strClientCode= :clientCode and a.strClientCode= :clientCode");
			query.setParameter("intId", intId);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public boolean funAddUpdateSalesDtl(clsExciseManualSaleDtlModel objSalesDtl) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.save(objSalesDtl);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetList(String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsExciseManualSaleHdModel.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetSalesDtlList(long intId, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseManualSaleDtlModel a, clsBrandMasterModel b " + "where a.intSalesHd= :intSalesHd and a.strBrandCode=b.strBrandCode and a.strClientCode= :clientCode");
			query.setParameter("intSalesHd", intId);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public boolean funDeleteHd(long intId, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsExciseManualSaleHdModel where intId = :intId " + "and strClientCode=:clientCode");
			query.setParameter("intId", intId);
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
	public boolean funDeleteDtl(long intId, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsExciseManualSaleDtlModel where intSalesHd = :intSalesHd  " + "and strClientCode=:clientCode");
			query.setParameter("intSalesHd", intId);
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
	public boolean funDeleteSaleData(long intId, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsExciseSaleModel where strSalesCode = :strSalesCode " + "and strClientCode=:clientCode");
			query.setParameter("strSalesCode", "" + intId);
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
