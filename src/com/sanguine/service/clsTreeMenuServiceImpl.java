package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsTreeMenuDao;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDesktopModel;
import com.sanguine.util.clsTreeRootNodeItemUtil;
import com.sanguine.util.clsUserDesktopUtil;

@Service("objclsTreeMenuServic")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsTreeMenuServiceImpl implements clsTreeMenuService {
	@Autowired
	clsTreeMenuDao objTreeMenuDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeMasterModel> funGetMenuForm() {

		return objTreeMenuDao.funGetMenuForm();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeRootNodeItemUtil> getRootNodeItems(String userCode, String clientCode, String rootNode) {

		return objTreeMenuDao.getRootNodeItems(userCode, clientCode, rootNode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsTreeRootNodeItemUtil> getRootNodeItems(String rootNode) {
		return objTreeMenuDao.getRootNodeItems(rootNode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsUserDesktopUtil> funGetForms() {

		return objTreeMenuDao.funGetForms();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsUserDesktopUtil> funGetForms(String userCode, String clientCode) {

		return objTreeMenuDao.funGetForms(userCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsUserDesktopModel> getUserDesktopForm(String userCode) {
		// TODO Auto-generated method stub
		return objTreeMenuDao.getUserDesktopForm(userCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDesktopForm(String userCode) {
		objTreeMenuDao.funDeleteDesktopForm(userCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funInsertDesktopForm(String strformname, String userCode) {
		objTreeMenuDao.funInsertDesktopForm(strformname, userCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsUserDesktopUtil> funGetDesktopForms(String userCode) {

		return objTreeMenuDao.funGetDesktopForms(userCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsUserDesktopUtil> funGetDesktopForms(String userCode, String clientCode) {

		return objTreeMenuDao.funGetDesktopForms(userCode, clientCode);
	}

}
