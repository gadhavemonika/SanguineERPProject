package com.sanguine.service;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsGroupMasterModel;

public interface clsGroupMasterService {
	public void funAddGroup(clsGroupMasterModel group);

	public List<clsGroupMasterModel> funListGroups(String clientCode);

	public clsGroupMasterModel funGetGroup(String groupCode, String clientCode);

	public Map<String, String> funGetGroups(String clientCode);

	public List<clsGroupMasterModel> funGetList(String groupCode, String clientCode);

	public String funCheckGroupName(String suppName, String clientCode);
}
