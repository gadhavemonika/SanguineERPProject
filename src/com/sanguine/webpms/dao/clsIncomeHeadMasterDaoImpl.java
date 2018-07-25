package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsIncomeHeadMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;

@Repository("clsIncomeHeadMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsIncomeHeadMasterDaoImpl implements clsIncomeHeadMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateIncomeHeadMaster(clsIncomeHeadMasterModel objIncomeMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objIncomeMasterModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetIncomeHeadMaster(String incomeCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsIncomeHeadMasterModel.class);
			cr.add(Restrictions.eq("strIncomeHeadCode", incomeCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
