package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDtlModel;

public interface clsSecurityShellDao {
	public void funAddUpdate(clsUserDtlModel code);

	/*
	 * public List<clsUserDtlModel> funListForms(); public clsUserDtlModel
	 * funGetForms(String Code); public clsUserDtlModel funGetObject(String
	 * Code);
	 */

	public List<clsTreeMasterModel> funGetFormList(String userCode, String strModuleNo);

	@SuppressWarnings("rawtypes")
	public List funGetForms(String userCode);

	public void funDeleteForms(String userCode, String clientCode, String module);

	public List<clsTreeMasterModel> funGetFormList(String strModuleNo);

}
