package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

@Repository("clsPMSReasonMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsPMSReasonMasterDaoImpl implements clsPMSReasonMasterDao {
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateReasonMaster(clsPMSReasonMasterModel objReasonMasterModel)

	{
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objReasonMasterModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetPMSReasonMaster(String reasonCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsPMSReasonMasterModel.class);
			cr.add(Restrictions.eq("strReasonCode", reasonCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
