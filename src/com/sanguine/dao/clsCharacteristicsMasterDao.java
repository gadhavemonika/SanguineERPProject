package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsCharacteristicsMasterModel;

public interface clsCharacteristicsMasterDao {

	public void funAddCharacteristics(clsCharacteristicsMasterModel characteristics);

	public List<clsCharacteristicsMasterModel> funListCharacteristics();

	public clsCharacteristicsMasterModel funGetCharacteristics(String characteristicsCode, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

}
