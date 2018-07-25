package com.sanguine.service;

import com.sanguine.model.clsUDReportCategoryMasterModel;

public interface clsUDReportCategoryMasterService {

	public void funAddUpdateUDReportCategoryMaster(clsUDReportCategoryMasterModel objMaster);

	public clsUDReportCategoryMasterModel funGetUDReportCategoryMaster(String docCode, String clientCode);

}
