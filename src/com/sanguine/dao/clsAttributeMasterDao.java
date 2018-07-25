package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsAttributeMasterModel;

public interface clsAttributeMasterDao {
	public void funAddUpdate(clsAttributeMasterModel objModel);

	public List<clsAttributeMasterModel> funGetList(String attCode, String clientCode);

	public clsAttributeMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

}
