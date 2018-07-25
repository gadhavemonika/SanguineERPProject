package com.sanguine.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sanguine.model.clsMaterialReturnDtlModel;
import com.sanguine.model.clsMaterialReturnHdModel;

@Repository("clsMaterialReturnDao")
public class clsMaterialReturnDaoImpl implements clsMaterialReturnDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funDeleteDtl(String MRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsMaterialReturnDtlModel where strMRetCode = :MRCode " + "and strClientCode=:clientCode");
		query.setParameter("MRCode", MRCode);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
	}

	@Override
	public void funAddUpdateMaterialReturnHd(clsMaterialReturnHdModel objMaterialReturnHd) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaterialReturnHd);
	}

	@Override
	public void funAddUpdateMaterialReturnDtl(clsMaterialReturnDtlModel objMaterialReturnDtl) {
		sessionFactory.getCurrentSession().saveOrUpdate(objMaterialReturnDtl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsMaterialReturnHdModel> funGetList(String clientCode) {
		return (List<clsMaterialReturnHdModel>) sessionFactory.getCurrentSession().createCriteria(clsMaterialReturnHdModel.class, clientCode).list();
	}

	@Override
	public List funGetObject(String MRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsMaterialReturnHdModel  " + "where strMRetCode= :mrCode and strClientCode= :clientCode");
		query.setParameter("mrCode", MRCode);
		query.setParameter("clientCode", clientCode);
		return query.list();
	}

	@Override
	public List funGetMRDtlList(String MRCode, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsMaterialReturnDtlModel " + "where strMRetCode = :mrCode and strClientCode= :clientCode");
		query.setParameter("mrCode", MRCode);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
	}
}
