package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsPMSSettlementMasterHdModel;

public interface clsPMSSettlementMasterDao {
	public void funAddUpdateSettlementMaster(clsPMSSettlementMasterHdModel objSettlementMasterModel);

	public List funGetPMSSettlementMaster(String settlementCode, String clientCode);

}
