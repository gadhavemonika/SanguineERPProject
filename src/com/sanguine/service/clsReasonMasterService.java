package com.sanguine.service;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsReasonMaster;

public interface clsReasonMasterService {
	public void funAddUpdateReason(clsReasonMaster reason);

	public List<clsReasonMaster> funListReasons();

	public clsReasonMaster funGetReason(String reasonCode);

	public clsReasonMaster funGetObject(String code, String clientCode);

	public Map<String, String> funGetResonList(String clientCode);

}
