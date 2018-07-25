package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsAttributeValueMasterModel;

public interface clsAttributeValueMasterDao {
	public void funAddUpdate(clsAttributeValueMasterModel objModel);

	public List<clsAttributeValueMasterModel> funGetList();

	public clsAttributeValueMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

}
