package com.sanguine.service;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsSubGroupMasterModel;

public interface clsSubGroupMasterService {
	public void funAddUpdate(clsSubGroupMasterModel object);

	public List<clsSubGroupMasterModel> funGetList();

	public clsSubGroupMasterModel funGetObject(String code, String clientCode);

	public Map<String, String> funGetSubgroups(String GroupCode, String clientCode);

	public Map<String, String> funGetSubgroupsCombobox(String clientCode);
}
