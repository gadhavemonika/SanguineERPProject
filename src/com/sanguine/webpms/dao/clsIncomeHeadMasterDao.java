package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsIncomeHeadMasterModel;
import com.sanguine.webpms.model.clsPMSReasonMasterModel;

public interface clsIncomeHeadMasterDao {
	public void funAddUpdateIncomeHeadMaster(clsIncomeHeadMasterModel objIncomeMasterModel);

	public List funGetIncomeHeadMaster(String incomeCode, String clientCode);
}
