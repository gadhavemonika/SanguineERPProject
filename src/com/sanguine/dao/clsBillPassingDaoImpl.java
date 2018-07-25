package com.sanguine.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsBillPassDtlModel;
import com.sanguine.model.clsBillPassHdModel;
import com.sanguine.model.clsBillPassingTaxDtlModel;

@Repository("clsBillPassingDao")
public class clsBillPassingDaoImpl implements clsBillPassingDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void funAddUpdateBillPassingHD(clsBillPassHdModel BillPassHdModel) {

		sessionFactory.getCurrentSession().saveOrUpdate(BillPassHdModel);

	}

	@Override
	public void funAddUpdateBillPassingDtl(clsBillPassDtlModel BillPassDtlModel) {
		sessionFactory.getCurrentSession().save(BillPassDtlModel);

	}

	@Override
	public void funDeleteBillPassingDtlData(String billNo, String clientCode) {
		String sql = "DELETE clsBillPassDtlModel WHERE strBillPassNo= :billNo and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("billNo", billNo);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();

	}

	@Override
	public clsBillPassHdModel funGetObject(String billNo, String clientCode) {

		return (clsBillPassHdModel) sessionFactory.getCurrentSession().get(clsBillPassHdModel.class, billNo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<clsBillPassDtlModel> funGetDtlList(String billNo, String clientCode) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM clsBillPassDtlModel WHERE strBillPassNo= :billPassNo AND strClientCode= :clientCode ");
		query.setParameter("billPassNo", billNo);
		query.setParameter("clientCode", clientCode);
		List list = query.list();
		return (List<clsBillPassDtlModel>) list;
	}

	public void funDeleteBillPassTaxDtl(String billPassNo, String clientCode) {
		String sql = "DELETE clsBillPassingTaxDtlModel WHERE strBillPassNo= :billPassNo and strClientCode= :clientCode";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("billPassNo", billPassNo);
		query.setParameter("clientCode", clientCode);
		query.executeUpdate();
	}

	public void funAddUpdateBillPassingTaxDtl(clsBillPassingTaxDtlModel objTaxDtlModel) {
		sessionFactory.getCurrentSession().saveOrUpdate(objTaxDtlModel);
	}

}
