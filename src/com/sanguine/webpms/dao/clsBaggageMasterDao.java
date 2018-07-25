package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsBaggageMasterModel;

public interface clsBaggageMasterDao {

	public void funAddUpdateBaggageMaster(clsBaggageMasterModel objBaggageMasterModel);

	public List funGetBaggageMaster(String baggageCode, String clientCode);
}