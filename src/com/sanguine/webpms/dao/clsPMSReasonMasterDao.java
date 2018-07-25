package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

public interface clsPMSReasonMasterDao {
	public void funAddUpdateReasonMaster(clsPMSReasonMasterModel objReasonMasterModel);

	public List funGetPMSReasonMaster(String reasonCode, String clientCode);
}
