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
import com.sanguine.webpms.model.clsDepartmentMasterModel;

@Repository("clsBaggageMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsBaggageMasterDaoImpl implements clsBaggageMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateBaggageMaster(clsBaggageMasterModel objBaggageMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objBaggageMasterModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetBaggageMaster(String baggageCode, String clientCode) {
		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsBaggageMasterModel.class);
			cr.add(Restrictions.eq("strBaggageCode", baggageCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

}
