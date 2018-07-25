package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsPropertyMaster;

public interface clsPropertyMasterDao {
	public void funAddProperty(clsPropertyMaster property);

	public List<clsPropertyMaster> funListProperty(String clientCode);

	public clsPropertyMaster getProperty(String propertyCode, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

}
