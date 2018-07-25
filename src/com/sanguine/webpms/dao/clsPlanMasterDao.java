package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsPlanMasterModel;

public interface clsPlanMasterDao {

	public void funAddUpdatePlanMaster(clsPlanMasterModel objPlanMasterModel);

	public List funGetPlanMaster(String planCode, String clientCode);
}
