package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsGroupMasterModel;

public interface clsGroupMasterDao {
	public void funAddGroup(clsGroupMasterModel group);

	public List<clsGroupMasterModel> funListGroups(String clientCode);

	public clsGroupMasterModel funGetGroup(String groupCode, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public Map<String, String> funGetGroups(String clientCode);

	public List funGetList(String groupCode, String clientCode);

	public String funCheckGroupName(String GroupName, String clientCode);
}
