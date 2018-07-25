package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsExtraBedMasterModel;
import com.sanguine.webpms.model.clsIncomeHeadMasterModel;

@Repository("clsExtraBedMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsExtraBedMasterDaoImpl implements clsExtraBedMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateExtraBedMaster(clsExtraBedMasterModel objExtraBedeMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objExtraBedeMasterModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetExtraBedMaster(String extraBedCode, String clientCode) {
		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsExtraBedMasterModel.class);
			cr.add(Restrictions.eq("strExtraBedTypeCode", extraBedCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
