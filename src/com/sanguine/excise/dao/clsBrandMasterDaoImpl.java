package com.sanguine.excise.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsRateMasterModel;
import com.sanguine.excise.model.clsRateMasterModel_ID;

@Repository("clsBrandMasterDao")
public class clsBrandMasterDaoImpl implements clsBrandMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateBrandMaster(clsBrandMasterModel objMaster) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
			success = true;

		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsBrandMasterModel> funGetBrandMaster(String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsBrandMasterModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsBrandMasterModel a,clsSubCategoryMasterModel b,clsCategoryMasterModel c ,clsSizeMasterModel d " + "	where a.strBrandCode = :brandCode and a.strSubCategoryCode=b.strSubCategoryCode and b.strCategoryCode= c.strCategoryCode and a.strSizeCode = d.strSizeCode and a.strClientCode = :clientCode");
			query.setParameter("brandCode", code);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public boolean funAddUpdateRateMaster(clsRateMasterModel objMaster) {

		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.saveOrUpdate(objMaster);
			success = true;

		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@Override
	public clsRateMasterModel funGetRateObject(String brandCode, String clientCode) {

		clsRateMasterModel objModel = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			objModel = (clsRateMasterModel) currentSession.get(clsRateMasterModel.class, new clsRateMasterModel_ID(brandCode, clientCode));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objModel;
	}

}
