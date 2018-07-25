package com.sanguine.excise.service;

import java.util.List;
import java.util.Map;

import com.sanguine.excise.model.clsExciseLocationMasterModel;

public interface clsExciseLocationMasterService {
	public boolean funAddUpdate(clsExciseLocationMasterModel object);

	public List<clsExciseLocationMasterModel> funGetList();

	public clsExciseLocationMasterModel funGetObject(String code, String clientCode);

	public List<clsExciseLocationMasterModel> funGetdtlList(String locCode, String clientCode);

	public Map<String, String> funGetLocMapPropertyWise(String propertyCode, String clientCode);

}
