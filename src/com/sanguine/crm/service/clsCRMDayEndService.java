package com.sanguine.crm.service;

import com.sanguine.crm.model.clsCRMDayEndModel;

public interface clsCRMDayEndService {

	public void funAddUpdadte(clsCRMDayEndModel objModel);

	public String funGetCRMDayEndLocationDate(String strLocCode, String strClientCode);

}
