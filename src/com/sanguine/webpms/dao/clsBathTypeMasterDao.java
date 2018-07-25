package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;

public interface clsBathTypeMasterDao {
	public void funAddUpdateBathTypeMaster(clsBathTypeMasterModel objBathTypeMasterModel);

	public List funGetBathTypeMaster(String bathTypeCode, String clientCode);
}