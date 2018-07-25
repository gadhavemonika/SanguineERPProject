package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsReasonMaster;

public interface clsReasonMasterDao {

	public void funAddUpdateReason(clsReasonMaster reason);

	public List<clsReasonMaster> funListReasons();

	public clsReasonMaster funGetReason(String reasonCode);

	public clsReasonMaster funGetObject(String reasonCode, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public Map<String, String> funGetResonList(String clientCode);
}
