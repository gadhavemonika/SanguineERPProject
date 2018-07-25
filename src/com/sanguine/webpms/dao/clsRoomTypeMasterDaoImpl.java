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
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

@Repository("clsRoomTypeMasterDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsRoomTypeMasterDaoImpl implements clsRoomTypeMasterDao {

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateRoomMaster(clsRoomTypeMasterModel objRoomMasterModel) {
		webPMSSessionFactory.getCurrentSession().saveOrUpdate(objRoomMasterModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List funGetRoomTypeMaster(String roomCode, String clientCode) {

		List list = null;
		try {
			Criteria cr = webPMSSessionFactory.getCurrentSession().createCriteria(clsRoomTypeMasterModel.class);
			cr.add(Restrictions.eq("strRoomTypeCode", roomCode));
			cr.add(Restrictions.eq("strClientCode", clientCode));
			list = cr.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
