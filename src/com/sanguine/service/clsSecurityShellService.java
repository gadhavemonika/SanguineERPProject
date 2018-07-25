package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDtlModel;

public interface clsSecurityShellService {
	public void funAddUpdate(clsUserDtlModel object);

	public void funAddUpdateDtl(clsUserDtlModel object);

	/*
	 * public List<clsUserDtlModel> funGetList();
	 * 
	 * public clsUserDtlModel funGetObject(String code);
	 */

	public List<clsTreeMasterModel> funGetFormList(String userCode, String strModuleNo);

	@SuppressWarnings("rawtypes")
	public List funGetForms(String userCode);

	public void funDeleteForms(String userCode, String clientCode, String module);

	public List<clsTreeMasterModel> funGetFormList(String strModuleNo);

}
