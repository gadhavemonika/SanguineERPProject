package com.sanguine.excise.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExciseStkPostingDtlModel;
import com.sanguine.excise.model.clsExciseStkPostingHdModel;

@Repository("clsExciseStkPostingDao")
public class clsExciseStkPostingDaoImpl implements clsExciseStkPostingDao {
	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdate(clsExciseStkPostingHdModel object) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(object);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;

	}

	@Override
	public boolean funAddUpdateDtl(List<clsExciseStkPostingDtlModel> objList) {
		Session currentSession = exciseSessionFactory.openSession();
		Transaction tx = null;
		Boolean result = false;
		try {
			tx = currentSession.beginTransaction();
			for (int i = 0; i < objList.size(); i++) {
				clsExciseStkPostingDtlModel obj = objList.get(i);
				currentSession.save(obj);
				if (i % 50 == 0) {
					currentSession.flush();
					currentSession.clear();
				}
			}
			tx.commit();
			result = true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			result = false;
		} finally {
			currentSession.close();
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsExciseStkPostingHdModel> funGetList(String clientCode) {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseStkPostingHdModel where strClientCode= :clientCode");
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String strPSPCode, String clientCode) {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseStkPostingHdModel a,clsExciseLicenceMasterModel b " + " where a.strPSPCode= :strPSPCode and a.strLicenceCode=b.strLicenceCode " + " and a.strClientCode= :clientCode and b.strClientCode= :licClientCode");
			query.setParameter("strPSPCode", strPSPCode);
			query.setParameter("clientCode", clientCode);
			query.setParameter("licClientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetDtlList(String strPSPCode, String clientCode, String tempSizeClientCode, String tempBrandClientCode) {
		List ls = new LinkedList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseStkPostingDtlModel a,clsBrandMasterModel b, clsSizeMasterModel c " + " where a.strPSPCode = :strPSPCode and a.strBrandCode=b.strBrandCode " + " and b.strSizeCode = c.strSizeCode and a.strClientCode= :clientCode" + " and b.strClientCode= :tempBrandClientCode and c.strClientCode= :tempSizeClientCode");
			query.setParameter("strPSPCode", strPSPCode);
			query.setParameter("clientCode", clientCode);
			query.setParameter("tempBrandClientCode", tempBrandClientCode);
			query.setParameter("tempSizeClientCode", tempSizeClientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ls;
	}

	@SuppressWarnings("unused")
	public void funDeleteDtl(String strPSPCode, String clientCode) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsExciseStkPostingDtlModel where strPSPCode = :strPSPCode and strClientCode= :clientCode");
			query.setParameter("strPSPCode", strPSPCode);
			query.setParameter("clientCode", clientCode);
			int result = query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
