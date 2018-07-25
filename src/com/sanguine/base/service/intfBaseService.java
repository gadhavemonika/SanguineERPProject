package com.sanguine.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.sanguine.base.model.clsBaseModel;

public interface intfBaseService {

	public String funSave(clsBaseModel objBaseModel) throws Exception;

	public clsBaseModel funLoad(clsBaseModel objBaseModel, Serializable key) throws Exception;

	public clsBaseModel funGet(clsBaseModel objBaseModel, Serializable key) throws Exception;

	public List funLoadAll(clsBaseModel objBaseModel, String clientCode) throws Exception;

	public List funGetSerachList(String query, String clientCode) throws Exception;

	public List funGetList(StringBuilder query, String queryType) throws Exception;

	public int funExecuteUpdate(String query, String queryType) throws Exception;

	public List funLoadAllPOSWise(clsBaseModel objBaseModel, String clientCode, String strPOSCode) throws Exception;

	public List funLoadAllCriteriaWise(clsBaseModel objBaseModel, String criteriaName, String criteriaValue);

	public clsBaseModel funGetModelCriteriaWise(String sql, Map<String, String> hmParameters);
	
	public String funSaveForPMS(clsBaseModel objBaseModel) throws Exception;

	public clsBaseModel funLoadForPMS(clsBaseModel objBaseModel, Serializable key) throws Exception;

	public List funGetListModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception;
	
	public String funSaveForWebBooks(clsBaseModel objBaseModel) throws Exception; 
	
	public List funGetListForWebBooks(StringBuilder query, String queryType) throws Exception;
	
	public void funExcecteUpdateModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception;
}
