package com.sanguine.dao;

import com.sanguine.model.clsUDReportCategoryMasterModel;

public interface clsUDReportCategoryMasterDao {

	public void funAddUpdateUDReportCategoryMaster(clsUDReportCategoryMasterModel objMaster);

	public clsUDReportCategoryMasterModel funGetUDReportCategoryMaster(String docCode, String clientCode);

}
