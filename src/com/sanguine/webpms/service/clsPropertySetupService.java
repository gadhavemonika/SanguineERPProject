package com.sanguine.webpms.service;

import java.util.List;

import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.webpms.model.clsPropertySetupHdModel;

public interface clsPropertySetupService {

	public void funAddUpdatePropertySetup(clsPropertySetupHdModel clsPropertySetupHdModel);

	public clsPropertySetupHdModel funGetPropertySetup(String docCode, String clientCode);

	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode);

}
