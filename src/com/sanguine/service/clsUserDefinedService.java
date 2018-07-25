package com.sanguine.service;

import java.util.Map;

import com.sanguine.model.clsUserDefinedReportModel;

public interface clsUserDefinedService {
	public Map<String, String> funGetTableNames(String queryType);

	public Map<String, String> funGetColumnNames(String entityName);

	public Map<String, String> funGetPropertyNames(String entityName);

	public boolean funCheckQuery(String query);

	public void funSaveUpdateUR(clsUserDefinedReportModel objURModel);
}
