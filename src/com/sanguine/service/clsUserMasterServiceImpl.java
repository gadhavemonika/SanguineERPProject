package com.sanguine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsUserMasterDao;
import com.sanguine.model.clsUserMasterModel;

@Service("clsUserMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsUserMasterServiceImpl implements clsUserMasterService {

	@Autowired
	private clsUserMasterDao objUserMasterDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateUser(clsUserMasterModel userMaster) {
		objUserMasterDao.funAddUpdateUser(userMaster);
	}

	@Override
	public List<clsUserMasterModel> funListUserMaster() {

		return objUserMasterDao.funListUserMaster();
	}

	@Override
	public clsUserMasterModel funGetUser(String userCode, String clientCode) throws SQLGrammarException {

		return objUserMasterDao.funGetUser(userCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsUserMasterModel funGetObject(String userCode, String clientCode) {

		return objUserMasterDao.funGetObject(userCode, clientCode);
	}

	@Override
	public Map<String, String> funProperties(String strClientCode) {

		return objUserMasterDao.funGetProperties(strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetUserProperties(String strClientCode) {

		return objUserMasterDao.funGetUserProperties(strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetUsers() {
		// TODO Auto-generated method stub
		return objUserMasterDao.funGetUsers();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String clientCode) {
		return objUserMasterDao.funGetDtlList(clientCode);
	}

	public Map<String, String> funGetLocMapPropertyNUserWise(String propertyCode, String clientCode, String usercode, HttpServletRequest req) {

		return objUserMasterDao.funGetLocMapPropertyNUserWise(propertyCode, clientCode, usercode, req);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetUserWiseProperties(List<String> propCodes, String strClientCode) {
		return objUserMasterDao.funGetUserWiseProperties(propCodes, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> funGetUserBasedProperty(String strUserCode, String strClientCode) {
		return objUserMasterDao.funGetUserBasedProperty(strUserCode, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, String> funGetUserBasedPropertyLocation(String strPropertyCode, String strUserCode, String strClientCode) {
		return objUserMasterDao.funGetUserBasedPropertyLocation(strPropertyCode, strUserCode, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<String> funGetUserWiseModules(String userCode, String clientCode) {
		return objUserMasterDao.funGetUserWiseModules(userCode, clientCode);
	}
}
