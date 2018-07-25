package com.sanguine.dao;

import com.sanguine.model.clsDeliveryScheduleModulehd;

public interface clsDeliveryScheduleDao {

	public void funAddUpdate(clsDeliveryScheduleModulehd objDeliveryScheduleModulehd);

	public clsDeliveryScheduleModulehd funLoadDSData(String dsCode, String clientCode);

}
