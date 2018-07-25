package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsExtraBedMasterModel;

public interface clsExtraBedMasterDao {
	public void funAddUpdateExtraBedMaster(clsExtraBedMasterModel objExtraBedeMasterModel);

	public List funGetExtraBedMaster(String extraBedCode, String clientCode);
}
