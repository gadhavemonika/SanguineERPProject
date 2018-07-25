package com.sanguine.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.base.dao.intfBaseDao;
import com.sanguine.base.model.clsBaseModel;

@Service("intfBaseService")
public class clsBaseServiceImpl implements intfBaseService {

	@Autowired
	intfBaseDao objBaseDao;

	@Override
	public String funSave(clsBaseModel objBaseModel) throws Exception {
		return objBaseDao.funSave(objBaseModel);
	}

	@Override
	public clsBaseModel funLoad(clsBaseModel objBaseModel, Serializable key) throws Exception {
		return objBaseDao.funLoad(objBaseModel, key);
	}

	@Override
	public clsBaseModel funGet(clsBaseModel objBaseModel, Serializable key) throws Exception {
		return objBaseDao.funGet(objBaseModel, key);
	}

	@Override
	public List funLoadAll(clsBaseModel objBaseModel, String clientCode) throws Exception {
		return objBaseDao.funLoadAll(objBaseModel, clientCode);
	}

	@Override
	public List funGetSerachList(String query, String clientCode) throws Exception {
		return objBaseDao.funGetSerachList(query, clientCode);
	}

	public List funGetList(StringBuilder query, String queryType) throws Exception {
		return objBaseDao.funGetList(query, queryType);
	}

	@Override
	public int funExecuteUpdate(String query, String queryType) throws Exception {

		return objBaseDao.funExecuteUpdate(query, queryType);
	}

	public List funLoadAllPOSWise(clsBaseModel objBaseModel, String clientCode, String strPOSCode) throws Exception {
		return objBaseDao.funLoadAllPOSWise(objBaseModel, clientCode, strPOSCode);
	}

	public List funLoadAllCriteriaWise(clsBaseModel objBaseModel, String criteriaName, String criteriaValue) {
		return objBaseDao.funLoadAllCriteriaWise(objBaseModel, criteriaName, criteriaValue);
	}

	public clsBaseModel funGetModelCriteriaWise(String sql, Map<String, String> hmParameters) {
		return objBaseDao.funGetModelCriteriaWise(sql, hmParameters);
	}
	
	@Override
	public String funSaveForPMS(clsBaseModel objBaseModel) throws Exception {
		return objBaseDao.funSaveForPMS(objBaseModel);
	}

	@Override
	public clsBaseModel funLoadForPMS(clsBaseModel objBaseModel, Serializable key) throws Exception {
		return objBaseDao.funLoadForPMS(objBaseModel, key);
	}
	
	@Override
	public List funGetListModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception{
		return objBaseDao.funGetListModuleWise(strQuery, queryType, moduleType);
	}
	
	@Override
	public String funSaveForWebBooks(clsBaseModel objBaseModel) throws Exception {
		return objBaseDao.funSaveForWebBooks(objBaseModel);
	}
	
	public List funGetListForWebBooks(StringBuilder query, String queryType) throws Exception {
		return objBaseDao.funGetListForWebBooks(query, queryType);
	}
	@Override
	public void funExcecteUpdateModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception
	{
		objBaseDao.funExcecteUpdateModuleWise(strQuery, queryType, moduleType);
	}

	
	
}
