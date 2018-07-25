package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsMarketSourceMasterModel;

@Repository("clsMarketSourceMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsMarketSourceMasterDaoImpl implements clsMarketSourceMasterDao{
	
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateMarketMaster(clsMarketSourceMasterModel objMarketMasterModel) {

		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objMarketMasterModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetMarketMaster(String MarketCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsMarketSourceMasterModel.class);
			cr.add(Restrictions.eq("strMarketSourceCode", MarketCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



}
