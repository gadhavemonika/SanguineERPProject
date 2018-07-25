package com.sanguine.service;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsTCMasterModel;

public interface clsTCMasterService {
	public void funAddUpdate(clsTCMasterModel objTCMaster);

	public List<clsTCMasterModel> funGetTCMasterList(String clientCode);

	public clsTCMasterModel funGetTCMaster(String tcCode, String clientCode);
}
