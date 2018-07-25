package com.sanguine.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sanguine.base.model.clsBaseModel;

public interface intfBaseDao {

	public String funSave(clsBaseModel objBaseModel);

	public clsBaseModel funLoad(clsBaseModel objBaseModel, Serializable key);

	public clsBaseModel funGet(clsBaseModel objBaseModel, Serializable key);

	public List funLoadAll(clsBaseModel objBaseModel, String clientCode);

	public List funGetSerachList(String query, String clientCode) throws Exception;

	public List funGetList(StringBuilder query, String queryType) throws Exception;

	public int funExecuteUpdate(String query, String queryType) throws Exception;

	public List funLoadAllPOSWise(clsBaseModel objBaseModel, String clientCode, String strPOSCode) throws Exception;

	public List funLoadAllCriteriaWise(clsBaseModel objBaseModel, String criteriaName, String criteriaValue);

	public clsBaseModel funGetModelCriteriaWise(String sql, Map<String, String> hmParameters);
	
	public String funSaveForPMS(clsBaseModel objBaseModel);

	public clsBaseModel funLoadForPMS(clsBaseModel objBaseModel, Serializable key);

	public List funGetListModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception;
	
	public String funSaveForWebBooks(clsBaseModel objBaseModel) throws Exception; 
	
	public List funGetListForWebBooks(StringBuilder query, String queryType) throws Exception;
	
	public void funExcecteUpdateModuleWise(StringBuilder strQuery, String queryType, String moduleType) throws Exception;
}
