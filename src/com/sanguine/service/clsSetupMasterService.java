package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsCompanyLogoModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsProcessSetupModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsWorkFlowForSlabBasedAuth;
import com.sanguine.model.clsWorkFlowModel;

public interface clsSetupMasterService {
	public void funAddUpdate(clsCompanyMasterModel object);

	/* public List<clsCompanySetupModel> funGetCompanyInfo(); */
	public clsCompanyMasterModel funGetObject(String clientCode);

	public List<clsCompanyMasterModel> funGetListCompanyMasterModel();

	public void funDeleteProcessSetup(String propertyCode, String clientCode);

	public void funAddUpdateProcessSetup(clsProcessSetupModel ProcessSetupModel);

	public List<clsTreeMasterModel> funGetProcessSetupForms();

	public List<clsProcessSetupModel> funGetProcessSetupModelList(String propertyCode, String clientCode);

	public void funDeleteWorkFlowAutorization(String propertyCode, String clientCode);

	public void funAddWorkFlowAuthorization(clsWorkFlowModel WorkFlowModel);

	public void funDeleteWorkFlowForslabBasedAuth(String propertyCode, String clientCode);

	public void funAddWorkFlowForslabBasedAuth(clsWorkFlowForSlabBasedAuth WorkFlowForSlabBasedAuth);

	public clsPropertySetupModel funGetObjectPropertySetup(String propertyCode, String clientCode);

	public void funAddUpdatePropertySetupModel(clsPropertySetupModel PropertySetupModel);

	public List<clsWorkFlowModel> funGetWorkFlowModelList(String propertyCode, String clientCode);

	public List<clsWorkFlowForSlabBasedAuth> funGetWorkFlowForSlabBasedAuthList(String propertyCode, String clientCode);

	public clsTCMasterModel funGetTCForSetup(String tcCode, String clientCode);

	public void funSaveUpdateCompanyLogo(clsCompanyLogoModel comLogo);

	public clsCompanyLogoModel funGetCompanyLogoObject(String strCompanyCode);

	public List<clsTreeMasterModel> funGetAuditForms();

	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode);

}
