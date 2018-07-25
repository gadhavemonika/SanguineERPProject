package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsGuestMasterHdModel;

@Repository("clsGuestMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsGuestMasterDaoImpl implements clsGuestMasterDao {
	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateGuestMaster(clsGuestMasterHdModel objGuestMasterModel) {
		String sqlDelete = "delete from tblguestmaster where lngMobileNo='" + objGuestMasterModel.getLngMobileNo() + "' " + " and strClientCode='" + objGuestMasterModel.getStrClientCode() + "'";
		webPMSSessionFactory.getCurrentSession().createSQLQuery(sqlDelete).executeUpdate();
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objGuestMasterModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetGuestMaster(String guestCode, String clientCode) {

		List list = null;
		clsGuestMasterHdModel objModel = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsGuestMasterHdModel.class);
			cr.add(Restrictions.eq("strGuestCode", guestCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
