package com.sanguine.webpms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.model.clsFloorMasterModel;



@Repository("clsFloorMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsFloorMasterDaoImpl implements clsFloorMasterDao{
	
	@Autowired
	private SessionFactory webPMSSessionFactory;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateFloorMaster(clsFloorMasterModel objFloorMasterModel) {

		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objFloorMasterModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetFloorMaster(String floorCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsFloorMasterModel.class);
			cr.add(Restrictions.eq("strFloorCode", floorCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


}
