package com.sanguine.excise.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsExciseRecipeMasterDtlModel;
import com.sanguine.excise.model.clsExciseRecipeMasterHdModel;

@SuppressWarnings("rawtypes")
@Repository("clsExciseRecipeMasterDao")
public class clsExciseRecipeMasterDaoImpl implements clsExciseRecipeMasterDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateRecipeMaster(clsExciseRecipeMasterHdModel objMaster) {
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
	public boolean funAddUpdateRecipeDtl(clsExciseRecipeMasterDtlModel objRecipeDtl) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			currentSession.save(objRecipeDtl);
			success = true;

		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	@Override
	public List funGetList(String clientCode) {
		List ls = new ArrayList();
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			ls = currentSession.createCriteria(clsExciseRecipeMasterHdModel.class, clientCode).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public List funGetObject(String RecipeCode, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			// Query query =
			// currentSession.createQuery("from clsExciseRecipeMasterHdModel a, clsBrandMasterModel b  "
			// +
			// "where strRecipeCode= :RecipeCode and a.strParentCode=b.strBrandCode and a.strClientCode= :clientCode");

			Query query = currentSession.createQuery("from clsExciseRecipeMasterHdModel a " + "where a.strRecipeCode= :RecipeCode and a.strClientCode= :clientCode");

			query.setParameter("RecipeCode", RecipeCode);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsExciseRecipeMasterDtlModel> funGetRecipeDtlList(String RecipeCode, String clientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsExciseRecipeMasterDtlModel a, clsBrandMasterModel b " + "where a.strRecipeCode= :RecipeCode and a.strBrandCode=b.strBrandCode and a.strClientCode= :clientCode");
			query.setParameter("RecipeCode", RecipeCode);
			query.setParameter("clientCode", clientCode);
			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@SuppressWarnings("unused")
	@Override
	public void funDeleteDtl(String RecipeCode, String clientCode) {
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsExciseRecipeMasterDtlModel where strRecipeCode = :RecipeCode " + "and strClientCode=:clientCode");
			query.setParameter("RecipeCode", RecipeCode);
			query.setParameter("clientCode", clientCode);
			int reult = query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
