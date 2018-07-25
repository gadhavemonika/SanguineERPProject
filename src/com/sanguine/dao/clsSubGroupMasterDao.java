package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsSubGroupMasterModel;

public interface clsSubGroupMasterDao {
	public void funAddUpdate(clsSubGroupMasterModel objModel);

	public List<clsSubGroupMasterModel> funGetList();

	public clsSubGroupMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public Map<String, String> funGetSubgroups(String GroupCode, String clientCode);

	public Map<String, String> funGetSubgroupsCombobox(String clientCode);

}
