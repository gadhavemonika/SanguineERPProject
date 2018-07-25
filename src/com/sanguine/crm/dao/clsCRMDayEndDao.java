package com.sanguine.crm.dao;

import com.sanguine.crm.model.clsCRMDayEndModel;

public interface clsCRMDayEndDao {

	public void funAddUpdadte(clsCRMDayEndModel objModel);

	public String funGetCRMDayEndLocationDate(String strLocCode, String strClientCode);

}
