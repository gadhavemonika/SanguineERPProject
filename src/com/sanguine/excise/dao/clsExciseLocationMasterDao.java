package com.sanguine.excise.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.excise.model.clsExciseLocationMasterModel;

public interface clsExciseLocationMasterDao {
	public boolean funAddUpdate(clsExciseLocationMasterModel objModel);

	public List<clsExciseLocationMasterModel> funGetList();

	public clsExciseLocationMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public List<clsExciseLocationMasterModel> funGetdtlList(String locCode, String clientCode);

	public Map<String, String> funGetLocMapPropertyWise(String propertyCode, String clientCode);

}
