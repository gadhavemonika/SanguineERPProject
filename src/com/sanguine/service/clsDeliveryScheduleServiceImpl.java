package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sanguine.dao.clsDeliveryScheduleDao;
import com.sanguine.model.clsDeliveryScheduleModulehd;
import com.sanguine.model.clsGroupMasterModel;

import org.springframework.transaction.annotation.Propagation;

@Repository("clsDeliveryScheduleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsDeliveryScheduleServiceImpl implements clsDeliveryScheduleService {

	@Autowired
	private clsDeliveryScheduleDao objDeliveryScheduleDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsDeliveryScheduleModulehd objDeliveryScheduleModulehd) {
		objDeliveryScheduleDao.funAddUpdate(objDeliveryScheduleModulehd);
	}

	public clsDeliveryScheduleModulehd funLoadDSData(String dsCode, String clientCode) {
		return objDeliveryScheduleDao.funLoadDSData(dsCode, clientCode);
	}

}
