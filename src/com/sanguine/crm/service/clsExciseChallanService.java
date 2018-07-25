package com.sanguine.crm.service;

import java.util.List;

import com.sanguine.crm.model.clsExciseChallanModel;

public interface clsExciseChallanService {

	public boolean funAddUpdateExciseChallan(clsExciseChallanModel objMaster);

	public List<clsExciseChallanModel> funGetExciseChallanMaster(String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String code, String clientCode);

}
