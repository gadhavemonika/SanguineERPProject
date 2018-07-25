package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsSetupMasterDao;
import com.sanguine.model.clsCompanyLogoModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsProcessSetupModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsWorkFlowForSlabBasedAuth;
import com.sanguine.model.clsWorkFlowModel;

@Service("clsSetupMasterService")
public class clsSetupMasterServiceImpl implements clsSetupMasterService {

	@Autowired
	clsSetupMasterDao objSetupMasterDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsCompanyMasterModel object) {
		objSetupMasterDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsCompanyMasterModel funGetObject(String clientCode) {

		return objSetupMasterDao.funGetObject(clientCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sanguine.service.clsSetupMasterService#funDeleteProcessSetup(java
	 * .lang.String, java.lang.String) delete all process setup from
	 * tblprocesssetup before insert
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteProcessSetup(String propertyCode, String clientCode) {
		objSetupMasterDao.funDeleteProcessSetup(propertyCode, clientCode);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sanguine.service.clsSetupMasterService#funAddUpdateProcessSetup(com
	 * .sanguine.model.clsProcessSetupModel) service to
	 */

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProcessSetup(clsProcessSetupModel ProcessSetupModel) {
		objSetupMasterDao.funAddUpdateProcessSetup(ProcessSetupModel);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeMasterModel> funGetProcessSetupForms() {

		return objSetupMasterDao.funGetProcessSetupForms();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProcessSetupModel> funGetProcessSetupModelList(String propertyCode, String clientCode) {

		return objSetupMasterDao.funGetProcessSetupModelList(propertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteWorkFlowAutorization(String propertyCode, String clientCode) {
		objSetupMasterDao.funDeleteWorkFlowAutorization(propertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddWorkFlowAuthorization(clsWorkFlowModel WorkFlowModel) {
		objSetupMasterDao.funAddWorkFlowAuthorization(WorkFlowModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteWorkFlowForslabBasedAuth(String propertyCode, String clientCode) {
		objSetupMasterDao.funDeleteWorkFlowForslabBasedAuth(propertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddWorkFlowForslabBasedAuth(clsWorkFlowForSlabBasedAuth WorkFlowForSlabBasedAuth) {
		objSetupMasterDao.funAddWorkFlowForslabBasedAuth(WorkFlowForSlabBasedAuth);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsPropertySetupModel funGetObjectPropertySetup(String propertyCode, String clientCode) {
		return objSetupMasterDao.funGetObjectPropertySetup(propertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePropertySetupModel(clsPropertySetupModel PropertySetupModel) {
		objSetupMasterDao.funAddUpdatePropertySetupModel(PropertySetupModel);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsWorkFlowModel> funGetWorkFlowModelList(String propertyCode, String clientCode) {
		// TODO Auto-generated method stub
		return objSetupMasterDao.funGetWorkFlowModelList(propertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsWorkFlowForSlabBasedAuth> funGetWorkFlowForSlabBasedAuthList(String propertyCode, String clientCode) {

		return objSetupMasterDao.funGetWorkFlowForSlabBasedAuthList(propertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsTCMasterModel funGetTCForSetup(String tcCode, String clientCode) {
		return objSetupMasterDao.funGetTCForSetup(tcCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsCompanyMasterModel> funGetListCompanyMasterModel() {
		// TODO Auto-generated method stub
		return objSetupMasterDao.funGetListCompanyMasterModel();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funSaveUpdateCompanyLogo(clsCompanyLogoModel comLogo) {
		objSetupMasterDao.funSaveUpdateCompanyLogo(comLogo);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsCompanyLogoModel funGetCompanyLogoObject(String strCompanyCode) {

		return objSetupMasterDao.funGetCompanyLogoObject(strCompanyCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeMasterModel> funGetAuditForms() {
		return objSetupMasterDao.funGetAuditForms();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode) {
		return objSetupMasterDao.funGetListCompanyMasterModel(clientCode);
	}

}
