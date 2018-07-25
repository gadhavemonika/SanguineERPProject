package com.sanguine.crm.dao;

import java.util.List;

import com.sanguine.crm.model.clsExciseChallanModel;

public interface clsExciseChallanDao {

	public boolean funAddUpdateExciseChallan(clsExciseChallanModel objMaster);

	public List<clsExciseChallanModel> funGetExciseChallan(String clientCode);

	@SuppressWarnings("rawtypes")
	public List funGetObject(String code, String clientCode);

}
