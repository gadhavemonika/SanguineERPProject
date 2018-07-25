package com.sanguine.excise.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.excise.model.clsOpeningStockModel;

@Repository("clsOpeningStockDao")
public class clsOpeningStockDaoImpl implements clsOpeningStockDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Override
	public boolean funAddUpdateExBrandOpMaster(clsOpeningStockModel objMaster) {
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
	public clsOpeningStockModel funGetExBrandOpMaster(Long intId, String strClientCode) {

		clsOpeningStockModel objModel = null;
		try {
			List ls = null;
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsOpeningStockModel a " + "where a.intId= :intId and a.strClientCode= :strClientCode");
			query.setParameter("intId", intId);
			query.setParameter("strClientCode", strClientCode);
			ls = query.list();
			if (ls.size() > 0) {
				objModel = (clsOpeningStockModel) ls.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetMasterObject(Long intId, String strClientCode) {
		List ls = null;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsOpeningStockModel a,clsBrandMasterModel b " + "where a.intId= :intId and a.strBrandCode=b.strBrandCode and a.strClientCode= :strClientCode ");
			query.setParameter("intId", intId);
			query.setParameter("strClientCode", strClientCode);

			ls = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public Boolean funDeleteBrandOpening(Long intId, String clientCode) {
		boolean success = false;
		try {
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("delete clsOpeningStockModel where intId = :intId " + "and strClientCode=:clientCode ");
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
	public clsOpeningStockModel funGetOpenedEXBrandMaster(String brandCode, String licenceCode, String clientCode) {
		clsOpeningStockModel objModel = null;
		try {
			@SuppressWarnings("rawtypes")
			List ls = null;
			Session currentSession = exciseSessionFactory.getCurrentSession();
			Query query = currentSession.createQuery("from clsOpeningStockModel where strBrandCode = :brandCode " + "and strClientCode=:clientCode and strLicenceCode=:licenceCode");
			query.setParameter("brandCode", brandCode);
			query.setParameter("clientCode", clientCode);
			query.setParameter("licenceCode", licenceCode);
			ls = query.list();
			if (ls.size() > 0) {
				objModel = (clsOpeningStockModel) ls.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objModel;
	}

}
