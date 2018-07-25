package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;

public interface clsPropertySetupDao {

	public void funAddUpdatePropertySetup(clsPropertySetupHdModel objMaster);

	public clsPropertySetupHdModel funGetPropertySetup(String docCode, String clientCode);

	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode);

}
