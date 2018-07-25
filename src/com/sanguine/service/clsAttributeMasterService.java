package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsAttributeMasterModel;

public interface clsAttributeMasterService {
	public void funAddUpdate(clsAttributeMasterModel object);

	public List<clsAttributeMasterModel> funGetList(String attCode, String clientCode);

	public clsAttributeMasterModel funGetObject(String code, String clientCode);
}
