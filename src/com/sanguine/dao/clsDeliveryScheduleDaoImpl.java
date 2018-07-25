package com.sanguine.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsDeliveryScheduleModulehd;
import com.sanguine.model.clsDeliveryScheduleModulehd_ID;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;

@Repository("clsDeliveryScheduleDao")
public class clsDeliveryScheduleDaoImpl implements clsDeliveryScheduleDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void funAddUpdate(clsDeliveryScheduleModulehd objDeliveryScheduleModulehd) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(objDeliveryScheduleModulehd);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public clsDeliveryScheduleModulehd funLoadDSData(String dsCode, String clientCode) {
		return (clsDeliveryScheduleModulehd) sessionFactory.getCurrentSession().get(clsDeliveryScheduleModulehd.class, new clsDeliveryScheduleModulehd_ID(dsCode, clientCode));
	}

}
