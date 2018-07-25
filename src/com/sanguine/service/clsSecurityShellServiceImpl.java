package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsSecurityShellDao;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDtlModel;

@Service("objSecurityShellService")
public class clsSecurityShellServiceImpl implements clsSecurityShellService {

	@Autowired
	private clsSecurityShellDao objSecurityShellDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsUserDtlModel object) {

		objSecurityShellDao.funAddUpdate(object);
	}

	@Override
	public void funAddUpdateDtl(clsUserDtlModel object) {
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeMasterModel> funGetFormList(String userCode, String strModuleNo) {
		return objSecurityShellDao.funGetFormList(userCode, strModuleNo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetForms(String userCode) {
		return objSecurityShellDao.funGetForms(userCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteForms(String userCode, String clientCode, String module) {

		objSecurityShellDao.funDeleteForms(userCode, clientCode, module);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeMasterModel> funGetFormList(String strModuleNo) {
		return objSecurityShellDao.funGetFormList(strModuleNo);
	}

}
