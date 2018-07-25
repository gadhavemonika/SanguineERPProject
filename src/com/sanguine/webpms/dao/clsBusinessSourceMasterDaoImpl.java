package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;

@Repository("clsBusinessSourceMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsBusinessSourceMasterDaoImpl implements clsBusinessSourceMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateBusinessMaster(clsBusinessSourceMasterModel objBusinessMasterModel) {

		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBusinessMasterModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetBusinessMaster(String businessCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsBusinessSourceMasterModel.class);
			cr.add(Restrictions.eq("strBusinessSourceCode", businessCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
