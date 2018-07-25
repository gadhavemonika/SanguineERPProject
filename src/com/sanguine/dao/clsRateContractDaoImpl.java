package com.sanguine.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sanguine.model.clsRateContractDtlModel;
import com.sanguine.model.clsRateContractHdModel;

@Repository("clsRateContractDao")
public class clsRateContractDaoImpl implements clsRateContractDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdate(clsRateContractHdModel object) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@Override
	public void funAddUpdateDtl(clsRateContractDtlModel object) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsRateContractHdModel> funGetList(String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsRateContractHdModel where strClientCode= :clientCode");
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsRateContractHdModel>) list;
	}

	@Override
	public List funGetObject(String rateContractNo, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("from clsRateContractHdModel a,clsSupplierMasterModel b " + "where a.strRateContNo= :rateContNo and a.strSuppCode=b.strPCode and a.strClientCode= :clientCode");
		query.setParameter("rateContNo", rateContractNo);
		query.setParameter("clientCode", clientCode);
		return query.list();
		// return
		// (clsRateContractHdModel)sessionFactory.getCurrentSession().get(clsRateContractHdModel.class,rateContractNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsRateContractDtlModel> funGetDtlList(String rateContractNo, String clientCode) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("from clsRateContractDtlModel where strRateContNo = :rateContNo and strClientCode= :clientCode");
		query.setParameter("rateContNo", rateContractNo);
		query.setParameter("clientCode", clientCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return (List<clsRateContractDtlModel>) list;
	}

	@SuppressWarnings("rawtypes")
	public List funGetSupplierList(String sql) {
		return sessionFactory.getCurrentSession().createSQLQuery(sql).list();
	}

	public void funDeleteDtl(String rateContractNo, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete clsRateContractDtlModel where strRateContNo = :rateContNo and strClientCode= :clientCode");
		query.setParameter("rateContNo", rateContractNo);
		query.setParameter("clientCode", clientCode);
		int result = query.executeUpdate();
	}
}
