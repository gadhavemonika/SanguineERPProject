package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;

public interface clsBusinessSourceMasterDao {
	public void funAddUpdateBusinessMaster(clsBusinessSourceMasterModel objBusinessMasterModel);

	public List funGetBusinessMaster(String businessCode, String clientCode);
}
