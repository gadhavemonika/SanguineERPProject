package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;

public interface clsLocationMasterDao {
	public void funAddUpdate(clsLocationMasterModel objModel);

	public List<clsLocationMasterModel> funGetList();

	public clsLocationMasterModel funGetObject(String code, String clientCode);

	public long funGetLastNo(String tableName, String masterName, String columnName);

	public List funGetdtlList(String prodCode, String clientCode);

	public Map<String, String> funGetLocMapPropertyWise(String propertyCode, String clientCode, String usercode);

	public void funAddUpdateProductReOrderLevel(List<clsProductReOrderLevelModel> ProductReOrderLevelModel, String strLocationCode, String strClientCode);

	public List<clsLocationMasterModel> funLoadLocationPropertyWise(String PropertyCode, String ClientCode);
}
