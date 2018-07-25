package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;

@Repository("clsBathTypeMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsBathTypeMasterDaoImpl implements clsBathTypeMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateBathTypeMaster(clsBathTypeMasterModel objBathTypeeMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBathTypeeMasterModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetBathTypeMaster(String bathTypeCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsBathTypeMasterModel.class);
			cr.add(Restrictions.eq("strBathTypeCode", bathTypeCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}