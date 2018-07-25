package com.sanguine.service;

import com.sanguine.model.clsDeliveryScheduleModulehd;

public interface clsDeliveryScheduleService {

	public void funAddUpdate(clsDeliveryScheduleModulehd objDeliveryScheduleModulehd);

	public clsDeliveryScheduleModulehd funLoadDSData(String dsCode, String clientCode);

	// public clsDeliveryScheduleModulehd funGetObject(String poCode,String
	// clientCode);

}
